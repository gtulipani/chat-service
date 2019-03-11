package com.chat.entity.model.message;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VideoMessageContent extends MessageContent {
	private String url;
	private VideoMessageContentSource source;

	public VideoMessageContent(String url, VideoMessageContentSource source, Map<String, String> content) {
		super(MessageContentType.VIDEO, content);
		this.url = url;
		this.source = source;
	}
}
