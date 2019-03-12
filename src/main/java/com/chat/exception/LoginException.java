package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class LoginException extends RuntimeException {
	private static final String LOGIN_EXCEPTION_ERROR = "Couldn't authenticate user with username=%s";
	
	public LoginException(String username) {
		super(String.format(LOGIN_EXCEPTION_ERROR, username));
	}
}
