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

import com.chat.entity.model.User;
import com.chat.service.LoginService;

@Slf4j
@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginControllerImpl implements LoginController {
	private LoginService loginService;

	@Autowired
	public LoginControllerImpl(LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public Callable<ResponseEntity> login(@RequestBody User user) {
		return () -> {
			log.info("Received API call to login user with username={}", user.getUsername());
			return ResponseEntity.ok(loginService.createToken(user));
		};
	}
}
