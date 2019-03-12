package com.chat.authentication;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.chat.authentication.utils.RandomStringUtils;

@Component
public class AuthenticationTokenProviderImpl implements AuthenticationTokenProvider {
	private final Supplier<String> randomStringProvider = RandomStringUtils::getRandomString;

	@Override
	public String getToken() {
		return randomStringProvider.get();
	}
}
