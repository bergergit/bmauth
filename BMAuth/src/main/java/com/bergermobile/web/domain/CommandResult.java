package com.bergermobile.web.domain;

import java.util.ArrayList;
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

	public CommandResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public enum CodeResults {

		FIELD_ERRORS(0, "FIELD_ERRORS"), DELETE_OK(1, "DELETE_OK"), SAVE_OK(2, "SAVE_OK"), INTEGRITY_ERROR(3,
				"INTEGRITY_ERROR");

		private final String codeType;
		private final int codeNumber;

		CodeResults(int codeNumber, String codeType) {

			this.codeNumber = codeNumber;
			this.codeType = codeType;

		}

		public int getCodeNumber() {

			return this.codeNumber;
		}

		public String getCodeType() {

			return this.codeType;
		}

	}

	private int code;
	private int id;
	private String message;
	private Map<String, String> fieldErrors;
	private List<Integer> changedIds;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getChangedIds() {
		return changedIds;
	}

	public void setChangedIds(List<Integer> changedIds) {
		this.changedIds = changedIds;
	}

	public void addChangedId(Integer id) {
		if (changedIds == null) {
			changedIds = new ArrayList<Integer>();
		}
		changedIds.add(id);
	}

}
