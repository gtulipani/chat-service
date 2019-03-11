package com.chat.entity.model.message;

import java.util.stream.Stream;

import lombok.Getter;

public enum VideoMessageContentSource {
	YOUTUBE("youtube"),
	VIMEO("vimeo");

	@Getter
	private final String displayValue;

	VideoMessageContentSource(String displayValue) {
		this.displayValue = displayValue;
	}

	public static VideoMessageContentSource ofDisplayName(String displayName) {
		return Stream.of(values())
				.filter(value -> value.displayValue.equalsIgnoreCase(displayName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("Couldn't find VideoMessageContentSource with displayName=%s", displayName)));
	}
}
