package com.chat.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.chat.exception.EncryptionException;

public class AESEncryptionServiceImplTest {
	private static final String KEY = "1234567890123456";
	private static final String SMALLER_KEY = "123456";
	private static final String SMALLER_KEY_PADDED = "0000000000123456";
	private static final String BIGGER_KEY = "123456789012345678";
	private static final String BIGGER_KEY_PADDED = "1234567890123456";
	private static final String INIT_VECTOR = "initVector123456";
	private static final String INVALID_INIT_VECTOR = "initVector";

	private static final String UNENCRYPTED_DATA = "Data to Encrypt";
	private static final String ENCRYPTED_DATA = "y6vjor8/pMInpWeBCLR40Q==";

	private AESEncryptionServiceImpl aesEncryptionServiceImpl;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		aesEncryptionServiceImpl = new AESEncryptionServiceImpl(KEY, INIT_VECTOR);
	}

	@Test
	public void testEncrypt() {
		assertThat(aesEncryptionServiceImpl.encrypt(UNENCRYPTED_DATA)).isEqualTo(ENCRYPTED_DATA);
	}

	@Test
	public void testDecrypt() {
		assertThat(aesEncryptionServiceImpl.decrypt(ENCRYPTED_DATA)).isEqualTo(UNENCRYPTED_DATA);
	}

	@Test
	public void testGetKey_smallerSize() {
		aesEncryptionServiceImpl = new AESEncryptionServiceImpl(SMALLER_KEY, INIT_VECTOR);

		assertThat(aesEncryptionServiceImpl.getKey()).isEqualTo(SMALLER_KEY_PADDED);
	}

	@Test
	public void testGetKey_acceptedSize() {
		assertThat(aesEncryptionServiceImpl.getKey()).isEqualTo(KEY);
	}

	@Test
	public void testGetKey_biggerSize() {
		aesEncryptionServiceImpl = new AESEncryptionServiceImpl(BIGGER_KEY, INIT_VECTOR);

		assertThat(aesEncryptionServiceImpl.getKey()).isEqualTo(BIGGER_KEY_PADDED);
	}

	@Test
	public void testEncryptWithInvalidInitVector_throwsEncryptionException() {
		aesEncryptionServiceImpl = new AESEncryptionServiceImpl(KEY, INVALID_INIT_VECTOR);

		assertThatExceptionOfType(EncryptionException.class)
				.isThrownBy(() -> aesEncryptionServiceImpl.encrypt(UNENCRYPTED_DATA));
	}

	@Test
	public void testDecryptWithInvalidInitVector_throwsEncryptionException() {
		aesEncryptionServiceImpl = new AESEncryptionServiceImpl(KEY, INVALID_INIT_VECTOR);

		assertThatExceptionOfType(EncryptionException.class)
				.isThrownBy(() -> aesEncryptionServiceImpl.decrypt(ENCRYPTED_DATA));
	}
}
