package com.RogueBasic.beans;

import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Equipment extends Item {
	
	private int constitutionBonus;
	private int strengthBonus;
	private int dexterityBonus;
	private int intelligenceBonus;
	private int powerBonus;
	private int healthBonus;
	private int healthRegenBonus;
	private int encumberanceBonus;
	private int carryCapacityBonus;
	private int dodgeRatingBonus;
	private int critRatingBonus;
	private int energyBonus;
	private int energyRegenBonus;
	private int armorBonus;
	
	public Equipment() {}
	
	public Equipment(String name, String description, int cost, String action) {
		super(name, description, cost, action);
		this.constitutionBonus = 0;
		this.strengthBonus = 0;
		this.dexterityBonus = 0;
		this.intelligenceBonus = 0;
		this.powerBonus = 0;
		this.healthBonus = 0;
		this.healthRegenBonus = 0;
		this.encumberanceBonus = 0;
		this.carryCapacityBonus = 0;
		this.dodgeRatingBonus = 0;
		this.critRatingBonus = 0;
		this.energyBonus = 0;
		this.energyRegenBonus = 0;
		this.armorBonus = 0;
	}
	
	public Equipment(String name, String description, int cost, double weight, String action, int constitutionBonus, int strengthBonus, int dexterityBonus, int intelligenceBonus, int powerBonus, int healthBonus, int healthRegenBonus, int encumberanceBonus, int carryCapacityBonus, int dodgeRatingBonus, int critRatingBonus, int energyBonus, int energyRegenBonus, int armorBonus) {
		super(name, description, cost, action);	
		this.constitutionBonus = constitutionBonus;
		this.strengthBonus = strengthBonus;
		this.dexterityBonus = dexterityBonus;
		this.intelligenceBonus = intelligenceBonus;
		this.powerBonus = powerBonus;
		this.healthBonus = healthBonus;
		this.healthRegenBonus = healthRegenBonus;
		this.encumberanceBonus = encumberanceBonus;
		this.carryCapacityBonus = carryCapacityBonus;
		this.dodgeRatingBonus = dodgeRatingBonus;
		this.critRatingBonus = critRatingBonus;
		this.energyBonus = energyBonus;
		this.energyRegenBonus = energyRegenBonus;
		this.armorBonus = armorBonus;
	}

	public int getConstitutionBonus() {
		return constitutionBonus;
	}
	
	public void setConstitutionBonus(int constitutionBonus) {
		this.constitutionBonus = constitutionBonus;
	}
	
	public int getStrengthBonus() {
		return strengthBonus;
	}
	
	public void setStrengthBonus(int strengthBonus) {
		this.strengthBonus = strengthBonus;
	}
	
	public int getDexterityBonus() {
		return dexterityBonus;
	}
	
	public void setDexterityBonus(int dexterityBonus) {
		this.dexterityBonus = dexterityBonus;
	}
	
	public int getIntelligenceBonus() {
		return intelligenceBonus;
	}
	
	public void setIntelligenceBonus(int intelligenceBonus) {
		this.intelligenceBonus = intelligenceBonus;
	}
	
	public int getPowerBonus() {
		return powerBonus;
	}
	
	public void setPowerBonus(int powerBonus) {
		this.powerBonus = powerBonus;
	}
	
	public int getHealthBonus() {
		return healthBonus;
	}
	
	public void setHealthBonus(int healthBonus) {
		this.healthBonus = healthBonus;
	}
	
	public int getHealthRegenBonus() {
		return healthRegenBonus;
	}
	
	public void setHealthRegenBonus(int healthRegenBonus) {
		this.healthRegenBonus = healthRegenBonus;
	}
	
	public int getEncumberanceBonus() {
		return encumberanceBonus;
	}
	
	public void setEncumberanceBonus(int encumberanceBonus) {
		this.encumberanceBonus = encumberanceBonus;
	}
	
	public int getCarryCapacityBonus() {
		return carryCapacityBonus;
	}
	
	public void setCarryCapacityBonus(int carryCapacityBonus) {
		this.carryCapacityBonus = carryCapacityBonus;
	}
	
	public int getDodgeRatingBonus() {
		return dodgeRatingBonus;
	}
	
	public void setDodgeRatingBonus(int dodgeRatingBonus) {
		this.dodgeRatingBonus = dodgeRatingBonus;
	}
	
	public int getCritRatingBonus() {
		return critRatingBonus;
	}
	
	public void setCritRatingBonus(int critRatingBonus) {
		this.critRatingBonus = critRatingBonus;
	}
	
	public int getEnergyBonus() {
		return energyBonus;
	}
	
	public void setEnergyBonus(int energyBonus) {
		this.energyBonus = energyBonus;
	}
	
	public int getEnergyRegenBonus() {
		return energyRegenBonus;
	}
	
	public void setEnergyRegenBonus(int energyRegenBonus) {
		this.energyRegenBonus = energyRegenBonus;
	}
	
	public int getArmorBonus() {
		return armorBonus;
	}
	
	public void setArmorBonus(int armorBonus) {
		this.armorBonus = armorBonus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getCost(), getDescription(), getId(), getName(), armorBonus, carryCapacityBonus, constitutionBonus, critRatingBonus,
				dexterityBonus, dodgeRatingBonus, encumberanceBonus, energyBonus, energyRegenBonus, healthBonus,
				healthRegenBonus, intelligenceBonus, powerBonus, strengthBonus);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipment other = (Equipment) obj;
		return Objects.equals(getId(), other.getId()) && getCost() == other.getCost() && getDescription() == other.getDescription() 
				&& getName() == other.getName() && getAction() == other.getAction()
				&& armorBonus == other.armorBonus && carryCapacityBonus == other.carryCapacityBonus
				&& constitutionBonus == other.constitutionBonus && critRatingBonus == other.critRatingBonus
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& encumberanceBonus == other.encumberanceBonus && energyBonus == other.energyBonus
				&& energyRegenBonus == other.energyRegenBonus && healthBonus == other.healthBonus
				&& healthRegenBonus == other.healthRegenBonus && intelligenceBonus == other.intelligenceBonus
				&& powerBonus == other.powerBonus && strengthBonus == other.strengthBonus;
	}
	

	@Override
	public String toString() {
		return "Equipment [id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", cost=" + getCost() + ", action=" + getAction() + ", constitutionBonus=" + constitutionBonus + ", strengthBonus=" + strengthBonus
				+ ", dexterityBonus=" + dexterityBonus + ", intelligenceBonus=" + intelligenceBonus + ", powerBonus="
				+ powerBonus + ", healthBonus=" + healthBonus + ", healthRegenBonus=" + healthRegenBonus
				+ ", encumberanceBonus=" + encumberanceBonus + ", carryCapacityBonus=" + carryCapacityBonus
				+ ", dodgeRatingBonus=" + dodgeRatingBonus + ", critRatingBonus=" + critRatingBonus + ", energyBonus="
				+ energyBonus + ", energyRegenBonus=" + energyRegenBonus + ", armorBonus=" + armorBonus + "]";
	}
}
