package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.persistence.repository.UserRoleRepository;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.UserRest;

@Service
public class UserServiceImpl implements UserService {
	
	static Log LOG = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RestConversionService conversionService;

	@Override
	public List<UserRest> findAllUsers() {

		List<UserRest> userRestList = new ArrayList<UserRest>();

		for (User user : userRepository.findAll()) {

			UserRest userRest = new UserRest();

			BeanUtils.copyProperties(user, userRest);

			userRestList.add(userRest);

		}

		return userRestList;

	}

	@Override
	@Transactional
	public void save(UserRest userRest, boolean saveRoles) {

		User user = new User();

		BeanUtils.copyProperties(userRest, user, "simpleUserRoles");
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			user.setUsername(user.getEmail());
		}
		user.setUserType(User.UserType.CPF.getValue());
		user.setActive(true);
		
		if (saveRoles) {
			LOG.debug("Saving roles " + userRest.getSimpleUserRoles());
			List<UserRole> userRoles = userRoleRepository.findByUserUserIdAndRoleApplicationIsNotNull(user.getUserId());

			// delete old roles
			if (userRoles != null) {
				userRoleRepository.delete(userRoles);
			}
			
			// store new roles (from json)
			user.setUserRoles(conversionService.simpleUserRolesToUserRoles(userRest.getSimpleUserRoles(), user));
		}
		
		user = userRepository.save(user);
	}
	
	/**
	 * This will verify if we already have the social media user (Facebook) 
	 * in the DB. If we have, we just authenticate the user with USER
	 * role. Or else we create this user in the DB, and then authenticate
	 */
	@Override
	public void saveFacebook(FacebookRest facebookRest) {
		// look for existent user
		User facebookUser = userRepository.findByLoginTypeAndUsername(User.LoginType.FACEBOOK.getValue(), facebookRest.getAuthResponse().getUserID());
		if (facebookUser == null) {
			LOG.debug("No Facebook user found for Facebok userid: " + facebookRest.getAuthResponse().getUserID());
			facebookUser = saveFacebookInformation(facebookRest);
		}
	}
	
	/**
	 * This will verify if we already have the social media user (Google) 
	 * in the DB. If we have, we just authenticate the user with USER
	 * role. Or else we create this user in the DB, and then authenticate
	 */
	@Override
	public void saveGoogle(GoogleRest googleRest) {
		LOG.debug("Verifying google user for token " + googleRest.getAccessToken() + ", clientId: " + googleRest.getClientId());
		
		// aquire connection
		GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(googleRest.getClientId(), "");
		AccessGrant accessGrant = new AccessGrant(googleRest.getAccessToken());
		connectionFactory.createConnection(accessGrant);
		Connection<Google> connection = connectionFactory.createConnection(accessGrant);
		Google google = connection.getApi();
		
		//Google google = new GoogleTemplate(accessToken);
		String username = google.plusOperations().getGoogleProfile().getId();
		
		User googleUser = userRepository.findByLoginTypeAndUsername(User.LoginType.GOOGLE_PLUS.getValue(), username);
		if (googleUser == null) {
			LOG.debug("No Google user found for id " + username + ". Saving new");
			googleUser = saveGoogleInformation(google);
		}
	}

	@Override
	public void delete(int userId) {

		userRepository.delete(userId);

	}
	
	/**
	 * This assynchronously retrieves Facebook information using the Graph API, and saves the result in the Database
	 * 
	 * (obs: I dont think we can do this asynchronously since we need to authenticate the user right after save)
	 */
	//@Async
	public User saveFacebookInformation(FacebookRest facebookRest) {
		LOG.debug("Will invoke facebook graph api to save the user");
		
		// aquire connection
		FacebookConnectionFactory connectionFactory=new FacebookConnectionFactory(facebookRest.getAppId(),"");
		// upon receiving the callback from the provider:
		AccessGrant accessGrant = new AccessGrant(facebookRest.getAuthResponse().getAccessToken());
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		Facebook facebook = connection.getApi();
		
		User facebookUser = new User();
		//Facebook facebook = new FacebookTemplate(facebookRest.getAuthResponse().getAccessToken());

		// create the user object
		facebookUser.setUsername(facebookRest.getAuthResponse().getUserID());
		facebookUser.setActive(true);
		facebookUser.setLoginType(User.LoginType.FACEBOOK.getValue());
		facebookUser.setUserType(User.UserType.CPF.getValue());
		facebookUser.setEmail(facebook.userOperations().getUserProfile().getEmail());
		facebookUser.setName(facebook.userOperations().getUserProfile().getName());
		
		LOG.debug("Saving Facebook user " + facebookUser);
		
		return userRepository.save(facebookUser);
	}
	
	/**
	 * This assynchronously retrieves Google information using the OAuth API, and saves the result in the Database
	 */
	//@Async
	public User saveGoogleInformation(Google google) {
		// create the user object
		User googleUser = new User();
		googleUser.setUsername(google.plusOperations().getGoogleProfile().getId());
		googleUser.setActive(true);
		googleUser.setLoginType(User.LoginType.GOOGLE_PLUS.getValue());
		googleUser.setUserType(User.UserType.CPF.getValue());
		googleUser.setEmail(google.plusOperations().getGoogleProfile().getAccountEmail());
		googleUser.setName(google.plusOperations().getGoogleProfile().getDisplayName());
		
		LOG.debug("Saving Google user " + googleUser);
		
		return userRepository.save(googleUser);
	}

	@Override
	public UserRest findByUserId(int userId) {
		User user = userRepository.findByUserId(userId);
		UserRest userRest = new UserRest();
		if (user != null) {
			BeanUtils.copyProperties(user, userRest);
			//userRest.setUserRolesRest(ConversionUtilities.setRolesToRolesRest(user.getUserRoles()));
			userRest.setSimpleUserRoles(RestConversionService.setSimpleUserRoles(user.getUserRoles()));
			//userRest.setSimpleUserApplications(RestConversionService.setSimpleUserApplications(user.getUserRoles()));
			
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

}
