package com.bergermobile;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import com.bergermobile.persistence.domain.Application;
import com.bergermobile.persistence.domain.Role;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.persistence.repository.ApplicationRepository;
import com.bergermobile.persistence.repository.RoleRepository;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.services.SerializableResourceBundleMessageSource;

@SpringBootApplication
//@EnableRedisHttpSession
@EnableAsync
public class BmAuthApplication {

	static Log LOG = LogFactory.getLog(BmAuthApplication.class);
	
	@Autowired
    private Environment environment;
	

	public static void main(String[] args) {
		SpringApplication.run(BmAuthApplication.class, args);
	}
	
	/*
	@Bean
	public HeaderHttpSessionStrategy sessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}
	*/
	
	/**
	 * @see <a href="http://docs.spring.io/spring-session/docs/current/reference/html5/#api-redisoperationssessionrepository-sessiondestroyedevent>http://docs.spring.io/spring-session/docs/current/reference/html5/#api-redisoperationssessionrepository-sessiondestroyedevent</a>
	 * @return
	 */
	@Bean
	public static ConfigureRedisAction configureRedisAction() {
	    return ConfigureRedisAction.NO_OP;
	}
	
	@Bean
	public BCryptPasswordEncoder bcryptEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Configuration
	protected static class Configurator {

		static Log LOG = LogFactory.getLog(Configurator.class);

		@Autowired
		ApplicationRepository applicationRepository;
		
		@Autowired
		UserRepository userRepository;

		@Autowired
		RoleRepository roleRepository;

		@Autowired
		Environment environment;

		/**
		 * Inserts the default admin username/password if there is no ADMIN user
		 */
		@PostConstruct
		public void init() {
			if (!userRepository.findAll().iterator().hasNext()) {
				
				LOG.debug("Inserting default user \"admin\" with password: "
						+ environment.getProperty("security.user.password"));
				userRepository.save(defaultAdminUser());
								
			}
		}
		
		/**
		 * Creates a default admin user, with a default admin role
		 * 
		 * @return
		 */
		private User defaultAdminUser() {
			
			Application app = new Application();
			app.setApplicationName("BMAuth");
			app.setActive(true);
			app.setMandatoryContract(false);
			app.setTestMode(false);
			app.setUrl(environment.getProperty("bmauth.url.init"));
			app.setRealm("bmauth");
			app = applicationRepository.save(app);
			
			User user = new User();
			user.setActive(true);
			user.setUsername("admin");
			user.setName("Admin");
			user.setPassword(environment.getProperty("security.user.password"));
			user.setUserType((short) User.UserType.CPF.getValue());
			user.setLoginType((short) User.LoginType.INTERNAL.getValue());

			Role role = new Role();
			role.setRoleName("BMAUTH-ADMIN");
			role.setApplication(app);
			role = roleRepository.save(role);

			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);

			List<UserRole> userRoleList = Arrays.asList(userRole);
			user.setUserRoles(userRoleList);
			role.setUserRoles(userRoleList);

			return user;
		}
	}
	
    @Bean
    public SerializableResourceBundleMessageSource messageBundle() {
    	SerializableResourceBundleMessageSource messageSource = new SerializableResourceBundleMessageSource();
    	messageSource.setDefaultEncoding("UTF-8");
    	messageSource.setBasename("classpath:/messages");
    	return messageSource;
    }
    
    @Bean
    public ResourceBundleMessageSource messageSource() {
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    	messageSource.setBasename("messages");
    	messageSource.setDefaultEncoding("UTF-8");
    	return messageSource;
    }

}
