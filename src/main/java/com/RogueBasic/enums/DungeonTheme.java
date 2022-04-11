package com.RogueBasic.enums;

import java.util.stream.Stream;

import io.netty.util.internal.ThreadLocalRandom;

public enum DungeonTheme {
	ARCANE("Arcane", 
			new MonsterSpecies[] {MonsterSpecies.GOLEM, MonsterSpecies.SKELETON},
			new String[] {"Glowing ", "Demonic ", "Cursed "}, 
			new String[] {"Manaweft ", "Leycrossed ", "Lorestrewn "}, 
			new String[] {"Mage's ", "Wizard's ", "Cultist's "}, 
			new String[] {"Ritual Site", "Altar", "Tower"}, 
			new String[] {" of Conjuring", " of the Elders", " of Resonance"}, 
			new String[] {" of the Blood Pact", " of Storms", " of Flames"}, 
			new String[] {" iminiently dangerous", " trap-strewn", " perilously unwelcoming"}, 
			new String[] {" brilliant", " rune-carved", " legendarily treasure-filled"}, 
			new String[] {" %s full of hostile spellcasters", " %s beckoning any adventurer who dare its depths", " deviously alluring %s"}, 
			new String[] {", renowned for its hearty inhabitants.", ", full of only the most resolute."}, 
			new String[] {", which promises grave danger.", ", that only a fool would venture into.", ", that bears every hallmark of foul activity."}
		),
	CASTLE("Castle", 
			new MonsterSpecies[] {MonsterSpecies.HUMAN1, MonsterSpecies.HUMAN2},
			new String[] {"Fortified ", "Imposing ", "Vigilant ", "High-Walled "}, 
			new String[] {"Gilded ", "Noble ", "Rich ", "Luxurious "}, 
			new String[] {"Stonehewn ", "Granite ", "Crumbling "}, 
			new String[] {"Castle", "Bastion", "Fortress", "Bassilica"}, 
			new String[] {" of Iron", " of the Order", " of the Urarch"}, 
			new String[] {" of the Theif-Prince", " of the Black", " of Treachery"}, 
			new String[] {" iminiently dangerous", " trap-strewn", " perilously unwelcoming"}, 
			new String[] {" brilliant", " gold-trimmed", " legendarily treasure-filled"}, 
			new String[] {" %s full of enemies", " %s beckoning any adventurer who dare its halls", " alluring %s"}, 
			new String[] {", renowned for its hearty inhabitants.", ", full of only the most resolute."}, 
			new String[] {", which promises grave danger.", ", that only a fool would venture into.", ", that bears every hallmark of foul activity."}
		),
	CAVE("Cave", 
			new MonsterSpecies[] {MonsterSpecies.GOBLIN, MonsterSpecies.OGRE},
			new String[] {"Bloodrent ", "Hazardous ", "Ominous "}, 
			new String[] {"Scintillating ", "Luminous ", "Laden "}, 
			new String[] {"Crystal ", "Dark ", "Dank "}, 
			new String[] {"Cave", "Cavern", "Crevice"}, 
			new String[] {" of Goblins", " of Spiders", " of the Red Death"}, 
			new String[] {" of Gnashing", " of the Lost", " of Clan Goretusk"}, 
			new String[] {" iminiently dangerous", " trap-strewn", " perilously unwelcoming"}, 
			new String[] {" brilliant", " gem-hewn", " legendarily treasure-filled"}, 
			new String[] {" %s full of monsters", " %s beckoning any adventurer who dare its depths", " deviously alluring %s"}, 
			new String[] {", renowned for its hearty inhabitants.", ", full of only the most resolute."}, 
			new String[] {", which promises grave danger.", ", that only a fool would venture into.", ", that bears every hallmark of foul activity."}
		);
	
	private final String theme;
	private final MonsterSpecies[] species;
	private final String[] goldPrefixes;
	private final String[] equipmentPrefixes;
	private final String[] adjectives;
	private final String[] nouns;
	private final String[] healthPostfixes;
	private final String[] damagePostfixes;
	private final String[] goldDescriptions;
	private final String[] equipmentDescriptions;
	private final String[] baseDescriptions;
	private final String[] healthDescriptions;
	private final String[] damageDescriptions;
	
	DungeonTheme(String theme, MonsterSpecies[] species, String[] goldPrefixes, String[] equipmentPrefixes, 
			String[] adjectives, String[] nouns, String[] healthPostfixes, String[] damagePostfixes, 
			String[] goldDescriptions, String[] equipmentDescriptions, String[] baseDescriptions, 
			String[] healthDescriptions, String[] damageDescriptions){
		this.theme = theme;
		this.species = species;
		this.goldPrefixes = goldPrefixes;
		this.equipmentPrefixes = equipmentPrefixes;
		this.adjectives = adjectives;
		this.nouns = nouns;
		this.healthPostfixes = healthPostfixes;
		this.damagePostfixes = damagePostfixes;
		this.goldDescriptions = goldDescriptions;
		this.equipmentDescriptions = equipmentDescriptions;
		this.baseDescriptions = baseDescriptions;
		this.healthDescriptions = healthDescriptions;
		this.damageDescriptions = damageDescriptions;
	}
	
	public String getTheme() {
		return this.theme;
	}
	
	public MonsterSpecies getRandomSpecies() {
		return this.species[ThreadLocalRandom.current().nextInt(this.species.length)];
	}
	
	public String getRandomGoldPrefix() {
		return this.goldPrefixes[ThreadLocalRandom.current().nextInt(this.goldPrefixes.length)];
	}
	
	public String getRandomEquipmentPrefix() {
		return this.equipmentPrefixes[ThreadLocalRandom.current().nextInt(this.equipmentPrefixes.length)];
	}
	
	public String getRandomAdjective() {
		return this.adjectives[ThreadLocalRandom.current().nextInt(this.adjectives.length)];
	}
	
	public String getRandomNoun() {
		return this.nouns[ThreadLocalRandom.current().nextInt(this.nouns.length)];
	}
	
	public String getRandomHealthPostfix() {
		return this.healthPostfixes[ThreadLocalRandom.current().nextInt(this.healthPostfixes.length)];
	}
	
	public String getRandomDamagePostfix() {
		return this.damagePostfixes[ThreadLocalRandom.current().nextInt(this.damagePostfixes.length)];
	}
	
	public String getRandomGoldDescription() {
		return this.goldDescriptions[ThreadLocalRandom.current().nextInt(this.goldDescriptions.length)];
	}
	
	public String getRandomEquipmentDescription() {
		return this.equipmentDescriptions[ThreadLocalRandom.current().nextInt(this.equipmentDescriptions.length)];
	}
	
	public String getRandomBaseDescription() {
		return this.baseDescriptions[ThreadLocalRandom.current().nextInt(this.baseDescriptions.length)];
	}
	
	public String getRandomHealthDescription() {
		return this.healthDescriptions[ThreadLocalRandom.current().nextInt(this.healthDescriptions.length)];
	}
	
	public String getRandomDamageDescription() {
		return this.damageDescriptions[ThreadLocalRandom.current().nextInt(this.damageDescriptions.length)];
	}
	
	public static DungeonTheme fromString(String string) {
		return Stream.of(DungeonTheme.values())
				.filter(theme -> theme.getTheme().equals(string))
				.findFirst().orElse(null);
	}
	
	public static DungeonTheme getRandomTheme() {
		return DungeonTheme.values()[ThreadLocalRandom.current().nextInt(DungeonTheme.values().length)];
	}
}
