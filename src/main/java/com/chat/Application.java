package com.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chat.authentication.AuthenticationTokenInterceptor;
import com.chat.service.UserService;

@SpringBootApplication
public class Application implements WebMvcConfigurer {
	private final UserService userService;

	@Autowired
	public Application(UserService userService) {
		this.userService = userService;
	}

	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationTokenInterceptor(userService)).addPathPatterns("/messages");
	}
}
