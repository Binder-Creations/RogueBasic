package com.RogueBasic;

import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class Initialize {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(CassandraConnector.getSession());
		cu.dropAllTables();
		cu.initialize();		
	}
}
