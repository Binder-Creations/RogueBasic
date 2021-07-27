package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import com.RogueBasic.data.MonsterDAO;
import com.RogueBasic.util.RogueUtilities;

public class Monster {
	private UUID id;
	private String name;
	private String description;
	private boolean isBoss;
	private boolean isMiniBoss;
	private int level;
	private int health;
	private int power;
	private int armor;
	private int dodgeRating;
	private int critRating;
	private UUID[] abilityIds;
	
	
	
	public Monster(String name, String description, boolean isBoss, boolean isMiniBoss, int level, int health,
			int power, int armor, int dodgeRating, int critRating, UUID[] abilityIds) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.isBoss = isBoss;
		this.isMiniBoss = isMiniBoss;
		this.level = level;
		this.health = health;
		this.power = power;
		this.armor = armor;
		this.dodgeRating = dodgeRating;
		this.critRating = critRating;
		this.abilityIds = abilityIds;
	}

	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isBoss() {
		return isBoss;
	}
	
	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}
	
	public boolean isMiniBoss() {
		return isMiniBoss;
	}
	
	public void setMiniBoss(boolean isMiniBoss) {
		this.isMiniBoss = isMiniBoss;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
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
	
	public UUID[] getAbilityIds() {
		return abilityIds;
	}
	
	public void setAbilityIds(UUID[] abilityIds) {
		this.abilityIds = abilityIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(abilityIds);
		result = prime * result + Objects.hash(armor, critRating, description, dodgeRating, health, id, isBoss,
				isMiniBoss, level, name, power);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Monster other = (Monster) obj;
		return Arrays.equals(abilityIds, other.abilityIds) && armor == other.armor && critRating == other.critRating
				&& Objects.equals(description, other.description) && dodgeRating == other.dodgeRating
				&& health == other.health && id == other.id && isBoss == other.isBoss && isMiniBoss == other.isMiniBoss
				&& level == other.level && Objects.equals(name, other.name) && power == other.power;
	}

	@Override
	public String toString() {
		return "Monster [id=" + id + ", name=" + name + ", description=" + description + ", isBoss=" + isBoss
				+ ", isMiniBoss=" + isMiniBoss + ", level=" + level + ", health=" + health + ", power=" + power
				+ ", armor=" + armor + ", dodgeRating=" + dodgeRating + ", critRating=" + critRating + ", abilityIds="
				+ Arrays.toString(abilityIds) + "]";
	}
	
	

}
