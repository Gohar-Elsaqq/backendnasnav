package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;

@Service
public interface UserService {

	User fetchUserByEmailAndPassword(String email,String password);
	
	User saveuser(User user);

	 User fetchUserByEmail(String email);
	
	
}
