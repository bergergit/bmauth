package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the Application database table.
 * 
 */
public class ApplicationRest extends BaseTableRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer applicationId;

	private short active;

	private short mandatoryContract;

	private String applicationName;

	private short testMode;

	private String url;

	private List<OnlineContractRest> onlineContractsRest;

	private List<RoleRest> rolesRest;

	public ApplicationRest() {
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public short getActive() {
		return active;
	}

	public void setActive(short active) {
		this.active = active;
	}

	public short getMandatoryContract() {
		return mandatoryContract;
	}

	public void setMandatoryContract(short mandatoryContract) {
		this.mandatoryContract = mandatoryContract;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public short getTestMode() {
		return testMode;
	}

	public void setTestMode(short testMode) {
		this.testMode = testMode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<OnlineContractRest> getOnlineContractsRest() {
		return onlineContractsRest;
	}

	public void setOnlineContractsRest(List<OnlineContractRest> onlineContractsRest) {
		this.onlineContractsRest = onlineContractsRest;
	}

	public List<RoleRest> getRolesRest() {
		return rolesRest;
	}

	public void setRolesRest(List<RoleRest> rolesRest) {
		this.rolesRest = rolesRest;
	}

}