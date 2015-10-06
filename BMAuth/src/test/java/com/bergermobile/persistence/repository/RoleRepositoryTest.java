package com.bergermobile.persistence.repository;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.RoleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	// This test checks if the connection with database is working
	@Test
	public void testThatInsertWorks() {

		Role role = null;

		Application app = new Application();

		role = PersistenceFixture.roleAdmin(app);

		Role savedRole = roleRepository.save(role);

		assertNotNull(savedRole);

		assertEquals(savedRole.getRoleName(), "ADMIN");

	}

}
