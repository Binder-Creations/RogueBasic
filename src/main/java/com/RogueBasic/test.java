package com.RogueBasic;

import java.util.UUID;

import com.RogueBasic.beans.Equipment;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.EquipmentDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class test {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(session);
		cu.dropAllTables();
		cu.initialize();
		cu.populate();
//		PlayerDao pdao = new PlayerDao(session);
//		Player p = new Player("taco", "taco");
//		pdao.save(p);
//		PlayerCharacterDao pcdao = new PlayerCharacterDao(session);
//		PlayerCharacter pc = new PlayerCharacter(UUID.randomUUID(), "taco", "taco", 0, 0, 0, 0);
//		pcdao.save(pc);
//		System.out.println(pcdao.findById(pc.getId()).toString());
//		EquipmentDao edao = new EquipmentDao(session);
//		Equipment e = new Equipment();
//		pc
	}
}
