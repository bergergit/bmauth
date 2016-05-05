package com.bergermobile.rest.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import com.bergermobile.commons.rest.DataTableBase;
import com.bergermobile.commons.rest.DataTableCriterias;
import com.bergermobile.commons.rest.PageService;
import com.bergermobile.commons.security.SecurityUser;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.ContractUser;
import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.persistence.repository.OnlineContractRepository;
import com.bergermobile.persistence.repository.RoleRepository;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.persistence.repository.UserRoleRepository;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.LanguageContractRest;
import com.bergermobile.rest.domain.UserRest;

@Service
public class UserServiceImpl implements UserService {

	static Log LOG = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private OnlineContractRepository onlineContractRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private RestConversionService conversionService;

	@Autowired
	BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	SerializableResourceBundleMessageSource messageBundle;

	@Autowired
	PageService pageService;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	/**
	 * Retrieves the user list, based on the criterias (Search) and Order of the
	 * Datatable
	 */
	public DataTableBase<UserRest> findAllUsers(DataTableCriterias criterias) {

		String normalizedSearch = EncodingUtils
				.normalizeSearch(criterias.getSearch().get(DataTableCriterias.SearchCriterias.value));
		Page<User> page = userRepository.findAllWithCriterias(normalizedSearch,
				pageService.buildPageRequest(criterias));

		List<UserRest> userRestList = new ArrayList<UserRest>();
		for (User user : page.getContent()) {
			UserRest userRest = new UserRest();
			BeanUtils.copyProperties(user, userRest);
			userRestList.add(userRest);
		}

		// adding to DataTableBase
		DataTableBase<UserRest> dataTableBase = new DataTableBase<UserRest>();
		dataTableBase.setDraw(criterias.getDraw());
		dataTableBase.setRecordsFiltered(page.getTotalElements());
		dataTableBase.setRecordsTotal(userRepository.count());
		dataTableBase.setData(userRestList);

		return dataTableBase;
	}

	@Override
	@Transactional
	public User save(UserRest userRest, boolean saveRoles) {
		LOG.debug("Saving userId " + userRest.getUserId());

		User user = new User();

		BeanUtils.copyProperties(userRest, user, "simpleUserRoles");
		//if (user.getUsername() == null || user.getUsername().isEmpty()) {
		if (user.getLoginType() == User.LoginType.INTERNAL.getValue()) {
			user.setUsername(user.getEmail());
		}
		user.setUserType(User.UserType.CPF.getValue());
		user.setActive(true);

		// if password is not set and this is an update, then we need to set the
		// original password back to user object
		if (userRest.getUserId() != null && userRest.getPassword() == null) {
			user.setPassword(userRepository.findOne(userRest.getUserId()).getPassword());
		} else {
			// encoding the password
			user.setPassword(bcryptEncoder.encode(userRest.getPassword()));
		}

		if (saveRoles) {
			LOG.debug("Saving roles " + userRest.getSimpleUserRoles());
			List<UserRole> userRoles = userRoleRepository.findByUserUserIdAndRoleApplicationIsNotNull(user.getUserId());

			// delete old roles
			if (userRoles != null) {
				userRoleRepository.delete(userRoles);
			}

			// store new roles (from json)
			user.setUserRoles(conversionService.simpleUserRolesToUserRoles(userRest.getSimpleUserRoles(), user));
		} else {
			// new users get automatically associated with USER role
			if (user.getUserId() == null) {
				setUserRole(user, userRest.getRealm());
			} else {
				user.setUserRoles(userRepository.findOne(user.getUserId()).getUserRoles());
			}
		}

		return userRepository.save(user);
	}

	/**
	 * Sets default USER role for this new User, for all Applications of the given real
	 * @param user the new user
	 * @param real the signup realm
	 */
	private void setUserRole(User user, String realm) {
		List<UserRole> userRolesList = new ArrayList<UserRole>();
		for (Application application : applicationRepository.findByRealm(realm)) {
			Role role = roleRepository.findByRoleNameAndApplication("USER", application);
			if (role == null) {
				// no User role? Create one
				role = new Role();
				role.setApplication(application);
				role.setRoleName("USER");
				roleRepository.save(role);
			}
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			userRolesList.add(userRole);
		}
		user.setUserRoles(userRolesList);
	}

	/**
	 * This will verify if we already have the social media user (Facebook) in
	 * the DB. If we have, we just authenticate the user with USER role. Or else
	 * we create this user in the DB, and then authenticate
	 */
	@Override
	public User saveFacebook(FacebookRest facebookRest) {
		// look for existent user
		User facebookUser = userRepository.findByLoginTypeAndUsername(User.LoginType.FACEBOOK.getValue(),
				facebookRest.getAuthResponse().getUserID());
		if (facebookUser == null) {
			LOG.debug("No Facebook user found for Facebok userid: " + facebookRest.getAuthResponse().getUserID());
			facebookUser = saveFacebookInformation(facebookRest);
		}
		
		return facebookUser;
	}

