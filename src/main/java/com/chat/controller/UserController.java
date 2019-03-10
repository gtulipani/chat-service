package com.chat.controller;

import java.util.concurrent.Callable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat.entity.model.User;

public interface UserController {
	Callable<ResponseEntity> createUser(@RequestBody User user);
}
