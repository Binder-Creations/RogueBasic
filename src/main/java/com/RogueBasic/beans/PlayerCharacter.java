package com.RogueBasic.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.services.ItemServices;
import com.RogueBasic.util.CassandraConnector;


public class PlayerCharacter {
	
	private UUID id;
	private String location;
	private Set<UUID> dungeonBoard;
	private UUID currentDungeon;
	private UUID currentShop;
	private int day;
	private boolean ate;
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
		this.level = 1;
		this.currentHealth = -1;
		this.currentEnergy = -1;
		this.inventory = new HashMap<>();
		this.inventoryCache = new HashSet<>();
		
		ItemServices iservice = new ItemServices(CassandraConnector.connect());
		Item potion = iservice.getPremade(2);
		Item ration = iservice.getPremade(3);
		this.inventory.put(potion.getId(), 1);
		this.inventory.put(ration.getId(), 2);
		this.inventoryCache.add(potion);
		this.inventoryCache.add(ration);
		
		switch(characterClass) {
			case "Rogue":
				this.equippedBody = iservice.getPremade(7);
				this.equippedPrimary = iservice.getPremade(8);
				this.equippedSecondary = iservice.getPremade(9);
				this.currency = 150 + currencyMetabonus;
				this.abilities = new HashSet<>();
				this.abilities.add(new Ability(1, "Deadeye", 10, 160, 1, "Power", "Shoot the back center enemy with unerring precision. This attack cannot be dodged."));
				this.abilities.add(new Ability(2, "Shadowmeld", 20, 60, 1, "Armor", "Become one with the shadows, making it harder for enemies to track you. Increases your dodge and critical ratings until end of combat."));
				this.abilities.add(new Ability(4, "Assassinate", 40, 260, 1, "Power", "Attack the highest-health enemy with a deadly strike. Your critical chance is doubled for this attack."));
				this.abilities.add(new Ability(6, "Blade Dance", 80, 80, 8, "Power", "Unleash a flurry of attacks against the lowest-health enemies. This attack deals damage 8 times. If an enemy dies, it moves to the next lowest-health target."));
				this.powerBonus = 15;
				this.healthBonus = 20;
				this.healthRegenBonus = 2;
				this.armorPenBonus = 5;
				this.armorBonus = 5;
				this.dodgeRatingBonus = 15;
				this.critRatingBonus = 15;
				this.energyBonus = 30;
				this.energyRegenBonus = 4;	
				break;
			case "Warrior":
				this.equippedBody = iservice.getPremade(10);
				this.equippedPrimary = iservice.getPremade(11);
				this.equippedSecondary = iservice.getPremade(12);
				this.currency = 100 + currencyMetabonus;
				this.abilities = new HashSet<>();
				this.abilities.add(new Ability(1, "Straight Slash", 0, 1f, 25, 1, "frontCenter", "Attack", "Power", "Make a basic attack."));
				this.abilities.add(new Ability(1, "Heavy Strike", 8, 1.6f, 35, 1, "frontRight", "Attack", "Power", "Attack with a mighty blow. 150% of Armor Penetration applies.", "{\"pc\": {\"highPen\": 1}}"));
				this.abilities.add(new Ability(2, "Stunning Bash", 16, 1.4f, 20, 1, "frontLeft", "Attack", "Power", "Attack with a stunning shield strike. Stuns non-boss enemies for 1 round.", "{\"monster\": {\"stun\": 1}}"));
				this.abilities.add(new Ability(4, "Wide Sweep", 32, 1.8f, 30, 1, "frontRow", "Attack", "Power", "Attack all enemie in a row."));
				this.abilities.add(new Ability(6, "Living Fortress", 64, 5f, 0, 1, "self", "Buff", "Armor", "Dramatically increases armor and provites immunity to critical hits until end of combat.", "{\"pc\": {\"fortress\": true}}", "{\"fortress\": {\"stat\": \"armor\", \"duration\": true}}", null));
				this.powerBonus = 10;
				this.healthBonus = 30;
				this.healthRegenBonus = 4;
				this.armorPenBonus = 10;
				this.armorBonus = 10;
				this.dodgeRatingBonus = 5;
				this.critRatingBonus = 5;
				this.energyBonus = 20;
				this.energyRegenBonus = 3;
				break;
			case "Wizard":
				this.equippedBody = iservice.getPremade(4);
				this.equippedPrimary = iservice.getPremade(5);
				this.equippedSecondary = iservice.getPremade(6);
				this.currency = 100 + currencyMetabonus;
				this.abilities = new HashSet<>();
				this.abilities.add(new Ability(1, "Ice Shards", 16, 75, 3, "Power", "Conjure 3 frozen knives and throw them at random enemies."));
				this.abilities.add(new Ability(2, "Cone of Flame", 32, 90, 1, "Power", "Blast the front center enemy and all back-row enemies with fire."));
				this.abilities.add(new Ability(4, "Death Circuit", 64, 120, 5, "Power", "Shock the back center enemy with a bolt of lightning that then jumps to another random enemy up to 4 times."));
				this.abilities.add(new Ability(6, "Extinction Event", 128, 60, 20, "Power", "Call down a cataclysmic rain of 20 meteorites to damage random enemies."));
				this.powerBonus = 20;
				this.healthBonus = 10;
				this.healthRegenBonus = 1;
				this.armorPenBonus = 0;
				this.armorBonus = 0;
				this.dodgeRatingBonus = 0;
				this.critRatingBonus = 0;
				this.energyBonus = 40;
				this.energyRegenBonus = 5;		
				break;
		}
		ItemServices iss = new ItemServices(CassandraConnector.getSession());
		for(int i = 0; i < 30; i++) {
			String[] exceptions = {};
			Item item = iss.genEquipment(exceptions, 8);
			inventory.put(item.getId(), 1);
			inventoryCache.add(item);
		}
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
	
