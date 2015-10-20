package com.bergermobile.rest.domain;

import java.util.List;
import java.util.Map;

/**
 * Pojo for result codes and messages for command processes (delete, save)
 * 
 * @author fabioberger
 *
 */
public class CommandResult {

	public CommandResult() {
	}

	public CommandResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public enum CodeResults {
		FIELD_ERRORS, DELETE_OK, SAVE_OK, INTEGRITY_ERROR,
	}

	private Integer code;
	private Integer id;
	private String message;
	private List<Map<String, String>> fieldErrors;

	public int getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Map<String, String>> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<Map<String, String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
