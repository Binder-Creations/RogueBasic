package com.RogueBasic.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraConnectorTest {
	
	
	@Test
	public void connectTest() {
		
		//A successful connection should generate a session
		try(CqlSession session = CassandraConnector.connect()){
			assertNotNull(session);
		}
	}

	@Test
	public void closeTest() {
		
		//Statements should be executable while connected, but not after the connection closes
		try(CqlSession session = CassandraConnector.connect()){
			assertNotNull(session.execute("CREATE TABLE rogue.test (id text PRIMARY KEY);"));
			session.execute("DROP TABLE IF EXISTS rogue.test");
			CassandraConnector.close();
			assertThrows(Exception.class, ()->session.execute("CREATE TABLE rogue.test (id text PRIMARY KEY);"));
		}
	}
	
}
