package com.bergermobile.rest.functional;

import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.internalUser1;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.internalUser2;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.bergermobile.BmAuthApplication;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.controller.UserQueryController;
import com.bergermobile.rest.domain.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("dev")
public class UserControllerFunctionalTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	ObjectMapper mapper;

	MockMvc mockMvc;

	@InjectMocks
	UserQueryController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}


	
	@Test
	public void thatAdminJsonPostWillSaveUserAndRoles() throws Exception {
		
	}
}
