//public class UserRepositoryTestHibernate {

package com.bergermobile.persistence.repository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;

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
import com.bergermobile.persistence.domain.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class UserRepositoryTestHibernate {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testThatSavedPasswordDontDeleteUserRoles() {

		User savedUser = userRepository.findByEmail("fabiofilz@gmail.com");

		System.out.println("-------------------------------------");

		List<UserRole> userRoles = savedUser.getUserRoles();

		for (UserRole user : userRoles) {
			System.out.println(user);
		}

		System.out.println("-------------------------------------");

		assertNotNull(savedUser);

		savedUser.setPassword("tiririca");

		User foundUser = userRepository.save(savedUser);

		List<UserRole> userRoles2 = foundUser.getUserRoles();

		System.out.println("-------------------------------------");
		for (UserRole user : userRoles2) {
			System.out.println(user);
		}
		System.out.println("-------------------------------------");

		// check if returns one record
		assertEquals(savedUser.getName(), foundUser.getName());

	}

	@Test
	public void testThatSavedPasswordDontDeleteUserRoles2() {

		User savedUser = userRepository.findByEmail("fabiofilz@gmail.com");

		assertNotNull(savedUser);

		User userCopy = new User();

		BeanUtils.copyProperties(savedUser, userCopy);

		userCopy.setPassword("tiririca2");

		User foundUser = userRepository.save(userCopy);

		// check if returns one record
		assertEquals(userCopy.getName(), foundUser.getName());

	}

}
