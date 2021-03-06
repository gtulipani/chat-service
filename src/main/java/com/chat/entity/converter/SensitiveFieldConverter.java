package com.chat.entity.converter;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.chat.security.EncryptionService;

@Slf4j
@Component
@Converter
public class SensitiveFieldConverter implements AttributeConverter<String, String> {
	private static final String MASK_PATTERN = "(?<=.{4})(.*)$";
	private static final String MASK = "***";

	private static EncryptionService encryptionService;

	@Autowired
	public void init(EncryptionService encryptionService) {
		SensitiveFieldConverter.encryptionService = encryptionService;
	}

	@Override
	public String convertToDatabaseColumn(String value) {
		try {
			return Objects.nonNull(value) ? encryptionService.encrypt(value) : null;
		} catch (Exception e) {
			log.warn("Unable to encrypt data to database, storing plain value={}", value.replaceAll(MASK_PATTERN, MASK));
			return value;
		}
	}

	@Override
	public String convertToEntityAttribute(String value) {
		try {
			return Objects.nonNull(value) ? encryptionService.decrypt(value) : null;
		} catch (Exception e) {
			log.warn("Unable to decrypt data from database, retrieving plain value={}", value.replaceAll(MASK_PATTERN, MASK));
			return value;
		}
	}
}
