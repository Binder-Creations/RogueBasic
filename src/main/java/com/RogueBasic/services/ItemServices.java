package com.RogueBasic.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class ItemServices {
	private ItemDao dao;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	public ItemServices(CqlSession session) {
		super();
		this.dao = new ItemDao(session);
		this.ru = new RogueUtilities();
	}

	public void generate(Dungeon dungeon, Room room, int level){
		int lootValue = genLV(dungeon.getChallengeRating(), level, dungeon.getPrefixMod(), room.isMiniboss(), room.isBoss(), room.getMonsterIds() != null, room.getTrapId() != null);	
		if (lootValue == 0)
			return;
		room.setLoot(genLoot(lootValue, dungeon.getChallengeRating(), room.isMiniboss(), room.isBoss()));
	}
	
	private int genLV(int challengeRating, int level, String prefixMod, boolean miniboss, boolean boss, boolean monsters,boolean trapped) {
		int modifier = 40*(2+(challengeRating/2)+(level));
		int base = boss 
				? challengeRating*180
				: miniboss
					? challengeRating*120
					: monsters
						? challengeRating*90
						: trapped
							? challengeRating*60
							:challengeRating*30;
		int lootValue = base + ThreadLocalRandom.current().nextInt(modifier);
		if(prefixMod != null)
			if (prefixMod.substring(0, 4).equals("loot"))
				lootValue = (lootValue*(100 + Integer.parseInt(prefixMod.substring(5))))/100;
		return lootValue > 0 ? lootValue : 0;
	}
	
	private Map<UUID, Integer> genLoot(int lootValue, int challengeRating, boolean miniboss, boolean boss) {
		
		
		Map<UUID, Integer> loot = new HashMap<>();
		
		if(miniboss) {
			loot.put(UUID.fromString("7fc49a0c-ec99-4afa-9913-584ecdde409f"), 1);
		}
		if(boss) {
			loot.put(UUID.fromString("7fc49a0c-ec99-4afa-9913-584ecdde409f"), 3);
		}
		
		int averageCost = (int)Math.round(Math.pow(challengeRating*5, 0.65)*10);
		
		while(lootValue >= averageCost) {
			int roll = ThreadLocalRandom.current().nextInt(1, 13);
			if(roll == 1) {
				loot.put(UUID.fromString("d822106a-e753-40db-898d-d438d1592baa"), 1);
				lootValue -= 50;
			}
			if(roll == 2) {
				loot.put(UUID.fromString("ab0d75df-8457-4dde-adfe-77c9b37d262d"), 1);
				lootValue -= 25;
			}
			if(roll >= 3 && roll < 8) {
				UUID gold = UUID.fromString("ce0c5d64-a685-4c0e-97d4-56d4a83edf08");
				int goldCount = loot.containsKey(gold)
					? loot.get(gold)
					: 0;
				int addedGold = ThreadLocalRandom.current().nextInt(averageCost/2, averageCost*2);
				loot.put(gold, goldCount+addedGold);
				lootValue -= addedGold;
				
			}
			if(roll >= 8) {
				String[] exceptions = {};
				Item equipment = genEquipment(exceptions, challengeRating);
				dao.save(equipment);
				loot.put(equipment.getId(), 1);
				lootValue -= equipment.getCost();
			}

		}

		
		return loot;
	}
	
	private List<Item> genEquipmentList(String[] exceptions, int count, int challengeRating){
		List<Item> equipment = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			equipment.add(genEquipment(exceptions, challengeRating));
		}
		return equipment;
	}
	
	public Item genEquipment(String[] exceptions, int challengeRating){
		String[] defaultTypes = {"headLight", "headMedium", "headHeavy", "bodyLight", "bodyMedium", "bodyHeavy", "back", "neck", "sword", "bow", "staff", "shield", "dagger", "spellbook"};
		List<String> types = new ArrayList<>(Arrays.asList(defaultTypes));
		
		for(String exception: exceptions) {
			types.remove(exception);
		}
			String type = types.get(ThreadLocalRandom.current().nextInt(types.size()));
			List<String[]> components = ru.readFileToArrays("source/itemTypes/" + type + ".rbt");

			int rarityRoll = ThreadLocalRandom.current().nextInt(0, 51) + challengeRating;
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
			
			int statSum = (int)Math.round(Math.pow(ThreadLocalRandom.current().nextInt(challengeRating, ((challengeRating*3)/2+3))*5, 0.65)*rarityMultiplier);		
			int cost = ThreadLocalRandom.current().nextInt(statSum*8, statSum*12);
			String image = Integer.toString(ThreadLocalRandom.current().nextInt(1, new File("src/main/app/src/images/items/" + type).list().length));
			
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
			
		return new Item(name, description, type, rarity, image, cost, null, 0, constitutionBonus, strengthBonus, dexterityBonus, intelligenceBonus, powerBonus, healthBonus, healthRegenBonus, armorBonus, armorPenBonus, dodgeRatingBonus, critRatingBonus, energyBonus, energyRegenBonus);
	}
}
