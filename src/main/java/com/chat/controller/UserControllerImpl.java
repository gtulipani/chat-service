package com.chat.controller;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.User;
import com.chat.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {
	private UserService userService;

	@Autowired
	public UserControllerImpl(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Callable<ResponseEntity> createUser(@RequestBody User user) {
		return () -> {
			log.info("Received API call to create user with username={}", user.getUsername());
			return ResponseEntity.ok(userService.createUser(user));
		};
	}
}
