package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the online_contract database table.
 * 
 */
public class OnlineContractRest extends BaseTableRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer onlineContractId;

	private String contractVersion;

	private String description;

	private List<ContractUserRest> contractUsersRest;

	private List<LanguageContractRest> languageContractRest;

	private ApplicationRest applicationRest;

	public Integer getOnlineContractId() {
		return onlineContractId;
	}

	public void setOnlineContractId(Integer onlineContractId) {
		this.onlineContractId = onlineContractId;
	}

	public String getContractVersion() {
		return contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ContractUserRest> getContractUsersRest() {
		return contractUsersRest;
	}

	public void setContractUsersRest(List<ContractUserRest> contractUsersRest) {
		this.contractUsersRest = contractUsersRest;
	}

	public List<LanguageContractRest> getLanguageContractRest() {
		return languageContractRest;
	}

	public void setLanguageContractRest(List<LanguageContractRest> languageContractRest) {
		this.languageContractRest = languageContractRest;
	}

	public ApplicationRest getApplicationRest() {
		return applicationRest;
	}

	public void setApplicationRest(ApplicationRest applicationRest) {
		this.applicationRest = applicationRest;
	}

}