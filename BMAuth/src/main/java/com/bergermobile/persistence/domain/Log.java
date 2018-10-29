package com.bergermobile.persistence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bergermobile.commons.domain.BaseTable;

/**
 * The persistent class for the log database table.
 * 
 */
@Entity
@Table(name = "log")
public class Log extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "log_id")
	private Integer logId;

	@Column(name = "description_log")
	private String descriptionLog;

	@Column(name = "log_record_type")
	private String logRecordType;

	@Column(name = "record_id")
	private Integer recordId;

	public Log() {
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getDescriptionLog() {
		return this.descriptionLog;
	}

	public void setDescriptionLog(String descriptionLog) {
		this.descriptionLog = descriptionLog;
	}

	public String getLogRecordType() {
		return this.logRecordType;
	}

	public void setLogRecordType(String logRecordType) {
		this.logRecordType = logRecordType;
	}

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

}