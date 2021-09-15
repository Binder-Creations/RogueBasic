package com.RogueBasic;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterExport;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class test {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		System.out.println(session);
		CassandraUtilities cu = new CassandraUtilities(session);
		cu.dropAllTables();
		cu.initialize();
		cu.populate();

		ItemDao id = new ItemDao(session);
		List<Item> ilist = id.getAll();
		for(Item i: ilist) {
			System.out.println(i.toString());
		}
		
//		
//		Equipment eqq = new Equipment(UUID.fromString("16b9203f-5560-44b9-9d0a-e8fc5139f6f5"), "Test", "test", "test", 0, "test");
//		ed.save(eqq);
//		Equipment eq = ed.findById(UUID.fromString("16b9203f-5560-44b9-9d0a-e8fc5139f6f5"));
//		System.out.println(eq.toString());
//		List<Item> ilist = id.getAll();
//		for(Item i: ilist) {
//			System.out.println(i.toString());
//		}
		
//		PlayerDao pdao = new PlayerDao(session);
//		PlayerCharacterDao pcdao = new PlayerCharacterDao(session);
//		System.out.println(new PlayerCharacterExport(pcdao.findById(UUID.fromString(id))));
//		Player p = new Player("taco", "taco");
//		pdao.save(p);
//		PlayerCharacterDao pcdao = new PlayerCharacterDao(session);
//		PlayerCharacter pc = new PlayerCharacter("taco", "taco", 0, 0, 0, 0, 0);
//		pcdao.save(pc);
//		System.out.println(pcdao.getAll());
//		EquipmentDao edao = new EquipmentDao(session);
//		Equipment e = new Equipment();
//		pc
//		for(int i = 0; i < 20; i++) {
//			System.out.println(UUID.randomUUID());
//		}
	}
}
