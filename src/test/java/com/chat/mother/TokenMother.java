package com.chat.mother;

import java.util.UUID;

import com.chat.entity.model.Token;

public class TokenMother {
	public static final String RANDOM_TOKEN = UUID.randomUUID().toString();
	public static final String ANOTHER_RANDOM_TOKEN = UUID.randomUUID().toString();
	
	/**
	 * Creates a basic {@link Token}
	 */
	public static Token aToken() {
		return Token.builder()
				.id(1L)
				.token(RANDOM_TOKEN)
				.build();
	}
}
