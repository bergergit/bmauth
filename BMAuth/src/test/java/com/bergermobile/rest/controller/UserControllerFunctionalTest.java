package com.bergermobile.rest.controller;

import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.internalUser1;
import static com.bergermobile.persistence.domain.fixture.PersistenceFixture.internalUser2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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
	private UserRepository userRepository;

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
	public void userQueryControllerReturnJsonCorrectly() throws Exception {

		userRepository.save(internalUser1());
		userRepository.save(internalUser2());

		MvcResult result = mockMvc.perform(get("/bmauth/users")).andDo(print()).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		assertTrue(content.contains("Fabio Berger"));
		assertTrue(content.contains("Fabio Filz"));

	}

	@Test
	public void jsonPostCreatesUserCorrectly() throws Exception {
		UserRest userRest = new UserRest();
		userRest.setName("Fabio Berger");
		userRest.setEmail("fabioberger@gmail.com");
		userRest.setUsername("fabioberger@gmail.com");
		userRest.setLoginType(UserRest.LoginType.INTERNAL.getValue());

		mockMvc.perform(post("/bmauth/users").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRest))).andExpect(status().isCreated());

		User user1 = userRepository.findOne(1);

		assertThat(user1.getUsername(), is("fabioberger@gmail.com"));

	}
}
