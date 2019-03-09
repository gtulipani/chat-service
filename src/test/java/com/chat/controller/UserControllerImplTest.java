package com.chat.controller;

import static com.chat.mother.UserMother.aUser;
import static com.chat.mother.UserMother.aUserCreationResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.User;
import com.chat.entity.UserCreationResponseEntity;
import com.chat.service.UserService;

public class UserControllerImplTest {
	private UserController userController;

	@Mock
	private UserService userService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		userController = new UserControllerImpl(userService);
	}

	@Test
	public void testCreateUser_returnsId() throws Exception {
		User user = aUser();
		UserCreationResponseEntity userCreationResponseEntity = aUserCreationResponseEntity();
		when(userService.createUser(user)).thenReturn(userCreationResponseEntity);

		ResponseEntity responseEntity = userController.createUser(user).call();

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(userCreationResponseEntity);
	}
}
