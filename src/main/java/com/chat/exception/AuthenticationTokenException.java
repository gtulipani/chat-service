package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationTokenException extends RuntimeException {
	private static final String INVALID_TOKEN_MESSAGE = "Invalid token";

	public AuthenticationTokenException(String message) {
		super(message);
	}

	public AuthenticationTokenException() {
		this(INVALID_TOKEN_MESSAGE);
	}
}
