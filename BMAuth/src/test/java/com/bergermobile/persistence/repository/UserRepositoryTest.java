package com.bergermobile.persistence.repository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@Rollback(true)
@ActiveProfiles("dev")
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testThatFindByNameWorks() {

		User user = PersistenceFixture.facebookUserActive();
		User savedUser = userRepository.save(user);

		assertNotNull(savedUser);

		User foundUser = userRepository.findByName("Facebook User Active");

		// check if returns one record
		assertEquals(foundUser.getName(), "Facebook User Active");

	}

	@Test
	public void testThatFindByUserTypeAndUsernameWorks() throws Exception {

		User user = null;
		user = PersistenceFixture.facebookUserActive();

		User savedUser = userRepository.save(user);

		assertNotNull(savedUser);

		User foundUser = userRepository.findByUserTypeAndUsername(User.LoginType.FACEBOOK.getValue(), "token:12345");

		// check if returns the same record
		assertEquals(foundUser, user);

	}
	
	@Test
	public void testThatRolesAreCorrectlySaved() {
		User user1 = internalUser1WithRoles("ADMIN");
		User savedUser = userRepository.save(user1);
		
		assertThat(savedUser.getUserRoles(), hasSize(1));
		assertNotNull(savedUser.getUserRoles().get(0).getRole());
		assertEquals("ADMIN", savedUser.getUserRoles().get(0).getRole().getRoleName());
	}

}
