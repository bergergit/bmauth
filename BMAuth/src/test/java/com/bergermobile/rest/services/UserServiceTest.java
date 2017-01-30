package com.bergermobile.rest.services;

import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.application1;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.internalUser1;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.roleAdmin;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.roleUser;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.userDataTableCriterias;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.userWithRoles;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.Hashtable;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.commons.rest.DataTableBase;
import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.fixture.PersistenceFixture;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.persistence.repository.RoleRepository;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.domain.UserRest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Rollback(true)
@ActiveProfiles("dev")
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Test
	public void testIfFindAllReturnsAllUsers() {

		userRepository.save(PersistenceFixture.facebookUserActive());
		userRepository.save(PersistenceFixture.facebookUserInactive());
		userRepository.save(PersistenceFixture.googlePlusUserActive());
		userRepository.save(PersistenceFixture.googlePlusUserInactive());
		
		DataTableBase<UserRest> listUser = userService.findAllUsers(userDataTableCriterias());

		assertEquals(5, listUser.getData().size());	// the application creates a new admin user for default, if this table is empty

	}
	
	@Test
	public void testThatInsertWorks() {

		UserRest userRest = new UserRest();
		User user = PersistenceFixture.facebookUserActive();

		// Copy the User attributes to UserRest attributes
		BeanUtils.copyProperties(user, userRest);
		
		userService.save(userRest, false);

		UserRest savedUser = userService.findByName("Facebook User Active");

		assertNotNull(savedUser);

		assertEquals(savedUser.getName(), "Facebook User Active");

	}
	
	@Test
	public void thatSaveUserRolesWorks() {
		// create 2 new roles
		Application application = applicationRepository.save(application1());
		Role roleAdmin = roleRepository.save(roleAdmin(application));
		Role roleUser = roleRepository.save(roleUser(application));
		
		// we 1st insert 2 roles in the user, to check if service correctly remove the old ones first
		User user1 = userWithRoles(internalUser1(), roleAdmin, roleUser);
		User savedUser = userRepository.save(user1);
		
		// create the UserRest with the roles above
		UserRest userRest = new UserRest();
		BeanUtils.copyProperties(savedUser, userRest);
		Map<Integer, Boolean> simpleRoleMap = new Hashtable<Integer, Boolean>();
		simpleRoleMap.put(roleAdmin.getRoleId(), true);
		simpleRoleMap.put(roleUser.getRoleId(), true);
		userRest.setSimpleUserRoles(simpleRoleMap);
		userService.save(userRest, true);
		
		User foundUser = userRepository.findByUserId(savedUser.getUserId());
		
		assertNotNull(foundUser);
		assertThat(foundUser.getUserRoles(), hasSize(2));
		assertNotNull(foundUser.getUserRoles().get(0).getRole());
		assertEquals("USER", foundUser.getUserRoles().get(0).getRole().getRoleName());
		assertEquals("ADMIN", foundUser.getUserRoles().get(1).getRole().getRoleName());
	}


}
