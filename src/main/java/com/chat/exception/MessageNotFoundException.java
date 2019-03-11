package com.chat.exception;

public class MessageNotFoundException extends RuntimeException {
	public MessageNotFoundException(Long id) {
		super(String.format("Couldn't find message with id=%s", id));
	}
}
