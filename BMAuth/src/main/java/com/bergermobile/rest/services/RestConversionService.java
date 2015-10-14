package com.bergermobile.rest.services;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.persistence.repository.RoleRepository;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.LanguageContractRest;
import com.bergermobile.rest.domain.OnlineContractRest;
import com.bergermobile.rest.domain.RoleRest;

@Service
public class RestConversionService {
	
	@Autowired
	private RoleRepository roleRepository;

	public static Timestamp timestamp() {

		return new Timestamp(new Date().getTime());

	}

	public static Date stringToDate(String date) throws ParseException {

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date d = format.parse(date);
		return d;

	}

	public static ApplicationRest applicationToApplicationRest(Application application) {

		ApplicationRest applicationRest = new ApplicationRest();

		applicationRest.setRolesRest(setRolesToRolesRest(application));

		applicationRest.setOnlineContractsRest(setOnlineContractToOnlineContractRest(application));

		// Copy the Application attributes to ApplicationRest attributes
		BeanUtils.copyProperties(application, applicationRest);

		return applicationRest;

	}

	public static Application applicationRestToApplication(ApplicationRest applicationRest) {

		Application application = new Application();

		BeanUtils.copyProperties(applicationRest, application);

		application.setRoles(setRolesRestToRoles(applicationRest.getRolesRest(), application));

		application.setOnlineContracts(setOnlineContractRestToOnlineContract(applicationRest.getOnlineContractsRest(), application));

		return application;

	}
	
	private static List<RoleRest> setRolesToRolesRest(Application application) {

		List<Role> roleList = application.getRoles();

		List<RoleRest> roleRestList = new ArrayList<RoleRest>();

		for (Role role : roleList) {

			RoleRest roleRest = new RoleRest();

			BeanUtils.copyProperties(role, roleRest);

			roleRestList.add(roleRest);

		}

		return roleRestList;

	}
	
	public static List<RoleRest> setRolesToRolesRest(List<UserRole> userRoleList) {

		List<RoleRest> roleRestList = new ArrayList<RoleRest>();

		for (UserRole userRole : userRoleList) {

			RoleRest roleRest = new RoleRest();

			BeanUtils.copyProperties(userRole.getRole(), roleRest);

			roleRestList.add(roleRest);
		}

		return roleRestList;

	}
	
	/** 
	 * Intended to simply contain the RoleID with true values, for the roles the user belongs to
	 */
	public static Map<Integer, Boolean> setSimpleUserRoles(List<UserRole> userRoleList) {
		Map<Integer, Boolean> simpleUserRole = new HashMap<Integer, Boolean>(); 
		for (UserRole userRole : userRoleList) {
			simpleUserRole.put(userRole.getRole().getRoleId(), true);
		}
		return simpleUserRole;
	}
	
	/** 
	 * Intended to simply contain the ApplicationID with true values, for the roles the user belongs to
	 */
	/*
	public static Map<Integer, Boolean> setSimpleUserApplications(List<UserRole> userRoleList) {
		Map<Integer, Boolean> simpleUserRole = new HashMap<Integer, Boolean>(); 
		for (UserRole userRole : userRoleList) {
			try {
				simpleUserRole.put(userRole.getRole().getApplication().getApplicationId(), true);
			} catch (NullPointerException npe) {}
		}
		return simpleUserRole;
	}
	*/

	private static List<Role> setRolesRestToRoles(List<RoleRest> rolesRestList, Application application) {

		List<Role> roleList = new ArrayList<Role>();

		if (rolesRestList == null) {
			return roleList;
		}

		for (RoleRest roleRest : rolesRestList) {

			Role role = new Role();

			BeanUtils.copyProperties(roleRest, role);

			role.setApplication(application);

			roleList.add(role);

		}

		return roleList;

	}

	private static List<OnlineContractRest> setOnlineContractToOnlineContractRest(Application application) {

		List<OnlineContract> onlineContractList = application.getOnlineContracts();
		List<OnlineContractRest> onlineContractRestList = new ArrayList<OnlineContractRest>();

		for (OnlineContract onlineContract : onlineContractList) {
			List<LanguageContractRest> languageContractRestList = new ArrayList<LanguageContractRest>();
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

	private static List<OnlineContract> setOnlineContractRestToOnlineContract(
			List<OnlineContractRest> onlineContractRestList, Application application) {

		List<OnlineContract> onlineContractList = new ArrayList<OnlineContract>();
		List<LanguageContract> languageContractList = new ArrayList<LanguageContract>();

		if (onlineContractRestList == null) {
			return onlineContractList;
		}

		for (OnlineContractRest onlineContractRest : onlineContractRestList) {
			OnlineContract onlineContract = new OnlineContract();
			// Copy attributes from onlineContract to onlineContractRest
			BeanUtils.copyProperties(onlineContractRest, onlineContract);

			onlineContract.setApplication(application);

			List<LanguageContractRest> languageRestList = onlineContractRest.getLanguageContractsRest();

			if (languageRestList != null) {
				for (LanguageContractRest languageContractRest : languageRestList) {
					LanguageContract languageContract = new LanguageContract();
					BeanUtils.copyProperties(languageContractRest, languageContract);
					languageContract.setOnlineContract(onlineContract);
					languageContractList.add(languageContract);
				}
				onlineContract.setLanguageContract(languageContractList);
			}
			onlineContractList.add(onlineContract);
		}

		return onlineContractList;

	}

	/**
	 * Convert from a simple user role list, to a true list of UserRole
	 * @param simpleUserRoles
	 * @return
	 */
	public List<UserRole> simpleUserRolesToUserRoles(Map<Integer, Boolean> simpleUserRoles, User user) {
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		if (simpleUserRoles != null) {
			for (Integer roleId : simpleUserRoles.keySet()) {
				// we only considered checked values (true)
				if (simpleUserRoles.get(roleId)) {
					Role role = roleRepository.findByRoleId(roleId);
					if (role != null) {
						UserRole userRole = new UserRole();
						userRole.setRole(role);
						userRole.setUser(user);
						userRoleList.add(userRole);
					}
				}
			}
		}
		return userRoleList;
	}

}
