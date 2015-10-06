package com.bergermobile.persistence.domain.fixture;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bergermobile.persistence.domain.Active;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.rest.services.RestConversionService;

public class PersistenceFixture {
	
	public static Application application1() {
		Application application = new Application();
		application.setApplicationName( "Bomber Cast");
		application.setMandatoryContract(true);
		application.setTestMode(false);
		application.setUrl("http://localhost");
		return application;
	}


	public static User facebookUserActive() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("20/01/1980");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.FACEBOOK.getValue());
		user.setUsername("token:12345");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Facebook User Active");
		user.setEmail("facebook_user_active@gmail.com");
		user.setBirthday(dob);
		user.setPassword("password:123456");
		user.setActive(Active.YES.getValue());
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;
	}

	public static User internalUser1() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("20/01/1981");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.INTERNAL.getValue());
		user.setUsername("fabioberger@gmail.com");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Fabio Berger internal");
		user.setEmail("fabioberger@gmail.com");
		user.setBirthday(dob);
		user.setPassword("mypassword123");
		user.setActive(Active.YES.getValue());
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static User internalUser2() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("23/01/1990");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.INTERNAL.getValue());
		user.setUsername("fabiofilz@gmail.com");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("444.232.121-00");
		user.setName("Fabio Filz internal");
		user.setEmail("fabiofilz@gmail.com");
		user.setBirthday(dob);
		user.setPassword("thepassword456");
		user.setActive(Active.YES.getValue());
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}
	
	public static User internalUser1WithRoles(String... roles) {
		User user = internalUser1();
		for (String roleStr : roles) {
			Role role = new Role();
			role.setRoleName(roleStr);
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			user.addUserRole(userRole);
		}
		return user;
	}
	
	public static User userWithRoles(User user, String... roles) {
		for (String roleStr : roles) {
			Role role = new Role();
			role.setRoleName(roleStr);
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			user.addUserRole(userRole);
		}
		return user;
	}
	
	public static User userWithRoles(User user, Role... roles) {
		for (Role role : roles) {
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			user.addUserRole(userRole);
		}
		return user;
	}

	public static User facebookUserInactive() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("21/01/1981");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.FACEBOOK.getValue());
		user.setUsername("token:67890");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Facebook User Inactive");
		user.setEmail("facebook_user_inactive@gmail.com");
		user.setBirthday(dob);
		user.setPassword("password:67890");
		user.setActive(true);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static User googlePlusUserActive() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("20/01/1980");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.GOOGLE_PLUS.getValue());
		user.setUsername("token:12345");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Google Plus User Active");
		user.setEmail("googleplus_user_active@gmail.com");
		user.setBirthday(dob);
		user.setPassword("password:123456");
		user.setActive(true);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static User googlePlusUserInactive() {

		User user = new User();

		// Date of Birth
		Date dob;
		try {
			dob = RestConversionService.stringToDate("21/01/1981");
		} catch (ParseException e) {
			dob = null;
		}

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		user.setLoginType(User.LoginType.GOOGLE_PLUS.getValue()); // Google+
		user.setUsername("token:67890");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Google Plus User Inactive");
		user.setEmail("googleplus_user_inactive@gmail.com");
		user.setBirthday(dob);
		user.setPassword("password:67890");
		user.setActive(true);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static Application megaFunkSystem() {

		Application application = new Application();

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		application.setApplicationName("Mega Funk");
		application.setUrl("www.megafunk.com.br");
		application.setTestMode(Active.YES.getValue());
		application.setMandatoryContract(Active.YES.getValue());
		application.setActive(Active.YES.getValue());
		application.setCreatedBy(0); // user Admin
		application.setCreationDate(timestamp);
		application.setLastUpdatedBy(0); // user Admin
		application.setLastUpdateDate(timestamp);

		List<Role> roles = new ArrayList<Role>();

		roles.add(roleAdmin(application));
		roles.add(roleManager(application));
		roles.add(roleUser(application));
		roles.add(roleArtist(application));

		application.setRoles(roles);

		List<OnlineContract> contracts = new ArrayList<OnlineContract>();

		contracts.add(contractVersion1(application));
		contracts.add(contractVersion2(application));

		application.setOnlineContracts(contracts);

		return application;

	}

	public static Role roleAdmin(Application application) {

		Role role = new Role();

		// Take current time
		java.sql.Timestamp timestamp = RestConversionService.timestamp();

		role.setRoleName("ADMIN");
		role.setApplication(application);
		role.setCreatedBy(0); // user Admin
		role.setCreationDate(timestamp);
		role.setLastUpdatedBy(0); // user Admin
		role.setLastUpdateDate(timestamp);

		return role;

	}

	public static Role roleManager(Application application) {

		Role role = new Role();

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		role.setRoleName("MANAGER");
		role.setApplication(application);
		role.setCreatedBy(0); // user Admin
		role.setCreationDate(timestamp);
		role.setLastUpdatedBy(0); // user Admin
		role.setLastUpdateDate(timestamp);

		return role;

	}

	public static Role roleUser(Application application) {

		Role role = new Role();

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		role.setRoleName("USER");
		role.setApplication(application);
		role.setCreatedBy(0); // user Admin
		role.setCreationDate(timestamp);
		role.setLastUpdatedBy(0); // user Admin
		role.setLastUpdateDate(timestamp);

		return role;

	}

	public static Role roleArtist(Application application) {

		Role role = new Role();

		// Take current time
		Timestamp timestamp = RestConversionService.timestamp();

		role.setRoleName("ARTIST");
		role.setApplication(application);
		role.setCreatedBy(0); // user Admin
		role.setCreationDate(timestamp);
		role.setLastUpdatedBy(0); // user Admin
		role.setLastUpdateDate(timestamp);

		return role;

	}

	public static OnlineContract contractVersion1(Application application) {

		// Take current time
		java.sql.Timestamp timestamp = RestConversionService.timestamp();

		OnlineContract contract = new OnlineContract();

		contract.setApplication(application);
		contract.setContractVersion("1.0");
		contract.setDescription("Description of Contract Version 1.0");
		contract.setCreatedBy(0); // user Admin
		contract.setCreationDate(timestamp);
		contract.setLastUpdatedBy(0); // user Admin
		contract.setLastUpdateDate(timestamp);

		LanguageContract lg1 = new LanguageContract();

		lg1.setOnlineContract(contract);
		lg1.setHtmlContract("HTML PTB version 1.0");
		lg1.setLanguage("PTB");
		lg1.setCreatedBy(0); // user Admin
		lg1.setCreationDate(timestamp);
		lg1.setLastUpdatedBy(0); // user Admin
		lg1.setLastUpdateDate(timestamp);

		LanguageContract lg2 = new LanguageContract();

		lg2.setOnlineContract(contract);
		lg2.setHtmlContract("HTML ING version 1.0");
		lg2.setLanguage("ING");
		lg2.setCreatedBy(0); // user Admin
		lg2.setCreationDate(timestamp);
		lg2.setLastUpdatedBy(0); // user Admin
		lg2.setLastUpdateDate(timestamp);

		List<LanguageContract> langList = new ArrayList<LanguageContract>();

		langList.add(lg1);
		langList.add(lg2);

		contract.setLanguageContract(langList);

		return contract;

	}

	public static OnlineContract contractVersion2(Application application) {

		// Take current time
		java.sql.Timestamp timestamp = RestConversionService.timestamp();

		OnlineContract contract = new OnlineContract();

		contract.setApplication(application);
		contract.setContractVersion("2.0");
		contract.setDescription("Description of Contract Version 2.0");
		contract.setCreatedBy(0); // user Admin
		contract.setCreationDate(timestamp);
		contract.setLastUpdatedBy(0); // user Admin
		contract.setLastUpdateDate(timestamp);

		LanguageContract lg1 = new LanguageContract();

		lg1.setOnlineContract(contract);
		lg1.setHtmlContract("HTML PTB Version 2.0");
		lg1.setLanguage("PTB");
		lg1.setCreatedBy(0); // user Admin
		lg1.setCreationDate(timestamp);
		lg1.setLastUpdatedBy(0); // user Admin
		lg1.setLastUpdateDate(timestamp);

		LanguageContract lg2 = new LanguageContract();

		lg2.setOnlineContract(contract);
		lg2.setHtmlContract("HTML ING version 2.0");
		lg2.setLanguage("ING");
		lg2.setCreatedBy(0); // user Admin
		lg2.setCreationDate(timestamp);
		lg2.setLastUpdatedBy(0); // user Admin
		lg2.setLastUpdateDate(timestamp);

		List<LanguageContract> langList = new ArrayList<LanguageContract>();

		langList.add(lg1);
		langList.add(lg2);

		contract.setLanguageContract(langList);

		return contract;

	}

}
