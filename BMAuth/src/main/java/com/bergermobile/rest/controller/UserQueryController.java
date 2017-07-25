package com.bergermobile.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.commons.rest.DataTableBase;
import com.bergermobile.commons.rest.DataTableCriterias;
import com.bergermobile.commons.security.SecurityUser;
import com.bergermobile.rest.domain.LanguageContractRest;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.UserService;

@RestController
@RequestMapping(value = "/bmauth", method = RequestMethod.GET)
public class UserQueryController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PagedResourcesAssembler<UserRest> assembler;
	
	@RequestMapping(value = "/users")
	public DataTableBase<UserRest> findAllUsers(@ModelAttribute DataTableCriterias criterias) {

		return userService.findAllUsers(criterias);

	}
	
	/**
	 * Find all users by AppName
	 * @param criterias
	 * @return
	 */
	@RequestMapping(value = "/users/appname/{appName}")
	public List<UserRest> findUsersByAppName(@PathVariable String appName) {
		return userService.findByApplicationName(appName);
	}
	
	@RequestMapping(value = "/users/appname/{appName}/hateoas", produces = "application/json")
	@ResponseBody
	public PagedResources<Resource<UserRest>> findUsersByAppNameHateoas(@PathVariable String appName) {
		
		List<UserRest> content = userService.findByApplicationName(appName);
		
		Page<UserRest> page = new PageImpl<UserRest>(content);
		
		return assembler.toResource(page);
	}

	@RequestMapping(value = "/users/{userId}")
	public UserRest findByUserId(@PathVariable int userId) {
		return userService.findByUserId(userId);
	}
	
	/**
	 * Will allow retrieving userId if he belongs to the logged in user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}")
	public UserRest findByUserIdSingle(@PathVariable int userId) {
		SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (securityUser.getUserId() == userId) {
			return userService.findByUserId(userId);
		} else {
			throw new AccessDeniedException("Not authorized");
		}
		
	}
	
	@RequestMapping(value = "/token/check_token/{token}/{userId}")
	public boolean validateToken(@PathVariable String token, @PathVariable int userId) {
		return userService.validateUserToken(userId, token);
	}
	
	@RequestMapping(value = "/user/latestContract/{appName}")
	public LanguageContractRest getLatestContract(@PathVariable String appName) {
		return userService.getLatestContract(appName);
	}

}
