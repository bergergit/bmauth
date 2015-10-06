package com.bergermobile.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice to handle basic exceptions
 * @author fabioberger
 *
 */
@ControllerAdvice
public class RestExceptionAdvice {
	
	static Log LOG = LogFactory.getLog(RestExceptionAdvice.class);

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
		LOG.error("DataIntegrityException raised", e);
	}

}
