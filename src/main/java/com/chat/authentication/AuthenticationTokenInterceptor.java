package com.chat.authentication;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chat.exception.AuthenticationTokenException;
import com.chat.service.UserService;

@Component
public class AuthenticationTokenInterceptor extends HandlerInterceptorAdapter {
	private static final String AUTHORIZATION_HEADER = "authorization";
	private static final String OPTIONS_REQUEST = "OPTIONS";
	private static final String BEARER_TOKEN_PREFIX = "Bearer ";

	private final UserService userService;

	@Autowired
	public AuthenticationTokenInterceptor(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String authHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (!OPTIONS_REQUEST.equals(request.getMethod())) {
			if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
				throw new AuthenticationTokenException("Invalid authentication type");
			}

			String token = StringUtils.substringAfter(authHeader, BEARER_TOKEN_PREFIX);
			userService.verifyToken(token);
		}
		return true;
	}
}
