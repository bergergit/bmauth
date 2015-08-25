package com.bergermobile.persistence.domain.fixture;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bergermobile.persistence.domain.Active;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.LanguageContract;
import com.bergermobile.persistence.domain.OnlineContract;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;

public class PersistenceFixture {

	public static Timestamp timestamp() {

		return new Timestamp(new Date().getTime());

	}

	public static Date stringToDate(String date) throws ParseException {

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date d = format.parse(date);
		return d;

	}

	public static User facebookUserActive() throws ParseException {

		User user = new User();

		// Date of Birth
		Date dob = stringToDate("20/01/1980");

		// Take current time
		Timestamp timestamp = timestamp();
		
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
	
	public static User internalUser1() throws ParseException {

		User user = new User();

		// Date of Birth
		Date dob = stringToDate("20/01/1981");

		// Take current time
		Timestamp timestamp = timestamp();
		
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
	
	public static User internalUser2() throws ParseException {

		User user = new User();

		// Date of Birth
		Date dob = stringToDate("23/01/1990");

		// Take current time
		Timestamp timestamp = timestamp();
		
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

	public static User facebookUserInactive() throws ParseException {

		User user = new User();

		// Date of Birth
		Date DOB = stringToDate("21/01/1981");

		// Take current time
		Timestamp timestamp = timestamp();

		user.setLoginType(User.LoginType.FACEBOOK.getValue());
		user.setUsername("token:67890");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Facebook User Inactive");
		user.setEmail("facebook_user_inactive@gmail.com");
		user.setBirthday(DOB);
		user.setPassword("password:67890");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static User googlePlusUserActive() throws ParseException {

		User user = new User();

		// Date of Birth
		Date DOB = stringToDate("20/01/1980");

		// Take current time
		Timestamp timestamp = timestamp();

		user.setLoginType(User.LoginType.GOOGLE_PLUS.getValue());
		user.setUsername("token:12345");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Google Plus User Active");
		user.setEmail("googleplus_user_active@gmail.com");
		user.setBirthday(DOB);
		user.setPassword("password:123456");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static User googlePlusUserInactive() throws ParseException {

		User user = new User();

		// Date of Birth
		Date dob = stringToDate("21/01/1981");

		// Take current time
		Timestamp timestamp = timestamp();

		user.setLoginType(User.LoginType.GOOGLE_PLUS.getValue()); // Google+
		user.setUsername("token:67890");
		user.setUserType(User.UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Google Plus User Inactive");
		user.setEmail("googleplus_user_inactive@gmail.com");
		user.setBirthday(dob);
		user.setPassword("password:67890");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);

		return user;

	}

	public static Application megaFunkSystem() {

		Application application = new Application();

		// Take current time
		Timestamp timestamp = timestamp();

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

		contracts.add(contract1(application));
		contracts.add(contract2(application));

		application.setOnlineContracts(contracts);

		return application;

	}

	public static Role roleAdmin(Application application) {

		Role role = new Role();

		// Take current time
		java.sql.Timestamp timestamp = timestamp();

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
		Timestamp timestamp = timestamp();

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
		Timestamp timestamp = timestamp();

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
		Timestamp timestamp = timestamp();

		role.setRoleName("ARTIST");
		role.setApplication(application);
		role.setCreatedBy(0); // user Admin
		role.setCreationDate(timestamp);
		role.setLastUpdatedBy(0); // user Admin
		role.setLastUpdateDate(timestamp);

		return role;

	}

	public static OnlineContract contract1(Application application) {

		// Take current time
		java.sql.Timestamp timestamp = timestamp();

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

	public static OnlineContract contract2(Application application) {

		// Take current time
		java.sql.Timestamp timestamp = timestamp();

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
