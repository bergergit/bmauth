package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the language_contract database table.
 * 
 */
@Entity
@Table(name = "language_contract")
public class LanguageContract extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "language_contract_id")
	private Integer languageContractId;

	@Lob
	@Column(name = "html_contract")
	private String htmlContract;

	private String language;

	// bi-directional many-to-one association to OnlineContract
	@ManyToOne
	@JoinColumn(name = "online_contract_online_contract_id")
	private OnlineContract onlineContract;

	public LanguageContract() {
	}

	public Integer getLanguageContractId() {
		return this.languageContractId;
	}

	public void setLanguageContractId(Integer languageContractId) {
		this.languageContractId = languageContractId;
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

	public OnlineContract getOnlineContract() {
		return this.onlineContract;
	}

	public void setOnlineContract(OnlineContract onlineContract) {
		this.onlineContract = onlineContract;
	}

}