package com.chat.security;

import com.chat.exception.EncryptionException;

public interface EncryptionService {
	String encrypt(String value) throws EncryptionException;
	String decrypt(String value) throws EncryptionException;
}
