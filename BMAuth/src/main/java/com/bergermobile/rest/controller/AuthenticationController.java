package com.bergermobile.rest.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.commons.security.SecurityUser;
import com.bergermobile.rest.services.ApplicationService;
import com.bergermobile.rest.services.UserService;
import com.bergermobile.security.CustomUserDetailsService;

@RestController
@RequestMapping(method = RequestMethod.GET)
public class AuthenticationController {
	
	static Log LOG = LogFactory.getLog(CustomUserDetailsService.class);
	
	@Autowired
    private Environment environment;

	@Autowired
    private UserService userService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal SecurityUser user, HttpServletRequest request) throws NotFoundException {
		LOG.debug("Trying to retrieve user " + user);
		
		// if remember me is checked, session will be longer
		if (request.getParameter("rememberMe") != null && request.getParameter("rememberMe").equals("true")) {
			LOG.debug("Setting remember me session to higher value");
			request.getSession().setMaxInactiveInterval(Integer.parseInt(environment.getProperty("bmauth.rememberme.expire").trim()) * 60);
		} 
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (user != null) {
			//map.put("username", user.getName());
			map.put("id", user.getUserId());
			map.put("roles", AuthorityUtils.authorityListToSet(user.getAuthorities()));
			//map.put("mandatoryContract", userService.getMustSignTheContract(user.get))
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
