package com.bergermobile.rest.domain;

import java.io.Serializable;

/**
 * The persistent class for the log database table.
 * 
 */
public class LogRest extends BaseTableRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer logId;

	private String descriptionLog;

	private String logRecordType;

	private Integer recordId;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getDescriptionLog() {
		return descriptionLog;
	}

	public void setDescriptionLog(String descriptionLog) {
		this.descriptionLog = descriptionLog;
	}

	public String getLogRecordType() {
		return logRecordType;
	}

	public void setLogRecordType(String logRecordType) {
		this.logRecordType = logRecordType;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

}