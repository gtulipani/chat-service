package com.chat.controller;

import static com.chat.mother.UserMother.aUser;
import static com.chat.mother.UserMother.aUserCreationResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.concurrent.Callable;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.User;
import com.chat.entity.UserCreationResponseEntity;
import com.chat.service.UsersService;

public class UsersControllerImplTest {
	private UsersController usersController;

	@Mock
	private UsersService usersService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		usersController = new UsersControllerImpl(usersService);
	}

	@Test
	public void testCreateUser_returnsId() throws Exception {
		User user = aUser();
		UserCreationResponseEntity userCreationResponseEntity = aUserCreationResponseEntity();
		when(usersService.createUser(user)).thenReturn(userCreationResponseEntity);

		ResponseEntity responseEntity = usersController.createUser(user).call();

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(userCreationResponseEntity);
	}
}
