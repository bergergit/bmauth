package com.bergermobile.rest.services;

import java.util.List;

import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.UserRest;

public interface UserService {

	/**
	 * It returns all users
	 */
	public List<UserRest> findAllUsers();

	public UserRest findByUserId(int userId);
	
	public UserRest findByName(String name);
	
	public void save(UserRest userRest);
	
	public void saveFacebook(FacebookRest userRest);
	public void saveGoogle(GoogleRest googleRest);

	public void delete(int userId);

}
