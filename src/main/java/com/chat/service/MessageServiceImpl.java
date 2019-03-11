package com.chat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessageResponse;
import com.chat.entity.model.message.MessagesResponse;
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

	@Override
	@Transactional
	public MessagesResponse getMessages(Long recipient, Long start, Long limit) {
		Long messageIdStart = messageRepository.findFirstMessageForRecipientAndIdLowerThan(recipient, start)
				.map(Message::getId)
				.orElse(start);

		MessagesResponse messagesResponse = MessagesResponse.builder()
				.messages(messageRepository.findMessagesForRecipientAndIdLowerThanWithLimit(recipient, messageIdStart, limit)
						.stream()
						.map(message -> messageMapper.map(message, MessageResponse.class))
						.collect(Collectors.toList()))
				.build();

		log.info("Successfully fetched messages for recipient={}, start={}, limit={}", recipient, start, limit);
		return messagesResponse;
	}
}
