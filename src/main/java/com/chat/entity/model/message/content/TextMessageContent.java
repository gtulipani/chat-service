package com.chat.entity.model.message.content;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.chat.entity.model.message.content.MessageContent;
import com.chat.entity.model.message.content.MessageContentType;

@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessageContent extends MessageContent {
	private String text;

	public TextMessageContent(String text, Map<String, String> content) {
		super(MessageContentType.TEXT, content);
		this.text = text;
	}
}
