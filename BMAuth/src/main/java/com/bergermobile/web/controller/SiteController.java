package com.bergermobile.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class SiteController {

	@RequestMapping(value = "/")
	public String home() {
		return "index";
	}

	/**
	 * Matches to forwards to the single page angular application, so that we
	 * can use natural routes.
	 */
	// @RequestMapping(value = "/{[path:[^\\.]*}")
	// @RequestMapping(value = "/{[^\\.]}*/{[^\\.]}*/{[^\\.]}*")
	@RequestMapping(value = { "", "signup", "login", "users", "users/**", "applications", "applications/**" })
	public String redirect() {
		// Forward to home page so that route is preserved.
		return "forward:/";
	}
}
