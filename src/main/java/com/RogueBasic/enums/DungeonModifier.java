package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.netty.util.internal.ThreadLocalRandom;

public enum DungeonModifier {
	NONE("", false, false,
			t -> "",
			t -> ""),
	GOLD_10("gold-10", true, false,
			DungeonTheme::getRandomGoldPrefix,
			DungeonTheme::getRandomGoldDescription),
	GOLD_20("gold-20", true, false,
			DungeonTheme::getRandomGoldPrefix,
			DungeonTheme::getRandomGoldDescription),
	GOLD_30("gold-30", true, false,
			DungeonTheme::getRandomGoldPrefix,
			DungeonTheme::getRandomGoldDescription),
	EQUIPMENT_10("equipment-10", true, false,
			DungeonTheme::getRandomEquipmentPrefix,
			DungeonTheme::getRandomEquipmentDescription),
	EQUIPMENT_20("equipment-20", true, false,
			DungeonTheme::getRandomEquipmentPrefix,
			DungeonTheme::getRandomEquipmentDescription),
	EQUIPMENT_30("equipment-30", true, false,
			DungeonTheme::getRandomEquipmentPrefix,
			DungeonTheme::getRandomEquipmentDescription),
	HEALTH_10("health-10", false, true,
			DungeonTheme::getRandomHealthPostfix,
			DungeonTheme::getRandomHealthDescription),
	HEALTH_20("health-20", false, true,
			DungeonTheme::getRandomHealthPostfix,
			DungeonTheme::getRandomHealthDescription),
	HEALTH_30("health-30", false, true,
			DungeonTheme::getRandomHealthPostfix,
			DungeonTheme::getRandomHealthDescription),
	DAMAGE_10("damage-10", false, true,
			DungeonTheme::getRandomDamagePostfix,
			DungeonTheme::getRandomDamageDescription),
	DAMAGE_20("damage-20", false, true,
			DungeonTheme::getRandomDamagePostfix,
			DungeonTheme::getRandomDamageDescription),
	DAMAGE_30("damage-30", false, true,
			DungeonTheme::getRandomDamagePostfix,
			DungeonTheme::getRandomDamageDescription);
	
	private final String modifier;
	private final boolean prefix;
	private final boolean postfix;
	private final Function<DungeonTheme, String> nameModifier;
	private final Function<DungeonTheme, String> descriptionModifier;
	
	DungeonModifier(String modifier, boolean prefix, boolean postfix, Function<DungeonTheme, String> nameModifier, Function<DungeonTheme, String> descriptionModifier){
		this.modifier = modifier;
		this.prefix = prefix;
		this.postfix = postfix;
		this.nameModifier = nameModifier;
		this.descriptionModifier = descriptionModifier;
	}
	
	public String getModifier() {
		return modifier;
	}
	
	public boolean isPrefix() {
		return prefix;
	}
	
	public boolean isPostfix() {
		return postfix;
	}
	
	public String getNameModifier(DungeonTheme theme) {
		return nameModifier.apply(theme);
	}
	
	public String getDescriptionModifier(DungeonTheme theme) {
		return descriptionModifier.apply(theme);
	}
	
	public static DungeonModifier getRandomPrefix() {
		if(ThreadLocalRandom.current().nextBoolean()) {
			return DungeonModifier.NONE;
		} else {
			List<DungeonModifier> prefixes = new ArrayList<>();
			for(DungeonModifier modifier : DungeonModifier.values()) {
				if(modifier.prefix) {
					prefixes.add(modifier);
				}
			}
			return prefixes.get(ThreadLocalRandom.current().nextInt(prefixes.size()));
		}
	}
	
	public static DungeonModifier getRandomPostfix() {
		if(ThreadLocalRandom.current().nextBoolean()) {
			return DungeonModifier.NONE;
		} else {
			List<DungeonModifier> postfixes = new ArrayList<>();
			for(DungeonModifier modifier : DungeonModifier.values()) {
				if(modifier.postfix) {
					postfixes.add(modifier);
				}
			}
			return postfixes.get(ThreadLocalRandom.current().nextInt(postfixes.size()));
		}
	}
}