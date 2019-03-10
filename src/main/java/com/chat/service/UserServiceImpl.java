package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;
import com.chat.repository.UserRepository;
import com.chat.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserCreationResponseEntity createUser(User user) {
		return userMapper.map(userRepository.save(user), UserCreationResponseEntity.class);
	}
}
