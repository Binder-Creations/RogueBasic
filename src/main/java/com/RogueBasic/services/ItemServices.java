package com.RogueBasic.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Room;
import com.RogueBasic.enums.EquipmentModifier;
import com.RogueBasic.enums.EquipmentType;
import com.RogueBasic.enums.StaticItem;

@Component
public class ItemServices {
	public ItemServices() {}
	
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
		Function<Item, Integer> addConsumable = (item) -> {
			for(Item i: loot) {
				if(i.getId() == item.getId()) {
					i.setCount(i.getCount() + item.getCount());
					return item.getCost()*item.getCount();
				}
			}
			loot.add(item);
			return item.getCost()*item.getCount();
		};
		
		if(miniboss) {
			loot.add(StaticItem.SOUL.getItem());
		}
		if(boss) {
			loot.add(StaticItem.SOUL.getItem(3));	
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
				lootValue -= addConsumable.apply(StaticItem.HEALTH_POTION.getItem());
			}
			if(roll == 2) {
				lootValue -= addConsumable.apply(StaticItem.ENERGY_POTION.getItem());
			}
			if(roll == 3) {
				lootValue -= addConsumable.apply(StaticItem.RATIONS.getItem());
			}
			if(roll == 4) {
				lootValue -= addConsumable.apply(StaticItem.WINE.getItem());
			}
			if(roll >= 5 && roll < 10) {
				lootValue -= addConsumable.apply(StaticItem.GOLD.getItem(ThreadLocalRandom.current().nextInt(averageCost/2, averageCost*2)*(100+goldMod)/100));
			}
			if(roll >= 10) {
				int equipmentMultiplier = 1;
				int doubleEquipmentRoll = ThreadLocalRandom.current().nextInt(100) + 1;
				if(doubleEquipmentRoll > 100 - equipmentMod) {
					equipmentMultiplier = 2;
				}
				
				Item equipment = null;
				for(int i = 0; i < equipmentMultiplier; i++) {
					equipment = genEquipment(EquipmentType.asList(), challengeRating);
					loot.add(equipment);
				}
				lootValue -= equipment.getCost();
			}

		}

		
		room.setLoot(loot);
	}
	
	public Item genEquipment(List<EquipmentType> equipmentTypes, int challengeRating){		
		EquipmentType equipmentType = equipmentTypes.get(ThreadLocalRandom.current().nextInt(equipmentTypes.size()));
		EquipmentModifier modifier = equipmentType.getRandomModifier();
		String noun = equipmentType.getRandomNoun();
		int rarityRoll = ThreadLocalRandom.current().nextInt(0, 51) + challengeRating;
		
		StringBuffer nameBuffer = new StringBuffer();
		StringBuffer descriptionBuffer = new StringBuffer();
		String rarity = "";
		double rarityMultiplier = 1;
		if(rarityRoll <= 10) {
			rarity = "Common";
			nameBuffer.append(equipmentType.getRandomPrefixCommon());
			descriptionBuffer.append(String.format(equipmentType.getRandomDescriptionCommon(), noun.toLowerCase()));
		}
		if(rarityRoll > 10 && rarityRoll <= 35) {
			rarity = "Uncommon";
			rarityMultiplier = 1.4;
			nameBuffer.append(equipmentType.getRandomPrefixUncommon());
			descriptionBuffer.append(String.format(equipmentType.getRandomDescriptionUncommon(), noun.toLowerCase()));
		}
		if(rarityRoll > 35 && rarityRoll <= 50) {
			rarity = "Rare";
			rarityMultiplier = 1.8;
			nameBuffer.append(equipmentType.getRandomPrefixRare());
			descriptionBuffer.append(String.format(equipmentType.getRandomDescriptionRare(), noun.toLowerCase()));
		}
		if(rarityRoll > 50) {
			rarity = "Epic";
			rarityMultiplier = 2.5;
			nameBuffer.append(equipmentType.getRandomPrefixEpic());
			descriptionBuffer.append(String.format(equipmentType.getRandomDescriptionEpic(), noun.toLowerCase()));
		}
		int statSum = (int)Math.round(Math.pow(((challengeRating*4)/3+2)*5, 0.65)*rarityMultiplier);		
		int cost = ThreadLocalRandom.current().nextInt(statSum*9, statSum*11);
		
		nameBuffer.append(noun);
		nameBuffer.append(modifier.getSuffix());
		String name = nameBuffer.toString();
				
		char c = descriptionBuffer.charAt(1);
		descriptionBuffer.insert(0, c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'
				         ? "An"
				         : "A");
		String description = descriptionBuffer.toString();
		
		Item item = new Item(name, description, equipmentType.getType(), rarity, equipmentType.getRandomImage(), cost);
		return equipmentType.modifyStats(modifier.modifyStats(item, statSum), statSum);
	}
}
