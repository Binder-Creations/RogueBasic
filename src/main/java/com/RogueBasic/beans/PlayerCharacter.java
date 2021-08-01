package com.RogueBasic.beans;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue", name = "player_character")
public class PlayerCharacter {
	
	@PartitionKey private UUID id;
	private UUID playerId;
	private String name;
	private String characterClass;
	private int experience;
	private int level;
	private int currency;
	private Set<UUID> abilityIds;
	private Map<UUID, Integer> inventory;
	
	//base primary stats
	private int constitution;
	private int strength;
	private int dexterity;
	private int intelligence;
	
	//primary stat bonuses
	private int constitutionBonus;
	private int strengthBonus;
	private int dexterityBonus;
	private int intelligenceBonus;
	
	//derived stat bonuses
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
	
	//temporary stats
	private int currentHealth;
	private int currentEnergy;
	private int currentEncumberance;
	private int currentCarryCapacity;
	
	public PlayerCharacter() {}
	
	public PlayerCharacter(UUID playerId, String name, int constitution, int strength, int dexterity, int intelligence) {
		super();
		this.playerId = playerId;
		this.id = UUID.randomUUID();
		this.name = name;
		this.constitution = constitution;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.currency = 0;
		this.experience = 0;
		this.level = 1;
	}
	
	public UUID getId() {
		return id;
	}
	
	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCharacterClass() {
		return characterClass;
	}

	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}

	public int getExperience() {
		return experience;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getCurrency() {
		return currency;
	}
	
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	
	public Set<UUID> getAbilityIds() {
		return abilityIds;
	}
	
	public void setAbilityIds(Set<UUID> abilityIds) {
		this.abilityIds = abilityIds;
	}
	
	public Map<UUID,Integer> getInventory() {
		return inventory;
	}
	
	public void setInventory(Map<UUID,Integer> inventory) {
		this.inventory = inventory;
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
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public int getCurrentEnergy() {
		return currentEnergy;
	}
	
	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}
	
	public int getCurrentEncumberance() {
		return currentEncumberance;
	}
	
	public void setCurrentEncumberance(int currentEncumberance) {
		this.currentEncumberance = currentEncumberance;
	}
	
	public int getCurrentCarryCapacity() {
		return currentCarryCapacity;
	}
	
	public void setCurrentCarryCapacity(int currentCarryCapacity) {
		this.currentCarryCapacity = currentCarryCapacity;
	}

	//calculations for stat totals (base + gear)
	public int calcTotalConstitution() {
		return constitution + constitutionBonus;
	}
	
	public int calcTotalStrength() {
		return strength + strengthBonus;
	}
	
	public int calcTotalDexterity() {
		return dexterity + dexterityBonus;
	}
	
	public int calcTotalIntelligence() {
		return intelligence + intelligenceBonus;
	}
	
	//calculations for derived stats
	public int calcPower() {
		return ((strength + strengthBonus + dexterity + dexterityBonus + intelligence + intelligenceBonus)/2 + powerBonus);
	}
	
	public int calcHealth() {
		return constitution*4 + healthBonus;
	}
	
	public int calcHealthRegen() {
		return constitution/3 + healthRegenBonus;
	}
	
	public int calcEncumberance() {
		return strength*3 + encumberanceBonus;
	}
	
	public int calcCarryCapacity() {
		return strength*15 + carryCapacityBonus;
	}
	
	public int calcDodgeRating() {
		return dexterity*2 + dodgeRatingBonus;
	}
	
	public int calcCritRating() {
		return dexterity*2 + critRatingBonus;
	}
	
	public int calcEnergy() {
		return intelligence*6 + energyBonus;
	}
	
	public int calcEnergyRegen() {
		return intelligence/2 + energyRegenBonus;
	}
	
	public int calcArmor() {
		return strength*2 + armorBonus;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(abilityIds, armorBonus, carryCapacityBonus, characterClass, constitution, constitutionBonus,
				critRatingBonus, currency, currentCarryCapacity, currentEncumberance, currentEnergy, currentHealth,
				dexterity, dexterityBonus, dodgeRatingBonus, encumberanceBonus, energyBonus, energyRegenBonus,
				experience, healthBonus, healthRegenBonus, id, intelligence, intelligenceBonus, inventory, level, name,
				playerId, powerBonus, strength, strengthBonus);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCharacter other = (PlayerCharacter) obj;
		return Objects.equals(abilityIds, other.abilityIds) && armorBonus == other.armorBonus
				&& carryCapacityBonus == other.carryCapacityBonus
				&& Objects.equals(characterClass, other.characterClass) && constitution == other.constitution
				&& constitutionBonus == other.constitutionBonus && critRatingBonus == other.critRatingBonus
				&& currency == other.currency && currentCarryCapacity == other.currentCarryCapacity
				&& currentEncumberance == other.currentEncumberance && currentEnergy == other.currentEnergy
				&& currentHealth == other.currentHealth && dexterity == other.dexterity
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& encumberanceBonus == other.encumberanceBonus && energyBonus == other.energyBonus
				&& energyRegenBonus == other.energyRegenBonus && experience == other.experience
				&& healthBonus == other.healthBonus && healthRegenBonus == other.healthRegenBonus
				&& Objects.equals(id, other.id) && intelligence == other.intelligence
				&& intelligenceBonus == other.intelligenceBonus && Objects.equals(inventory, other.inventory)
				&& level == other.level && Objects.equals(name, other.name) && Objects.equals(playerId, other.playerId)
				&& powerBonus == other.powerBonus && strength == other.strength && strengthBonus == other.strengthBonus;
	}
	
	@Override
	public String toString() {
		return "PlayerCharacter [id=" + id + ", playerId=" + playerId + ", name=" + name + ", characterClass="
				+ characterClass + ", experience=" + experience + ", level=" + level + ", currency=" + currency
				+ ", abilityIds=" + abilityIds + ", inventory=" + inventory + ", constitution=" + constitution
				+ ", strength=" + strength + ", dexterity=" + dexterity + ", intelligence=" + intelligence
				+ ", constitutionBonus=" + constitutionBonus + ", strengthBonus=" + strengthBonus + ", dexterityBonus="
				+ dexterityBonus + ", intelligenceBonus=" + intelligenceBonus + ", powerBonus=" + powerBonus
				+ ", healthBonus=" + healthBonus + ", healthRegenBonus=" + healthRegenBonus + ", encumberanceBonus="
				+ encumberanceBonus + ", carryCapacityBonus=" + carryCapacityBonus + ", dodgeRatingBonus="
				+ dodgeRatingBonus + ", critRatingBonus=" + critRatingBonus + ", energyBonus=" + energyBonus
				+ ", energyRegenBonus=" + energyRegenBonus + ", armorBonus=" + armorBonus + ", currentHealth="
				+ currentHealth + ", currentEnergy=" + currentEnergy + ", currentEncumberance=" + currentEncumberance
				+ ", currentCarryCapacity=" + currentCarryCapacity + "]";
	}
}
