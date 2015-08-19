package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the log database table.
 * 
 */
@Entity
@Table(name="log")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="log_id")
	private int logId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Column(name="description_log")
	private String descriptionLog;

	@Column(name="last_update_date")
	private Timestamp lastUpdateDate;

	@Column(name="last_updated_by")
	private int lastUpdatedBy;

	@Column(name="log_record_type")
	private String logRecordType;

	@Column(name="record_id")
	private int recordId;

	public Log() {
	}

	public int getLogId() {
		return this.logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescriptionLog() {
		return this.descriptionLog;
	}

	public void setDescriptionLog(String descriptionLog) {
		this.descriptionLog = descriptionLog;
	}

	public Timestamp getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLogRecordType() {
		return this.logRecordType;
	}

	public void setLogRecordType(String logRecordType) {
		this.logRecordType = logRecordType;
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

}