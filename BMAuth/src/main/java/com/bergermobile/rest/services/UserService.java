package com.bergermobile.rest.services;

import java.util.List;

import com.bergermobile.rest.controller.RestToken;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.UserRest;

import javassist.NotFoundException;

public interface UserService {

	/**
	 * It returns all users
	 */
	public List<UserRest> findAllUsers();

	public UserRest findByUserId(int userId);
	
	public UserRest findByEmail(String email);
	
	public UserRest findByName(String name);
	
	public void save(UserRest userRest, boolean saveRoles);
	
	public void saveFacebook(FacebookRest userRest);
	public void saveGoogle(GoogleRest googleRest);

	public void delete(int userId);
	
	public String generateUserToken(UserRest userRest) throws NotFoundException;
	
	public boolean validateUserToken(Integer userId, String token);

}
