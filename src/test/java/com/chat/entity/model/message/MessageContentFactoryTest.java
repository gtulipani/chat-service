package com.chat.entity.model.message;

import static com.chat.mother.MessageContentMother.HEIGHT_STRING;
import static com.chat.mother.MessageContentMother.IMAGE_MESSAGE_CONTENT_HEIGHT_KEY;
import static com.chat.mother.MessageContentMother.IMAGE_MESSAGE_CONTENT_URL_KEY;
import static com.chat.mother.MessageContentMother.IMAGE_MESSAGE_CONTENT_WIDTH_KEY;
import static com.chat.mother.MessageContentMother.SOURCE_STRING;
import static com.chat.mother.MessageContentMother.TEXT;
import static com.chat.mother.MessageContentMother.TEXT_MESSAGE_CONTENT_TEXT_KEY;
import static com.chat.mother.MessageContentMother.TYPE_KEY;
import static com.chat.mother.MessageContentMother.URL;
import static com.chat.mother.MessageContentMother.VIDEO_MESSAGE_CONTENT_SOURCE_KEY;
import static com.chat.mother.MessageContentMother.VIDEO_MESSAGE_CONTENT_URL_KEY;
import static com.chat.mother.MessageContentMother.WIDTH_STRING;
import static com.chat.mother.MessageContentMother.aImageMessageContent;
import static com.chat.mother.MessageContentMother.aImageMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aImageMessageContentAsMapWithMissingKeys;
import static com.chat.mother.MessageContentMother.aTextMessageContent;
import static com.chat.mother.MessageContentMother.aTextMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aTextMessageContentAsMapWithMissingKeys;
import static com.chat.mother.MessageContentMother.aVideoMessageContent;
import static com.chat.mother.MessageContentMother.aVideoMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aVideoMessageContentAsMapWithMissingKeys;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import com.chat.entity.model.message.content.MessageContent;
import com.chat.entity.model.message.content.MessageContentFactory;
import com.chat.entity.model.message.content.MessageContentType;

public class MessageContentFactoryTest {
	private static final String INVALID_PARAMS_QUANTITY_ERROR = "Invalid quantity of params for building a MessageContent of type=%s";

	private MessageContentFactory messageContentFactory;

	@BeforeMethod
	public void setup() {
		messageContentFactory = new MessageContentFactory(
				TYPE_KEY,
				TEXT_MESSAGE_CONTENT_TEXT_KEY,
				IMAGE_MESSAGE_CONTENT_URL_KEY,
				IMAGE_MESSAGE_CONTENT_HEIGHT_KEY,
				IMAGE_MESSAGE_CONTENT_WIDTH_KEY,
				VIDEO_MESSAGE_CONTENT_URL_KEY,
				VIDEO_MESSAGE_CONTENT_SOURCE_KEY);
	}

