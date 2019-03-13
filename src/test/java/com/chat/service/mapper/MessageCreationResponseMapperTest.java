package com.chat.service.mapper;

import static com.chat.entity.utils.DateUtils.toTimestamp;
import static com.chat.mother.MessageMother.aCompleteTextMessage;
import static com.chat.mother.MessageMother.aMessageCreationResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.exception.MessageNotFoundException;
import com.chat.repository.MessageRepository;
import ma.glasnost.orika.MappingContext;

public class MessageCreationResponseMapperTest {
	private MessageCreationResponseMapper messageCreationResponseMapper;

	@Mock
	private MessageRepository messageRepository;
	@Mock
	private MappingContext context;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageCreationResponseMapper = new MessageCreationResponseMapper(messageRepository);
	}

	@Test
	public void testSupports_trueForMessageCreationResponse() {
		Class clazz = MessageCreationResponse.class;

		assertThat(messageCreationResponseMapper.supports(clazz)).isTrue();
	}

	@Test
	public void testSupports_falseForOtherClass() {
		Class clazz = Object.class;

		assertThat(messageCreationResponseMapper.supports(clazz)).isFalse();
	}

	@Test
	public void testMapAToB() {
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		Message completeMessage = aCompleteTextMessage();
		Message message = new Message();
		when(messageRepository.findById(messageCreationResponse.getId())).thenReturn(Optional.of(completeMessage));

		messageCreationResponseMapper.mapAtoB(messageCreationResponse, message, context);

		assertThat(message).isEqualTo(completeMessage);
	}

	@Test
	public void testMapAToB_throwsMessageNotFoundException() {
		MessageCreationResponse messageCreationResponse = aMessageCreationResponseEntity();
		Message message = new Message();
		when(messageRepository.findById(messageCreationResponse.getId())).thenReturn(Optional.empty());

		assertThatExceptionOfType(MessageNotFoundException.class)
				.isThrownBy(() -> messageCreationResponseMapper.mapAtoB(messageCreationResponse, message, context))
				.withMessage("Couldn't find message with id=%s", messageCreationResponse.getId());
	}

	@Test
	public void testMapBToA() {
		Message message = aCompleteTextMessage();
		MessageCreationResponse messageCreationResponse = new MessageCreationResponse();

		messageCreationResponseMapper.mapBtoA(message, messageCreationResponse, context);

		assertThat(messageCreationResponse.getId()).isEqualTo(message.getId());
		assertThat(messageCreationResponse.getTimestamp()).isEqualTo(toTimestamp(message.getCreatedOn()));
	}
}
