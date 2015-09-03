package com.bergermobile.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;

import com.bergermobile.rest.services.CustomUserDetailsService;


/**
 * Security config
 * 
 * @author fabioberger
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable();

		// @formatter:off
		http.httpBasic()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/fonts/**", "/webjars/**", "/messageBundle/**",
					"/fragments/**", "/signup", "/bmauth/login")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/bmauth/users")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/bmauth/**")
				.hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/applications/**","/users/**")
				.hasRole("ADMIN")
			.anyRequest()
				.authenticated()
			.and()
            .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            .csrf().csrfTokenRepository(csrfTokenRepository());
		// @formatter:on
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
		
		auth.userDetailsService(customUserDetailsService);
	}
	
	
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
	
	public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

		private Log LOG = LogFactory.getLog(CustomAuthenticationEntryPoint.class);
	    
		public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException {
	    	LOG.debug("<--- Inside authentication entry point --->");
	        // WWW-Authenticate header should be set as FormBased , else browser will show login dialog with realm
	        response.setHeader("WWW-Authenticate", "FormBased");
	        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
	    }
	}
	
	
}