	/**
	 * This will verify if we already have the social media user (Google) in the
	 * DB. If we have, we just authenticate the user with USER role. Or else we
	 * create this user in the DB, and then authenticate
	 */
	@Override
	public User saveGoogle(GoogleRest googleRest) {
		LOG.debug("Verifying google user for token " + googleRest.getAccessToken() + ", clientId: "
				+ googleRest.getClientId());

		// aquire connection
		GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(googleRest.getClientId(), "");
		AccessGrant accessGrant = new AccessGrant(googleRest.getAccessToken());
		connectionFactory.createConnection(accessGrant);
		Connection<Google> connection = connectionFactory.createConnection(accessGrant);
		Google google = connection.getApi();

		// Google google = new GoogleTemplate(accessToken);
		String username = google.plusOperations().getGoogleProfile().getId();

		User googleUser = userRepository.findByLoginTypeAndUsername(User.LoginType.GOOGLE_PLUS.getValue(), username);
		if (googleUser == null) {
			LOG.debug("No Google user found for id " + username + ". Saving new");
			googleUser = saveGoogleInformation(google, googleRest.getRealm());
		}
		
		return googleUser;
	}

	@Override
	public void delete(int userId) {

		userRepository.delete(userId);

	}

	/**
	 * This assynchronously retrieves Facebook information using the Graph API,
	 * and saves the result in the Database
	 * 
	 * (obs: I dont think we can do this asynchronously since we need to
	 * authenticate the user right after save)
	 */
	// @Async
	public User saveFacebookInformation(FacebookRest facebookRest) {
		LOG.debug("Will invoke facebook graph api to save the user");

		// aquire connection
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookRest.getAppId(), "");
		// upon receiving the callback from the provider:
		AccessGrant accessGrant = new AccessGrant(facebookRest.getAuthResponse().getAccessToken());
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		Facebook facebook = connection.getApi();

		User facebookUser = new User();
		// Facebook facebook = new
		// FacebookTemplate(facebookRest.getAuthResponse().getAccessToken());

		// create the user object
		facebookUser.setUsername(facebookRest.getAuthResponse().getUserID());
		facebookUser.setActive(true);
		facebookUser.setLoginType(User.LoginType.FACEBOOK.getValue());
		facebookUser.setUserType(User.UserType.CPF.getValue());
		facebookUser.setEmail(facebook.userOperations().getUserProfile().getEmail());
		facebookUser.setName(facebook.userOperations().getUserProfile().getName());

		LOG.debug("Saving Facebook user " + facebookUser);
		
		setUserRole(facebookUser, facebookRest.getRealm());

