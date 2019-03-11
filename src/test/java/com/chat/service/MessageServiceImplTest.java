package com.chat.service;

import static com.chat.mother.MessageMother.aCompleteImageMessage;
import static com.chat.mother.MessageMother.aCompleteImageMessageWithoutId;
import static com.chat.mother.MessageMother.aCompleteVideoMessage;
import static com.chat.mother.MessageMother.aCompleteVideoMessageWithoutId;
import static com.chat.mother.MessageMother.aImageMessageCreationRequest;
import static com.chat.mother.MessageMother.aMessageCreationResponseEntity;
import static com.chat.mother.MessageMother.aCompleteTextMessage;
import static com.chat.mother.MessageMother.aTextMessageCreationRequest;
import static com.chat.mother.MessageMother.aCompleteTextMessageWithoutId;
import static com.chat.mother.MessageMother.aVideoMessageCreationRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.repository.MessageRepository;
import com.chat.service.mapper.MessageMapper;

public class MessageServiceImplTest {
	private MessageServiceImpl messageService;

	@Mock
	private MessageRepository messageRepository;
	@Mock
	private MessageMapper messageMapper;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageService = new MessageServiceImpl(messageRepository, messageMapper);
	}

	@Test
	public void testCreateMessageText() {
		MessageCreationRequest messageCreationRequest = aTextMessageCreationRequest();
		Message message = aCompleteTextMessageWithoutId();
		Message messageWithId = aCompleteTextMessage();
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		when(messageMapper.map(messageCreationRequest, Message.class)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(messageWithId);
		when(messageMapper.map(messageWithId, MessageCreationResponse.class)).thenReturn(messageCreationResponse);

		MessageCreationResponse response = messageService.createMessage(messageCreationRequest);

		assertThat(response).isEqualTo(messageCreationResponse);
	}

	@Test
	public void testCreateMessageImage() {
		MessageCreationRequest messageCreationRequest = aImageMessageCreationRequest();
		Message message = aCompleteImageMessageWithoutId();
		Message messageWithId = aCompleteImageMessage();
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		when(messageMapper.map(messageCreationRequest, Message.class)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(messageWithId);
		when(messageMapper.map(messageWithId, MessageCreationResponse.class)).thenReturn(messageCreationResponse);

		MessageCreationResponse response = messageService.createMessage(messageCreationRequest);

		assertThat(response).isEqualTo(messageCreationResponse);
	}

	@Test
	public void testCreateMessageVideo() {
		MessageCreationRequest messageCreationRequest = aVideoMessageCreationRequest();
		Message message = aCompleteVideoMessageWithoutId();
		Message messageWithId = aCompleteVideoMessage();
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		when(messageMapper.map(messageCreationRequest, Message.class)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(messageWithId);
		when(messageMapper.map(messageWithId, MessageCreationResponse.class)).thenReturn(messageCreationResponse);

		MessageCreationResponse response = messageService.createMessage(messageCreationRequest);

		assertThat(response).isEqualTo(messageCreationResponse);
	}
}
