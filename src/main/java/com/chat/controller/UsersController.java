package com.chat.controller;

import java.util.concurrent.Callable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat.entity.User;

public interface UsersController {
	Callable<ResponseEntity> createUser(@RequestBody User user);
}
