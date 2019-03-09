package com.chat.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chat.exception.EncryptionException;
import com.google.common.collect.Sets;

@Slf4j
@Service
public class AESEncryptionServiceImpl implements EncryptionService {
	private static final String CHARSET = "UTF-8";
	private static final String PADDING_KEY = "0000000000000000";
	private static final String CIPHER_TYPE = "AES/CBC/PKCS5PADDING";
	private static final String ALGORITHM = "AES";

	private final String key;
	private final String initVector;
	private final Set<Integer> acceptedKeyLengths;

	public AESEncryptionServiceImpl(@Value("${encryption.key}") String key,
									@Value("${encryption.initVector}") String initVector) {
		this.key = key;
		this.initVector = initVector;
		this.acceptedKeyLengths = Sets.newHashSet();
		acceptedKeyLengths.add(16);
		acceptedKeyLengths.add(24);
		acceptedKeyLengths.add(32);
	}

	@Override
	public String encrypt(String value) throws EncryptionException {
		try {
			return Base64.encodeBase64String(getCipher(Cipher.ENCRYPT_MODE).doFinal(value.getBytes()));
		} catch (Exception e) {
			log.error("Failed to encrypt data - Error={}", e);
			throw new EncryptionException(e);
		}
	}

	@Override
	public String decrypt(String value) throws EncryptionException {
		try {
			return new String(getCipher(Cipher.DECRYPT_MODE).doFinal(Base64.decodeBase64(value)));
		} catch (Exception e) {
			log.error("Failed to decrypt data - Error={}", e);
			throw new EncryptionException(e);
		}
	}

	private String getKey() {
		int keyLength = key.length();
		if (!acceptedKeyLengths.isEmpty()) {
			Integer min = acceptedKeyLengths.stream().min(Integer::compareTo).orElse(0);
			if (acceptedKeyLengths.contains(keyLength)) {
				return key;
			} else if (keyLength < min) {
				return PADDING_KEY.substring(keyLength) + key;
			} else {
				return key.substring(0, min);
			}
		}
		return key;
	}

	private Cipher getCipher(int mode) throws GeneralSecurityException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		SecretKeySpec skeySpec = new SecretKeySpec(getKey().getBytes(CHARSET), ALGORITHM);
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(CHARSET));
		cipher.init(mode, skeySpec, iv);
		return cipher;
	}
}
