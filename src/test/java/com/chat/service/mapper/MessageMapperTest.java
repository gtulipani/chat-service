package com.chat.service.mapper;

import static com.chat.mother.MessageMother.aImageMessageCreationRequest;
import static com.chat.mother.MessageMother.aTextMessageCreationRequest;
import static com.chat.mother.MessageMother.aVideoMessageCreationRequest;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.exception.CustomerMapperException;
import ma.glasnost.orika.MappingContext;

public class MessageMapperTest {
	private MessageMapper messageMapper;

	@Mock
	private CustomMapperStrategy customMapperStrategy;
	private List<CustomMapperStrategy> customMappers;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		when(customMapperStrategy.supports(MessageCreationRequest.class)).thenReturn(true);
		when(customMapperStrategy.supports(MessageCreationResponse.class)).thenReturn(true);
		customMappers = Lists.newArrayList(customMapperStrategy);
		messageMapper = new MessageMapper(customMappers);
	}

	@Test
	public void testNoMapper_throwsCustomMapperException() {
		when(customMapperStrategy.supports(MessageCreationRequest.class)).thenReturn(false);
		customMappers = Lists.newArrayList(customMapperStrategy);

		assertThatExceptionOfType(CustomerMapperException.class)
				.isThrownBy(() -> new MessageMapper(customMappers))
				.withMessage("Could not find any custom mapper to convert to MessageCreationRequest");
	}

	@Test
	public void testMapMessageCreationRequestText() {
		MessageCreationRequest messageCreationRequest = aTextMessageCreationRequest();

		messageMapper.map(messageCreationRequest, Message.class);

		verify(customMapperStrategy, times(1)).mapAtoB(eq(messageCreationRequest), any(Message.class), any(MappingContext.class));
	}

	@Test
	public void testMapMessageCreationRequestImage() {
		MessageCreationRequest messageCreationRequest = aImageMessageCreationRequest();

		messageMapper.map(messageCreationRequest, Message.class);

		verify(customMapperStrategy, times(1)).mapAtoB(eq(messageCreationRequest), any(Message.class), any(MappingContext.class));
	}

	@Test
	public void testMapMessageCreationRequestVideo() {
		MessageCreationRequest messageCreationRequest = aVideoMessageCreationRequest();

		messageMapper.map(messageCreationRequest, Message.class);

		verify(customMapperStrategy, times(1)).mapAtoB(eq(messageCreationRequest), any(Message.class), any(MappingContext.class));
	}
}
