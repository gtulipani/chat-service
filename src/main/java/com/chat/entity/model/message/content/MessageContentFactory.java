package com.chat.entity.model.message.content;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

@Component
public class MessageContentFactory {
	private static final String INVALID_PARAMS_QUANTITY_ERROR = "Invalid quantity of params for building a MessageContent of type=%s";

	private final String typeKey;
	private final String textMessageContentTextKey;
	private final String imageMessageContentUrlKey;
	private final String imageMessageContentHeightKey;
	private final String imageMessageContentWidthKey;
	private final String videoMessageContentUrlKey;
	private final String videoMessageContentSourceKey;
	private final Map<MessageContentType, List<String>> mandatoryKeys;
	private final Map<MessageContentType, Function<List<String>, MessageContent>> contentBuildersByList;
	private final Map<MessageContentType, Function<Map<String, String>, MessageContent>> contentBuildersByMap;

	public MessageContentFactory(@Value("${message.content.typeKey}") String typeKey,
			@Value("${message.content.text.textKey}") String textMessageContentTextKey,
			@Value("${message.content.image.urlKey}") String imageMessageContentUrlKey,
			@Value("${message.content.image.heightKey}") String imageMessageContentHeightKey,
			@Value("${message.content.image.widthKey}") String imageMessageContentWidthKey,
			@Value("${message.content.video.urlKey}") String videoMessageContentUrlKey,
			@Value("${message.content.video.sourceKey}") String videoMessageContentSourceKey) {
		this.typeKey = typeKey;
		this.textMessageContentTextKey = textMessageContentTextKey;
		this.imageMessageContentUrlKey = imageMessageContentUrlKey;
		this.imageMessageContentHeightKey = imageMessageContentHeightKey;
		this.imageMessageContentWidthKey = imageMessageContentWidthKey;
		this.videoMessageContentUrlKey = videoMessageContentUrlKey;
		this.videoMessageContentSourceKey = videoMessageContentSourceKey;

		this.mandatoryKeys = ImmutableMap.of(
				MessageContentType.TEXT, Collections.singletonList(textMessageContentTextKey),
				MessageContentType.IMAGE, Arrays.asList(
						imageMessageContentUrlKey,
						imageMessageContentHeightKey,
						imageMessageContentWidthKey),
				MessageContentType.VIDEO, Arrays.asList(
						videoMessageContentUrlKey,
						videoMessageContentSourceKey
				));

		this.contentBuildersByList = ImmutableMap.of(
				MessageContentType.TEXT, textBuilderByList(),
				MessageContentType.IMAGE, imageBuilderByList(),
				MessageContentType.VIDEO, videoBuilderByList());

		this.contentBuildersByMap = ImmutableMap.of(
				MessageContentType.TEXT, textBuilderByMap(),
				MessageContentType.IMAGE, imageBuilderByMap(),
				MessageContentType.VIDEO, videoBuilderByMap()
		);
	}

	/**
	 * Gets a proper MessageContent using the {@link MessageContentType} inside the map received as parameter
	 */
	public MessageContent getMessageContent(Map<String, String> map) {
		// Check existence of MessageContentType
		if (!map.containsKey(typeKey)) {
			throw new IllegalArgumentException(String.format("Couldn't find key=%s as part of the map", typeKey));
		}

		// Check existence of mandatoryKeys entry for requested MessageContentType
		MessageContentType messageContentType = MessageContentType.ofDisplayName(map.get(typeKey));
		if (!mandatoryKeys.containsKey(messageContentType)) {
			throw new IllegalArgumentException(String.format("Couldn't find mandatory keys for type=%s", messageContentType));
		}

		// Check existence of all required keys for the given MessageContentType
		mandatoryKeys.get(messageContentType).forEach(key -> {
				if (!map.containsKey(key)) {
					throw new IllegalArgumentException(String.format("Couldn't find key=%s as part of the map", key));
				}
		});

		// Call proper builder
		return contentBuildersByMap.get(messageContentType).apply(map);
	}

