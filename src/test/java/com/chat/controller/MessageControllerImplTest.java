package com.chat.controller;

import static com.chat.mother.MessageMother.aMessageCreationRequestWithEmptyContent;
import static com.chat.mother.MessageMother.aMessageCreationResponseEntity;
import static com.chat.mother.MessageMother.aMessagesResponseWithMultipleMessages;
import static com.chat.mother.MessageMother.aMessagesResponseWithOneTextMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessagesResponse;
import com.chat.service.MessageService;

public class MessageControllerImplTest {
	private MessageControllerImpl messageController;

	@Mock
	private MessageService messageService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageController = new MessageControllerImpl(messageService);
	}

	@Test
	public void testCreateMessage() throws Exception {
		MessageCreationRequest messageCreationRequest = aMessageCreationRequestWithEmptyContent();
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		when(messageService.createMessage(messageCreationRequest)).thenReturn(messageCreationResponse);

		ResponseEntity responseEntity = messageController.createMessage(messageCreationRequest).call();

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(messageCreationResponse);
	}

	@Test
	public void testGetMessages_oneMessage() throws Exception {
		Integer recipient = 1;
		Integer start = 1;
		Integer limit = 100;
		MessagesResponse messagesResponse = aMessagesResponseWithOneTextMessage();
		when(messageService.getMessages(recipient, start, limit)).thenReturn(messagesResponse);

		ResponseEntity responseEntity = messageController.getMessages(recipient, start, limit).call();

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(messagesResponse);
	}

	@Test
	public void testGetMessages_multipleMessages() throws Exception {
		Integer recipient = 1;
		Integer start = 1;
		Integer limit = 100;
		MessagesResponse messagesResponse = aMessagesResponseWithMultipleMessages();
		when(messageService.getMessages(recipient, start, limit)).thenReturn(messagesResponse);

		ResponseEntity responseEntity = messageController.getMessages(recipient, start, limit).call();

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(messagesResponse);
	}
}
