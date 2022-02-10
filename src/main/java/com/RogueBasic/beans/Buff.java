package com.RogueBasic.beans;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

public class Buff {
	private String name;
	private int duration;
	private int regenerate;
	private int poison;
	private int bleed;
	private int burn;
	private int constitution;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int power;
	private int health;
	private int healthRegen;
	private int armorPen;
	private int armor;
	private int dodgeRating;
	private int critRating;
	private int energy;
	private int energyRegen;
	
	public Buff() {}
	
	public Buff(String name, int duration, Map<String, Integer> stats) {
		this.name = name;
		this.duration = duration;
		stats.forEach((String stat, Integer statValue) -> {
			assign(stat, statValue);
		});
	}
	
	public Buff(String name, int duration, String[] stats) {
		this.name = name;
		this.duration = duration;
		for(int i = 0; i < stats.length; i++) {
			assign(stats[i], -1);
		}
	}
	
	public Buff(String name, int duration, String stat, int statValue) {
		this.name = name;
		this.duration = duration;
		assign(stat, statValue);
	}
	
	public Buff(String name, String stat) {
		this(name, -1, stat, -1);
	}
	
	public Buff(String name, int duration, String stat) {
		this(name, duration, stat, -1);
	}
	
	private void assign(String stat, int statValue) {
		switch(stat) {
			case "regenerate":
				this.regenerate = statValue;
				break;
			case "poison":
				this.poison = statValue;
				break;
			case "bleed":
				this.bleed = statValue;
				break;
			case "burn":
				this.burn = statValue;
				break;
			case "constitution":
				this.constitution = statValue;
				break;
			case "strength":
				this.strength = statValue;
				break;
			case "dexterity":
				this.dexterity = statValue;
				break;
			case "intelligence":
				this.intelligence = statValue;
				break;
			case "power":
				this.power = statValue;
				break;
			case "health":
				this.health = statValue;
				break;
			case "healthRegen":
				this.healthRegen = statValue;
				break;
			case "armor":
				this.armor = statValue;
				break;
			case "armorPen":
				this.armorPen = statValue;
				break;
			case "dodgeRating":
				this.dodgeRating = statValue;
				break;
			case "critRating":
				this.critRating = statValue;
				break;
			case "energy":
				this.energy = statValue;
				break;
			case "energyRegen":
				this.energyRegen = statValue;
				break;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getRegenerate() {
		return regenerate;
	}
	public void setRegenerate(int regenerate) {
		this.regenerate = regenerate;
	}
	public int getPoison() {
		return poison;
	}
	public void setPoison(int poison) {
		this.poison = poison;
	}
	public int getBleed() {
		return bleed;
	}
	public void setBleed(int bleed) {
		this.bleed = bleed;
	}
	public int getBurn() {
		return burn;
	}
	public void setBurn(int burn) {
		this.burn = burn;
	}
	public int getConstitution() {
		return constitution;
	}
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getHealthRegen() {
		return healthRegen;
	}
	public void setHealthRegen(int healthRegen) {
		this.healthRegen = healthRegen;
	}
	public int getArmorPen() {
		return armorPen;
	}
	public void setArmorPen(int armorPen) {
		this.armorPen = armorPen;
	}
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	public int getDodgeRating() {
		return dodgeRating;
	}
	public void setDodgeRating(int dodgeRating) {
		this.dodgeRating = dodgeRating;
	}
	public int getCritRating() {
		return critRating;
	}
	public void setCritRating(int critRating) {
		this.critRating = critRating;
	}
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public int getEnergyRegen() {
		return energyRegen;
	}
	public void setEnergyRegen(int energyRegen) {
		this.energyRegen = energyRegen;
	}
	@Override
	public int hashCode() {
		return Objects.hash(armor, armorPen, bleed, burn, constitution, critRating, dexterity, dodgeRating, duration,
				energy, energyRegen, health, healthRegen, intelligence, name, poison, power, regenerate, strength);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Buff other = (Buff) obj;
		return armor == other.armor && armorPen == other.armorPen && bleed == other.bleed && burn == other.burn
				&& constitution == other.constitution && critRating == other.critRating && dexterity == other.dexterity
				&& dodgeRating == other.dodgeRating && duration == other.duration && energy == other.energy
				&& energyRegen == other.energyRegen && health == other.health && healthRegen == other.healthRegen
				&& intelligence == other.intelligence && Objects.equals(name, other.name) && poison == other.poison
				&& power == other.power && regenerate == other.regenerate && strength == other.strength;
	}
	@Override
	public String toString() {
		return "Buff [name=" + name + ", duration=" + duration + ", regenerate=" + regenerate + ", poison=" + poison
				+ ", bleed=" + bleed + ", burn=" + burn + ", constitution=" + constitution + ", strength=" + strength
				+ ", dexterity=" + dexterity + ", intelligence=" + intelligence + ", power=" + power + ", health="
				+ health + ", healthRegen=" + healthRegen + ", armorPen=" + armorPen + ", armor=" + armor
				+ ", dodgeRating=" + dodgeRating + ", critRating=" + critRating + ", energy=" + energy
				+ ", energyRegen=" + energyRegen + "]";
	}
}
