package com.sekko.user;

import javax.validation.Valid;
import javax.websocket.server.PathParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	SessionService sessionService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority({'admin', 'user'})")
	public User getUserById(@PathVariable("id") long id) {
		return userService.getUserById(id);
	}
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public Iterable<User> getAllUsers() {
		return userService.listUsers();
	}
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@PreAuthorize("permitAll()")
	public User createUser(@Valid @RequestBody User user) {
		return userService.createUser(user);
	}
	
}
