package com.bergermobile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security config
 * 
 * @author fabioberger
 *
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.httpBasic()
			.and()
		.authorizeRequests()
          .antMatchers("/","/login/**","/fonts/**","/help","/webjars/**","/messageBundle/**","/fragments/**","/rest/**").permitAll()
          .antMatchers(HttpMethod.POST, "/bmauth/**").hasAnyRole("ADMIN")
          .anyRequest().authenticated();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
    }

}
