package com.RogueBasic;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class Test {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(session);
		cu.dropAllTables();
		cu.initialize();
		PlayerDao pd = new PlayerDao(session);
		Player p = new Player("testing", "hotsauce");
		pd.save(p);
		pd.save(p);
		System.out.println(pd.findByName("testing").toString());
	}
}
