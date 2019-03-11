package com.chat.entity.converter;

import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.entity.model.message.content.MessageContent;
import com.chat.entity.model.message.content.MessageContentFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Slf4j
@Component
@Converter
public class MessageContentConverter implements AttributeConverter<MessageContent, String> {
	private static MessageContentFactory messageContentFactory;

	@Autowired
	public void init(MessageContentFactory messageContentFactory) {
		MessageContentConverter.messageContentFactory = messageContentFactory;
	}

	@Override
	public String convertToDatabaseColumn(MessageContent messageContent) {
		Map<String, String> messageContentMap = messageContentFactory.getMessageContentAsMap(messageContent);

		return new Gson().toJson(messageContentMap);
	}

	@Override
	public MessageContent convertToEntityAttribute(String json) {
		Map<String, String> map = jsonToMap(json);
		return messageContentFactory.getMessageContent(map);
	}

	private Map<String, String> jsonToMap(String json) {
		Type mapType = new TypeToken<Map<String, String>>() {}.getType();
		return new Gson().fromJson(json, mapType);
	}
}
