package com.chat.mother;

import static com.chat.entity.utils.DateUtils.toTimestamp;
import static com.chat.mother.MessageContentMother.aImageMessageContent;
import static com.chat.mother.MessageContentMother.aImageMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aTextMessageContent;
import static com.chat.mother.MessageContentMother.aTextMessageContentAsMap;
import static com.chat.mother.MessageContentMother.aVideoMessageContent;
import static com.chat.mother.MessageContentMother.aVideoMessageContentAsMap;
import static com.chat.mother.UserMother.aDifferentUser;
import static com.chat.mother.UserMother.aUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.testng.collections.Maps;

import com.chat.entity.model.User;
import com.chat.entity.model.message.Message;
import com.chat.entity.model.message.content.MessageContent;
import com.chat.entity.model.message.MessageCreationRequest;
import com.chat.entity.model.message.MessageCreationResponse;
import com.chat.entity.model.message.MessageResponse;
import com.chat.entity.model.message.MessagesResponse;
import com.chat.entity.model.message.content.ImageMessageContent;
import com.chat.entity.model.message.content.TextMessageContent;
import com.chat.entity.model.message.content.VideoMessageContent;

public class MessageMother {
	private static final LocalDateTime CREATED_ON = LocalDateTime.now();
	private static final LocalDateTime LAST_MODIFIED = LocalDateTime.now();
	private static final Timestamp TIMESTAMP = Timestamp.valueOf(CREATED_ON);
	private static final User SENDER = aUser();
	private static final User RECIPIENT = aDifferentUser();

	/**
	 * Creates a {@link MessagesResponse} with one message from text type
	 */
	public static MessagesResponse aMessagesResponseWithOneTextMessage() {
		return MessagesResponse.builder()
				.messages(Collections.singletonList(aCompleteTextMessageResponse()))
				.build();
	}

	/**
	 * Creates a {@link MessagesResponse} with one message from each type
	 */
	public static MessagesResponse aMessagesResponseWithMultipleMessages() {
		return MessagesResponse.builder()
				.messages(Arrays.asList(
						aCompleteTextMessageResponse(),
						aCompleteImageMessageResponse(),
						aCompleteVideoMessageResponse()))
				.build();
	}

	/**
	 * Creates a {@link MessageResponse} from text type
	 */
	public static MessageResponse aCompleteTextMessageResponse() {
		return aCompleteMessageResponse(aCompleteTextMessage(), aTextMessageContentAsMap());
	}

	/**
	 * Creates a {@link MessageResponse} from image type
	 */
	public static MessageResponse aCompleteImageMessageResponse() {
		return aCompleteMessageResponse(aCompleteImageMessage(), aImageMessageContentAsMap());
	}

	/**
	 * Creates a {@link MessageResponse} from video type
	 */
	public static MessageResponse aCompleteVideoMessageResponse() {
		return aCompleteMessageResponse(aCompleteVideoMessage(), aVideoMessageContentAsMap());
	}

	/**
	 * Creates a basic {@link MessageCreationRequest} with {@link TextMessageContent}
	 */
	public static MessageCreationRequest aTextMessageCreationRequest() {
		return MessageCreationRequest.builder()
				.sender(SENDER.getId())
				.recipient(RECIPIENT.getId())
				.content(aTextMessageContentAsMap())
				.build();
	}

	/**
	 * Creates a basic {@link MessageCreationRequest} with {@link ImageMessageContent}
	 */
	public static MessageCreationRequest aImageMessageCreationRequest() {
		return MessageCreationRequest.builder()
				.sender(SENDER.getId())
				.recipient(RECIPIENT.getId())
				.content(aImageMessageContentAsMap())
				.build();
	}

	/**
	 * Creates a basic {@link MessageCreationRequest} with {@link VideoMessageContent}
	 */
	public static MessageCreationRequest aVideoMessageCreationRequest() {
		return MessageCreationRequest.builder()
				.sender(SENDER.getId())
				.recipient(RECIPIENT.getId())
				.content(aVideoMessageContentAsMap())
				.build();
	}

	/**
	 * Creates a basic {@link MessageCreationRequest} with empty content
	 */
	public static MessageCreationRequest aMessageCreationRequestWithEmptyContent() {
		return MessageCreationRequest.builder()
				.sender(SENDER.getId())
				.recipient(RECIPIENT.getId())
				.content(Maps.newHashMap())
				.build();
	}

	/**
	 * Creates a basic {@link MessageCreationResponse}
	 */
	public static MessageCreationResponse aMessageCreationResponseEntity() {
		return MessageCreationResponse.builder()
				.id(1L)
				.timestamp(TIMESTAMP)
				.build();
	}

	/**
	 * Creates a complete {@link Message} with {@link TextMessageContent}
	 */
	public static Message aCompleteTextMessage() {
		return aCompleteMessage(aCompleteTextMessageWithoutId());
	}

	/**
	 * Creates a complete {@link Message} with {@link ImageMessageContent}
	 */
	public static Message aCompleteImageMessage() {
		return aCompleteMessage(aCompleteImageMessageWithoutId());
	}

	/**
	 * Creates a complete {@link Message} with {@link VideoMessageContent}
	 */
	public static Message aCompleteVideoMessage() {
		return aCompleteMessage(aCompleteVideoMessageWithoutId());
	}

	/**
	 * Creates a complete {@link Message} with {@link TextMessageContent} without id
	 */
	public static Message aCompleteTextMessageWithoutId() {
		return aCompleteMessageWithoutId(aTextMessageContent());
	}

	/**
	 * Creates a complete {@link Message} with {@link ImageMessageContent} without id
	 */
	public static Message aCompleteImageMessageWithoutId() {
		return aCompleteMessageWithoutId(aImageMessageContent());
	}

	/**
	 * Creates a complete {@link Message} with {@link VideoMessageContent} without id
	 */
	public static Message aCompleteVideoMessageWithoutId() {
		return aCompleteMessageWithoutId(aVideoMessageContent());
	}

	private static MessageResponse aCompleteMessageResponse(Message message, Map<String, String> messageContentAsMap) {
		return MessageResponse.builder()
				.id(message.getId())
				.timestamp(toTimestamp(message.getCreatedOn()))
				.sender(message.getSender().getId())
				.recipient(message.getRecipient().getId())
				.content(messageContentAsMap)
				.build();
	}

	private static Message aCompleteMessage(Message message) {
		message.setId(1L);
		return message;
	}

	private static Message aCompleteMessageWithoutId(MessageContent messageContent) {
		Message message = aMessageWithoutIdOrContent();
		message.setMessageContent(messageContent);
		return message;
	}

	private static Message aMessageWithoutIdOrContent() {
		return Message.builder()
				.createdOn(CREATED_ON)
				.lastModified(LAST_MODIFIED)
				.sender(SENDER)
				.recipient(RECIPIENT)
				.build();
	}
}
