package com.chat.service.mapper;

import static com.chat.entity.utils.DateUtils.toTimestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.exception.MessageNotFoundException;
import com.chat.repository.MessageRepository;
import ma.glasnost.orika.MappingContext;

@Component
public class MessageCreationResponseMapper extends CustomMapperStrategy<MessageCreationResponse, Message> {
	private final MessageRepository messageRepository;

	@Autowired
	public MessageCreationResponseMapper(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public void mapAtoB(MessageCreationResponse messageCreationResponse, Message message, MappingContext context) {
		super.mapAtoB(messageCreationResponse, message, context);

		Message storedMessage = getMessage(messageCreationResponse.getId());

		message.setId(storedMessage.getId());
		message.setCreatedOn(storedMessage.getCreatedOn());
		message.setLastModified(storedMessage.getLastModified());
		message.setSender(storedMessage.getSender());
		message.setRecipient(storedMessage.getRecipient());
		message.setMessageContent(storedMessage.getMessageContent());
	}

	@Override
	public void mapBtoA(Message message, MessageCreationResponse messageCreationResponse, MappingContext context) {
		super.mapBtoA(message, messageCreationResponse, context);

		messageCreationResponse.setId(message.getId());
		messageCreationResponse.setTimestamp(toTimestamp(message.getCreatedOn()));
	}

	@Override
	public boolean supports(Class c) {
		return (c == MessageCreationResponse.class);
	}

	/**
	 * Fetches a message from the repository and returns it, or throws an exception if can't be found
	 */
	private Message getMessage(Long id) {
		return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
	}
}
