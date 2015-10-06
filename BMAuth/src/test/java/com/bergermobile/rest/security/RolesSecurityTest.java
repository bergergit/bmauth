package com.bergermobile.rest.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.bergermobile.BmAuthApplication;
import com.fasterxml.jackson.databind.ObjectMapper;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { BmAuthApplication.class })
//@ContextConfiguration
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("dev")
public class RolesSecurityTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private FilterChainProxy filterChainProxy;


	@Autowired
	ObjectMapper mapper;

	MockMvc mockMvc;

	/*
	@InjectMocks
	UserQueryController controller;
	*/

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).addFilters(filterChainProxy).build();
	}

	@Test
	public void thatAnonymousUsersCantGetUserScreen() throws Exception {
		mockMvc.perform(
				get("/users/"))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	@Test
	public void thatAdminUsersCanGetUserScreen() throws Exception {
		mockMvc.perform(
				get("/users/").with(user("admin").roles("BMAUTH-ADMIN")))
				.andExpect(status().isOk())
				.andReturn();

	}
	
	@Test
	public void thatAdminUsersCanGetApplicationsScreen() throws Exception {
		mockMvc.perform(
				get("/applications/").with(user("admin").roles("BMAUTH-ADMIN")))
				.andExpect(status().isOk())
				.andReturn();

	}

}
