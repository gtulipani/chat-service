package com.chat.controller;

import static com.chat.mother.TokenMother.aToken;
import static com.chat.mother.UserMother.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.Token;
import com.chat.entity.model.User;
import com.chat.service.LoginService;

public class LoginControllerImplTest {
	private LoginControllerImpl loginController;

	@Mock
	private LoginService loginService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		loginController = new LoginControllerImpl(loginService);
	}

	@Test
	public void testLogin() throws Exception {
		User user = aUser();
		Token token = aToken();
		when(loginService.createToken(user)).thenReturn(token);

		ResponseEntity result = loginController.login(user).call();

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(result.getBody()).isEqualTo(token);
	}
}
