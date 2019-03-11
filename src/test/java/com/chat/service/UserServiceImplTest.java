package com.chat.service;

import static com.chat.mother.UserMother.aUser;
import static com.chat.mother.UserMother.aUserCreationResponseEntity;
import static com.chat.mother.UserMother.aUserWithoutId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;
import com.chat.exception.UserNotFoundException;
import com.chat.repository.UserRepository;
import com.chat.service.mapper.UserMapper;

public class UserServiceImplTest {
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserMapper userMapper;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		userService = new UserServiceImpl(userRepository, userMapper);
	}

	@Test
	public void testCreateUser_noErrors() {
		User userWithoutId = aUserWithoutId();
		User user = aUser();
		UserCreationResponseEntity userCreationResponseEntity = aUserCreationResponseEntity();
		when(userRepository.save(userWithoutId)).thenReturn(user);
		when(userMapper.map(user, UserCreationResponseEntity.class)).thenReturn(userCreationResponseEntity);

		assertThat(userService.createUser(userWithoutId)).isEqualTo(userCreationResponseEntity);
	}

	@Test
	public void testGetUserById_noErrors() {
		User user = aUser();
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

		assertThat(userService.getUserById(user.getId())).isEqualTo(user);
	}

	@Test
	public void testGetUserById_notFound_throwsUserNotFoundException() {
		User user = aUser();
		doThrow(new UserNotFoundException(user.getId())).when(userRepository).findById(user.getId());

		assertThatExceptionOfType(UserNotFoundException.class)
				.isThrownBy(() -> userService.getUserById(user.getId()))
				.withMessage("Couldn't find user with id=%s", user.getId());
	}
}
