package com.bergermobile.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class SiteController {
	
	//private static Logger LOG = LoggerFactory.getLogger(SiteController.class);

	@RequestMapping(value = "/")
	public String home() {
		//LOG.debug("Site controller");
		return "index";
	}

	/**
	 * Matches to forwards to the single page angular application, so that we
	 * can use natural routes.
	 */
	// @RequestMapping(value = "/{[path:[^\\.]*}")
	// @RequestMapping(value = "/{[^\\.]}*/{[^\\.]}*/{[^\\.]}*")
	@RequestMapping(value = { "", "signup", "login", "logout", "users", "users/**", "applications", "applications/**" })
	public String redirect() {
		// Forward to home page so that route is preserved.
		return "forward:/";
	}
}
