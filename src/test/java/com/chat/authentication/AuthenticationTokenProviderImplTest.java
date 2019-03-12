package com.chat.authentication;

import static com.chat.mother.TokenMother.RANDOM_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Supplier;

import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthenticationTokenProviderImplTest {
	private AuthenticationTokenProviderImpl authenticationTokenProvider;

	@BeforeMethod
	public void setup() {
		authenticationTokenProvider = new AuthenticationTokenProviderImpl();
	}

	@Test
	public void testGetToken() {
		Supplier<String> randomStringProvider = () -> RANDOM_TOKEN;
		ReflectionTestUtils.setField(authenticationTokenProvider, 
				"randomStringProvider",
				randomStringProvider);

		assertThat(authenticationTokenProvider.getToken()).isEqualTo(randomStringProvider.get());
	}

	@Test
	public void testGetToken_areDifferent() {
		String firstToken = authenticationTokenProvider.getToken();
		String secondToken = authenticationTokenProvider.getToken();

		assertThat(firstToken).isNotEqualTo(secondToken);
	}
}
