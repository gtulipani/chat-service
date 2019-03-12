package com.chat.service;

import com.chat.entity.model.Token;
import com.chat.entity.model.User;
import com.chat.entity.model.UserCreationResponseEntity;

public interface UserService {
	UserCreationResponseEntity createUser(User user);

	User getUserById(Long id);

	Token refreshToken(User user);
}
