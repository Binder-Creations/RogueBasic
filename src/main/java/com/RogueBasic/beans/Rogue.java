package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Map;

public class Rogue extends PlayerCharacter{

	public Rogue(long playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super(playerId, name, constitution, strength, dexterity, intelligence);
		
		long[] abilities = {4l, 5l, 6l};
		Map<Long, Integer> inventory = this.getInventory();
		inventory.put(5l, 1);
		inventory.put(6l, 1);
		inventory.put(7l, 1);
		inventory.put(8l, 1);
		this.setInventory(inventory);
		this.setAbilityIds(abilities);
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
	
	@Override
	public String toString() {
		return "Rogue [id=" + getId() + "playerId=" + getPlayerId() + ", name=" + getName() + ", experience=" + getExperience() + ", level=" + getLevel()
				+ ", currency=" + getCurrency() + ", abilityIds=" + Arrays.toString(getAbilityIds()) + ", inventory="
				+ getInventory().toString() + ", constitution=" + getConstitution() + ", strength=" + getStrength()
				+ ", dexterity=" + getDexterity() + ", intelligence=" + getIntelligence() + ", constitutionBonus="
				+ getConstitutionBonus() + ", strengthBonus=" + getStrengthBonus() + ", dexterityBonus=" + getDexterityBonus()
				+ ", intelligenceBonus=" + getIntelligenceBonus() + ", powerBonus=" + getPowerBonus() + ", healthBonus="
				+ getHealthBonus() + ", healthRegenBonus=" + getHealthRegenBonus() + ", encumberanceBonus=" + getEncumberanceBonus()
				+ ", carryCapacityBonus=" + getCarryCapacityBonus() + ", dodgeRatingBonus=" + getDodgeRatingBonus()
				+ ", critRatingBonus=" + getCritRatingBonus() + ", energyBonus=" + getEnergyBonus() + ", energyRegenBonus="
				+ getEnergyRegenBonus() + ", armorBonus=" + getArmorBonus() + ", currentHealth=" + getCurrentHealth()
				+ ", currentEnergy=" + getCurrentEnergy() + ", currentEncumberance=" + getCurrentEncumberance()
				+ ", currentCarryCapacity=" + getCurrentCarryCapacity() + "]";
	}
	
}
