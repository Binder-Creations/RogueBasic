package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "monster")
public class Monster {
	private UUID id;
	private String name;
	private String type;
	private String species;
	private String position;
	private boolean boss;
	private boolean miniboss;
	private int variant;
	private int level;
	private int healthTotal;
	private int currentHealth;
	private int power;
	private int armor;
	private int dodgeRating;
	private int critRating;
	private Set<Ability> abilities;
	
	public Monster() {};
	
	public Monster(String name, String type, String species, String position, boolean boss, boolean miniboss, int level, int variant, int healthTotal,
			int power, int armor, int dodgeRating, int critRating, Set<Ability> abilities) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.type = type;
		this.species = species;
		this.position = position;
		this.boss = boss;
		this.miniboss = miniboss;
		this.level = level;
		this.variant = variant;
		this.healthTotal = healthTotal;
		this.power = power;
		this.armor = armor;
		this.dodgeRating = dodgeRating;
		this.critRating = critRating;
		this.abilities = abilities;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
	}

	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getHealthTotal() {
		return healthTotal;
	}
	
	public void setHealthTotal(int healthTotal) {
		this.healthTotal = healthTotal;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
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

	public Set<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(Set<Ability> abilities) {
		this.abilities = abilities;
	}

	@Override
	public int hashCode() {
		return Objects.hash(abilities, armor, boss, critRating, currentHealth, dodgeRating, healthTotal, id, level, miniboss,
				name, position, power, species, type, variant);
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
		return Objects.equals(abilities, other.abilities) && armor == other.armor && boss == other.boss
				&& critRating == other.critRating && currentHealth == other.currentHealth
				&& dodgeRating == other.dodgeRating && healthTotal == other.healthTotal && Objects.equals(id, other.id)
				&& level == other.level && miniboss == other.miniboss && Objects.equals(name, other.name)
				&& Objects.equals(position, other.position) && power == other.power
				&& Objects.equals(species, other.species) && Objects.equals(type, other.type)
				&& variant == other.variant;
	}

	@Override
	public String toString() {
		return "Monster [id=" + id + ", name=" + name + ", type=" + type + ", species=" + species + ", position="
				+ position + ", boss=" + boss + ", miniboss=" + miniboss + ", variant=" + variant + ", level=" + level
				+ ", healthTotal=" + healthTotal + ", currentHealth=" + currentHealth + ", power=" + power + ", armor=" + armor
				+ ", dodgeRating=" + dodgeRating + ", critRating=" + critRating + ", abilities=" + abilities + "]";
	}
	
	

}
