package com.chat.service;

import static com.chat.mother.MessageMother.aCompleteImageMessage;
import static com.chat.mother.MessageMother.aCompleteImageMessageResponse;
import static com.chat.mother.MessageMother.aCompleteImageMessageWithoutId;
import static com.chat.mother.MessageMother.aCompleteTextMessage;
import static com.chat.mother.MessageMother.aCompleteTextMessageResponse;
import static com.chat.mother.MessageMother.aCompleteTextMessageWithoutId;
import static com.chat.mother.MessageMother.aCompleteVideoMessage;
import static com.chat.mother.MessageMother.aCompleteVideoMessageResponse;
import static com.chat.mother.MessageMother.aCompleteVideoMessageWithoutId;
import static com.chat.mother.MessageMother.aImageMessageCreationRequest;
import static com.chat.mother.MessageMother.aMessageCreationResponseEntity;
import static com.chat.mother.MessageMother.aMessagesResponseWithMultipleMessages;
import static com.chat.mother.MessageMother.aMessagesResponseWithOneTextMessage;
import static com.chat.mother.MessageMother.aTextMessageCreationRequest;
import static com.chat.mother.MessageMother.aVideoMessageCreationRequest;
import static com.chat.mother.UserMother.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.User;
import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessageResponse;
import com.chat.entity.model.message.MessagesResponse;
import com.chat.exception.UserNotFoundException;
import com.chat.repository.MessageRepository;
import com.chat.service.mapper.MessageMapper;

public class MessageServiceImplTest {
	private MessageServiceImpl messageService;

	@Mock
	private MessageRepository messageRepository;
	@Mock
	private MessageMapper messageMapper;
	@Mock
	private UserService userService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageService = new MessageServiceImpl(messageRepository, messageMapper, userService);
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

	@Test
	public void testGetMessages_oneMessage() {
		Message message = aCompleteTextMessage();
		MessageResponse messageResponse = aCompleteTextMessageResponse();
		MessagesResponse messagesResponse = aMessagesResponseWithOneTextMessage();
		List<Message> messageList = Collections.singletonList(message);
		User recipientUser = aUser();
		Long recipient = recipientUser.getId();
		Long start = message.getId();
		Long limit = 100L;
		when(messageRepository.findFirstByRecipientAndIdLessThanEqualOrderByIdDesc(recipientUser, start, PageRequest.of(0, 1)))
				.thenAnswer(invocationOnMock -> new PageImpl<>(messageList, new PageRequest(0, 1), messageList.size()));
		when(messageRepository.findAllByRecipientAndIdGreaterThanEqualOrderByIdAsc(recipientUser, start, PageRequest.of(0, Math.toIntExact(limit))))
				.thenAnswer(invocationOnMock -> new PageImpl<>(messageList, new PageRequest(0, 1), messageList.size()));
		when(messageMapper.map(message, MessageResponse.class)).thenReturn(messageResponse);
		when(userService.getUserById(recipient)).thenReturn(recipientUser);

		assertThat(messageService.getMessages(recipient, start, limit)).isEqualTo(messagesResponse);
	}

	@Test
	public void testGetMessages_multipleMessages() {
		Message textMessage = aCompleteTextMessage();
		Message imageMessage = aCompleteImageMessage();
		Message videoMessage = aCompleteVideoMessage();
		MessageResponse textMessageResponse = aCompleteTextMessageResponse();
		MessageResponse imageMessageResponse = aCompleteImageMessageResponse();
		MessageResponse videoMessageResponse = aCompleteVideoMessageResponse();
		MessagesResponse messagesResponse = aMessagesResponseWithMultipleMessages();
		List<Message> messageList = Arrays.asList(textMessage, imageMessage, videoMessage);
		User recipientUser = aUser();
		Long recipient = recipientUser.getId();
		Long start = textMessage.getId();
		Long limit = 100L;
		when(messageRepository.findFirstByRecipientAndIdLessThanEqualOrderByIdDesc(recipientUser, start, PageRequest.of(0, 1)))
				.thenAnswer(invocationOnMock -> new PageImpl<>(Collections.singletonList(textMessage), new PageRequest(0, 1), 1));
		when(messageRepository.findAllByRecipientAndIdGreaterThanEqualOrderByIdAsc(recipientUser, start, PageRequest.of(0, Math.toIntExact(limit))))
				.thenAnswer(invocationOnMock -> new PageImpl<>(messageList, new PageRequest(0, messageList.size()), messageList.size()));
		when(messageMapper.map(textMessage, MessageResponse.class)).thenReturn(textMessageResponse);
		when(messageMapper.map(imageMessage, MessageResponse.class)).thenReturn(imageMessageResponse);
		when(messageMapper.map(videoMessage, MessageResponse.class)).thenReturn(videoMessageResponse);
		when(userService.getUserById(recipient)).thenReturn(recipientUser);

		assertThat(messageService.getMessages(recipient, start, limit)).isEqualTo(messagesResponse);
	}

	@Test
	public void testGetMessages_recipientNotFound_throwsUserNotFoundException() {
		User recipientUser = aUser();
		Long recipient = recipientUser.getId();
		Long start = aCompleteTextMessage().getId();
		Long limit = 100L;
		doThrow(new UserNotFoundException(recipient)).when(userService).getUserById(recipient);

		assertThatExceptionOfType(UserNotFoundException.class)
				.isThrownBy(() -> messageService.getMessages(recipient, start, limit))
				.withMessage("Couldn't find user with id=%s", recipient);
	}
}
