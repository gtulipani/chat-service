package com.chat.mother;

import static com.chat.mother.TokenMother.ANOTHER_RANDOM_TOKEN;
import static com.chat.mother.TokenMother.RANDOM_TOKEN;

import java.time.LocalDateTime;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;

public class UserMother {
	private static final LocalDateTime CREATED_ON = LocalDateTime.now();
	private static final LocalDateTime LAST_MODIFIED = LocalDateTime.now();
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String ANOTHER_USERNAME = "another_username";
	private static final String ANOTHER_PASSWORD = "another_password";

	/**
	 * Creates a basic {@link User} with all fields set except from {@link User#id} which remains null
	 */
	public static User aUserWithoutId() {
		return aUserWithData(USERNAME, PASSWORD, RANDOM_TOKEN);
	}

	/**
	 * Creates a basic {@link User} with all fields set except from {@link User#id} which remains null. Different
	 * username and password than {@link #aUserWithoutId()}
	 */
	public static User aDifferentUserWithoutId() {
		return aUserWithData(ANOTHER_USERNAME, ANOTHER_PASSWORD, ANOTHER_RANDOM_TOKEN);
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
	 * Creates a basic {@link User} but with different id, username, password and token than {@link #aUser()}
	 */
	public static User aDifferentUser() {
		User user = aDifferentUserWithoutId();
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

	private static User aUserWithData(String username, String password, String token) {
		return User.builder()
				.createdOn(CREATED_ON)
				.lastModified(LAST_MODIFIED)
				.username(username)
				.password(password)
				.token(token)
				.build();
	}
}
