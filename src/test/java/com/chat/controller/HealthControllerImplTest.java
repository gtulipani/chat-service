package com.chat.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HealthControllerImplTest {
	private final static String HEALTH = "health";
	private final static String HEALTH_OK = "ok";
	private final static String HEALTH_ERROR_MESSAGE = "Unexpected query result";

	private HealthController healthController;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		healthController = new HealthControllerImpl(jdbcTemplate);
	}

	@Test
	public void testCheck_returnsOk() {
		when(jdbcTemplate.queryForObject("SELECT 1", Integer.class)).thenReturn(1);

		Map<String, String> result = healthController.check();

		assertThat(result).containsExactly(entry(HEALTH, HEALTH_OK));
	}

	@Test
	public void testCheck_throwsRuntimeException() {
		when(jdbcTemplate.queryForObject("SELECT 1", Integer.class)).thenReturn(2);

		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> healthController.check())
				.withMessage(HEALTH_ERROR_MESSAGE);
	}
}
