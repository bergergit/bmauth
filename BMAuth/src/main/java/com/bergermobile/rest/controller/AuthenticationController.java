package com.bergermobile.rest.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.security.CustomUserDetailsService;

@RestController
@RequestMapping(method = RequestMethod.GET)
public class AuthenticationController {
	
	static Log LOG = LogFactory.getLog(CustomUserDetailsService.class);
	
	@Autowired
    private Environment environment;

	@RequestMapping("/user")
	public Map<String, Object> user(Principal user, HttpServletRequest request) {
		LOG.debug("Trying to retrieve user " + user);
		
		// if remember me is checked, session will be longer
		if (request.getParameter("rememberMe") != null && request.getParameter("rememberMe").equals("true")) {
			LOG.debug("Setting remember me session to higher value");
			request.getSession().setMaxInactiveInterval(Integer.parseInt(environment.getProperty("bmauth.rememberme.expire").trim()) * 60);
		} 
		
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
