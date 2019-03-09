package com.chat.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControllerImpl implements HealthController {
	private final static String HEALTH = "health";
	private final static String HEALTH_OK = "ok";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public HealthControllerImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> check() {
		if (this.jdbcTemplate.queryForObject("SELECT 1", Integer.class) != 1) {
			throw new RuntimeException("Unexpected query result");
		}
		return Collections.singletonMap(HEALTH, HEALTH_OK);
	}

}
