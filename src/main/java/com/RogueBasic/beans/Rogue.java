package com.RogueBasic.beans;

import java.util.UUID;

public class Rogue extends PlayerCharacter{

	public Rogue(UUID playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super(playerId, name, constitution, strength, dexterity, intelligence);
		this.setCharacterClass("Rogue");
		this.setCurrency(150);
		this.setPowerBonus(15);
		this.setHealthBonus(20);
		this.setHealthRegenBonus(2);
		this.setEncumberanceBonus(12);
		this.setCarryCapacityBonus(30);
		this.setDodgeRatingBonus(15);
		this.setCritRatingBonus(20);
		this.setEnergyBonus(30);
		this.setEnergyRegenBonus(4);
		this.setArmorBonus(5);
	}
	
}
