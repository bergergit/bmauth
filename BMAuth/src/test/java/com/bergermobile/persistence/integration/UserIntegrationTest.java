package com.bergermobile.persistence.integration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserIntegrationTest {

	@Autowired
	private UserService userService;

	// This test checks if the connection with database is working
	@Test
	public void testThatInsertWorks() {

		UserRest userRest = new UserRest();
		User user = null;

		try {
			user = PersistenceFixture.facebookUserActive();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Copy the User attributes to UserRest attributes
		BeanUtils.copyProperties(user, userRest);
		
		userService.save(userRest);

		UserRest savedUser = userService.findByName("Facebook User Active");

		assertNotNull(savedUser);

		assertEquals(savedUser.getName(), "Facebook User Active");

	}

}
