package com.RogueBasic.util;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtilities {

	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;

	private PasswordUtilities() { }

	public static ByteBuffer getSalt() {
		byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return ByteBuffer.wrap(salt);
	}

	public static ByteBuffer hash(String password, ByteBuffer salt) {
	    char[] passwordArray = password.toCharArray();
		PBEKeySpec spec = new PBEKeySpec(passwordArray, salt.array(), ITERATIONS, KEY_LENGTH);
	    Arrays.fill(passwordArray, Character.MIN_VALUE);
	    try {
	    	SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    	return ByteBuffer.wrap(skf.generateSecret(spec).getEncoded());
	    } catch (Exception e) {
	    	throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	    } finally {
	    	spec.clearPassword();
	    }
	}

	public static boolean isExpectedPassword(String password, ByteBuffer salt, ByteBuffer expectedHash) {
		byte[] pwdHash = hash(password, salt).array();
		byte[] expectedHashArray = expectedHash.array();
	    if (pwdHash.length != expectedHashArray.length) return false;
	    for (int i = 0; i < pwdHash.length; i++) {
	      if (pwdHash[i] != expectedHashArray[i]) return false;
	    }
	    return true;
	}	
}
