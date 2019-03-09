package com.chat.entity.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.exception.EncryptionException;
import com.chat.security.EncryptionService;

public class SensitiveFieldConverterTest {
	private static final String UNENCRYPTED = "unencrypted";
	private static final String ENCRYPTED = "encrypted";

	@Mock
	private EncryptionService encryptionService;

	private SensitiveFieldConverter sensitiveFieldConverter;

	@BeforeMethod
	public void setUp() throws EncryptionException {
		initMocks(this);

		when(encryptionService.encrypt(eq(UNENCRYPTED))).thenReturn(ENCRYPTED);
		when(encryptionService.decrypt(eq(ENCRYPTED))).thenReturn(UNENCRYPTED);

		sensitiveFieldConverter = new SensitiveFieldConverter();
		sensitiveFieldConverter.init(encryptionService);
	}

	@Test
	public void testConvertToDatabaseColumn() {
		String encryptedValue = sensitiveFieldConverter.convertToDatabaseColumn(UNENCRYPTED);

		assertThat(encryptedValue).isEqualTo(ENCRYPTED);
	}

	@Test
	public void testConvertToDatabaseColumn_nullValue() {
		String encryptedValue = sensitiveFieldConverter.convertToDatabaseColumn(null);

		assertThat(encryptedValue).isNull();
	}

	@Test
	public void testConvertToDatabaseColumn_noEncryption() {
		when(encryptionService.encrypt(eq(UNENCRYPTED))).thenThrow(new EncryptionException());

		String encryptedValue = sensitiveFieldConverter.convertToDatabaseColumn(UNENCRYPTED);

		assertThat(encryptedValue).isEqualTo(UNENCRYPTED);
	}

	@Test
	public void testConvertToEntityAttribute() {
		String unencryptedValue = sensitiveFieldConverter.convertToEntityAttribute(ENCRYPTED);

		assertThat(unencryptedValue).isEqualTo(UNENCRYPTED);
	}

	@Test
	public void testConvertToEntityAttribute_nullValue() {
		String unencryptedValue = sensitiveFieldConverter.convertToEntityAttribute(null);

		assertThat(unencryptedValue).isNull();
	}

	@Test
	public void testConvertToEntityAttribute_noEncryption() {
		when(encryptionService.decrypt(eq(ENCRYPTED))).thenThrow(new EncryptionException());

		String unencryptedValue = sensitiveFieldConverter.convertToEntityAttribute(ENCRYPTED);

		assertThat(unencryptedValue).isEqualTo(ENCRYPTED);
	}
}
