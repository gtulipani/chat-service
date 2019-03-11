package com.chat.entity.model.message.content;

import java.util.stream.Stream;

import lombok.Getter;

public enum MessageContentType {
	TEXT("text"),
	IMAGE("image"),
	VIDEO("video");

	@Getter
	private String displayValue;

	MessageContentType(String displayValue) {
		this.displayValue = displayValue;
	}

	public static MessageContentType ofDisplayName(String displayName) {
		return Stream.of(values())
				.filter(value -> value.displayValue.equalsIgnoreCase(displayName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("Couldn't find MessageContentType with displayName=%s", displayName)));
	}
}
