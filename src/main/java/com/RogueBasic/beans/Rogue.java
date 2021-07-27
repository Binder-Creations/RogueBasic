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
	
//	@Override
//	public String toString() {
//		return "Rogue [id=" + getId() + "playerId=" + getPlayerId() + ", name=" + getName() + ", experience=" + getExperience() + ", level=" + getLevel()
//				+ ", currency=" + getCurrency() + ", abilityIds=" + Arrays.toString(getAbilityIds()) + ", inventory="
//				+ getInventory().toString() + ", constitution=" + getConstitution() + ", strength=" + getStrength()
//				+ ", dexterity=" + getDexterity() + ", intelligence=" + getIntelligence() + ", constitutionBonus="
//				+ getConstitutionBonus() + ", strengthBonus=" + getStrengthBonus() + ", dexterityBonus=" + getDexterityBonus()
//				+ ", intelligenceBonus=" + getIntelligenceBonus() + ", powerBonus=" + getPowerBonus() + ", healthBonus="
//				+ getHealthBonus() + ", healthRegenBonus=" + getHealthRegenBonus() + ", encumberanceBonus=" + getEncumberanceBonus()
//				+ ", carryCapacityBonus=" + getCarryCapacityBonus() + ", dodgeRatingBonus=" + getDodgeRatingBonus()
//				+ ", critRatingBonus=" + getCritRatingBonus() + ", energyBonus=" + getEnergyBonus() + ", energyRegenBonus="
//				+ getEnergyRegenBonus() + ", armorBonus=" + getArmorBonus() + ", currentHealth=" + getCurrentHealth()
//				+ ", currentEnergy=" + getCurrentEnergy() + ", currentEncumberance=" + getCurrentEncumberance()
//				+ ", currentCarryCapacity=" + getCurrentCarryCapacity() + "]";
//	}
	
}
