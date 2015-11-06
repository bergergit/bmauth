package com.bergermobile.persistence.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the online_contract database table.
 * 
 */
@Entity
@Table(name = "online_contract")
public class OnlineContract extends BaseTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "online_contract_id")
	private Integer onlineContractId;

	@Column(name = "contract_version")
	private String contractVersion;

	@Lob
	private String description;

	// bi-directional many-to-one association to ContractUser
	@OneToMany(mappedBy = "onlineContract")
	private List<ContractUser> contractUsers;

	// bi-directional many-to-one association to LanguageContract
	@OneToMany(mappedBy = "onlineContract", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<LanguageContract> languageContract;

	// bi-directional many-to-one association to Application
	@ManyToOne
	private Application application;

	public OnlineContract() {
	}

	public Integer getOnlineContractId() {
		return this.onlineContractId;
	}

	public void setOnlineContractId(Integer onlineContractId) {
		this.onlineContractId = onlineContractId;
	}

	public String getContractVersion() {
		return this.contractVersion;
	}

	public void setContractVersion(String contractVersion) {
		this.contractVersion = contractVersion;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<LanguageContract> getLanguageContract() {
		return this.languageContract;
	}

	public void setLanguageContract(List<LanguageContract> languageContract) {
		this.languageContract = languageContract;
	}

	public LanguageContract addLanguageContract(LanguageContract languageContract) {
		getLanguageContract().add(languageContract);
		languageContract.setOnlineContract(this);

		return languageContract;
	}

	public LanguageContract removeLanguageContract(LanguageContract languageContract) {
		getLanguageContract().remove(languageContract);
		languageContract.setOnlineContract(null);

		return languageContract;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return "OnlineContract [onlineContractId=" + onlineContractId + ", contractVersion=" + contractVersion
				+ ", description=" + description + ", contractUsers=" + contractUsers + ", languageContract="
				+ languageContract + ", application=" + application + "]";
	}

}