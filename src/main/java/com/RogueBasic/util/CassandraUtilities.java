package com.RogueBasic.util;

import com.datastax.driver.core.Session;

public class CassandraUtilities {
	CassandraConnector cc = new CassandraConnector();
	Session session = cc.getSession();
	
	public void initialize() {
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.PLAYER (id uuid PRIMARY KEY, userName text, password text, characterIds list<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)");
	}
}
