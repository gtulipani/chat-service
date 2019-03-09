package com.chat.entity.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import org.testng.annotations.Test;

public class DateUtilsTest {
	@Test
	public void testToLocalDateTime() {
		Timestamp timestamp = Timestamp.from(Instant.now());

		LocalDateTime localDateTime = DateUtils.toLocalDateTime(timestamp);

		assertThat(localDateTime).isEqualTo(timestamp.toLocalDateTime());
	}

	@Test
	public void testToLocalDateTime_fromNull() {
		Timestamp timestamp = null;

		assertThat(DateUtils.toLocalDateTime(timestamp)).isNull();
	}

	@Test
	public void testToTimestamp() {
		LocalDateTime localDateTime = LocalDateTime.now();

		Timestamp timestamp = DateUtils.toTimestamp(localDateTime);

		assertThat(timestamp).hasYear(localDateTime.getYear());
		assertThat(timestamp).hasMonth(localDateTime.getMonthValue());
		assertThat(timestamp).hasDayOfMonth(localDateTime.getDayOfMonth());
		assertThat(timestamp).hasHourOfDay(localDateTime.getHour());
		assertThat(timestamp).hasMinute(localDateTime.getMinute());
		assertThat(timestamp).hasSecond(localDateTime.getSecond());
		assertThat(timestamp).hasMillisecond(localDateTime.get(ChronoField.MILLI_OF_SECOND));
	}

	@Test
	public void testToTimestamp_fromNull() {
		LocalDateTime localDateTime = null;

		assertThat(DateUtils.toTimestamp(localDateTime)).isNull();
	}
}
