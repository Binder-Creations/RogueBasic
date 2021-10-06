package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.data.AbilityDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;


public class PlayerCharacterExport {
	
	private UUID id;
	private String location;
	private UUID currentDungeon;
	private UUID currentShop;
	private int day;
	private String name;
	private String characterClass;
	private int experience;
	private int level;
	private int attributePoints;
	private int currency;
	private Set<Ability> abilities;
	private Map<UUID, Integer> inventory;
	private Set<Item> inventoryCache;
	private Item equippedHead;
	private Item equippedBody;
	private Item equippedBack;
	private Item equippedNeck;
	private Item equippedPrimary;
	private Item equippedSecondary;
	private int constitution;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int constitutionBonus;
	private int strengthBonus;
	private int dexterityBonus;
	private int intelligenceBonus;
	private int powerBonus;
	private int healthBonus;
	private int healthRegenBonus;
	private int armorPenBonus;
	private int armorBonus;
	private int dodgeRatingBonus;
	private int critRatingBonus;
	private int energyBonus;
	private int energyRegenBonus;
	private int currentHealth;
	private int currentEnergy;
	
	public PlayerCharacterExport() {}
	
	public PlayerCharacterExport(PlayerCharacter pc) {
		CqlSession session = CassandraConnector.getSession();
		AbilityDao adao = new AbilityDao(session);
		ItemDao idao = new ItemDao(session);
		
		this.id = pc.getId();
		this.location = pc.getLocation();
		this.currentDungeon = pc.getCurrentDungeon();
		this.currentShop = pc.getCurrentShop();
		this.day = pc.getDay();
		this.name = pc.getName();
		this.characterClass = pc.getCharacterClass();
		this.experience = pc.getExperience();
		this.level = pc.getLevel();
		this.attributePoints = pc.getAttributePoints();
		this.currency = pc.getCurrency();
		if(pc.getAbilityIds() != null) {
			this.abilities = new HashSet<>();
			pc.getAbilityIds().forEach(id->this.abilities.add(adao.findById(id)));
		}
		if(pc.getInventory() != null) {
			this.inventory = pc.getInventory();
			this.inventoryCache = new HashSet<>();
			pc.getInventory().forEach((id,count)->this.inventoryCache.add(idao.findById(id)));
		}
		if (pc.getEquippedHead() != null)
			this.equippedHead = idao.findById(pc.getEquippedHead());
		if (pc.getEquippedBody() != null)
			this.equippedBody = idao.findById(pc.getEquippedBody());
		if (pc.getEquippedBack() != null)
			this.equippedBack = idao.findById(pc.getEquippedBack());
		if (pc.getEquippedNeck() != null)
			this.equippedNeck = idao.findById(pc.getEquippedNeck());
		if (pc.getEquippedPrimary() != null)
			this.equippedPrimary = idao.findById(pc.getEquippedPrimary());
		if (pc.getEquippedSecondary() != null)
			this.equippedSecondary = idao.findById(pc.getEquippedSecondary());
		this.constitution = pc.getConstitution();
		this.strength = pc.getStrength();
		this.dexterity = pc.getDexterity();
		this.intelligence = pc.getIntelligence();
		this.constitutionBonus = pc.getConstitutionBonus();
		this.strengthBonus = pc.getStrengthBonus();
		this.dexterityBonus = pc.getDexterityBonus();
		this.intelligenceBonus = pc.getIntelligenceBonus();
		this.powerBonus = pc.getPowerBonus();
		this.healthBonus = pc.getHealthBonus();
		this.healthRegenBonus = pc.getHealthRegenBonus();
		this.armorPenBonus = pc.getArmorPenBonus();
		this.armorBonus = pc.getArmorBonus();
		this.dodgeRatingBonus = pc.getDodgeRatingBonus();
		this.critRatingBonus = pc.getCritRatingBonus();
		this.energyBonus = pc.getEnergyBonus();
		this.energyRegenBonus = pc.getEnergyRegenBonus();
		this.currentHealth = pc.getCurrentHealth();
		this.currentEnergy = pc.getCurrentEnergy();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UUID getCurrentDungeon() {
		return currentDungeon;
	}

	public void setCurrentDungeon(UUID currentDungeon) {
		this.currentDungeon = currentDungeon;
	}

	public UUID getCurrentShop() {
		return currentShop;
	}

	public void setCurrentShop(UUID currentShop) {
		this.currentShop = currentShop;
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

	public Set<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(Set<Ability> abilities) {
		this.abilities = abilities;
	}

	public Map<UUID, Integer> getInventory() {
		return inventory;
	}

	public void setInventory(Map<UUID, Integer> inventory) {
		this.inventory = inventory;
	}
	
	public Set<Item> getInventoryCache() {
		return inventoryCache;
	}

	public void setInventoryCache(Set<Item> inventoryCache) {
		this.inventoryCache = inventoryCache;
	}

	public Item getEquippedHead() {
		return equippedHead;
	}

	public void setEquippedHead(Item equippedHead) {
		this.equippedHead = equippedHead;
	}

	public Item getEquippedBody() {
		return equippedBody;
	}

	public void setEquippedBody(Item equippedBody) {
		this.equippedBody = equippedBody;
	}

	public Item getEquippedBack() {
		return equippedBack;
	}

	public void setEquippedBack(Item equippedBack) {
		this.equippedBack = equippedBack;
	}

	public Item getEquippedNeck() {
		return equippedNeck;
	}

	public void setEquippedNeck(Item equippedNeck) {
		this.equippedNeck = equippedNeck;
	}

	public Item getEquippedPrimary() {
		return equippedPrimary;
	}

	public void setEquippedPrimary(Item equippedPrimary) {
		this.equippedPrimary = equippedPrimary;
	}

	public Item getEquippedSecondary() {
		return equippedSecondary;
	}

	public void setEquippedSecondary(Item equippedSecondary) {
		this.equippedSecondary = equippedSecondary;
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

	@Override
	public int hashCode() {
		return Objects.hash(abilities, armorBonus, armorPenBonus, attributePoints, characterClass, constitution,
				constitutionBonus, critRatingBonus, currency, currentDungeon, currentEnergy, currentHealth, currentShop,
				day, dexterity, dexterityBonus, dodgeRatingBonus, energyBonus, energyRegenBonus, equippedBack,
				equippedBody, equippedHead, equippedNeck, equippedPrimary, equippedSecondary, experience, healthBonus,
				healthRegenBonus, id, intelligence, intelligenceBonus, inventory, inventoryCache, level, location, name,
				powerBonus, strength, strengthBonus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCharacterExport other = (PlayerCharacterExport) obj;
		return Objects.equals(abilities, other.abilities) && armorBonus == other.armorBonus
				&& armorPenBonus == other.armorPenBonus && attributePoints == other.attributePoints
				&& Objects.equals(characterClass, other.characterClass) && constitution == other.constitution
				&& constitutionBonus == other.constitutionBonus && critRatingBonus == other.critRatingBonus
				&& currency == other.currency && Objects.equals(currentDungeon, other.currentDungeon)
				&& currentEnergy == other.currentEnergy && currentHealth == other.currentHealth
				&& Objects.equals(currentShop, other.currentShop) && day == other.day && dexterity == other.dexterity
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& energyBonus == other.energyBonus && energyRegenBonus == other.energyRegenBonus
				&& Objects.equals(equippedBack, other.equippedBack) && Objects.equals(equippedBody, other.equippedBody)
				&& Objects.equals(equippedHead, other.equippedHead) && Objects.equals(equippedNeck, other.equippedNeck)
				&& Objects.equals(equippedPrimary, other.equippedPrimary)
				&& Objects.equals(equippedSecondary, other.equippedSecondary) && experience == other.experience
				&& healthBonus == other.healthBonus && healthRegenBonus == other.healthRegenBonus
				&& Objects.equals(id, other.id) && intelligence == other.intelligence
				&& intelligenceBonus == other.intelligenceBonus && Objects.equals(inventory, other.inventory)
				&& Objects.equals(inventoryCache, other.inventoryCache) && level == other.level
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& powerBonus == other.powerBonus && strength == other.strength && strengthBonus == other.strengthBonus;
	}

	@Override
	public String toString() {
		return "PlayerCharacterExport [id=" + id + ", location=" + location + ", currentDungeon=" + currentDungeon
				+ ", currentShop=" + currentShop + ", day=" + day + ", name=" + name + ", characterClass="
				+ characterClass + ", experience=" + experience + ", level=" + level + ", attributePoints="
				+ attributePoints + ", currency=" + currency + ", abilities=" + abilities + ", inventory=" + inventory
				+ ", inventoryCache=" + inventoryCache + ", equippedHead=" + equippedHead + ", equippedBody="
				+ equippedBody + ", equippedBack=" + equippedBack + ", equippedNeck=" + equippedNeck
				+ ", equippedPrimary=" + equippedPrimary + ", equippedSecondary=" + equippedSecondary
				+ ", constitution=" + constitution + ", strength=" + strength + ", dexterity=" + dexterity
				+ ", intelligence=" + intelligence + ", constitutionBonus=" + constitutionBonus + ", strengthBonus="
				+ strengthBonus + ", dexterityBonus=" + dexterityBonus + ", intelligenceBonus=" + intelligenceBonus
				+ ", powerBonus=" + powerBonus + ", healthBonus=" + healthBonus + ", healthRegenBonus="
				+ healthRegenBonus + ", armorPenBonus=" + armorPenBonus + ", armorBonus=" + armorBonus
				+ ", dodgeRatingBonus=" + dodgeRatingBonus + ", critRatingBonus=" + critRatingBonus + ", energyBonus="
				+ energyBonus + ", energyRegenBonus=" + energyRegenBonus + ", currentHealth=" + currentHealth
				+ ", currentEnergy=" + currentEnergy + "]";
	}
	
	
}
