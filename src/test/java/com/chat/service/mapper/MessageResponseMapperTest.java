package com.chat.service.mapper;

import static com.chat.entity.utils.DateUtils.toTimestamp;
import static com.chat.mother.MessageContentMother.aTextMessageContentAsMap;
import static com.chat.mother.MessageMother.aCompleteTextMessage;
import static com.chat.mother.MessageMother.aCompleteTextMessageResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.MessageResponse;
import com.chat.entity.model.message.content.MessageContentFactory;
import com.chat.exception.CustomerMapperException;
import com.chat.exception.MessageNotFoundException;
import com.chat.repository.MessageRepository;
import ma.glasnost.orika.MappingContext;

public class MessageResponseMapperTest {
	private MessageResponseMapper messageResponseMapper;

	@Mock
	private MessageRepository messageRepository;
	@Mock
	private MessageContentFactory messageContentFactory;
	@Mock
	private MappingContext context;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageResponseMapper = new MessageResponseMapper(messageRepository, messageContentFactory);
	}

	@Test
	public void testSupports_trueForMessageResponse() {
		Class clazz = MessageResponse.class;

		assertThat(messageResponseMapper.supports(clazz)).isTrue();
	}

	@Test
	public void testSupports_falseForOtherClass() {
		Class clazz = Object.class;

		assertThat(messageResponseMapper.supports(clazz)).isFalse();
	}

	@Test
	public void testMapAToB() {
		MessageResponse messageResponse = aCompleteTextMessageResponse();
		Message completeMessage = aCompleteTextMessage();
		Message message = new Message();
		when(messageRepository.findById(messageResponse.getId())).thenReturn(Optional.of(completeMessage));

		messageResponseMapper.mapAtoB(messageResponse, message, context);

		assertThat(message).isEqualTo(completeMessage);
	}

	@Test
	public void testMapAToB_throwsMessageNotFoundException() {
		MessageResponse messageResponse = aCompleteTextMessageResponse();
		Message message = new Message();
		when(messageRepository.findById(messageResponse.getId())).thenReturn(Optional.empty());

		assertThatExceptionOfType(MessageNotFoundException.class)
				.isThrownBy(() -> messageResponseMapper.mapAtoB(messageResponse, message, context))
				.withMessage("Couldn't find message with id=%s", messageResponse.getId());
	}

	@Test
	public void testMapBToA() {
		Message message = aCompleteTextMessage();
		Map<String, String> messageContentAsMap = aTextMessageContentAsMap();
		MessageResponse messageResponse = new MessageResponse();
		when(messageContentFactory.getMessageContentAsMap(message.getMessageContent())).thenReturn(messageContentAsMap);

		messageResponseMapper.mapBtoA(message, messageResponse, context);

		assertThat(messageResponse.getId()).isEqualTo(message.getId());
		assertThat(messageResponse.getTimestamp()).isEqualTo(toTimestamp(message.getCreatedOn()));
		assertThat(messageResponse.getSender()).isEqualTo(message.getSender().getId());
		assertThat(messageResponse.getRecipient()).isEqualTo(message.getRecipient().getId());
		assertThat(messageResponse.getContent()).isEqualTo(messageContentAsMap);
	}

	@Test
	public void testMapBToA_throwsCustomerMapperException() {
		Message message = aCompleteTextMessage();
		MessageResponse messageResponse = new MessageResponse();
		doThrow(new IllegalArgumentException("Error")).when(messageContentFactory).getMessageContentAsMap(message.getMessageContent());

		assertThatExceptionOfType(CustomerMapperException.class)
				.isThrownBy(() -> messageResponseMapper.mapBtoA(message, messageResponse, context))
				.withMessage("Error while trying to obtain content as map from message=%s, error=%s",
						message,
						"Error");
	}
}
