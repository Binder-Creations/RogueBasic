package com.RogueBasic.enums;

import java.util.Map;
import java.util.function.Function;

import com.RogueBasic.beans.Monster;

public enum MonsterSpecies {
	GOBLIN("goblin", MonsterType.ARCHER, "Bigboss", MonsterType.WIZARD, "Skulldancer", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*0.7f));
				monster.setPower(Math.round((float)monster.getPower()*1.2f));
				monster.setArmor(Math.round((float)monster.getArmor()*0.8f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.4f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Sharpeye",
				MonsterType.ROGUE, "Dogshanker",
				MonsterType.WARRIOR, "Tallgob",
				MonsterType.WIZARD, "Speltinger"
			)),
	GOLEM("golem", MonsterType.WARRIOR, "Multi-Defense Platform", MonsterType.ARCHER, "Retrofitted Borer", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.3f));
				monster.setPower(Math.round((float)monster.getPower()*0.7f));
				monster.setArmor(Math.round((float)monster.getArmor()*1.5f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*0.6f));
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Quarrier",
				MonsterType.ROGUE, "Dissembler",
				MonsterType.WARRIOR, "Compactor",
				MonsterType.WIZARD, "Atmokineter"
			)),
	HUMAN1("human1", MonsterType.WIZARD, "Ascended One", MonsterType.WARRIOR, "Royal Guard",
			monster -> {
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Bowman",
				MonsterType.ROGUE, "Knave",
				MonsterType.WARRIOR, "Knight",
				MonsterType.WIZARD, "Stoic"
			)),
	HUMAN2("human2", MonsterType.ROGUE, "True Master", MonsterType.WIZARD, "Archmage",
			monster -> {
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Ramger",
				MonsterType.ROGUE, "Monk",
				MonsterType.WARRIOR, "Paladin",
				MonsterType.WIZARD, "Mage"
			)),
	OGRE("ogre", MonsterType.WARRIOR, "Crystal-Fused", MonsterType.ROGUE, "Shadow-Stolen", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.5f));
				monster.setPower(Math.round((float)monster.getPower()*1.3f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*0.4f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*0.7f));
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Boulderer",
				MonsterType.ROGUE, "Reaver",
				MonsterType.WARRIOR, "Ogre Heavy",
				MonsterType.WIZARD, "Crysogre"
			)),
	SKELETON("skeleton", MonsterType.WIZARD, "Hellflame Scion", MonsterType.WARRIOR, "Immortal",
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*0.8f));
				monster.setPower(Math.round((float)monster.getPower()*1.4f));
				monster.setArmor(Math.round((float)monster.getArmor()*0.7f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.4f));
				return monster;
			},
			Map.of(
				MonsterType.ARCHER, "Stalker",
				MonsterType.ROGUE, "Deathdealer",
				MonsterType.WARRIOR, "Blackguard",
				MonsterType.WIZARD, "Necromancer"
			));
	
	private final String species;
	private final MonsterType bossType;
	private final String bossName;
	private final MonsterType minibossType;
	private final String minibossName;
	private final Function<Monster, Monster> adjustStats;
	private final Map<MonsterType, String> namesByType;
	
	MonsterSpecies(String species, MonsterType bossType, String bossName, MonsterType minibossType, 
			String minibossName, Function<Monster, Monster> adjustStats, Map<MonsterType, String> namesByType){
		this.species = species;
		this.bossType = bossType;
		this.bossName = bossName;
		this.minibossType = minibossType;
		this.minibossName = minibossName;
		this.adjustStats = adjustStats;
		this.namesByType = namesByType;
	}
	
	public String getSpecies() {
		return this.species;
	}
	
	public MonsterType getBossType() {
		return this.bossType;
	}
	
	public String getBossName() {
		return this.bossName;
	}
	
	public MonsterType getMinibossType() {
		return this.minibossType;
	}
	
	public String getMinibossName() {
		return this.minibossName;
	}
	
	public Monster modifyStats(Monster monster) {
		return this.adjustStats.apply(monster);
	}
	
	public String getNameByType(MonsterType type) {
		return this.namesByType.get(type);
	}
}
