package com.chat.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.authentication.AuthenticationTokenProvider;
import com.chat.entity.model.Token;
import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;
import com.chat.exception.LoginException;
import com.chat.exception.UserNotFoundException;
import com.chat.repository.UserRepository;
import com.chat.service.mapper.UserMapper;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final AuthenticationTokenProvider authenticationTokenProvider;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthenticationTokenProvider authenticationTokenProvider) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.authenticationTokenProvider = authenticationTokenProvider;
	}

	@Override
	@Transactional
	public UserCreationResponseEntity createUser(User user) {
		User storedUser = userRepository.save(user);
		log.info("Successfully created message with userName={}", user.getUsername());
		return userMapper.map(storedUser, UserCreationResponseEntity.class);
	}

	@Override
	@Transactional
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	@Transactional
	public Token refreshToken(User user) {
		User storedUser = userRepository.findByUsername(user.getUsername())
				.orElseThrow(() -> new UserNotFoundException(user.getUsername()));

		if (!storedUser.getPassword().equals(user.getPassword())) {
			throw new LoginException(user.getUsername());
		}

		Token newToken = Token.builder()
				.id(storedUser.getId())
				.token(authenticationTokenProvider.getToken())
				.build();
		storedUser.setToken(newToken.getToken());
		userRepository.save(storedUser);

		log.info("Successfully created token for user with userName={}", user.getUsername());
		return newToken;
	}

	@Override
	@Transactional
	public void verifyToken(String token) {
		return ;
	}
}
