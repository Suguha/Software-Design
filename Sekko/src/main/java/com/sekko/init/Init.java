package com.sekko.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sekko.user.User;
import com.sekko.user.UserRepository;

@Component
public class Init {
	@Autowired
	UserRepository userRepository;
	
	@PostConstruct
	public void initUserData() {
		/*
		User admin = new User();
		admin.setPassword("123456");
		admin.setUsername("Matthew");
		admin.setRole(User.ROLE.admin);
		userRepository.save(admin);
		
		User user = new User();
		user.setPassword("123456");
		user.setRole(User.ROLE.user);
		user.setUsername("Amy");
		userRepository.save(user);
		*/
	}
}
