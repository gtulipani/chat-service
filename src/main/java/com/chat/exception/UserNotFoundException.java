package com.chat.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(Long id) {
		super(String.format("Couldn't find user with id=%s", id));
	}
}
