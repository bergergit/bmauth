package com.bergermobile.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bergermobile.rest.domain.CommandResult;
import com.bergermobile.rest.services.FormValidationException;

import javassist.NotFoundException;
import scala.annotation.meta.setter;

/**
 * Controller advice to handle basic exceptions
 * 
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

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFoundException(NotFoundException e) {
		LOG.info("User email not found for resetting password: " + e.getMessage());
	}

	@ExceptionHandler(FormValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	/**
	 * This intercepts the response when there are form validation errors This
	 * will set the in the CommandResult, all the fields that have errors, so
	 * they ca be used by our lovely screen interface.
	 * 
	 * @param e
	 * @return
	 */
	public CommandResult handleFormErrors(FormValidationException e) {
		LOG.error("FormValidationException raised");
		CommandResult commandResult = new CommandResult();
		commandResult.setCode(CommandResult.CodeResults.FIELD_ERRORS.ordinal());
		commandResult.setMessage("Form validation error");
		commandResult.setId(e.getId());

		List<Map<String, String>> fieldErrors = new ArrayList<Map<String, String>>();

		Map<String, String> errorMap = new HashMap<String, String>();
		for (FieldError error : e.getResult().getFieldErrors()) {
			errorMap.put("fieldName", error.getField());
			errorMap.put("errorCode", error.getCode());
			errorMap.put("defaultMessage", error.getDefaultMessage());
			fieldErrors.add(errorMap);
		}
		commandResult.setFieldErrors(fieldErrors);
		return commandResult;
		
	}
}
