package com.bergermobile.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.UserService;

@RestController
@RequestMapping(value = "/bmauth")
public class UserCommandController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveUser(@RequestBody UserRest user) {

		userService.save(user);

	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable int userId) {

		userService.delete(userId);

	}
	
	/**
	 * This will verify if we already have the social media user (Facebook, Google+) in the DB.
	 * If we have, we just authenticate the user with USER role. Or else we create this user in the
	 * DB, and then authenticate
	 * @param user
	 */
	public void saveSocialMediaUser(@RequestBody UserRest user) {
		
	}

}
