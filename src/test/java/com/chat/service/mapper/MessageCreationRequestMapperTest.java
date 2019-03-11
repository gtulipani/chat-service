package com.chat.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.content.MessageContentFactory;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.repository.UserRepository;
import com.chat.service.UserService;

public class MessageCreationRequestMapperTest {
	private MessageCreationRequestMapper messageCreationRequestMapper;

	@Mock
	private UserService userService;
	@Mock
	private MessageContentFactory messageContentFactory;

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
}
