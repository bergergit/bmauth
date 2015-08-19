package com.bergermobile.persistence.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the online_contract database table.
 * 
 */
@Entity
@Table(name="online_contract")
public class OnlineContract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="online_contract_id")
	private int onlineContractId;

	@Column(name="contract_version")
	private String contractVersion;

	@Column(name="created_by")
	private int createdBy;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Lob
	private String description;

	@Column(name="last_update_date")
	private Timestamp lastUpdateDate;

	@Column(name="last_updated_by")
	private int lastUpdatedBy;

	//bi-directional many-to-one association to ContractUser
	@OneToMany(mappedBy="onlineContract")
	private List<ContractUser> contractUsers;

	//bi-directional many-to-one association to LanguageContract
	@OneToMany(mappedBy="onlineContract")
	private List<LanguageContract> languageContracts;

	//bi-directional many-to-one association to System
	@ManyToOne
	private System system;

	public OnlineContract() {
	}

	public int getOnlineContractId() {
		return this.onlineContractId;
	}

	public void setOnlineContractId(int onlineContractId) {
		this.onlineContractId = onlineContractId;
	}

	public String getContractVersion() {
		return this.contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<ContractUser> getContractUsers() {
		return this.contractUsers;
	}

	public void setContractUsers(List<ContractUser> contractUsers) {
		this.contractUsers = contractUsers;
	}

	public ContractUser addContractUser(ContractUser contractUser) {
		getContractUsers().add(contractUser);
		contractUser.setOnlineContract(this);

		return contractUser;
	}

	public ContractUser removeContractUser(ContractUser contractUser) {
		getContractUsers().remove(contractUser);
		contractUser.setOnlineContract(null);

		return contractUser;
	}

	public List<LanguageContract> getLanguageContracts() {
		return this.languageContracts;
	}

	public void setLanguageContracts(List<LanguageContract> languageContracts) {
		this.languageContracts = languageContracts;
	}

	public LanguageContract addLanguageContract(LanguageContract languageContract) {
		getLanguageContracts().add(languageContract);
		languageContract.setOnlineContract(this);

		return languageContract;
	}

	public LanguageContract removeLanguageContract(LanguageContract languageContract) {
		getLanguageContracts().remove(languageContract);
		languageContract.setOnlineContract(null);

		return languageContract;
	}

	public System getSystem() {
		return this.system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

}