package com.RogueBasic.beans;

import java.util.UUID;

public class Wizard extends PlayerCharacter{
	
	public Wizard(UUID playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super(playerId, name, constitution, strength, dexterity, intelligence);
		
		this.setCharacterClass("Wizard");
		this.setCurrency(100);
		this.setPowerBonus(20);
		this.setHealthBonus(10);
		this.setHealthRegenBonus(1);
		this.setEncumberanceBonus(8);
		this.setCarryCapacityBonus(20);
		this.setDodgeRatingBonus(10);
		this.setCritRatingBonus(5);
		this.setEnergyBonus(40);
		this.setEnergyRegenBonus(5);
		this.setArmorBonus(0);
	}
}
