package com.chat.service;

import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessagesResponse;

public interface MessageService {
	MessageCreationResponse createMessage(MessageCreationRequest messageCreationRequest);

	MessagesResponse getMessages(Long recipient, Long start, Long limit);
}
