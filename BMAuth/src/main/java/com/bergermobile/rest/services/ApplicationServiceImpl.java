package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.persistence.repository.OnlineContractRepository;
import com.bergermobile.persistence.repository.RoleRepository;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.LanguageContractRest;
import com.bergermobile.rest.domain.OnlineContractRest;
import com.bergermobile.rest.domain.RoleRest;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OnlineContractRepository onlineContractRepository;

	@Override
	public List<ApplicationRest> findAllApplications() {

		// Get All Application
		List<ApplicationRest> applicationRestList = new ArrayList<ApplicationRest>();

		// for each Applications
		for (Application application : applicationRepository.findAll()) {

			ApplicationRest applicationRest = new ApplicationRest();

			// Get Roles of which application
			applicationRest.setRolesRest(setRolesToRolesRest(application));

			// Get OnlineContract of which application
			applicationRest.setOnlineContractsRest(setOnlineContractToOnlineContractRest(application));

			// Copy the Application attributes to ApplicationRest attributes
			BeanUtils.copyProperties(application, applicationRest);

			applicationRestList.add(applicationRest);

		}

		return applicationRestList;

	}

	@Override
	public ApplicationRest findByApplicationId(int applicationId) {

		Application application = applicationRepository.findByApplicationId(applicationId);

		if (application != null) {

			ApplicationRest applicationRest = new ApplicationRest();

			applicationRest.setRolesRest(setRolesToRolesRest(application));

			applicationRest.setOnlineContractsRest(setOnlineContractToOnlineContractRest(application));

			// Copy the Application attributes to ApplicationRest attributes
			BeanUtils.copyProperties(application, applicationRest);

			return applicationRest;

		}

		return null;

	}

	@Override
	public List<ApplicationRest> findByApplicationName(String applicationName) {

		// Get All Application
		List<ApplicationRest> applicationRestList = new ArrayList<ApplicationRest>();

		// for each Applications
		for (Application application : applicationRepository.findByApplicationName(applicationName)) {

			ApplicationRest applicationRest = new ApplicationRest();

			// Get Roles of which application
			applicationRest.setRolesRest(setRolesToRolesRest(application));

			// Get OnlineContract of which application
			applicationRest.setOnlineContractsRest(setOnlineContractToOnlineContractRest(application));

			// Copy the Application attributes to ApplicationRest attributes
			BeanUtils.copyProperties(application, applicationRest);

			applicationRestList.add(applicationRest);

		}

		return applicationRestList;

	}

	@Override
	public void save(ApplicationRest applicationRest) {
		
		Application application = new Application();
		
		BeanUtils.copyProperties(applicationRest, application);
		
		application.setRoles(setRolesRestToRoles(applicationRest.getRolesRest()));
		
		//application.setOnlineContracts(setOnlineContractRestToOnlineContract(applicationRest.getOnlineContractsRest()));
		
		applicationRepository.save(application);
		
	}

	@Override
	public void delete(int applicationId) {
		
		applicationRepository.delete(applicationId);

	}

	private List<RoleRest> setRolesToRolesRest(Application application) {

		List<Role> roleList = roleRepository.findByApplication(application);

		List<RoleRest> roleRestList = new ArrayList<RoleRest>();

		for (Role role : roleList) {

			RoleRest roleRest = new RoleRest();

			BeanUtils.copyProperties(role, roleRest);

			roleRestList.add(roleRest);

		}

		return roleRestList;

	}

	private List<Role> setRolesRestToRoles(List<RoleRest> rolesRestList) {

		List<Role> roleList = new ArrayList<Role>();

		for (RoleRest roleRest : rolesRestList) {

			Role role = new Role();

			BeanUtils.copyProperties(roleRest, role);

			roleList.add(role);

		}

		return roleList;

	}

	
	private List<OnlineContractRest> setOnlineContractToOnlineContractRest(Application application) {

		List<OnlineContract> onlineContractList = onlineContractRepository.findByApplication(application);
		List<OnlineContractRest> onlineContractRestList = new ArrayList<OnlineContractRest>();
		List<LanguageContractRest> languageContractRestList = new ArrayList<LanguageContractRest>();

		for (OnlineContract onlineContract : onlineContractList) {
			OnlineContractRest onlineContractRest = new OnlineContractRest();
			// Copy attributes from onlineContract to onlineContractRest
			BeanUtils.copyProperties(onlineContract, onlineContractRest);
			List<LanguageContract> languageList = onlineContract.getLanguageContract();

			for (LanguageContract languageContract : languageList) {
				LanguageContractRest languageContractRest = new LanguageContractRest();
				BeanUtils.copyProperties(languageContract, languageContractRest);
				languageContractRestList.add(languageContractRest);
			}
			onlineContractRest.setLanguageContractsRest(languageContractRestList);
			onlineContractRestList.add(onlineContractRest);
		}

		return onlineContractRestList;

	}

	
	private List<OnlineContract> setOnlineContractRestToOnlineContract(List<OnlineContractRest> onlineContractRestList) {

		List<OnlineContract> onlineContractList = new ArrayList<OnlineContract>();
		List<LanguageContract> languageContractList = new ArrayList<LanguageContract>();

		for (OnlineContractRest onlineContractRest : onlineContractRestList) {
			OnlineContract onlineContract = new OnlineContract();
			// Copy attributes from onlineContract to onlineContractRest
			BeanUtils.copyProperties(onlineContractRest, onlineContract);

			List<LanguageContractRest> languageRestList = onlineContractRest.getLanguageContractsRest();

			for (LanguageContractRest languageContractRest : languageRestList) {
				LanguageContract languageContract = new LanguageContract();
				BeanUtils.copyProperties(languageContractRest, languageContract);
				languageContractList.add(languageContract);
			}
			onlineContract.setLanguageContract(languageContractList);
			onlineContractList.add(onlineContract);
		}

		return onlineContractList;

	}

}
