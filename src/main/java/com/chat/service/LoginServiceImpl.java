package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.entity.model.Token;
import com.chat.entity.model.User;

@Service
public class LoginServiceImpl implements LoginService {
	private final UserService userService;

	@Autowired
	public LoginServiceImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Token createToken(User user) {
		return null;
	}
}
