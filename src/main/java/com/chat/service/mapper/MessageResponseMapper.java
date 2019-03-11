package com.chat.service.mapper;

import static com.chat.entity.utils.DateUtils.toTimestamp;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageResponse;
import com.chat.entity.model.message.content.MessageContentFactory;
import com.chat.exception.CustomerMapperException;
import com.chat.exception.MessageNotFoundException;
import com.chat.repository.MessageRepository;
import ma.glasnost.orika.MappingContext;

@Slf4j
@Component
public class MessageResponseMapper extends CustomMapperStrategy<MessageResponse, Message> {
	private final MessageRepository messageRepository;
	private final MessageContentFactory messageContentFactory;

	@Autowired
	public MessageResponseMapper(MessageRepository messageRepository, MessageContentFactory messageContentFactory) {
		this.messageRepository = messageRepository;
		this.messageContentFactory = messageContentFactory;
	}

	@Override
	public void mapAtoB(MessageResponse messageResponse, Message message, MappingContext context) {
		super.mapAtoB(messageResponse, message, context);

		Message storedMessage = getMessage(messageResponse.getId());

		message.setId(storedMessage.getId());
		message.setCreatedOn(storedMessage.getCreatedOn());
		message.setLastModified(storedMessage.getLastModified());
		message.setSender(storedMessage.getSender());
		message.setRecipient(storedMessage.getRecipient());
		message.setMessageContent(storedMessage.getMessageContent());
	}

	@Override
	public void mapBtoA(Message message, MessageResponse messageResponse, MappingContext context) {
		super.mapBtoA(message, messageResponse, context);

		Map<String, String> map;
		try {
			map = messageContentFactory.getMessageContentAsMap(message.getMessageContent());
		} catch(IllegalArgumentException e) {
			String error = String.format("Error while trying to obtain content as map from message=%s, error=%s", message, e.getMessage());
			log.error(error);
			throw new CustomerMapperException(error);
		}

		messageResponse.setId(message.getId());
		messageResponse.setTimestamp(toTimestamp(message.getCreatedOn()));
		messageResponse.setSender(message.getSender().getId());
		messageResponse.setRecipient(message.getRecipient().getId());
		messageResponse.setContent(map);
	}

	@Override
	public boolean supports(Class c) {
		return (c == MessageResponse.class);
	}

	/**
	 * Fetches a message from the repository and returns it, or throws an exception if can't be found
	 */
	private Message getMessage(Long id) {
		return messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
	}
}
