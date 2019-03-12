package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends RuntimeException {
	private static final String USER_ALREADY_EXISTS_ERROR = "User with username=%s already exists";
	
	public UserAlreadyExistsException(String username) {
		super(String.format(USER_ALREADY_EXISTS_ERROR, username));
	}
}
