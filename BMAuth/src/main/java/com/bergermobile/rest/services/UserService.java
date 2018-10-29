package com.bergermobile.rest.services;

import java.util.List;

import javassist.NotFoundException;

import com.bergermobile.commons.rest.DataTableBase;
import com.bergermobile.commons.rest.DataTableCriterias;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.LanguageContractRest;
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
	
	public User save(UserRest userRest, boolean saveRoles);
	
	public User saveFacebook(FacebookRest userRest);
	public User saveGoogle(GoogleRest googleRest);

	public void delete(int userId);
	
	public String generateUserToken(UserRest userRest) throws NotFoundException;
	
	public boolean validateUserToken(Integer userId, String token);
	
	public String generateBodyMailForgotMyPassword(UserRest userRest, ApplicationRest applicationRest, String link);

	public User findByUsernameAndRealm(String username, String realm);
	
	public boolean hasSignedLatestContract(User user, String appName);

	public void signContract(String appName);

	public LanguageContractRest getLatestContract(String appName);
	
}
