package com.RogueBasic.beans;

import java.util.UUID;

public class Warrior extends PlayerCharacter{

	public Warrior(UUID playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super(playerId, name, constitution, strength, dexterity, intelligence);
		this.setCharacterClass("Warrior");
		this.setCurrency(50);
		this.setPowerBonus(10);
		this.setHealthBonus(30);
		this.setHealthRegenBonus(4);
		this.setEncumberanceBonus(18);
		this.setCarryCapacityBonus(50);
		this.setDodgeRatingBonus(5);
		this.setCritRatingBonus(10);
		this.setEnergyBonus(20);
		this.setEnergyRegenBonus(3);
		this.setArmorBonus(10);
	}
	
}
