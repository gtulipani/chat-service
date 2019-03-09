package com.chat.mother;

import java.time.LocalDateTime;

import com.chat.entity.User;
import com.chat.entity.UserCreationResponseEntity;

public class UserMother {
	private static final LocalDateTime CREATED_ON = LocalDateTime.now();
	private static final LocalDateTime LAST_MODIFIED = LocalDateTime.now();
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	public static User aUser() {
		return User.builder()
				.id(1L)
				.createdOn(CREATED_ON)
				.lastModified(LAST_MODIFIED)
				.username(USERNAME)
				.password(PASSWORD)
				.build();
	}

	public static UserCreationResponseEntity aUserCreationResponseEntity() {
		return UserCreationResponseEntity.builder()
				.id(aUser().getId())
				.build();
	}
}
