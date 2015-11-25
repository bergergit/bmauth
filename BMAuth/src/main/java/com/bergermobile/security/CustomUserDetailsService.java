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

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.domain.UserRole;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.persistence.repository.UserRoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	static Log LOG = LogFactory.getLog(CustomUserDetailsService.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("Trying BMAuth login with username " +  username);
		
		String appName = request.getParameter("appName");
		User user = userRepository.findByLoginTypeAndUsernameAndApplicationName((Short)User.LoginType.INTERNAL.getValue(), username, appName);
		if(user == null){
			LOG.debug("UserName " + username + " not found");
			throw new UsernameNotFoundException("UserName " + username + " not found");
		}
		LOG.debug("Found the login user " +  user);
		
		// adding the ROLES of this user, that belongs to this appName
		List<String> userRolesStr = new ArrayList<String>();
		//user.getUserRoles().forEach(role -> userRoles.add(role.getRole().getRoleName()));
		List<UserRole> userRoles = userRoleRepository.findByRoleApplicationApplicationName(appName);
		if (userRoles != null) {
			userRoleRepository.findByRoleApplicationApplicationName(appName).forEach(role -> userRolesStr.add(role.getRole().getRoleName()));
		}
		
		return new SecurityUser(user.getUserId(), user.getUsername(), user.getPassword(), user.getActive(), userRolesStr);
	}
}