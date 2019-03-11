package com.chat.entity.model.message;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessageContent extends MessageContent {
	private String text;

	public TextMessageContent(String text, Map<String, String> content) {
		super(MessageContentType.TEXT, content);
		this.text = text;
	}
}
