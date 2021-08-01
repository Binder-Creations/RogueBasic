package com.RogueBasic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.DriverInternalError;

public class CassandraConnectorTest {
	private static CassandraConnector cc;
	
	@BeforeAll
	private static void setUp() {
		cc = new CassandraConnector();
	}
	
	@Test
	public void connectTest() {
		
		//A successful connection should generate a session with our keyspace
		Session session = cc.connect();
		assertNotNull(session);
		assertEquals(session.getLoggedKeyspace(), "rogue");
	}

	@Test
	public void closeTest() {
		
		//Statements should be executable while connected, but not after the connection closes
		Session session = cc.connect();
		assertNotNull(session.execute("CREATE TABLE rogue.test (id text PRIMARY KEY);"));
		session.execute("DROP TABLE IF EXISTS rogue.test");
		cc.close();
		assertThrows(DriverInternalError.class, ()->session.execute("CREATE TABLE rogue.test (id text PRIMARY KEY);"));
	}
	
}
