package com.bergermobile.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;


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
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/fonts/**", "/webjars/**", "/messageBundle/**",
					"/fragments/**", "/signup", "/bmauth/login")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/bmauth/users","/bmauth/users/facebook","/bmauth/users/google")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/bmauth/**")
				.hasRole("BMAUTH-ADMIN")
			.antMatchers(HttpMethod.GET, "/bmauth/applications/**","/bmauth/users/**", "/applications/**", "/users/**")
				.hasRole("BMAUTH-ADMIN") 
			.anyRequest()
				.authenticated()
//			.and()
//				.formLogin()
//				.loginPage("/")
//				.permitAll()
			.and()
				.httpBasic()
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
	
}
