package com.bergermobile.persistence.integration;

import com.bergermobile.persistence.domain.fixture.PersistenceFixture;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserIntegrationTest {

	@Autowired
	private UserRepository userRepository;

	// This test checks if the connection with database is working
	@Test
	public void testThatInsertWorks() {

		User user = null;

		try {
			user = PersistenceFixture.facebookUserActive();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User savedUser = userRepository.save(user);

		assertNotNull(savedUser);

		assertEquals(savedUser.getName(), "Facebook User Active");

	}

}
