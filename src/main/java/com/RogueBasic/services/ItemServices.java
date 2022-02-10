package com.RogueBasic.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Room;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemServices {
	private RogueUtilities ru;
	private List<String> allTypes;
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	public ItemServices(CqlSession session) {
		super();
		this.ru = new RogueUtilities();
		this.allTypes = new ArrayList<String>(Arrays.asList(new String[] {"headLight", "headMedium", "headHeavy", "bodyLight", "bodyMedium", "bodyHeavy", "staff", "spellbook", "bow", "dagger", "sword", "shield", "back", "neck"}));
	}

	public Item getPremade(int index) {
		ObjectMapper mapper = new ObjectMapper();
		log.trace("CassandraUtilities.populate() calling RogueUtilities.readFileToList() for Items.rbt");
		try {	
			return mapper.readValue(ru.readFileToList("source/items.rbt").get(index), Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void generate(Dungeon dungeon, Room room, int level){
		int lootValue = genLV(dungeon.getChallengeRating(), level, room.isMiniboss(), room.isBoss(), room.getMonsters() != null);	
		if (lootValue == 0)
			return;
		genLoot(room, lootValue, dungeon.getChallengeRating(), dungeon.getPrefixMod(), room.isMiniboss(), room.isBoss());
	}
	
	private int genLV(int challengeRating, int level, boolean miniboss, boolean boss, boolean monsters) {
		int modifier = 100*(2+(challengeRating/2)+(level));
		int base = boss 
				? challengeRating*450
				: miniboss
					? challengeRating*300
					: monsters
						? challengeRating*200
						: challengeRating*100;
		int lootValue = base + ThreadLocalRandom.current().nextInt(modifier);
		return lootValue > 0 ? lootValue : 0;
	}
	
	private void genLoot(Room room, int lootValue, int challengeRating, String prefixMod, boolean miniboss, boolean boss) {
		Set<Item> loot = new HashSet<>();
		ItemServices iService =  new ItemServices(CassandraConnector.connect());
		
		if(miniboss) {
			Item soul = iService.getPremade(1);
			loot.add(soul);
		}
		if(boss) {
			Item soul = iService.getPremade(1);
			soul.setCount(3);
			loot.add(soul);	
		}
		
		int goldMod = 0;
		int equipmentMod = 0;
		if(prefixMod != null) {
			String[] components = prefixMod.split("-");
			if (components[0].equals("gold")) {
				goldMod = Integer.parseInt(components[1]);
			}
			if (components[0].equals("equipment")) {
				equipmentMod = Integer.parseInt(components[1]);
			}
		}
		
		int averageCost = (int)Math.round(Math.pow(challengeRating*5, 0.65)*10);
		
		while(lootValue >= averageCost && loot.size() <= 25) {
			int roll = ThreadLocalRandom.current().nextInt(1, 15);
			if(roll == 1) {
				Item healthPotion = iService.getPremade(2);
				if(loot.stream().anyMatch(item -> item.getId() == healthPotion.getId())) {
					continue;
				} else {
					loot.add(healthPotion);
					lootValue -= 50;
				}	
			}
			if(roll == 2) {
				Item energyPotion = iService.getPremade(3);
				if(loot.stream().anyMatch(item -> item.getId() == energyPotion.getId())) {
					continue;
				} else {
					loot.add(energyPotion);
					lootValue -= 50;
				}	
			}
			if(roll == 3) {
				Item ration = iService.getPremade(4);
				if(loot.stream().anyMatch(item -> item.getId() == ration.getId())) {
					continue;
				} else {
					loot.add(ration);
					lootValue -= 25;
				}	
			}
			if(roll == 4) {
				Item wine = iService.getPremade(5);
				if(loot.stream().anyMatch(item -> item.getId() == wine.getId())) {
					continue;
				} else {
					loot.add(wine);
					lootValue -= 25;
				}	
			}
			if(roll >= 5 && roll < 10) {
				Item gold = iService.getPremade(0);
				int goldCount = 0;
				for (Iterator<Item> i = loot.iterator(); i.hasNext();) {
				    Item item = i.next();
				    if (item.getId() == gold.getId()) {
				    	goldCount += item.getCount();
				    	i.remove();
				    }
				}
				
				int addedGold = ThreadLocalRandom.current().nextInt(averageCost/2, averageCost*2);
				lootValue -= addedGold;
				gold.setCount(goldCount+(addedGold*(100+goldMod)/100));
				loot.add(gold);
			}
			if(roll >= 10) {
				int equipmentMultiplier = 1;
				int doubleEquipmentRoll = ThreadLocalRandom.current().nextInt(100) + 1;
				if(doubleEquipmentRoll > 100 - equipmentMod) {
					equipmentMultiplier = 2;
				}
				
				Item equipment = null;
				for(int i = 0; i < equipmentMultiplier; i++) {
					equipment = genEquipment(allTypes, challengeRating);
					loot.add(equipment);
				}
				lootValue -= equipment.getCost();
			}

		}

		
		room.setLoot(loot);
	}
	
	public Item genEquipment(List<String> types, int challengeRating){		
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
