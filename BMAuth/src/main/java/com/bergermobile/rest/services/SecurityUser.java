package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;

public class SecurityUser extends User implements UserDetails
{

	private static final long serialVersionUID = 1L;
	
	public SecurityUser(User user) {
		if(user != null) {
			BeanUtils.copyProperties(user, this);
			/*
			this.setUserId(user.getUserId());
			this.setUsername(user.getUsername());
			this.setName(user.getName());
			this.setUserType(user.getUserType());
			this.setEmail(user.getEmail());
			this.setPassword(user.getPassword());
			this.setBirthday(user.getBirthday());
			this.setActive(user.getActive());
			*/
		}		
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<UserRole> userRoles = this.getUserRoles();
		
		if(userRoles != null)
		{
			for (UserRole userRole : userRoles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole().getRoleName());
				authorities.add(authority);
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		//return super.getEmail();
		return super.getUsername();
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
		return this.getActive() == 1 ? true : false;
	}	
	
	
}