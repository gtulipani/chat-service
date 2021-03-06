package com.chat.entity.converter;

import static com.chat.entity.utils.DateUtils.toLocalDateTime;
import static com.chat.entity.utils.DateUtils.toTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return toTimestamp(localDateTime);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		return toLocalDateTime(timestamp);
	}
}
