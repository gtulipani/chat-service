package com.chat.service;

import com.chat.entity.User;
import com.chat.entity.UserCreationResponseEntity;

public interface UserService {
	UserCreationResponseEntity createUser(User user);
}
