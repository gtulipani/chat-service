package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationTokenException extends RuntimeException {
public AuthenticationTokenException(String message) {
		super(message);
	}
}
