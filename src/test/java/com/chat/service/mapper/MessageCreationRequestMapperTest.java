package com.chat.service.mapper;

import static com.chat.mother.MessageContentMother.aTextMessageContent;
import static com.chat.mother.MessageMother.aCompleteTextMessage;
import static com.chat.mother.MessageMother.aTextMessageCreationRequest;
import static com.chat.mother.UserMother.aDifferentUser;
import static com.chat.mother.UserMother.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.User;
import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.content.MessageContent;
import com.chat.entity.model.message.content.MessageContentFactory;
import com.chat.exception.CustomerMapperException;
import com.chat.service.UserService;
import ma.glasnost.orika.MappingContext;

public class MessageCreationRequestMapperTest {
	private MessageCreationRequestMapper messageCreationRequestMapper;

	@Mock
	private UserService userService;
	@Mock
	private MessageContentFactory messageContentFactory;
	@Mock
	private MappingContext context;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageCreationRequestMapper = new MessageCreationRequestMapper(userService, messageContentFactory);
	}

	@Test
	public void testSupports_trueForMessageCreationRequest() {
		Class clazz = MessageCreationRequest.class;

		assertThat(messageCreationRequestMapper.supports(clazz)).isTrue();
	}

	@Test
	public void testSupports_falseForOtherClass() {
		Class clazz = Object.class;

		assertThat(messageCreationRequestMapper.supports(clazz)).isFalse();
	}

	@Test
	public void testMapAToB() {
		MessageCreationRequest messageCreationRequest = aTextMessageCreationRequest();
		MessageContent messageContent = aTextMessageContent();
		User sender = aUser();
		User recipient = aDifferentUser();
		Message message = new Message();
		when(messageContentFactory.getMessageContent(messageCreationRequest.getContent())).thenReturn(messageContent);
		when(userService.getUserById(messageCreationRequest.getSender())).thenReturn(sender);
		when(userService.getUserById(messageCreationRequest.getRecipient())).thenReturn(recipient);

		messageCreationRequestMapper.mapAtoB(messageCreationRequest, message, context);

		assertThat(message.getSender()).isEqualTo(sender);
		assertThat(message.getRecipient()).isEqualTo(recipient);
		assertThat(message.getMessageContent()).isEqualTo(messageContent);
	}

	@Test
	public void testMapAToB_throwsCustomerMapperException() {
		MessageCreationRequest messageCreationRequest = aTextMessageCreationRequest();
		Message message = new Message();
		doThrow(new IllegalArgumentException("Error")).when(messageContentFactory).getMessageContent(messageCreationRequest.getContent());

		assertThatExceptionOfType(CustomerMapperException.class)
				.isThrownBy(() -> messageCreationRequestMapper.mapAtoB(messageCreationRequest, message, context))
				.withMessage("Error while trying to obtain content from messageCreationRequest=%s, error=%s",
						messageCreationRequest,
						"Error");
	}

	@Test
	public void testMapBToA() {
		Message message = aCompleteTextMessage();
		MessageCreationRequest messageCreationRequest = new MessageCreationRequest();

		messageCreationRequestMapper.mapBtoA(message, messageCreationRequest, context);

		assertThat(messageCreationRequest.getSender()).isEqualTo(message.getSender().getId());
		assertThat(messageCreationRequest.getRecipient()).isEqualTo(message.getRecipient().getId());
	}
}
