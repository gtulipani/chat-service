package com.chat.entity.converter;

import static com.chat.mother.MessageContentMother.aImageMessageContent;
import static com.chat.mother.MessageContentMother.aImageMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aTextMessageContent;
import static com.chat.mother.MessageContentMother.aTextMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aVideoMessageContent;
import static com.chat.mother.MessageContentMother.aVideoMessageContentAsMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.message.MessageContent;
import com.chat.entity.model.message.MessageContentFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessageContentConverterTest {
	private static final String TYPE_KEY = "type";

	private MessageContentConverter messageContentConverter;

	@Mock
	private MessageContentFactory messageContentFactory;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		messageContentConverter = new MessageContentConverter();
		messageContentConverter.init(TYPE_KEY, messageContentFactory);
	}

	@Test
	public void testConvertToDatabaseColumn_textMessageContent() {
		MessageContent messageContent = aTextMessageContent();
		Map<String, String> messageContentMapped = aTextMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		String result = messageContentConverter.convertToDatabaseColumn(messageContent);

		Map<String, String> mappedResult = jsonToMap(result);
		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}

	@Test
	public void testConvertToDatabaseColumn_imageMessageContent() {
		MessageContent messageContent = aImageMessageContent();
		Map<String, String> messageContentMapped = aImageMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		String result = messageContentConverter.convertToDatabaseColumn(messageContent);

		Map<String, String> mappedResult = jsonToMap(result);
		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}

	@Test
	public void testConvertToDatabaseColumn_videoMessageContent() {
		MessageContent messageContent = aVideoMessageContent();
		Map<String, String> messageContentMapped = aVideoMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		String result = messageContentConverter.convertToDatabaseColumn(messageContent);

		Map<String, String> mappedResult = jsonToMap(result);
		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}

	@Test
	public void testConvertToEntityAttribute_textMessageContent() {
		MessageContent messageContent = aTextMessageContent();
		Map<String, String> messageContentMapped = aTextMessageContentAsMap();
		String messageContentAsJson = mapToJson(messageContentMapped);
		when(messageContentFactory.getMessageContent(messageContentMapped)).thenReturn(messageContent);

		MessageContent result = messageContentConverter.convertToEntityAttribute(messageContentAsJson);

		assertThat(result).isEqualTo(messageContent);
	}

	@Test
	public void testConvertToEntityAttribute_imageMessageContent() {
		MessageContent messageContent = aImageMessageContent();
		Map<String, String> messageContentMapped = aImageMessageContentAsMap();
		String messageContentAsJson = mapToJson(messageContentMapped);
		when(messageContentFactory.getMessageContent(messageContentMapped)).thenReturn(messageContent);

		MessageContent result = messageContentConverter.convertToEntityAttribute(messageContentAsJson);

		assertThat(result).isEqualTo(messageContent);
	}

	@Test
	public void testConvertToEntityAttribute_videoMessageContent() {
		MessageContent messageContent = aVideoMessageContent();
		Map<String, String> messageContentMapped = aVideoMessageContentAsMap();
		String messageContentAsJson = mapToJson(messageContentMapped);
		when(messageContentFactory.getMessageContent(messageContentMapped)).thenReturn(messageContent);

		MessageContent result = messageContentConverter.convertToEntityAttribute(messageContentAsJson);

		assertThat(result).isEqualTo(messageContent);
	}

	/**
	 * Converts a {@link String} serialized as JSON to {@link Map}
	 */
	private Map<String, String> jsonToMap(String json) {
		Type mapType = new TypeToken<Map<String, String>>() {}.getType();
		return new Gson().fromJson(json, mapType);
	}

	/**
	 * Converts a {@link Map} to {@link String} serialized as JSON
	 */
	private String mapToJson(Map<String, String> map) {
		return new Gson().toJson(map);
	}
}
