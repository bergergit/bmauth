package com.bergermobile.rest.services;

import static junit.framework.TestCase.assertEquals;

import java.text.ParseException;
import java.util.List;

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
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.domain.UserRest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("dev")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testIfFindAllReturnsAllUsers() {

		try {
			userRepository.save(PersistenceFixture.facebookUserActive());
			userRepository.save(PersistenceFixture.facebookUserInactive());
			userRepository.save(PersistenceFixture.googlePlusUserActive());
			userRepository.save(PersistenceFixture.googlePlusUserInactive());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<UserRest> listUser = userService.findAllUsers();

		assertEquals(5, listUser.size());	// the application creates a new admin user for default, if this table is empty

	}

}
