package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@PostMapping(value = "/usersave")
	public User register(@RequestBody User user) throws Exception {
		String emailId = user.getEmail();
		String passwordId = user.getPassword();
		if (emailId != null && !"".equals(emailId)) {
			User userObj = userService.fetchUserByEmail(emailId);
			if (userObj != null) {
				throw new Exception("user whih" + emailId + "is alredy exist");
			}

		}
		if (!(passwordId.length() > 8) && passwordId != null) {
			throw new Exception("plase your password");

		}

		User userObj = null;
		userObj = userService.saveuser(user);
		return userObj;
	}

	@PostMapping(value = "/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public User login(@RequestBody User user) throws Exception {

		String email = user.getEmail();
		String password = user.getPassword();
		User userObj = null;
		if (email != null && password != null) {
			userObj = userService.fetchUserByEmailAndPassword(email, password);
		}
		if (userObj == null) {
			throw new Exception("bad credentials");
		}
		return userObj;
	}

}
