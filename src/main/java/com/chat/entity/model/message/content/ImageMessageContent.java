package com.chat.entity.model.message.content;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageMessageContent extends MessageContent {

	private String uri;
	private Integer height;
	private Integer width;

	public ImageMessageContent(String uri, Integer height, Integer width, Map<String, String> content) {
		super(MessageContentType.IMAGE, content);
		this.uri = uri;
		this.height = height;
		this.width = width;
	}
}
