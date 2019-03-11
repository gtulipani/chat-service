package com.chat.controller;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.service.MessageService;

@Slf4j
@RestController
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class MessageControllerImpl implements MessageController {
	private MessageService messageService;

	@Autowired
	public MessageControllerImpl(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public Callable<ResponseEntity> createMessage(@RequestBody MessageCreationRequest messageCreationRequest) {
		return () -> {
			log.info("Received API call to create a message with sender={}, recipient={}", messageCreationRequest.getSender(), messageCreationRequest.getRecipient());
			return ResponseEntity.ok(messageService.createMessage(messageCreationRequest));
		};
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public Callable<ResponseEntity> getMessages(@RequestParam(value = "recipient") Long recipient,
										 @RequestParam(value = "start") Long start,
										 @RequestParam(value = "limit", required = false, defaultValue = "100") Long limit) {
		return () -> {
			log.info("Received API call to get all messages from recipient={}, start={}, limit={}", recipient, start, limit);
			return ResponseEntity.ok(messageService.getMessages(recipient, start, limit));
		};
	}
}
