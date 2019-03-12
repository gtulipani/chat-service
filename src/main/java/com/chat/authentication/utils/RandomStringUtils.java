package com.chat.authentication.utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomStringUtils {
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
	private static final String DIGITS = "0123456789";
	private static final String ALPHANUM = UPPER + LOWER + DIGITS;

	public static String getRandomString(int length, Random random, String symbols) {
		if (length < 1) throw new IllegalArgumentException();
		if (symbols.length() < 2) throw new IllegalArgumentException();
		return createRandomString(length, random, symbols);
	}

	/**
	 * Create an alphanumeric string generator.
	 */
	public static String getRandomString(int length, Random random) {
		return getRandomString(length, random, ALPHANUM);
	}

	/**
	 * Create an alphanumeric strings from a secure generator.
	 */
	public static String getRandomString(int length) {
		return getRandomString(length, new SecureRandom());
	}

	/**
	 * Create session identifiers.
	 */
	public static String getRandomString() {
		return getRandomString(21);
	}

	/**
	 * Generate a random string.
	 */
	private static String createRandomString(int length, Random random, String symbols) {
		Objects.requireNonNull(random);
		char[] symbolsArray = symbols.toCharArray();
		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbolsArray[random.nextInt(symbolsArray.length)];
		return new String(buf);
	}
}
