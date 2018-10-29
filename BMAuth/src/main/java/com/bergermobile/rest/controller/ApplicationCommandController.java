package com.bergermobile.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.services.ApplicationService;

@RestController
@RequestMapping(value = "/bmauth")
public class ApplicationCommandController {

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/applications", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveApplication(@RequestBody ApplicationRest applicationRest) {
		
		applicationService.save(applicationRest);

	}

	@RequestMapping(value = "/applications/{applicationId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteApplication(@PathVariable int applicationId) {

		applicationService.delete(applicationId);

	}

}
