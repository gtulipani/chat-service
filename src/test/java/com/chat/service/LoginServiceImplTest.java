package com.chat.service;

import static com.chat.mother.TokenMother.aToken;
import static com.chat.mother.UserMother.aUserWithoutId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.Token;
import com.chat.entity.model.User;

public class LoginServiceImplTest {
	private LoginServiceImpl loginService;

	@Mock
	private UserService userService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		loginService = new LoginServiceImpl(userService);
	}

	@Test
	public void createToken() {
		User userWithoutId = aUserWithoutId();
		Token token = aToken();
		when(loginService.createToken(userWithoutId)).thenReturn(token);

		assertThat(loginService.createToken(userWithoutId)).isEqualTo(token);
	}
}
