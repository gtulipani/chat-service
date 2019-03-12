package com.chat.service;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.entity.model.User;
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
	private final UserService userService;

	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper, UserService userService) {
		this.messageRepository = messageRepository;
		this.messageMapper = messageMapper;
		this.userService = userService;
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
		User recipientUser = userService.getUserById(recipient);
		Long messageIdStart = messageRepository.findFirstByRecipientAndIdLessThanEqualOrderByIdDesc(recipientUser, start, PageRequest.of(0, 1)).getContent()
				.stream()
				.map(Message::getId)
				.findFirst()
				.orElse(start);

		MessagesResponse messagesResponse = MessagesResponse.builder()
				.messages(messageRepository.findAllByRecipientAndIdGreaterThanEqualOrderByIdAsc(recipientUser, messageIdStart, PageRequest.of(0, Math.toIntExact(limit))).getContent()
						.stream()
						.map(message -> messageMapper.map(message, MessageResponse.class))
						.collect(Collectors.toList()))
				.build();

		log.info("Successfully fetched messages for recipient={}, start={}, limit={}", recipient, start, limit);
		return messagesResponse;
	}
}
