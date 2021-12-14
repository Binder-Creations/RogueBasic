package com.RogueBasic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Flags;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Monster;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.data.ShopDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.services.MonsterServices;
import com.RogueBasic.services.PlayerCharacterServices;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class test {
	public static void main(String[] args) {
		CqlSession session = CassandraConnector.connect();
		CassandraUtilities cu = new CassandraUtilities(session);
//		RogueUtilities ru = new RogueUtilities();
//		cu.dropAllTables();
//		cu.initialize();
//		cu.populate();
//		PlayerCharacterDao pcd = new PlayerCharacterDao(session);
//		Flags flags = new Flags("hit");
//		Ability ability = new Ability();
//		ability.setPcFlags(flags);
//		PlayerCharacter pc = new PlayerCharacter();
//		pc.setId(UUID.randomUUID());
//		HashSet<Ability> abilities = new HashSet<>();
//		abilities.add(ability);
//		pc.setAbilities(abilities);
//		System.out.println(pc.toString());
//		pcd.save(pc);
//		PlayerCharacter pcLoad = pcd.findById(pc.getId());
//		System.out.println(pcLoad.toString());
;		
		
//		Set<Monster> monsters = MonsterServices.generate(6, "Castle", 1, true, false);
//		
//		monsters.forEach(monster -> {
//			System.out.println("Id: "+monster.getId());
//			System.out.println("Name: "+monster.getName());
//			System.out.println("Level: "+monster.getLevel());
//			System.out.println("Rank: "+(monster.isBoss() ? "Boss" : monster.isMiniboss() ? "Miniboss" : "Normal"));
//			System.out.println("Species: "+monster.getSpecies());
//			System.out.println("Type: "+monster.getType());
//			System.out.println("Position: "+monster.getPosition());
//			System.out.println("Variant: "+monster.getVariant());
//			System.out.println("Health: "+monster.getHealthTotal());
//			System.out.println("Power: "+monster.getPower());
//			System.out.println("Armor: "+monster.getArmor());
//			System.out.println("Crit: "+monster.getCritRating());
//			System.out.println("Dodge: "+monster.getDodgeRating());
//			System.out.println("Abilities: "+monster.getAbilities().toString());
//		});
		
//		test test = new test();
//		System.out.println(test.genLV(1, 1, null, false, false, true, false));
//		String[] exceptions = {};
//		test.genEquipment(exceptions, 50, 1).forEach(i ->{System.out.println(i.toString()); System.out.println("~~~");});
//		for(int i = 0; i < 50; i++) {
//		}
//		ShopDao sDao = new ShopDao(session);
//		System.out.println(sDao.findById(UUID.fromString("db56131c-9248-46cc-8b6a-0d022228aa10")));
//		sDao.getAll().forEach(item -> System.out.println(item));
//		DungeonServices du = new DungeonServices(session);

//		PlayerCharacterDao pcd = new PlayerCharacterDao(session);
//		PlayerCharacter pc = new PlayerCharacter("t", "Rogue", 10, 10, 10, 10, 10);
//		FloorDao fd = new FloorDao(session);
//		RoomDao rd = new RoomDao(session);
//		ItemDao idd = new ItemDao(session);
//		pcd.save(pc);
//		Dungeon d = du.addFloors(du.generateShell(pc.getId()));
//		System.out.println(d.toString());
//		for(UUID floor: d.getFloorIds()) {
//			Floor floorO = fd.findById(floor);
//			System.out.println(floorO.toString());
//			for(UUID room: floorO.getRoomIds()) {
//				Room roomO = rd.findById(room);
//				System.out.println(roomO.toString());
//				if(roomO.getLoot() != null) {
//					for(UUID item: roomO.getLoot().keySet()) {
//						System.out.println(idd.findById(item).toString());
//					}
//				}
//			}
//		}
//		Pattern name = Pattern.compile("(\\w+)\\s+");
//		ru.readFileToList("source/tables.rbt")
//		  .forEach((s)->{
//			  Matcher m = name.matcher(s);
//			  m.find();
//			  System.out.println(m.group(1));});
////
//		ItemDao id = new ItemDao(session);
//		List<Item> ilist = id.getAll();
//		for(Item i: ilist) {
//			System.out.println(i.toString());
//		}
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
