package com.bergermobile.rest.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * The persistent class for the Application database table.
 * 
 */
public class ApplicationRest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer applicationId;

	private Boolean active;

	// Boolen to String when create JSON => "true"
	@JsonSerialize(using=ToStringSerializer.class)
	private Boolean mandatoryContract;

	private String applicationName;
	
	private String realm;

	// Boolen to String when create JSON => "true"
	@JsonSerialize(using=ToStringSerializer.class)
	private Boolean testMode;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getMandatoryContract() {
		return mandatoryContract;
	}

	public void setMandatoryContract(Boolean mandatoryContract) {
		this.mandatoryContract = mandatoryContract;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Boolean getTestMode() {
		return testMode;
	}

	public void setTestMode(Boolean testMode) {
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
	
	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public String toString() {
		return "ApplicationRest [applicationId=" + applicationId + ", active=" + active + ", mandatoryContract="
				+ mandatoryContract + ", applicationName=" + applicationName + ", testMode=" + testMode + ", url=" + url
				+ ", onlineContractsRest=" + onlineContractsRest + ", rolesRest=" + rolesRest + "]";
	}
}