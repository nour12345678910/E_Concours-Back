package com.concours.services;

import java.util.List;

import com.concours.Model.User;


public interface UserService {
	
	public List<User> findAll();
	User createUser(User u);
	User get(Long id);
	boolean exist(User u);
	User updateUser(User u);

	

	
}

