package com.bergermobile.persistence.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bergermobile.commons.domain.BaseTable;

/**
 * The persistent class for the Application database table.
 * 
 */
@Entity
@Table(name = "application")
public class Application extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "application_id")
	private Integer applicationId;

	private boolean active;

	@Column(name = "mandatory_contract")
	private boolean mandatoryContract;

	@Column(name = "application_name", unique=true)
	private String applicationName;
	
	private String realm;

	@Column(name = "test_mode")
	private boolean testMode;

	private String url;

	// bi-directional many-to-one association to OnlineContract
	@OneToMany(mappedBy = "application", cascade = {CascadeType.ALL }, orphanRemoval = true)
	private List<OnlineContract> onlineContracts;

	// bi-directional many-to-one association to Role
	@OneToMany(mappedBy = "application", cascade = {CascadeType.ALL }, orphanRemoval = true)
	private List<Role> roles;

	public Application() {
	}

	public Integer getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getMandatoryContract() {
		return this.mandatoryContract;
	}

	public void setMandatoryContract(boolean mandatoryContract) {
		this.mandatoryContract = mandatoryContract;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String ApplicationName) {
		this.applicationName = ApplicationName;
	}

	public boolean getTestMode() {
		return this.testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<OnlineContract> getOnlineContracts() {
		return this.onlineContracts;
	}

	public void setOnlineContracts(List<OnlineContract> onlineContracts) {
		this.onlineContracts = onlineContracts;
	}

	public OnlineContract addOnlineContract(OnlineContract onlineContract) {
		getOnlineContracts().add(onlineContract);
		onlineContract.setApplication(this);

		return onlineContract;
	}

	public OnlineContract removeOnlineContract(OnlineContract onlineContract) {
		getOnlineContracts().remove(onlineContract);
		onlineContract.setApplication(null);

		return onlineContract;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Role addRole(Role role) {
		getRoles().add(role);
		role.setApplication(this);

		return role;
	}

	public Role removeRole(Role role) {
		getRoles().remove(role);
		role.setApplication(null);

		return role;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
}