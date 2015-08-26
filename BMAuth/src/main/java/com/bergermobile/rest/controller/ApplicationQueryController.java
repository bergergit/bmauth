package com.bergermobile.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.ApplicationService;

@RestController
@RequestMapping(value = "/bmauth", method = RequestMethod.GET)
public class ApplicationQueryController {

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/applications")
	public List<ApplicationRest> findAllApplications() {

		return applicationService.findAllApplications();

	}

	@RequestMapping(value = "/applications/{applicationId}")
	public ApplicationRest findByApplicationId(@PathVariable int applicationId) {

		return applicationService.findByApplicationId(applicationId);

	}

	@RequestMapping(value = "/applications/name/{applicationName}")
	public List<ApplicationRest>  findByApplicationName(@PathVariable String applicationName) {

		return applicationService.findByApplicationName(applicationName);

	}

}
