package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.netty.util.internal.ThreadLocalRandom;

public enum DungeonModifier {
	NONE("", false, false,
			type -> {return "";},
			type -> {return "";}),
	GOLD_10("gold-10", true, false,
			type -> {return type.randomGoldPrefix();},
			type -> {return type.randomGoldDescription();}),
	GOLD_20("gold-20", true, false,
			type -> {return type.randomGoldPrefix();},
			type -> {return type.randomGoldDescription();}),
	GOLD_30("gold-30", true, false,
			type -> {return type.randomGoldPrefix();},
			type -> {return type.randomGoldDescription();}),
	EQUIPMENT_10("equipment-10", true, false,
			type -> {return type.randomEquipmentPrefix();},
			type -> {return type.randomEquipmentDescription();}),
	EQUIPMENT_20("equipment-20", true, false,
			type -> {return type.randomEquipmentPrefix();},
			type -> {return type.randomEquipmentDescription();}),
	EQUIPMENT_30("equipment-30", true, false,
			type -> {return type.randomEquipmentPrefix();},
			type -> {return type.randomEquipmentDescription();}),
	HEALTH_10("health-10", false, true,
			type -> {return type.randomHealthPostfix();},
			type -> {return type.randomHealthDescription();}),
	HEALTH_20("health-20", false, true,
			type -> {return type.randomHealthPostfix();},
			type -> {return type.randomHealthDescription();}),
	HEALTH_30("health-30", false, true,
			type -> {return type.randomHealthPostfix();},
			type -> {return type.randomHealthDescription();}),
	DAMAGE_10("damage-10", false, true,
			type -> {return type.randomDamagePostfix();},
			type -> {return type.randomDamageDescription();}),
	DAMAGE_20("damage-20", false, true,
			type -> {return type.randomDamagePostfix();},
			type -> {return type.randomDamageDescription();}),
	DAMAGE_30("damage-30", false, true,
			type -> {return type.randomDamagePostfix();},
			type -> {return type.randomDamageDescription();});
	
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
	
	public String modifier() {
		return modifier;
	}
	
	public boolean prefix() {
		return prefix;
	}
	
	public boolean postfix() {
		return postfix;
	}
	
	public String nameModifier(DungeonTheme theme) {
		return nameModifier.apply(theme);
	}
	
	public String descriptionModifier(DungeonTheme theme) {
		return descriptionModifier.apply(theme);
	}
	
	public static DungeonModifier randomPrefix() {
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
	
	public static DungeonModifier randomPostfix() {
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
