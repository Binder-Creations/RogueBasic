package com.RogueBasic;

import java.util.UUID;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class Test {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(session);
//		cu.dropAllTables();
//		cu.initialize();
		PlayerDao pd = new PlayerDao(session);
		Player p = new Player("testing", "hotsauce");
		PlayerCharacterDao pcd = new PlayerCharacterDao(session);
		pd.findByName("flomp").getCharacterIds().forEach((pc)->System.out.println(pcd.findById(pc)));
	}
}
