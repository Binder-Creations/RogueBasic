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
//	
//	@Override
//	public String toString() {
//		return "Wizard [id=" + getId() + "playerId=" + getPlayerId() + ", name=" + getName() + ", experience=" + getExperience() + ", level=" + getLevel()
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
