package com.RogueBasic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.beans.*;
import com.RogueBasic.data.*;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.RogueBasic.util.RogueUtilities;
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
			cc.close();
			cc.connect();
			
			Session session = cc.getSession();
			CassandraUtilities cu = new CassandraUtilities(session);
			cu.dropAllTables();
			cu.initialize();
//			RogueUtilities ru = new RogueUtilities();
//			DungeonServices ds = new DungeonServices(session);
//			PlayerCharacter pc = new Warrior(UUID.randomUUID(), "taco", 1, 1, 1, 1);
//			pc.setLevel(20);
//			PlayerCharacterDao pcd = new PlayerCharacterDao(session);
//			FloorDao fd = new FloorDao(session);
//			RoomDao rd = new RoomDao(session);
//			pcd.save(pc);
//			Dungeon d = ds.generate(pc.getId());
//			System.out.println(d.toString());
//			for(UUID id: d.getFloorIds()) {
//				Floor f = fd.findById(id);
//				System.out.println(f.toString());
//				for(UUID rid: f.getRoomIds()) {
//					Room r = rd.findById(rid);
//					System.out.println(r.toString());
//				}
//			}
//			for(int i = 0; i < 100; i++) {
//				Dungeon d = ds.generate(UUID.randomUUID());
//				System.out.println(d.getName() + ": " + d.getDescription());
//			}
//			CassandraUtilities cu = new CassandraUtilities(session);
//			cu.dropAllTables();
//			cu.initialize();
//			long startTime = System.nanoTime();
//			PlayerDao dao = new PlayerDao(null);
//			Player a = new Player("taco", "pass123");
//			dao.save(a);
//			System.out.println(a.equals(dao.findById(a.getId())));
//			Player b = new Player("Waffle", "admin");
//			dao.mapper.getManager().getConfiguration().getPropertyMapper().mapTable(Player.class).;
//			System.out.println(dao.mapper.getTableMetadata().toString());
//			AbilityDao dao = new AbilityDao(session);
//			Ability a = new Ability("Spray", "Sprays", "damage 4");
//			Ability b = new Ability("Flare", "Flares", 3, 4, "single", "dot 3");
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
//			UsableDao dao = new UsableDao(session);
//			UsableItem a = new UsableItem("taco", "cheesy", 10, 3.5, "pfft");
//			UsableItem b = new UsableItem("taco", "cheesy", 10, 3.5, "pfft");
//			dao.save(a);
//			dao.save(b);
//			dao.getAll().stream().forEach(p -> System.out.println(p.toString()));
//			long endTime = System.nanoTime();
//			System.out.println((endTime-startTime)/1000000);	
		System.out.println("taco");
	}
}
