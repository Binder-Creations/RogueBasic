package com.RogueBasic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterExport;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.FloorDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.data.RoomDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.services.PlayerCharacterServices;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class test {
	public static void main(String[] args) {
		RogueUtilities ru = new RogueUtilities();
		
//		test test = new test();
//		System.out.println(test.genLV(1, 1, null, false, false, true, false));
//		String[] exceptions = {};
//		test.genEquipment(exceptions, 50, 1).forEach(i ->{System.out.println(i.toString()); System.out.println("~~~");});
//		for(int i = 0; i < 50; i++) {
//		}
		CqlSession session = CassandraConnector.connect();
//		System.out.println(session);
		CassandraUtilities cu = new CassandraUtilities(session);
//		DungeonServices du = new DungeonServices(session);
//		PlayerCharacterServices pcs = new PlayerCharacterServices(session);
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
	
		cu.dropAllTables();
		cu.initialize();
		cu.populate();
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
	private int genLV(int challengeRating, int level, String prefixMod, boolean miniboss, boolean boss, boolean monsters,boolean trapped) {
		int modifier = 10*(2+(challengeRating/2)+(level));
		int base = boss 
				? challengeRating*60
				: miniboss
					? challengeRating*45
					: monsters
						? challengeRating*30
						: trapped
							? challengeRating*20
							:challengeRating*10;
		int lootValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		if(prefixMod != null)
			if (prefixMod.substring(0, 4).equals("loot"))
				lootValue = (lootValue*(100 + Integer.parseInt(prefixMod.substring(5))))/100;
		return lootValue > 0 ? lootValue : 0;
	}
	private List<Item> genEquipment(String[] exceptions, int count, int level){
		List<Item> equipment = new ArrayList<>();
		String[] defaultTypes = {"headLight", "headMedium", "headHeavy", "bodyLight", "bodyMedium", "bodyHeavy", "back", "neck", "sword", "bow", "staff", "shield", "dagger", "spellbook"};
		List<String> types = new ArrayList<>(Arrays.asList(defaultTypes));
		
		for(String exception: exceptions) {
			types.remove(exception);
		}
		RogueUtilities ru = new RogueUtilities();
		for(int i = 0; i < count; i++) {
			
			String type = types.get(ThreadLocalRandom.current().nextInt(types.size()));
			List<String[]> components = ru.readFileToArrays("source/itemTypes/" + type + ".rbt");
			
			int rarityRoll = ThreadLocalRandom.current().nextInt(0, 51) + level;
			String rarity = "";
			double rarityMultiplier;
			if(rarityRoll <= 10)
				rarity = "Common";
				rarityMultiplier = 0.75;
			if(rarityRoll > 10 && rarityRoll <= 35)
				rarity = "Uncommon";
				rarityMultiplier = 1;
			if(rarityRoll > 35 && rarityRoll <= 50)
				rarity = "Rare";
				rarityMultiplier = 1.25;
			if(rarityRoll > 50)
				rarity = "Epic";
				rarityMultiplier = 1.5;
			
			int statSum = (int)Math.round(Math.pow(ThreadLocalRandom.current().nextInt(level, ((level*3)/2+3))*5, 0.65)*rarityMultiplier);		
			int cost = statSum*5;
			String image = Integer.toString(ThreadLocalRandom.current().nextInt(new File("src/main/app/src/images/items/" + type).list().length + 1));
			
			StringBuffer nameBuffer = new StringBuffer();
			StringBuffer descriptionBuffer = new StringBuffer();
			String modifier = components.get(4)[ThreadLocalRandom.current().nextInt(components.get(4).length)];
			String noun = components.get(3)[ThreadLocalRandom.current().nextInt(components.get(3).length)];
			
			if (rarity == "Common") {
				nameBuffer.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
				nameBuffer.append(noun);
				nameBuffer.append(modifier);
				descriptionBuffer.append(String.format(components.get(5)[ThreadLocalRandom.current().nextInt(components.get(5).length)], noun.toLowerCase()));
			}
			if (rarity == "Uncommon") {
				nameBuffer.append(noun);
				nameBuffer.append(modifier);
				descriptionBuffer.append(String.format(components.get(5)[ThreadLocalRandom.current().nextInt(components.get(5).length)], noun.toLowerCase()));
			}
			if (rarity == "Rare") {
				nameBuffer.append(components.get(1)[ThreadLocalRandom.current().nextInt(components.get(1).length)]);
				nameBuffer.append(noun);
				nameBuffer.append(modifier);
				descriptionBuffer.append(String.format(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)], noun.toLowerCase()));
			}
			
			if (rarity == "Epic") {
				nameBuffer.append(components.get(2)[ThreadLocalRandom.current().nextInt(components.get(2).length)]);
				nameBuffer.append(noun);
				nameBuffer.append(modifier);
				descriptionBuffer.append(String.format(components.get(7)[ThreadLocalRandom.current().nextInt(components.get(7).length)], noun.toLowerCase()));
			}
			
			char c = descriptionBuffer.charAt(1);
			descriptionBuffer.insert(0, c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'
					         ? "An"
					         : "A");
			
			String name = nameBuffer.toString();
			String description = descriptionBuffer.toString();
			
			int constitutionBonus = 0;
			int strengthBonus= 0;
			int dexterityBonus= 0;
			int intelligenceBonus= 0;
			int powerBonus= 0;
			int healthBonus= 0;
			int healthRegenBonus= 0;
			int dodgeRatingBonus= 0;
			int critRatingBonus= 0;
			int energyBonus= 0;
			int energyRegenBonus= 0;
			int armorBonus= 0;
			int armorPenBonus= 0;
			
			modifier = modifier.substring(4);
			switch(modifier) {
				case "Wellness":
					constitutionBonus = statSum;
					break;
				case "Might":
					strengthBonus = statSum;
					break;
				case "Grace":
					dexterityBonus = statSum;
					break;
				case "Clarity":
					intelligenceBonus = statSum;
					break;
				case "Hardiness":
					healthBonus = statSum*10;
					break;
				case "Regeneration":
					healthRegenBonus = Math.round((statSum*5)/6);
					break;
				case "Sundering":
					armorPenBonus = statSum*5;
					break;
				case "Fortification":
					armorBonus = statSum*5;
					break;
				case "Evasion":
					dodgeRatingBonus = statSum*5;
					break;
				case "Exploitation":
					critRatingBonus = statSum*5;
					break;
				case "Capacity":
					energyBonus = statSum*15;
					break;
				case "Insight":
					energyRegenBonus = Math.round((statSum*5)/4);
					break;
				case "Fitness":
					constitutionBonus = Math.round(statSum/2);
					strengthBonus = statSum - dexterityBonus;
					break;
				case "Nimbleness":
					constitutionBonus = Math.round(statSum/2);
					dexterityBonus = statSum - dexterityBonus;
					break;
				case "Consideration":
					constitutionBonus = Math.round(statSum/2);
					intelligenceBonus = statSum - dexterityBonus;
					break;
				case "Prowess":
					dexterityBonus = Math.round(statSum/2);
					strengthBonus = statSum - dexterityBonus;
					break;
				case "Force":
					strengthBonus = Math.round(statSum/2);
					intelligenceBonus = statSum - strengthBonus;
					break;
				case "Cunning":
					intelligenceBonus = Math.round(statSum/2);
					dexterityBonus = statSum - intelligenceBonus;
					break;
				case "Vitality":
					constitutionBonus = Math.round(statSum/2);
					healthBonus = (statSum - constitutionBonus)*10;
					break;
				case "Mending":
					constitutionBonus = Math.round(statSum/2);
					healthRegenBonus = Math.round(((statSum - constitutionBonus)*5)/6);
					break;
				case "Resillience":
					constitutionBonus = Math.round(statSum/2);
					armorBonus = (statSum - constitutionBonus)*5;
					break;
				case "Marathon":
					constitutionBonus = Math.round(statSum/2);
					dodgeRatingBonus = (statSum - constitutionBonus)*5;
					break;
				case "Atrition":
					constitutionBonus = Math.round(statSum/2);
					energyRegenBonus = Math.round(((statSum - constitutionBonus)*5)/4);
					break;
				case "Haleness":
					strengthBonus = Math.round(statSum/2);
					healthBonus = (statSum - strengthBonus)*10;
					break;
				case "the Troll":
					strengthBonus = Math.round(statSum/2);
					healthRegenBonus = Math.round(((statSum - strengthBonus)*5)/6);
					break;
				case "the Fortress":
					strengthBonus = Math.round(statSum/2);
					armorBonus = (statSum - strengthBonus)*5;
					break;
				case "the Dervish":
					strengthBonus = Math.round(statSum/2);
					dodgeRatingBonus = (statSum - strengthBonus)*5;
					break;
				case "Momentum":
					strengthBonus = Math.round(statSum/2);
					energyRegenBonus = Math.round(((statSum - strengthBonus)*5)/4);
					break;
				case "Persistence":
					strengthBonus = Math.round(statSum/2);
					energyBonus = (statSum - strengthBonus)*15;
					break;
				case "Dominance":
					strengthBonus = Math.round(statSum/2);
					armorPenBonus = (statSum - strengthBonus)*5;
					break;
				case "Victory":
					strengthBonus = Math.round(statSum/2);
					critRatingBonus = (statSum - strengthBonus)*5;
					break;
				case "Rebounding":
					dexterityBonus = Math.round(statSum/2);
					healthBonus = (statSum - dexterityBonus)*10;
					break;
				case "Cycling":
					dexterityBonus = Math.round(statSum/2);
					healthRegenBonus = Math.round(((statSum - dexterityBonus)*5)/6);
					break;
				case "Scales":
					dexterityBonus = Math.round(statSum/2);
					armorBonus = (statSum - dexterityBonus)*5;
					break;
				case "Dancing":
					dexterityBonus = Math.round(statSum/2);
					dodgeRatingBonus = (statSum - dexterityBonus)*5;
					break;
				case "Alacrity":
					dexterityBonus = Math.round(statSum/2);
					energyRegenBonus = Math.round(((statSum - dexterityBonus)*5)/4);
					break;
				case "Precision":
					dexterityBonus = Math.round(statSum/2);
					critRatingBonus = (statSum - dexterityBonus)*5;
					break;
				case "Agility":
					dexterityBonus = Math.round(statSum/2);
					energyBonus = (statSum - dexterityBonus)*15;
					break;
				case "Dissection":
					dexterityBonus = Math.round(statSum/2);
					armorPenBonus = (statSum - dexterityBonus)*5;
					break;
				case "the Warmage":
					intelligenceBonus = Math.round(statSum/2);
					healthBonus = (statSum - intelligenceBonus)*10;
					break;
				case "Continuity":
					intelligenceBonus = Math.round(statSum/2);
					healthRegenBonus = Math.round(((statSum - intelligenceBonus)*5)/6);
					break;
				case "the Turtle":
					intelligenceBonus = Math.round(statSum/2);
					armorBonus = (statSum - intelligenceBonus)*5;
					break;
				case "the Fox":
					intelligenceBonus = Math.round(statSum/2);
					dodgeRatingBonus = (statSum - intelligenceBonus)*5;
					break;
				case "Academia":
					intelligenceBonus = Math.round(statSum/2);
					energyRegenBonus = Math.round(((statSum - intelligenceBonus)*5)/4);
					break;
				case "Wit":
					intelligenceBonus = Math.round(statSum/2);
					critRatingBonus = (statSum - intelligenceBonus)*5;
					break;
				case "Genius":
					intelligenceBonus = Math.round(statSum/2);
					energyBonus = (statSum - intelligenceBonus)*15;
					break;
				case "Tactics":
					intelligenceBonus = Math.round(statSum/2);
					armorPenBonus = (statSum - intelligenceBonus)*5;
					break;
			}
			
			switch(type) {
				case "headLight":
					armorBonus += ThreadLocalRandom.current().nextInt(statSum, (statSum*3)/2+1);
					break;
				case "headMedium":
					armorBonus += ThreadLocalRandom.current().nextInt((statSum*3)/2, (statSum*9)/4+1);
					break;
				case "headHeavy":
					armorBonus += ThreadLocalRandom.current().nextInt(statSum*2, (statSum*3)+1);
					break;
				case "bodyLight":
					armorBonus += ThreadLocalRandom.current().nextInt(statSum*2, (statSum*3)+1);
					break;
				case "bodyMedium":
					armorBonus += ThreadLocalRandom.current().nextInt(statSum*3, (statSum*9)/2+1);
					break;
				case "bodyHeavy":
					armorBonus += ThreadLocalRandom.current().nextInt(statSum*4, statSum*6+1);
					break;
				case "back":
					armorBonus += ThreadLocalRandom.current().nextInt((statSum*3)/2, (statSum*9)/4+1);
					break;
				case "neck":
					powerBonus += ThreadLocalRandom.current().nextInt((statSum*3)/4, (statSum*9)/8+1);
					break;
				case "sword":
					powerBonus += ThreadLocalRandom.current().nextInt((statSum*2)/3, statSum+1);
					break;
				case "bow":
					powerBonus += ThreadLocalRandom.current().nextInt((statSum*3)/2, (statSum*9)/4+1);
					break;
				case "staff":
					powerBonus += ThreadLocalRandom.current().nextInt(statSum*2, statSum*3+1);
					break;
				case "shield":
					armorBonus += ThreadLocalRandom.current().nextInt((statSum*4)/3, statSum*2+1);
					break;
				case "dagger":
					powerBonus += ThreadLocalRandom.current().nextInt((statSum*3)/4, (statSum*9)/8+1);
					break;
				case "spellbook":
					powerBonus += ThreadLocalRandom.current().nextInt(statSum, (statSum*3)/2+1);
					break;
			}
			
			equipment.add(new Item(name, description, type, rarity, image, cost, null, 0, constitutionBonus, strengthBonus, dexterityBonus, intelligenceBonus, powerBonus, healthBonus, healthRegenBonus, armorBonus, armorPenBonus, dodgeRatingBonus, critRatingBonus, energyBonus, energyRegenBonus));
		}
		return equipment;
	}
}
