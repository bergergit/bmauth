package com.bergermobile.persistence.repository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("dev")
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

	// This test is test the logic

	@Test
	public void testThatFindByNameWorks() {

		User user = null;
		try {
			user = PersistenceFixture.facebookUserActive();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User savedUser = userRepository.save(user);

		assertNotNull(savedUser);

		User foundUser = userRepository.findByName("Facebook User Active");

		// check if returns one record
		assertEquals(foundUser.getName(), "Facebook User Active");

	}

	@Test
	public void testThatFindByUserTypeAndUsernameWorks() {

		User user = null;
		try {
			user = PersistenceFixture.facebookUserActive();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User savedUser = userRepository.save(user);

		assertNotNull(savedUser);

		User foundUser = userRepository.findByUserTypeAndUsername(User.LoginType.FACEBOOK.getValue(), "token:12345");

		// check if returns the same record
		assertEquals(foundUser, user);

	}

}
