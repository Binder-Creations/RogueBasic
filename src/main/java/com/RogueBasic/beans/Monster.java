package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue", name = "monster")
public class Monster {
	
	@PartitionKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private boolean boss;
	private boolean miniboss;
	private int level;
	private int health;
	private int power;
	private int armor;
	private int dodgeRating;
	private int critRating;
	private Set<UUID> abilityIds;
	
	public Monster() {};
	
	public Monster(String name, String description, String theme, boolean boss, boolean miniboss, int level, int health,
			int power, int armor, int dodgeRating, int critRating, Set<UUID> abilityIds) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.theme = theme;
		this.boss = boss;
		this.miniboss = miniboss;
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
	
	public void setId(UUID id) {
		this.id = id;
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
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isBoss() {
		return boss;
	}

	public void setBoss(boolean boss) {
		this.boss = boss;
	}

	public boolean isMiniboss() {
		return miniboss;
	}

	public void setMiniboss(boolean miniboss) {
		this.miniboss = miniboss;
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
	
	public Set<UUID> getAbilityIds() {
		return abilityIds;
	}
	
	public void setAbilityIds(Set<UUID> abilityIds) {
		this.abilityIds = abilityIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(abilityIds, armor, boss, critRating, description, dodgeRating, health, id, level, miniboss,
				name, power, theme);
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
		return Objects.equals(abilityIds, other.abilityIds) && armor == other.armor && boss == other.boss
				&& critRating == other.critRating && Objects.equals(description, other.description)
				&& dodgeRating == other.dodgeRating && health == other.health && Objects.equals(id, other.id)
				&& level == other.level && miniboss == other.miniboss && Objects.equals(name, other.name)
				&& power == other.power && Objects.equals(theme, other.theme);
	}

	@Override
	public String toString() {
		return "Monster [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme + ", boss="
				+ boss + ", miniboss=" + miniboss + ", level=" + level + ", health=" + health + ", power=" + power
				+ ", armor=" + armor + ", dodgeRating=" + dodgeRating + ", critRating=" + critRating + ", abilityIds="
				+ abilityIds + "]";
	}
	
	

}