	/**
	 * Gets a MessageContent but serialized as {@link Map} among with the proper {@link MessageContent#messageContentType}
	 */
	public Map<String, String> getMessageContentAsMap(MessageContent messageContent) {
		Map<String, String> messageContentMap = Maps.newHashMap();
		messageContentMap.putAll(messageContent.getContent());
		messageContentMap.put(typeKey, messageContent.getMessageContentType().getDisplayValue());
		return messageContentMap;
	}

	/**
	 * Gets a proper MessageContent with the {@link MessageContentType} and params specified as parameters
	 */
	public MessageContent getMessageContent(MessageContentType messageContentType, List<String> params) {
		// Check existence of builder for the given type
		if (!contentBuildersByList.containsKey(messageContentType)) {
			throw new IllegalArgumentException(String.format("Couldn't find supplier for MessageContent of type=%s", messageContentType));
		}

		// Check existence of mandatoryKeys entry for requested MessageContentType
		if (!mandatoryKeys.containsKey(messageContentType)) {
			throw new IllegalArgumentException(String.format("Couldn't find mandatory keys for type=%s", messageContentType));
		}

		// Check quantity of keys for requested MessageContentType
		if (mandatoryKeys.get(messageContentType).size() != params.size()) {
			throw new IllegalArgumentException(String.format(INVALID_PARAMS_QUANTITY_ERROR, messageContentType.getDisplayValue()));
		}

		// Call proper builder
		return contentBuildersByList.get(messageContentType).apply(params);
	}

	/**
	 * Private function that takes a Map of params and returns a {@link TextMessageContent}
	 */
	private Function<Map<String, String>, MessageContent> textBuilderByMap() {
		return map -> textBuilderByList().apply(Collections.singletonList(map.get(textMessageContentTextKey)));
	}

	/**
	 * Private function that takes a Map of params and returns a {@link ImageMessageContent}
	 */
	private Function<Map<String, String>, MessageContent> imageBuilderByMap() {
		return map -> imageBuilderByList().apply(Arrays.asList(
				map.get(imageMessageContentUrlKey),
				map.get(imageMessageContentHeightKey),
				map.get(imageMessageContentWidthKey)));
	}

	/**
	 * Private function that takes a Map of params and returns a {@link VideoMessageContent}
	 */
	private Function<Map<String, String>, MessageContent> videoBuilderByMap() {
		return map -> videoBuilderByList().apply(Arrays.asList(
				map.get(videoMessageContentUrlKey),
				map.get(videoMessageContentSourceKey)));
	}

	/**
	 * Private function that takes a list of params and returns a {@link TextMessageContent}
	 */
	private Function<List<String>, MessageContent> textBuilderByList() {
		return params -> {
			String text = params.get(0);

			return new TextMessageContent(
					text,
					ImmutableMap.of(textMessageContentTextKey, text));
		};
	}

	/**
	 * Private function that takes a list of params and returns a {@link ImageMessageContent}
	 */
	private Function<List<String>, MessageContent> imageBuilderByList() {
		return params -> {
			String url = params.get(0);
			String height = params.get(1);
			String width = params.get(2);

			return new ImageMessageContent(
					url,
					Integer.valueOf(height),
					Integer.valueOf(width),
					ImmutableMap.of(
							imageMessageContentUrlKey, url,
							imageMessageContentHeightKey, height,
							imageMessageContentWidthKey, width));
		};
	}

	/**
	 * Private function that takes a list of params and returns a {@link VideoMessageContent}
	 */
	private Function<List<String>, MessageContent> videoBuilderByList() {
		return params -> {
			String url = params.get(0);
			String source = params.get(1);

			return new VideoMessageContent(
					url,
					VideoMessageContentSource.ofDisplayName(source),
					ImmutableMap.of(
							videoMessageContentUrlKey, url,
							videoMessageContentSourceKey, source));
		};
	}
}
