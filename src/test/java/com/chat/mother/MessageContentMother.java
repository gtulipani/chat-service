package com.chat.mother;

import java.util.Map;

import org.testng.collections.Maps;

import com.chat.entity.model.message.ImageMessageContent;
import com.chat.entity.model.message.MessageContent;
import com.chat.entity.model.message.TextMessageContent;
import com.chat.entity.model.message.VideoMessageContent;
import com.chat.entity.model.message.VideoMessageContentSource;
import com.google.common.collect.ImmutableMap;

public class MessageContentMother {
	public static final String TYPE_KEY = "type";
	public static final String TEXT_MESSAGE_CONTENT_TEXT_KEY = "text";
	public static final String IMAGE_MESSAGE_CONTENT_URL_KEY = "url";
	public static final String IMAGE_MESSAGE_CONTENT_HEIGHT_KEY = "height";
	public static final String IMAGE_MESSAGE_CONTENT_WIDTH_KEY = "width";
	public static final String VIDEO_MESSAGE_CONTENT_URL_KEY = "url";
	public static final String VIDEO_MESSAGE_CONTENT_SOURCE_KEY = "source";

	public static final String TEXT = "text";
	public static final String URL = "url";
	public static final Integer HEIGHT = 1;
	public static final Integer WIDTH = 1;
	public static final String HEIGHT_STRING = String.valueOf(HEIGHT);
	public static final String WIDTH_STRING = String.valueOf(WIDTH);
	public static final VideoMessageContentSource SOURCE = VideoMessageContentSource.YOUTUBE;
	public static final String SOURCE_STRING = SOURCE.toString();

	private static final Map<String, String> TEXT_MESSAGE_CONTENT_MAP = ImmutableMap.of(TEXT_MESSAGE_CONTENT_TEXT_KEY, TEXT);
	private static final Map<String, String> IMAGE_MESSAGE_CONTENT_MAP = ImmutableMap.of(
			IMAGE_MESSAGE_CONTENT_URL_KEY, URL,
			IMAGE_MESSAGE_CONTENT_HEIGHT_KEY, HEIGHT_STRING,
			IMAGE_MESSAGE_CONTENT_WIDTH_KEY, WIDTH_STRING);
	private static final Map<String, String> VIDEO_MESSAGE_CONTENT_MAP = ImmutableMap.of(
			VIDEO_MESSAGE_CONTENT_URL_KEY, URL,
			VIDEO_MESSAGE_CONTENT_SOURCE_KEY, SOURCE_STRING);

	/**
	 * Creates a basic {@link TextMessageContent}
	 */
	public static MessageContent aTextMessageContent() {
		return new TextMessageContent(TEXT, TEXT_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link ImageMessageContent}
	 */
	public static MessageContent aImageMessageContent() {
		return new ImageMessageContent(URL, HEIGHT, WIDTH, IMAGE_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link VideoMessageContent}
	 */
	public static MessageContent aVideoMessageContent() {
		return new VideoMessageContent(URL, SOURCE, VIDEO_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link TextMessageContent} and transforms it to {@link Map}
	 */
	public static Map<String, String> aTextMessageContentAsMap() {
		return aMessageContentAsMap(aTextMessageContent(), TEXT_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link TextMessageContent} but with some missing keys
	 */
	public static Map<String, String> aTextMessageContentAsMapWithMissingKeys() {
		return aMessageContentAsMapWithMissingKeys(aTextMessageContent());
	}

	/**
	 * Creates a basic {@link ImageMessageContent} and transforms it to {@link Map}
	 */
	public static Map<String, String> aImageMessageContentAsMap() {
		return aMessageContentAsMap(aImageMessageContent(), IMAGE_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link ImageMessageContent} but with some missing keys
	 */
	public static Map<String, String> aImageMessageContentAsMapWithMissingKeys() {
		return aMessageContentAsMapWithMissingKeys(aImageMessageContent());
	}

	/**
	 * Creates a basic {@link VideoMessageContent} and transforms it to {@link Map}
	 */
	public static Map<String, String> aVideoMessageContentAsMap() {
		return aMessageContentAsMap(aVideoMessageContent(), VIDEO_MESSAGE_CONTENT_MAP);
	}

	/**
	 * Creates a basic {@link VideoMessageContent} but with some missing keys
	 */
	public static Map<String, String> aVideoMessageContentAsMapWithMissingKeys() {
		return aMessageContentAsMapWithMissingKeys(aVideoMessageContent());
	}

	/**
	 * Creates a basic {@link MessageContent} and transforms it to {@link Map}
	 */
	private static Map<String, String> aMessageContentAsMap(MessageContent messageContent, Map<String, String> contentMap) {
		Map<String, String> completeMap = aMessageContentAsMapWithMissingKeys(messageContent);
		completeMap.putAll(contentMap);
		return completeMap;
	}

	/**
	 * Creates a basic {@link Map}, only with the {@link #TYPE_KEY} entry
	 */
	private static Map<String, String> aMessageContentAsMapWithMissingKeys(MessageContent messageContent) {
		Map<String, String> incompleteMap = Maps.newHashMap();
		incompleteMap.put(TYPE_KEY, messageContent.getMessageContentType().getDisplayValue());
		return incompleteMap;
	}
}
