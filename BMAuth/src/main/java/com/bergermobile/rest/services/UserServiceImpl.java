package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.domain.FacebookGraph;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.web.controller.SiteController;

@Service
public class UserServiceImpl implements UserService {
	
	static Log LOG = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment environment;

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
	public void save(UserRest userRest) {

		User user = new User();

		BeanUtils.copyProperties(userRest, user);
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			user.setUsername(user.getEmail());
		}
		
		user.setActive(true);
		userRepository.save(user);

	}
	
	/**
	 * This will verify if we already have the social media user (Facebook,
	 * Google+) in the DB. If we have, we just authenticate the user with USER
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
		User facebookUser = new User();
		RestTemplate restTemplate = new RestTemplate();
		
		// Lets fetch user information using Graph API and save more information about this user
		String facebookUrl = environment.getProperty("facebook.graph.url");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("accessToken", facebookRest.getAuthResponse().getAccessToken());
		FacebookGraph facebookGraph = restTemplate.getForObject(facebookUrl, FacebookGraph.class, parameters);
		
		// create the user object
		facebookUser.setUsername(facebookRest.getAuthResponse().getUserID());
		facebookUser.setActive(true);
		facebookUser.setLoginType(User.LoginType.FACEBOOK.getValue());
		facebookUser.setEmail(facebookGraph.getEmail());
		facebookUser.setName(facebookGraph.getName());
		
		LOG.debug("Saving Facebook user " + facebookUser);
		
		return userRepository.save(facebookUser);
	}

	@Override
	public UserRest findByUserId(int userId) {

		User user = userRepository.findByUserId(userId);

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

}
