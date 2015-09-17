package com.bergermobile.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails
{

	private static final long serialVersionUID = 1L;
	private String username, password;
	private boolean active;
	private List<String> roles;
	
	/*
	public SecurityUser(User user) {
		if(user != null) {
			BeanUtils.copyProperties(user, this);
		}		
	}
	*/
	
	public SecurityUser(String username, String password, boolean active, List<String> roles) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if(roles != null) {
			for (String role : roles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
				authorities.add(authority);
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		//return super.getEmail();
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}	
	
	
}