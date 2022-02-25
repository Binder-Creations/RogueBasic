package com.RogueBasic.beans;

import java.util.Objects;

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
