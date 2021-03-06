package com.bergermobile.web.controller;

import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.services.SerializableResourceBundleMessageSource;

@RestController
@RequestMapping("/messageBundle")
public class InternalizationController {

	@Autowired
	SerializableResourceBundleMessageSource messageBundle;

	@RequestMapping(method = RequestMethod.GET)
	public Properties list(@RequestParam String lang) {
		return messageBundle.getAllProperties(new Locale(lang));
	}

}
