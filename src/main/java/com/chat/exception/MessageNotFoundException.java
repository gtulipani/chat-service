package com.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {
	private static final String MESSAGE_NOT_FOUND_EXCEPTION_ERROR_MESSAGE = "Couldn't find message with id=%s";
	
	public MessageNotFoundException(Long id) {
		super(String.format(MESSAGE_NOT_FOUND_EXCEPTION_ERROR_MESSAGE, id));
	}
}
