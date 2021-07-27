package com.RogueBasic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.beans.*;
import com.RogueBasic.data.*;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import java.util.List;

public class Driver {
//  public static void main(String[] args) {
//      try (Cluster cluster = Cluster.builder()
//                                    .addContactPoint("127.0.0.1")
//                                    .withPort(9042)
//                                    .withoutJMXReporting()
//                                    .build()) {
//          Session session = cluster.connect("rogue_basic");
//          session.execute("CREATE TABLE IF NOT EXISTS "
//                  + "rogue_basic.PLAYER (id uuid PRIMARY KEY, userName text, password text, characterIds list<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)");
//          MappingManager manager = new MappingManager(session);
//          Mapper<Player> mapper = manager.mapper(Player.class);
//
//          //save person
//          Player p = new Player("Tester", "test123");
//          mapper.save(p);
//          executeNativeSelectQuery(session);
//
//          //getting via mapper by primary key
//          Player person = mapper.get(p.getId());
//          System.out.println(person);
//
//          //getting via mapper by primary key
//          mapper.delete(p.getId());//delete by primary key
//          executeNativeSelectQuery(session);
//          session.execute("DROP TABLE rogue_basic.PLAYER");
//      }
//  }
//
//  private static void executeNativeSelectQuery(Session session) {
//      System.out.println("-- select query --");
//      ResultSet rs = session.execute("select * from rogue_basic.Player");
//      List<Row> rows = rs.all();
//      System.out.println("rows: " + rows.size());
//      for (Row row : rows) {
//          UUID id = row.getUUID("id");
//          System.out.println("Id: " + id);
//          String name = row.getString("userName");
//          System.out.println("name: " + name);
//          String password = row.getString("password");
//          System.out.println("password: " + password);
//          int constitutionMetabonus = row.getInt("constitutionMetabonus");
//          System.out.println("cm: " + constitutionMetabonus);
//      }
//  }
	
	public static void main(String[] args) {
		CassandraConnector cc = new CassandraConnector();
		cc.connect();
		Session session = cc.getSession();
		CassandraUtilities cu = new CassandraUtilities(session);
		cu.dropAllTables();
		cu.initialize();
		long startTime = System.nanoTime();
		AbilityDao dao = new AbilityDao(session);
		Ability a = new Ability("Spray", "Sprays", ()->System.out.println("sppp"));
		Ability b = new Ability("Flare", "Flares", ()->System.out.println("pfff"));
//		DungeonDao dao = new DungeonDao(session);
//		Dungeon a = new Dungeon("taco", "taco", "taco", 1, 2, false, false);
//		Dungeon b = new Dungeon("taco", "taco", "taco", 1, 2, false, false);
//		ItemDao dao = new ItemDao(session);
//		Item a = new Item("taco", "taco", 3, 4.5);
//		Item b = new Item("taco", "taco", 3, 4.5);
//		EquipmentDao dao = new EquipmentDao(session);
//		Equipment a = new Equipment("taco", "taco", 1, 3.4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5);
//		Equipment b = new Equipment("taco", "taco", 1, 3.4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5);
//		FloorDao dao = new FloorDao(session);
//		Set<UUID> sa = new HashSet<>();
//		sa.add(UUID.randomUUID());
//		sa.add(UUID.randomUUID());
//		Floor a = new Floor(1, 2, 3, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), sa);
//		Floor b = new Floor(1, 2, 3, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), sa);
//		RoomDao dao = new RoomDao(session);
//		Room a = new Room(UUID.randomUUID(), UUID.randomUUID(), 1, 2, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), sa, sa);
//		Room b = new Room(UUID.randomUUID(), UUID.randomUUID(), 1, 2, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), sa, sa);
//		MonsterDao dao = new MonsterDao(session);
//		Monster a = new Monster("taco", "taco", true, false, 1, 2, 3, 4, 5, 6, sa);
//		Monster b = new Monster("taco", "taco", true, false, 1, 2, 3, 4, 5, 6, sa);
		dao.save(a);
		dao.save(b);
		dao.getAll().stream().forEach(p -> System.out.println(p.toString()));
		long endTime = System.nanoTime();
		System.out.println((endTime-startTime)/1000000);
	}
}
