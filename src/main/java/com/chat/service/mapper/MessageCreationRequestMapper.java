package com.chat.service.mapper;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageContent;
import com.chat.entity.model.message.MessageContentFactory;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.User;
import com.chat.exception.CustomerMapperException;
import com.chat.exception.UserNotFoundException;
import com.chat.repository.UserRepository;
import ma.glasnost.orika.MappingContext;

@Slf4j
@Component
public class MessageCreationRequestMapper extends CustomMapperStrategy<MessageCreationRequest, Message> {
	private final UserRepository userRepository;
	private final MessageContentFactory messageContentFactory;

	@Autowired
	public MessageCreationRequestMapper(UserRepository userRepository, MessageContentFactory messageContentFactory) {
		this.userRepository = userRepository;
		this.messageContentFactory = messageContentFactory;
	}

	@Override
	public void mapAtoB(MessageCreationRequest messageCreationRequest, Message message, MappingContext context) {
		super.mapAtoB(messageCreationRequest, message, context);

		MessageContent messageContent;
		try {
			messageContent = messageContentFactory.getMessageContent(messageCreationRequest.getContent());
		} catch(IllegalArgumentException e) {
			String error = String.format("Error while trying to obtain content from messageCreationRequest=%s, error=%s", messageCreationRequest, e.getMessage());
			log.error(error);
			throw new CustomerMapperException(error);
		}

		message.setCreatedOn(LocalDateTime.now());
		message.setLastModified(LocalDateTime.now());
		message.setSender(getUser(messageCreationRequest.getSender()));
		message.setRecipient(getUser(messageCreationRequest.getRecipient()));
		message.setMessageContent(messageContent);
	}

	@Override
	public void mapBtoA(Message message, MessageCreationRequest messageCreationRequest, MappingContext context) {
		super.mapBtoA(message, messageCreationRequest, context);

		messageCreationRequest.setSender(message.getSender().getId());
		messageCreationRequest.setRecipient(message.getRecipient().getId());
	}

	@Override
	public boolean supports(Class c) {
		return (c == MessageCreationRequest.class);
	}

	/**
	 * Fetches a user from the repository and returns it, or throws an exception if can't be found
	 */
	private User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
}
