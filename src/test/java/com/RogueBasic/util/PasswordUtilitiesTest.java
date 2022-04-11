package com.RogueBasic.util;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

public class PasswordUtilitiesTest {
	String testPassword = "password";
	ByteBuffer testSalt = ByteBuffer.wrap(new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
	
	@Test
	public void getSaltTest() {
		ByteBuffer salt = PasswordUtilities.getSalt();
		assertNotNull(salt);
		assumeTrue(salt != null);
		assertTrue(salt.array().length == 16);
	}
	
	@Test
	public void hashTest() {
		ByteBuffer hash = PasswordUtilities.hash(testPassword, testSalt);
		assertNotNull(hash);
		assumeTrue(hash != null);
		assertEquals(hash.array().length, testPassword.length()*2 + testSalt.array().length);
	}
	
	@Test
	public void isExpectedPasswordTest() {
		assertTrue(PasswordUtilities.isExpectedPassword(testPassword, testSalt, PasswordUtilities.hash(testPassword, testSalt)));	
	}
	
	
}
