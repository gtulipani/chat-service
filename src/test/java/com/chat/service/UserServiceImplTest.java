package com.chat.service;

import static com.chat.mother.TokenMother.RANDOM_TOKEN;
import static com.chat.mother.TokenMother.aToken;
import static com.chat.mother.UserMother.aDifferentUser;
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

import com.chat.authentication.AuthenticationTokenProvider;
import com.chat.authentication.AuthenticationTokenProviderImpl;
import com.chat.entity.model.Token;
import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;
import com.chat.exception.LoginException;
import com.chat.exception.UserNotFoundException;
import com.chat.repository.UserRepository;
import com.chat.service.mapper.UserMapper;

public class UserServiceImplTest {
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserMapper userMapper;
	@Mock
	private AuthenticationTokenProviderImpl authenticationTokenProvider;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		userService = new UserServiceImpl(userRepository, userMapper, authenticationTokenProvider);
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

	@Test
	public void testRefreshToken() {
		User userWithoutId = aUserWithoutId();
		User user = aUser();
		Token expectedToken = aToken();
		when(userRepository.findByUsername(userWithoutId.getUsername())).thenReturn(Optional.of(user));
		when(authenticationTokenProvider.getToken()).thenReturn(RANDOM_TOKEN);

		Token token = userService.refreshToken(userWithoutId);

		assertThat(token).isEqualTo(expectedToken);
	}

	@Test
	public void testRefreshTokenWithNotFoundUser_throwsUserNotFoundException() {
		User userWithoutId = aUserWithoutId();
		doThrow(new UserNotFoundException(userWithoutId.getUsername())).when(userRepository).findByUsername(userWithoutId.getUsername());

		assertThatExceptionOfType(UserNotFoundException.class)
				.isThrownBy(() -> userService.refreshToken(userWithoutId))
				.withMessage("Couldn't find user with username=%s", userWithoutId.getUsername());
	}

	@Test
	public void testRefreshTokenWithIncorrectPassword_throwsLoginException() {
		User userWithoutId = aUserWithoutId();
		User anotherUserWithoutId = aDifferentUser();
		when(userRepository.findByUsername(userWithoutId.getUsername())).thenReturn(Optional.of(anotherUserWithoutId));

		assertThatExceptionOfType(LoginException.class)
				.isThrownBy(() -> userService.refreshToken(userWithoutId))
				.withMessage("Couldn't authenticate user with userName=%s", userWithoutId.getUsername());
	}
}
