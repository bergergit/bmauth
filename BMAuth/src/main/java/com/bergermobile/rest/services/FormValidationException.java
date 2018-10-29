package com.bergermobile.rest.services;

import org.springframework.validation.BindingResult;

public class FormValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private BindingResult result;
	
	public FormValidationException(BindingResult result) {
		this.result = result;
	}
	public FormValidationException(Integer id, BindingResult result) {
		this(result);
		this.id = id;
	}
	
	public BindingResult getResult() {
		return result;
	}
	
	public Integer getId() {
		return id;
	}

}
