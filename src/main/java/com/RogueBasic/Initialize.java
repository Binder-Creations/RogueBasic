package com.RogueBasic;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class Initialize {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(session);
//		cu.dropAllTables();
		cu.initialize();
		
	}
}
