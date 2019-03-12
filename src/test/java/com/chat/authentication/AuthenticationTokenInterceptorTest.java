package com.chat.authentication;

import static com.chat.mother.TokenMother.RANDOM_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.exception.AuthenticationTokenException;
import com.chat.service.UserServiceImpl;

public class AuthenticationTokenInterceptorTest {
	private static final String AUTHORIZATION_HEADER = "authorization";
	private static final String BEARER_TOKEN_PREFIX = "Bearer";
	private static final String BEARER_TOKEN_FORMAT = "%s %s";
	private static final String BEARER_TOKEN = String.format(BEARER_TOKEN_FORMAT, BEARER_TOKEN_PREFIX, RANDOM_TOKEN);
	private static final String INVALID_TOKEN = RANDOM_TOKEN;
	private static final String POST_METHOD = "POST";

	private AuthenticationTokenInterceptor authenticationTokenInterceptor;

	@Mock
	private UserServiceImpl userService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);

		authenticationTokenInterceptor = new AuthenticationTokenInterceptor(userService);
	}

	@Test
	public void testPreHandle() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN);
		request.setMethod(POST_METHOD);
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertThat(authenticationTokenInterceptor.preHandle(request, response, null)).isTrue();
		verify(userService, times(1)).verifyToken(RANDOM_TOKEN);
	}

	@Test
	public void testPreHandleWithoutAuthorizarationHeader_throwsAuthenticationTokenException() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertThatExceptionOfType(AuthenticationTokenException.class)
				.isThrownBy(() -> authenticationTokenInterceptor.preHandle(request, response, null))
				.withMessage("Invalid authentication type");
		verify(userService, never()).verifyToken(any());
	}

	@Test
	public void testPreHandleWithInvalidAuthorizarationHeader_throwsAuthenticationTokenException() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(AUTHORIZATION_HEADER, INVALID_TOKEN);
		request.setMethod(POST_METHOD);
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertThatExceptionOfType(AuthenticationTokenException.class)
				.isThrownBy(() -> authenticationTokenInterceptor.preHandle(request, response, null))
				.withMessage("Invalid authentication type");
		verify(userService, never()).verifyToken(any());
	}

	@Test
	public void testPreHandle_userServiceThrowsException() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(AUTHORIZATION_HEADER, BEARER_TOKEN);
		request.setMethod(POST_METHOD);
		MockHttpServletResponse response = new MockHttpServletResponse();
		doThrow(new AuthenticationTokenException("Another error")).when(userService).verifyToken(RANDOM_TOKEN);

		assertThatExceptionOfType(AuthenticationTokenException.class)
				.isThrownBy(() -> authenticationTokenInterceptor.preHandle(request, response, null))
				.withMessage("Another error");
		verify(userService, times(1)).verifyToken(RANDOM_TOKEN);
	}
}
