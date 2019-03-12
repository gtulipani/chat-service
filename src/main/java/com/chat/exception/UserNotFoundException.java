package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final String USER_NOT_FOUND_WITH_ID_ERROR = "Couldn't find user with id=%s";
	private static final String USER_NOT_FOUND_WITH_USERNAME_ERROR = "Couldn't find user with username=%s";

	public UserNotFoundException(Long id) {
		super(String.format(USER_NOT_FOUND_WITH_ID_ERROR, id));
	}

	public UserNotFoundException(String username) {
		super(String.format(USER_NOT_FOUND_WITH_USERNAME_ERROR, username));
	}
}
