package com.RogueBasic.beans;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table
public class PlayerCharacter {
	
	@PrimaryKey private UUID id;
	private String location;
	private int day;
	private String name;
	private String characterClass;
	private int experience;
	private int level;
	private int attributePoints;
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
	private int armorPenBonus;
	private int armorBonus;
	private int dodgeRatingBonus;
	private int critRatingBonus;
	private int energyBonus;
	private int energyRegenBonus;
	
	//temporary stats
	private int currentHealth;
	private int currentEnergy;
	
	public PlayerCharacter() {}
	
	public PlayerCharacter(String name, String characterClass, int constitution, int strength, int dexterity, int intelligence, int currencyMetabonus) {
		super();
		this.id = UUID.randomUUID();
		this.location = "Town";
		this.day = 1;
		this.name = name;
		this.characterClass = characterClass;
		this.constitution = constitution;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.experience = 60;
		this.level = 1;
		this.attributePoints = 4;
		this.constitutionBonus = 1;
		switch(characterClass) {
			case "Rogue":
				this.currency = 150 + currencyMetabonus;
				this.powerBonus = 15;
				this.healthBonus = 20;
				this.healthRegenBonus = 2;
				this.armorPenBonus = 5;
				this.armorBonus = 5;
				this.dodgeRatingBonus = 15;
				this.critRatingBonus = 20;
				this.energyBonus = 30;
				this.energyRegenBonus = 4;	
				break;
			case "Warrior":
				this.currency = 50 + currencyMetabonus;
				this.powerBonus = 10;
				this.healthBonus = 30;
				this.healthRegenBonus = 4;
				this.armorPenBonus = 10;
				this.armorBonus = 10;
				this.dodgeRatingBonus = 5;
				this.critRatingBonus = 10;
				this.energyBonus = 20;
				this.energyRegenBonus = 3;
				break;
			case "Wizard":
				this.currency = 100 + currencyMetabonus;
				this.powerBonus = 20;
				this.healthBonus = 10;
				this.healthRegenBonus = 1;
				this.armorPenBonus = 0;
				this.armorBonus = 0;
				this.dodgeRatingBonus = 10;
				this.critRatingBonus = 5;
				this.energyBonus = 40;
				this.energyRegenBonus = 5;		
				break;
		}
		this.currentHealth = calcHealth();
		this.currentEnergy = calcEnergy();
	}
	
	public UUID getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
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
	
	public int getAttributePoints() {
		return attributePoints;
	}

	public void setAttributePoints(int attributePoints) {
		this.attributePoints = attributePoints;
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
	
	public int getArmorPenBonus() {
		return armorPenBonus;
	}

	public void setArmorPenBonus(int armorPenBonus) {
		this.armorPenBonus = armorPenBonus;
	}

	public int getArmorBonus() {
		return armorBonus;
	}

	public void setArmorBonus(int armorBonus) {
		this.armorBonus = armorBonus;
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
		return (calcTotalStrength() + calcTotalDexterity() + calcTotalIntelligence())/2 + powerBonus;
	}
	
	public int calcHealth() {
		return calcTotalConstitution()*4 + healthBonus;
	}
	
	public int calcHealthRegen() {
		return calcTotalConstitution()/3 + healthRegenBonus;
	}
	
	public int calcArmorPen() {
		return calcTotalStrength()*2 + armorPenBonus;
	}
	
	public int calcArmor() {
		return calcTotalStrength()*2 + armorBonus;
	}
	
	public int calcDodgeRating() {
		return calcTotalDexterity()*2 + dodgeRatingBonus;
	}
	
	public int calcCritRating() {
		return calcTotalDexterity()*2 + critRatingBonus;
	}
	
	public int calcEnergy() {
		return calcTotalIntelligence()*6 + energyBonus;
	}
	
	public int calcEnergyRegen() {
		return calcTotalIntelligence()/2 + energyRegenBonus;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(abilityIds, armorBonus, armorPenBonus, attributePoints, characterClass, constitution,
				constitutionBonus, critRatingBonus, currency, currentEnergy, currentHealth, day, dexterity,
				dexterityBonus, dodgeRatingBonus, energyBonus, energyRegenBonus, experience, healthBonus,
				healthRegenBonus, id, intelligence, intelligenceBonus, inventory, level, location, name, powerBonus,
				strength, strengthBonus);
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
				&& armorPenBonus == other.armorPenBonus && attributePoints == other.attributePoints
				&& Objects.equals(characterClass, other.characterClass) && constitution == other.constitution
				&& constitutionBonus == other.constitutionBonus && critRatingBonus == other.critRatingBonus
				&& currency == other.currency && currentEnergy == other.currentEnergy
				&& currentHealth == other.currentHealth && day == other.day && dexterity == other.dexterity
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& energyBonus == other.energyBonus && energyRegenBonus == other.energyRegenBonus
				&& experience == other.experience && healthBonus == other.healthBonus
				&& healthRegenBonus == other.healthRegenBonus && Objects.equals(id, other.id)
				&& intelligence == other.intelligence && intelligenceBonus == other.intelligenceBonus
				&& Objects.equals(inventory, other.inventory) && level == other.level
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& powerBonus == other.powerBonus && strength == other.strength && strengthBonus == other.strengthBonus;
	}
	
	@Override
	public String toString() {
		return "PlayerCharacter [id=" + id + ", location=" + location + ", day=" + day + ", name=" + name
				+ ", characterClass=" + characterClass + ", experience=" + experience + ", level=" + level
				+ ", attributePoints=" + attributePoints + ", currency=" + currency + ", abilityIds=" + abilityIds
				+ ", inventory=" + inventory + ", constitution=" + constitution + ", strength=" + strength
				+ ", dexterity=" + dexterity + ", intelligence=" + intelligence + ", constitutionBonus="
				+ constitutionBonus + ", strengthBonus=" + strengthBonus + ", dexterityBonus=" + dexterityBonus
				+ ", intelligenceBonus=" + intelligenceBonus + ", powerBonus=" + powerBonus + ", healthBonus="
				+ healthBonus + ", healthRegenBonus=" + healthRegenBonus + ", armorPenBonus=" + armorPenBonus
				+ ", armorBonus=" + armorBonus + ", dodgeRatingBonus=" + dodgeRatingBonus + ", critRatingBonus="
				+ critRatingBonus + ", energyBonus=" + energyBonus + ", energyRegenBonus=" + energyRegenBonus
				+ ", currentHealth=" + currentHealth + ", currentEnergy=" + currentEnergy + "]";
	}
}
