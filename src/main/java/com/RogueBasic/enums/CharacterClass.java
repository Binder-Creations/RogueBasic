package com.RogueBasic.enums;

import java.util.stream.Stream;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.util.JsonUtilities;

public enum CharacterClass {
	ROGUE("Rogue"),
	WARRIOR("Warrior"),
	WIZARD("Wizard");
	
	private final String name;

	CharacterClass(String name){
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public PlayerCharacter getBase() {
		return JsonUtilities.getBasePlayerCharacter(this.name);
	}
	
	public static CharacterClass fromString(String className) {
		return Stream.of(CharacterClass.values())
				.filter((characterClass) -> characterClass.getName().equals(className))
				.findFirst().orElse(null);
	}
}