	public Set<UUID> getDungeonBoard() {
		return dungeonBoard;
	}

	public void setDungeonBoard(Set<UUID> dungeonBoard) {
		this.dungeonBoard = dungeonBoard;
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

	public boolean isAte() {
		return ate;
	}

	public void setAte(boolean ate) {
		this.ate = ate;
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
		return Objects.hash(abilities, armorBonus, armorPenBonus, ate, attributePoints, characterClass, constitution,
				constitutionBonus, critRatingBonus, currency, currentDungeon, currentEnergy, currentHealth, currentShop,
				day, dexterity, dexterityBonus, dodgeRatingBonus, dungeonBoard, energyBonus, energyRegenBonus,
				equippedBack, equippedBody, equippedHead, equippedNeck, equippedPrimary, equippedSecondary, experience,
				healthBonus, healthRegenBonus, id, intelligence, intelligenceBonus, inventory, inventoryCache, level,
				location, name, powerBonus, strength, strengthBonus);
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
		return Objects.equals(abilities, other.abilities) && armorBonus == other.armorBonus
				&& armorPenBonus == other.armorPenBonus && ate == other.ate && attributePoints == other.attributePoints
				&& Objects.equals(characterClass, other.characterClass) && constitution == other.constitution
				&& constitutionBonus == other.constitutionBonus && critRatingBonus == other.critRatingBonus
				&& currency == other.currency && Objects.equals(currentDungeon, other.currentDungeon)
				&& currentEnergy == other.currentEnergy && currentHealth == other.currentHealth
				&& Objects.equals(currentShop, other.currentShop) && day == other.day && dexterity == other.dexterity
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& Objects.equals(dungeonBoard, other.dungeonBoard) && energyBonus == other.energyBonus
				&& energyRegenBonus == other.energyRegenBonus && Objects.equals(equippedBack, other.equippedBack)
				&& Objects.equals(equippedBody, other.equippedBody) && Objects.equals(equippedHead, other.equippedHead)
				&& Objects.equals(equippedNeck, other.equippedNeck)
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
		return "PlayerCharacter [id=" + id + ", location=" + location + ", dungeonBoard=" + dungeonBoard
				+ ", currentDungeon=" + currentDungeon + ", currentShop=" + currentShop + ", day=" + day + ", ate="
				+ ate + ", name=" + name + ", characterClass=" + characterClass + ", experience=" + experience
				+ ", level=" + level + ", attributePoints=" + attributePoints + ", currency=" + currency
				+ ", abilities=" + abilities + ", inventory=" + inventory + ", inventoryCache=" + inventoryCache
				+ ", equippedHead=" + equippedHead + ", equippedBody=" + equippedBody + ", equippedBack=" + equippedBack
				+ ", equippedNeck=" + equippedNeck + ", equippedPrimary=" + equippedPrimary + ", equippedSecondary="
				+ equippedSecondary + ", constitution=" + constitution + ", strength=" + strength + ", dexterity="
				+ dexterity + ", intelligence=" + intelligence + ", constitutionBonus=" + constitutionBonus
				+ ", strengthBonus=" + strengthBonus + ", dexterityBonus=" + dexterityBonus + ", intelligenceBonus="
				+ intelligenceBonus + ", powerBonus=" + powerBonus + ", healthBonus=" + healthBonus
				+ ", healthRegenBonus=" + healthRegenBonus + ", armorPenBonus=" + armorPenBonus + ", armorBonus="
				+ armorBonus + ", dodgeRatingBonus=" + dodgeRatingBonus + ", critRatingBonus=" + critRatingBonus
				+ ", energyBonus=" + energyBonus + ", energyRegenBonus=" + energyRegenBonus + ", currentHealth="
				+ currentHealth + ", currentEnergy=" + currentEnergy + "]";
	}
	
	
}
