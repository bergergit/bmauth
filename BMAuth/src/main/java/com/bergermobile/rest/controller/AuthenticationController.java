package com.bergermobile.rest.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.services.CustomUserDetailsService;

@RestController
@RequestMapping(method = RequestMethod.GET)
public class AuthenticationController {
	
	static Log LOG = LogFactory.getLog(CustomUserDetailsService.class);

	@RequestMapping("/user")
	public Map<String, Object> user(Principal user) {
		LOG.debug("Trying to retrieve user " + user);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (user != null) {
			map.put("name", user.getName());
			map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user).getAuthorities()));
		}
		return map;
	}
	
	/*
	@RequestMapping("/login")
	public Principal user(Principal user) {
		return user;
	}
	*/
}
