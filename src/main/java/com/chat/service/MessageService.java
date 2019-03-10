package com.chat.service;

import com.chat.entity.model.MessageCreationRequest;
import com.chat.entity.model.MessageCreationResponseEntity;

public interface MessageService {
	MessageCreationResponseEntity createMessage(MessageCreationRequest messageCreationRequest);
}
