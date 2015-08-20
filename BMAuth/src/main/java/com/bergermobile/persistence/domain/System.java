package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the system database table.
 * 
 */
@Entity
@Table(name="system")
public class System extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="system_id")
	private Integer systemId;

	private short active;

	@Column(name="mandatory_contract")
	private short mandatoryContract;

	@Column(name="system_name")
	private String systemName;

	@Column(name="test_mode")
	private short testMode;

	private String url;

	//bi-directional many-to-one association to OnlineContract
	@OneToMany(mappedBy="system")
	private List<OnlineContract> onlineContracts;

	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="system")
	private List<Role> roles;

	public System() {
	}

	public Integer getSystemId() {
		return this.systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
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

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
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
		onlineContract.setSystem(this);

		return onlineContract;
	}

	public OnlineContract removeOnlineContract(OnlineContract onlineContract) {
		getOnlineContracts().remove(onlineContract);
		onlineContract.setSystem(null);

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
		role.setSystem(this);

		return role;
	}

	public Role removeRole(Role role) {
		getRoles().remove(role);
		role.setSystem(null);

		return role;
	}

}