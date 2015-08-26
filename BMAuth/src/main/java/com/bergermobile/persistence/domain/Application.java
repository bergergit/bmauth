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


/**
 * The persistent class for the Application database table.
 * 
 */
@Entity
@Table(name="application")
public class Application extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="application_id")
	private Integer applicationId;

	private short active;

	@Column(name="mandatory_contract")
	private short mandatoryContract;

	@Column(name="application_name")
	private String applicationName;

	@Column(name="test_mode")
	private short testMode;

	private String url;

	//bi-directional many-to-one association to OnlineContract
	@OneToMany(mappedBy="application", cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
	private List<OnlineContract> onlineContracts;

	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="application", cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
	private List<Role> roles;

	public Application() {
	}

	public Integer getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public short getActive() {
		return this.active;
	}

	public void setActive(short active) {
		this.active = active;
	}

	public short getMandatoryContract() {
		return this.mandatoryContract;
	}

	public void setMandatoryContract(short mandatoryContract) {
		this.mandatoryContract = mandatoryContract;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String ApplicationName) {
		this.applicationName = ApplicationName;
	}

	public short getTestMode() {
		return this.testMode;
	}

	public void setTestMode(short testMode) {
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

}