package com.chat.service;

import com.chat.entity.model.Token;
import com.chat.entity.model.User;

public interface LoginService {
	Token createToken(User user);
}
