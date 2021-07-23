package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Map;

public class Warrior extends Character{

	public Warrior(String name, int constitution, int strength, int dexterity, int intelligence) {
		super(name, constitution, strength, dexterity, intelligence);
		
		String[] abilities = {"1:Power_Attack", "2:Cleave", "3:Stunning_Strike"};
		Map<Long, Integer> inventory = this.getInventory();
		inventory.put(1l, 1);
		inventory.put(2l, 1);
		inventory.put(3l, 1);
		inventory.put(4l, 1);
		this.setInventory(inventory);
		this.setAbilities(abilities);
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
	
	@Override
	public String toString() {
		return "Warrior [id=" + getId() + ", name=" + getName() + ", experience=" + getExperience() + ", level=" + getLevel()
				+ ", currency=" + getCurrency() + ", abilities=" + Arrays.toString(getAbilities()) + ", inventory="
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
