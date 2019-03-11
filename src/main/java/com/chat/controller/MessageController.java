package com.chat.controller;

import java.util.concurrent.Callable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.entity.model.message.MessageCreationRequest;

public interface MessageController {
	Callable<ResponseEntity> createMessage(@RequestBody MessageCreationRequest messageCreationRequest);

	Callable<ResponseEntity> getMessages(@RequestParam(value = "recipient") Integer recipient,
										 @RequestParam(value = "start") Integer start,
										 @RequestParam(value = "limit", defaultValue = "100") Integer limit);
}
