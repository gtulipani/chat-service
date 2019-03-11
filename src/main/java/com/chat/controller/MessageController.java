package com.chat.controller;

import java.util.concurrent.Callable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat.entity.model.message.MessageCreationRequest;

public interface MessageController {
	Callable<ResponseEntity> createMessage(@RequestBody MessageCreationRequest messageCreationRequest);
}
