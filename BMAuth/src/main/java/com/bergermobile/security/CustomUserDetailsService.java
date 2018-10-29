package com.bergermobile.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bergermobile.commons.security.SecurityUser;
import com.bergermobile.persistence.domain.User;
import com.bergermobile.rest.services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	static Log LOG = LogFactory.getLog(CustomUserDetailsService.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserService userService;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("Trying BMAuth login with username " +  username);
		
		String realm = request.getParameter("realm");
		String appName = request.getParameter("appName");
		//User user = userRepository.findByLoginTypeAndUsernameAndRealm((Short)User.LoginType.INTERNAL.getValue(), username, realm);
		User user = userService.findByUsernameAndRealm(username, realm);
		if(user == null){
			LOG.debug("UserName " + username + " not found");
			throw new UsernameNotFoundException("UserName " + username + " not found");
		}
		LOG.debug("Found the login user " +  user);
		
		List<String> userRolesStr = new ArrayList<String>();
		
		// adding the ROLES of this user, that belongs to this realm, ONLY if he has already accepted the contract
		if (userService.hasSignedLatestContract(user, appName)) {
			user.getUserRoles().forEach(userRole -> {
				if (userRole.getRole().getApplication().getRealm().equals(realm)) {
					userRolesStr.add(userRole.getRole().getRoleName());
				}
			});
		} else {
			// if contract required, CONTRACT is the only ROLE this user will get
			userRolesStr.add("CONTRACT");
		}
		
		return new SecurityUser(user.getUserId(), user.getUsername(), user.getPassword(), user.getActive(), userRolesStr);
	}
}