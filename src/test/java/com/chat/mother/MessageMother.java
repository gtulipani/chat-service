package com.chat.mother;

import static com.chat.mother.UserMother.aDifferentUser;
import static com.chat.mother.UserMother.aUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.testng.collections.Maps;

import com.chat.entity.model.Message;
import com.chat.entity.model.MessageCreationRequest;
import com.chat.entity.model.MessageCreationResponseEntity;
import com.chat.entity.model.User;

public class MessageMother {
	private static final LocalDateTime CREATED_ON = LocalDateTime.now();
	private static final LocalDateTime LAST_MODIFIED = LocalDateTime.now();
	private static final Timestamp TIMESTAMP = Timestamp.valueOf(CREATED_ON);
	private static final User SENDER = aUser();
	private static final User RECIPIENT = aDifferentUser();

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
	 * Creates a basic {@link MessageCreationResponseEntity}
	 */
	public static MessageCreationResponseEntity aMessageCreationResponseEntity() {
		return MessageCreationResponseEntity.builder()
				.id(1L)
				.timestamp(TIMESTAMP)
				.build();
	}

	/**
	 * Creates a basic {@link Message} with all fields set except from {@link Message#id} which remains null
	 */
	public static Message aMessageWithoutId() {
		return Message.builder()
				.createdOn(CREATED_ON)
				.lastModified(LAST_MODIFIED)
				.sender(SENDER)
				.recipient(RECIPIENT)
				.build();
	}

	/**
	 * Creates a basic {@link Message} with all fields set
	 */
	public static Message aMessage() {
		Message message = aMessageWithoutId();
		message.setId(1L);
		return message;
	}
}
