package com.chat.mother;

import java.time.LocalDateTime;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;

public class UserMother {
	private static final LocalDateTime CREATED_ON = LocalDateTime.now();
	private static final LocalDateTime LAST_MODIFIED = LocalDateTime.now();
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	/**
	 * Creates a basic {@link User} with all fields set except from {@link User#id} which remains null
	 */
	public static User aUserWithoutId() {
		return User.builder()
				.createdOn(CREATED_ON)
				.lastModified(LAST_MODIFIED)
				.username(USERNAME)
				.password(PASSWORD)
				.build();
	}

	/**
	 * Creates a basic {@link User} with all fields set
	 */
	public static User aUser() {
		User user = aUserWithoutId();
		user.setId(1L);
		return user;
	}

	/**
	 * Creates a basic {@link User} but with different ID than {@link #aUser()}
	 */
	public static User aDifferentUser() {
		User user = aUserWithoutId();
		user.setId(2L);
		return user;
	}

	/**
	 * Creates a basic {@link UserCreationResponseEntity} using the {@link User} created with {@link #aUser()} as
	 * reference
	 */
	public static UserCreationResponseEntity aUserCreationResponseEntity() {
		return UserCreationResponseEntity.builder()
				.id(aUser().getId())
				.build();
	}
}
