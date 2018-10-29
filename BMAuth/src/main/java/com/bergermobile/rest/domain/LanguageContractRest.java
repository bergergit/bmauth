package com.bergermobile.rest.domain;

import java.io.Serializable;

/**
 * The persistent class for the language_contract database table.
 * 
 */
public class LanguageContractRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer languageContractId;

	private String htmlContract;

	private String language;

	public Integer getLanguageContractId() {
		return languageContractId;
	}

	public void setLanguageContractId(Integer languageContractId) {
		this.languageContractId = languageContractId;
	}

	public String getHtmlContract() {
		return htmlContract;
	}

	public void setHtmlContract(String htmlContract) {
		this.htmlContract = htmlContract;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}