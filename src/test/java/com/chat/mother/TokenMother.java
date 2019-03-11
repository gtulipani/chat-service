package com.chat.mother;

import java.util.UUID;

import com.chat.entity.model.Token;

public class TokenMother {
	private static final String RANDOM_TOKEN = UUID.randomUUID().toString();
	
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