		return userRepository.save(facebookUser);
	}

	/**
	 * This assynchronously retrieves Google information using the OAuth API,
	 * and saves the result in the Database
	 */
	// @Async
	public User saveGoogleInformation(Google google, String realm) {
		// create the user object
		User googleUser = new User();
		googleUser.setUsername(google.plusOperations().getGoogleProfile().getId());
		googleUser.setActive(true);
		googleUser.setLoginType(User.LoginType.GOOGLE_PLUS.getValue());
		googleUser.setUserType(User.UserType.CPF.getValue());
		googleUser.setEmail(google.plusOperations().getGoogleProfile().getAccountEmail());
		googleUser.setName(google.plusOperations().getGoogleProfile().getDisplayName());

		LOG.debug("Saving Google user " + googleUser);
		
		setUserRole(googleUser, realm);

		return userRepository.save(googleUser);
	}

	@Override
	public UserRest findByUserId(int userId) {
		User user = userRepository.findByUserId(userId);
		UserRest userRest = new UserRest();
		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			// userRest.setUserRolesRest(ConversionUtilities.setRolesToRolesRest(user.getUserRoles()));
			userRest.setSimpleUserRoles(RestConversionService.setSimpleUserRoles(user.getUserRoles()));
			// userRest.setSimpleUserApplications(RestConversionService.setSimpleUserApplications(user.getUserRoles()));
			return userRest;
		}
		return null;
	}

	@Override
	public UserRest findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		UserRest userRest = new UserRest();
		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			// userRest.setUserRolesRest(ConversionUtilities.setRolesToRolesRest(user.getUserRoles()));
			userRest.setSimpleUserRoles(RestConversionService.setSimpleUserRoles(user.getUserRoles()));
			// userRest.setSimpleUserApplications(RestConversionService.setSimpleUserApplications(user.getUserRoles()));
			return userRest;
		}
		return null;
	}

	@Override
	public UserRest findByEmailAndApplicationId(String email, Integer applicationId) {
		User user = userRepository.findByEmailAndApplicationId(email, applicationId);
		UserRest userRest = new UserRest();

		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			return userRest;
		}
		return null;
	}
	
	@Override
	public List<UserRest> findByApplicationName(String appName) {
		List<User> usersList = userRepository.findByApplicationName(appName);
		List<UserRest> usersRestList = new ArrayList<>();
		for (User user : usersList) {
			UserRest userRest = new UserRest();
			BeanUtils.copyProperties(user, userRest);
			usersRestList.add(userRest);
		}
		
		return usersRestList;
	}

	@Override
	public UserRest findByUserIdAndApplicationId(Integer userId, Integer applicationId) {
		User user = userRepository.findByUserIdAndApplicationId(userId, applicationId);
		UserRest userRest = new UserRest();

		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			return userRest;
		}
		return null;
	}

	@Override
	public UserRest findByName(String name) {

		User user = userRepository.findByName(name);
		UserRest userRest = new UserRest();

		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			return userRest;
		}
		return null;
	}

	/**
	 * Generates a random token, and adds to redis with a specific expiration
	 * time
	 * 
	 * @param userId
	 * @return
	 * @throws NotFoundException
	 */
	@Override
	public String generateUserToken(UserRest userRest) throws NotFoundException {

		String token;
		UserRest user = findByEmail(userRest.getEmail());

		if (user == null) {
			throw new NotFoundException(userRest.getEmail());
		} else {
			BeanUtils.copyProperties(user, userRest);
		}

		token = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set("token_" + userRest.getUserId(), token);
		redisTemplate.expire("token_" + userRest.getUserId(),
				Long.parseLong(environment.getProperty("bmauth.passwordtoken.expire").trim()), TimeUnit.MINUTES);

		return token;
	}
	
	@Override
	public boolean hasSignedLatestContract(User user, String appName) {
		Application application = applicationRepository.findByApplicationName(appName);
		if (application != null) {
			if (!application.getMandatoryContract()) return true;
			List<OnlineContract> onlineContractList = onlineContractRepository.getLatestByApplication(application);
			// if no contract found, we consider it is not mandatory
			if (onlineContractList == null || onlineContractList.isEmpty()) {
				return true;
			}
			return user.hasThisContract(onlineContractList.get(0)); 
		}
		return false;
	}

	/**
	 * Checks if this token is valid
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	@Override
	public boolean validateUserToken(Integer userId, String token) {
		String redisToken = redisTemplate.opsForValue().get("token_" + userId);
		UserRest user = findByUserId(userId);
		// redisTemplate.delete("token_" + userId); // this will have to be
		// implemented in another method, after password is set
		return redisToken != null && redisToken.equals(token) && user != null;
	}

	@Override
	public String generateBodyMailForgotMyPassword(UserRest userRest, ApplicationRest applicationRest, String link) {
		// Read the HTML of the body mail
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream("/static/bmauth-10/forgotPasswordEmail_en.html"), "UTF-8"));
			
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
		}

		String content = contentBuilder.toString();

		// replace de values
		return MessageFormat.format(content, userRest.getName(), applicationRest.getApplicationName(), link).toString();

	}

	@Override
	public User findByUsernameAndRealm(String username, String realm) {
		return userRepository.findByUsernameAndRealm(username, realm);
	}

	@Override
	public void signContract(String appName) {
		// getting signed in user
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findOne(securityUser.getUserId());
		Application application = applicationRepository.findByApplicationName(appName);
		if (application != null) {
			List<OnlineContract> onlineContractList = onlineContractRepository.getLatestByApplication(application);
			if (onlineContractList != null && !onlineContractList.isEmpty()) {
				OnlineContract onlineContract = onlineContractList.get(0);
				ContractUser contractUser = new ContractUser();
				contractUser.setOnlineContract(onlineContract);
				
				// setting the fields we can get from the user
				contractUser.setSignedDate(new Timestamp(new Date().getTime()));
				contractUser.setIp(request.getRemoteAddr());
				contractUser.setHeaders(request.getHeader("User-Agent"));
				
				user.addContractUser(contractUser);
			}
			
			userRepository.save(user);
		}
	}
	
	/**
	 * Returns the latests Language Contract for this Application
	 * It will try to match the language of the current Locale
	 * If it doesn't find a matching Locale, returns the English Contract
	 * And if no English found, return the only one available.
	 */
	@Override
	public LanguageContractRest getLatestContract(String appName) {
		Application application = applicationRepository.findByApplicationName(appName);
		if (application != null) {
			List<OnlineContract> onlineContractList = onlineContractRepository.getLatestByApplication(application);
			if (onlineContractList != null && !onlineContractList.isEmpty()) {
				OnlineContract onlineContract = onlineContractList.get(0);
				// now we try to match a contract with user language
				
				List<LanguageContract> languageContracts = onlineContract.getLanguageContract();
				if (languageContracts != null && languageContracts.size() == 1) {
					return conversionService.setLanguageContractToLanguageContractRest(languageContracts.get(0));
				}
				
				String locale = LocaleContextHolder.getLocale().toString();
				int enContractIndex = 0;
				for (int i = 0; i < languageContracts.size(); i++) {
					LanguageContract languageContract = languageContracts.get(i);
					if (languageContract.getLanguage() != null && languageContract.getLanguage().equals(locale)) {
						return conversionService.setLanguageContractToLanguageContractRest(languageContract);
					}
					if (languageContract.getLanguage() != null && languageContract.getLanguage().toLowerCase().equals("en")) {
						enContractIndex = i;
					}
				}
				// no matching language found
				return conversionService.setLanguageContractToLanguageContractRest(languageContracts.get(enContractIndex)); 
			}
		}
		return new LanguageContractRest();
	}
	
}
