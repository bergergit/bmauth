package com.bergermobile.rest.services;

import java.util.List;

import com.bergermobile.rest.domain.UserRest;

public interface UserService {

	/**
	 * It returns all users
	 */
	public List<UserRest> findAllUsers();
	
	public UserRest findByUserId(int userId);
	
	public void save(UserRest userRest);
	
	public void delete(int userId);
	
	
	
}
