package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the language_contract database table.
 * 
 */
@Entity
@Table(name="language_contract")
public class LanguageContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="language_contract_id")
	private int languageContractId;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Lob
	@Column(name="html_contract")
	private String htmlContract;

	private String language;

	@Column(name="last_update_date")
	private Timestamp lastUpdateDate;

	@Column(name="last_updated_by")
	private int lastUpdatedBy;

	//bi-directional many-to-one association to OnlineContract
	@ManyToOne
	@JoinColumn(name="online_contract_online_contract_id")
	private OnlineContract onlineContract;

	public LanguageContract() {
	}

	public int getLanguageContractId() {
		return this.languageContractId;
	}

	public void setLanguageContractId(int languageContractId) {
		this.languageContractId = languageContractId;
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

	public String getHtmlContract() {
		return this.htmlContract;
	}

	public void setHtmlContract(String htmlContract) {
		this.htmlContract = htmlContract;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public OnlineContract getOnlineContract() {
		return this.onlineContract;
	}

	public void setOnlineContract(OnlineContract onlineContract) {
		this.onlineContract = onlineContract;
	}

}