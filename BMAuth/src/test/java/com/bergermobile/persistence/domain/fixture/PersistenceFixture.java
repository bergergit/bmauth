package com.bergermobile.persistence.domain.fixture;


import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.bergermobile.persistence.domain.LoginType;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserType;

public class PersistenceFixture {

	public static User facebookUserActive() throws ParseException{
		
		User user = new User();

		// Date of Birth
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date DOB = format.parse("20/01/1980");
				
		// Take current time
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(
				date.getTime());

		user.setLoginType(LoginType.FACEBOOK.getValue());
		user.setUsername("token:12345");
		user.setUserType(UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Facebook User Active");
		user.setEmail("facebook_user_active@gmail.com" );
		user.setBirthday(DOB);
		user.setPassword("password:123456");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);
		
		return user;
		
	}

	public static User facebookUserInactive() throws ParseException{
		
		User user = new User();

		// Date of Birth
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date DOB = format.parse("21/01/1981");
				
		// Take current time
		java.sql.Timestamp timestamp = new java.sql.Timestamp(
				new Date().getTime());

		user.setLoginType(LoginType.FACEBOOK.getValue());
		user.setUsername("token:67890");
		user.setUserType(UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Facebook User Inactive");
		user.setEmail("facebook_user_inactive@gmail.com" );
		user.setBirthday(DOB);
		user.setPassword("password:67890");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);
		
		return user;
		
	}

	
	public static User googlePlusUserActive() throws ParseException{
		
		User user = new User();

		// Date of Birth
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date DOB = format.parse("20/01/1980");
				
		// Take current time
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(
				date.getTime());

		user.setLoginType(LoginType.GOOGLE_PLUS.getValue());
		user.setUsername("token:12345");
		user.setUserType(UserType.CPF.getValue());
		user.setDocumentNumber("123.456.786-00");
		user.setName("Google Plus User Active");
		user.setEmail("googleplus_user_active@gmail.com" );
		user.setBirthday(DOB);
		user.setPassword("password:123456");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);
		
		return user;
		
	}

	public static User googlePlusUserInactive() throws ParseException{
		
		User user = new User();

		// Date of Birth
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date DOB = format.parse("21/01/1981");
				
		// Take current time
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(
				date.getTime());

		user.setLoginType(LoginType.GOOGLE_PLUS.getValue()); // Google+
		user.setUsername("token:67890");
		user.setUserType(UserType.CPF.getValue());
		user.setDocumentNumber("098.765.543-11");
		user.setName("Google Plus User Inactive");
		user.setEmail("googleplus_user_inactive@gmail.com" );
		user.setBirthday(DOB);
		user.setPassword("password:67890");
		user.setActive((short) 1);
		user.setCreatedBy(0); // user Admin
		user.setCreationDate(timestamp);
		user.setLastUpdatedBy(0); // user Admin
		user.setLastUpdateDate(timestamp);
		
		return user;
		
	}

	
}
