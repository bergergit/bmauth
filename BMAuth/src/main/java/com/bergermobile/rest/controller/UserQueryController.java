package com.bergermobile.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.domain.DataTableBase;
import com.bergermobile.rest.domain.DataTableCriterias;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.UserService;

@RestController
@RequestMapping(value = "/bmauth", method = RequestMethod.GET)
public class UserQueryController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users")
	public DataTableBase<UserRest> findAllUsers(@ModelAttribute DataTableCriterias criterias) {

		return userService.findAllUsers(criterias);

	}

	@RequestMapping(value = "/users/{userId}")
	public UserRest findByUserId(@PathVariable int userId) {

		return userService.findByUserId(userId);

	}
	
	@RequestMapping(value = "/token/check_token/{token}/{userId}")
	public boolean validateToken(@PathVariable String token, @PathVariable int userId) {
		return userService.validateUserToken(userId, token);
	}

}
