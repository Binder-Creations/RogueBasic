package com.RogueBasic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.RogueBasic.util.CassandraConnector;
import com.datastax.driver.core.Session;

public class CassandraConnectorTest {
	private static CassandraConnector cc;
	
	@BeforeAll
	private static void setUp() {
		cc = new CassandraConnector();
	}
	
	@Test
	private void connectTest() {
		Session session = cc.connect();
		assertNotNull(session);
		assertEquals(session.getLoggedKeyspace(), "rogue");
	}

	@Test
	private void closeTest() {
		Session session = cc.connect();
		cc.close();
		assertNull(session);
	}
	
}