	@Test
	public void testGetMessageContentText_list() {
		MessageContent messageContent = aTextMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> creationParams = Collections.singletonList(TEXT);

		MessageContent result = messageContentFactory.getMessageContent(messageContentType, creationParams);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContentType);
		assertThat(result.getContent()).containsExactly(entry(
				TEXT_MESSAGE_CONTENT_TEXT_KEY, TEXT));
	}

	@Test
	public void testGetMessageContentText_list_invalidParamsQuantity_throwsIllegalArgumentException() {
		MessageContent messageContent = aTextMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> noCreationParams = Lists.newArrayList();
		List<String> moreCreationParams = Arrays.asList(TEXT, TEXT);

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, noCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, moreCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());
	}

	@Test
	public void testGetMessageContentImage_list() {
		MessageContent messageContent = aImageMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> creationParams = Arrays.asList(URL, HEIGHT_STRING, WIDTH_STRING);

		MessageContent result = messageContentFactory.getMessageContent(messageContentType, creationParams);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContentType);
		assertThat(result.getContent()).containsExactly(
				entry(IMAGE_MESSAGE_CONTENT_URL_KEY, URL),
				entry(IMAGE_MESSAGE_CONTENT_HEIGHT_KEY, HEIGHT_STRING),
				entry(IMAGE_MESSAGE_CONTENT_WIDTH_KEY, WIDTH_STRING));
	}

	@Test
	public void testGetMessageContentImage_list_invalidParamsQuantity_throwsIllegalArgumentException() {
		MessageContent messageContent = aImageMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> noCreationParams = Lists.newArrayList();
		List<String> lessCreationParams = Arrays.asList(URL, HEIGHT_STRING);
		List<String> moreCreationParams = Arrays.asList(URL, HEIGHT_STRING, WIDTH_STRING, WIDTH_STRING);

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, noCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, lessCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, moreCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());
	}

	@Test
	public void testGetMessageContentVideo_list() {
		MessageContent messageContent = aVideoMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> creationParams = Arrays.asList(URL, SOURCE_STRING);

		MessageContent result = messageContentFactory.getMessageContent(messageContentType, creationParams);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContentType);
		assertThat(result.getContent()).containsExactly(
				entry(VIDEO_MESSAGE_CONTENT_URL_KEY, URL),
				entry(VIDEO_MESSAGE_CONTENT_SOURCE_KEY, SOURCE_STRING));
	}

	@Test
	public void testGetMessageContentVideo_list_invalidParamsQuantity_throwsIllegalArgumentException() {
		MessageContent messageContent = aVideoMessageContent();
		MessageContentType messageContentType = messageContent.getMessageContentType();
		List<String> noCreationParams = Lists.newArrayList();
		List<String> lessCreationParams = Collections.singletonList(URL);
		List<String> moreCreationParams = Arrays.asList(URL, SOURCE_STRING, SOURCE_STRING);

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, noCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, lessCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(messageContentType, moreCreationParams))
				.withMessage(INVALID_PARAMS_QUANTITY_ERROR, messageContent.getMessageContentType().getDisplayValue());
	}

	@Test
	public void testGetMessageContent_withoutKey_throwsIllegalArgumentException() {
		Map<String, String> map = Maps.newHashMap();

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(map))
				.withMessage("Couldn't find key=%s as part of the map", TYPE_KEY);
	}

	@Test
	public void testGetMessageContentText_map() {
		MessageContent messageContent = aTextMessageContent();
		Map<String, String> map = aTextMessageContentAsMap();

		MessageContent result = messageContentFactory.getMessageContent(map);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContent.getMessageContentType());
		assertThat(result.getContent()).containsExactly(entry(
				TEXT_MESSAGE_CONTENT_TEXT_KEY, TEXT));
	}

	@Test
	public void testGetMessageContentText_map_missingKeys_throwsIllegalArgumentException() {
		Map<String, String> map = aTextMessageContentAsMapWithMissingKeys();

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(map))
				.withMessage("Couldn't find key=%s as part of the map", TEXT_MESSAGE_CONTENT_TEXT_KEY);
	}

	@Test
	public void testGetMessageContentImage_map() {
		MessageContent messageContent = aImageMessageContent();
		Map<String, String> map = aImageMessageContentAsMap();

		MessageContent result = messageContentFactory.getMessageContent(map);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContent.getMessageContentType());
		assertThat(result.getContent()).containsExactly(
				entry(IMAGE_MESSAGE_CONTENT_URL_KEY, URL),
				entry(IMAGE_MESSAGE_CONTENT_HEIGHT_KEY, HEIGHT_STRING),
				entry(IMAGE_MESSAGE_CONTENT_WIDTH_KEY, WIDTH_STRING));
	}

	@Test
	public void testGetMessageContentImage_map_missingKeys_throwsIllegalArgumentException() {
		Map<String, String> map = aImageMessageContentAsMapWithMissingKeys();

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(map))
				.withMessage("Couldn't find key=%s as part of the map", IMAGE_MESSAGE_CONTENT_URL_KEY);
	}

	@Test
	public void testGetMessageContentVideo_map() {
		MessageContent messageContent = aVideoMessageContent();
		Map<String, String> map = aVideoMessageContentAsMap();

		MessageContent result = messageContentFactory.getMessageContent(map);

		assertThat(result.getMessageContentType()).isEqualByComparingTo(messageContent.getMessageContentType());
		assertThat(result.getContent()).containsExactly(
				entry(VIDEO_MESSAGE_CONTENT_URL_KEY, URL),
				entry(VIDEO_MESSAGE_CONTENT_SOURCE_KEY, SOURCE_STRING));
	}

	@Test
	public void testGetMessageContentVideo_map_missingKeys_throwsIllegalArgumentException() {
		Map<String, String> map = aVideoMessageContentAsMapWithMissingKeys();

		assertThatIllegalArgumentException()
				.isThrownBy(() -> messageContentFactory.getMessageContent(map))
				.withMessage("Couldn't find key=%s as part of the map", VIDEO_MESSAGE_CONTENT_URL_KEY);
	}

	@Test
	public void testGetMessageContentAsMapText() {
		MessageContent messageContent = aTextMessageContent();
		Map<String, String> messageContentMapped = aTextMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		Map<String, String> mappedResult = messageContentFactory.getMessageContentAsMap(messageContent);

		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}

	@Test
	public void testGetMessageContentAsMapImage() {
		MessageContent messageContent = aImageMessageContent();
		Map<String, String> messageContentMapped = aImageMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		Map<String, String> mappedResult = messageContentFactory.getMessageContentAsMap(messageContent);

		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}

	@Test
	public void testGetMessageContentAsMapVideo() {
		MessageContent messageContent = aVideoMessageContent();
		Map<String, String> messageContentMapped = aVideoMessageContentAsMap();
		List<Map.Entry<String, String>> entries = new ArrayList<>(messageContentMapped.entrySet());

		Map<String, String> mappedResult = messageContentFactory.getMessageContentAsMap(messageContent);

		entries.forEach(e -> assertThat(mappedResult).contains(e));
	}
}
