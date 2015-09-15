package com.bergermobile.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bergermobile.persistence.domain.User;
import com.bergermobile.persistence.repository.UserRepository;
import com.bergermobile.rest.domain.UserRest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserRest> findAllUsers() {

		List<UserRest> userRestList = new ArrayList<UserRest>();

		for (User user : userRepository.findAll()) {

			UserRest userRest = new UserRest();

			BeanUtils.copyProperties(user, userRest);

			userRestList.add(userRest);

		}

		return userRestList;

	}

	@Override
	public void save(UserRest userRest) {

		User user = new User();

		BeanUtils.copyProperties(userRest, user);
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			user.setUsername(user.getEmail());
		}
		
		user.setActive(true);
		userRepository.save(user);

	}

	@Override
	public void delete(int userId) {

		userRepository.delete(userId);

	}

	@Override
	public UserRest findByUserId(int userId) {

		User user = userRepository.findByUserId(userId);

		UserRest userRest = new UserRest();

		if (user != null) {

			BeanUtils.copyProperties(user, userRest);

			return userRest;
		}

		return null;

	}

	@Override
	public UserRest findByName(String name) {

		User user = userRepository.findByName(name);

		UserRest userRest = new UserRest();

		if (user != null) {

			BeanUtils.copyProperties(user, userRest);

			return userRest;
		}

		return null;


	}

}
