package com.chat.service;

import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;

public interface MessageService {
	MessageCreationResponse createMessage(MessageCreationRequest messageCreationRequest);
}
