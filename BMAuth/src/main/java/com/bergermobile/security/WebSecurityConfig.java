package com.bergermobile.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import com.bergermobile.commons.security.CsrfHeaderFilter;


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
	
	@Autowired
    BCryptPasswordEncoder bcryptEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable();

		// @formatter:off
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/", "/fonts/**", "/webjars/**", "/messageBundle/**",
					"/fragments/**","/bmauth-10/**", "/signup", "/bmauth/login","/bmauth/logout", "/bmauth/token/**", "/reset/**", "/users/signingcontract/**", "/bmauth/user/**")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/logout", "/login","/bmauth/users","/bmauth/users/facebook","/bmauth/users/google", "/bmauth/token/**", "/bmauth/reset/**")
				.permitAll()
			.antMatchers(HttpMethod.POST, "/bmauth/**")
				.hasRole("BMAUTH-ADMIN")
			.antMatchers(HttpMethod.GET, "/bmauth/applications/**","/bmauth/users/**", "/applications/**", "/users/**")
				.hasRole("BMAUTH-ADMIN") 
			.anyRequest()
				.authenticated()
			//.and()
			//	.logout().logoutUrl("/logout")
			.and()
				.httpBasic()
				//.authenticationDetailsSource(customAuthenticationDetailsSource)
			.and()
            	.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
            	.csrf().csrfTokenRepository(CsrfHeaderFilter.csrfTokenRepository());
			
		// @formatter:on
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bcryptEncoder);
		//auth.authenticationProvider(customAuthenticationProvider);
	}
	
}
