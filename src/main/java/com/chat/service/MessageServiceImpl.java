package com.chat.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.repository.MessageRepository;
import com.chat.service.mapper.MessageMapper;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
	private final MessageRepository messageRepository;
	private final MessageMapper messageMapper;

	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
		this.messageRepository = messageRepository;
		this.messageMapper = messageMapper;
	}

	@Override
	@Transactional
	public MessageCreationResponse createMessage(MessageCreationRequest messageCreationRequest) {
		Message message = messageMapper.map(messageCreationRequest, Message.class);
		MessageCreationResponse messageCreationResponse = messageMapper.map(messageRepository.save(message), MessageCreationResponse.class);
		log.info("Successfully created message with sender={}, recipient={}", messageCreationRequest.getSender(), messageCreationRequest.getRecipient());
		return messageCreationResponse;
	}
}
