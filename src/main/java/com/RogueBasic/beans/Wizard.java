package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Wizard extends PlayerCharacter{

	public Wizard(long playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super(playerId, name, constitution, strength, dexterity, intelligence);
		
		long[] abilities = {7l, 8l, 9l, 10l, 11l, 12l};
		Map<Long, Integer> inventory = this.getInventory();
		inventory.put(9l, 1);
		inventory.put(10l, 1);
		inventory.put(11l, 1);
		inventory.put(12l, 1);
		this.setInventory(inventory);
		this.setAbilityIds(abilities);
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
	
	@Override
	public String toString() {
		return "Wizard [id=" + getId() + "playerId=" + getPlayerId() + ", name=" + getName() + ", experience=" + getExperience() + ", level=" + getLevel()
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
