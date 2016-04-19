package com.bergermobile.rest.services;

import java.util.List;

import javassist.NotFoundException;

import com.bergermobile.commons.rest.DataTableBase;
import com.bergermobile.commons.rest.DataTableCriterias;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.UserRest;

public interface UserService {

	/**
	 * It returns all users
	 */
	public DataTableBase<UserRest> findAllUsers(DataTableCriterias criterias);

	public UserRest findByUserId(int userId);
	
	public UserRest findByEmail(String email);

	public UserRest findByEmailAndApplicationId(String email, Integer applicationId);
	
	public List<UserRest> findByApplicationName(String appName);
	
	public UserRest findByUserIdAndApplicationId(Integer userId, Integer applicationId);

	public UserRest findByName(String name);
	
	public void save(UserRest userRest, boolean saveRoles);
	
	public void saveFacebook(FacebookRest userRest);
	public void saveGoogle(GoogleRest googleRest);

	public void delete(int userId);
	
	public String generateUserToken(UserRest userRest) throws NotFoundException;
	
	public boolean validateUserToken(Integer userId, String token);
	
	public String generateBodyMailForgotMyPassword(UserRest userRest, ApplicationRest applicationRest, String link);
	
}
