package com.sekko.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User "+ username +" not found");
		return user;
	}
	
	public UserService() {
		super();
	}
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User getUserById(long id) {
		return userRepository.findOne(id);
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUserById(long id) {
		userRepository.delete(id);
	}
	
}
