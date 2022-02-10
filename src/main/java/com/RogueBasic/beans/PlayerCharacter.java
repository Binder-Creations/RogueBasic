package com.RogueBasic.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.services.ItemServices;
import com.RogueBasic.util.CassandraConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


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
	private int metacurrency;
	private Set<Ability> abilities;
	private Set<Item> inventory;
	private List<String> equipTypes;
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
		this.inventory = new HashSet<>();
		
		ItemServices iservice = new ItemServices(CassandraConnector.connect());
		Item healthPotion = iservice.getPremade(2);
		Item energyPotion = iservice.getPremade(3);
		Item ration = iservice.getPremade(4);
		ration.setCount(2);
		Item wine = iservice.getPremade(5);
		wine.setCount(2);
		this.inventory.add(healthPotion);
		this.inventory.add(energyPotion);
		this.inventory.add(ration);
		this.inventory.add(wine);
		this.abilities = new HashSet<>();
		
		switch(characterClass) {
			case "Rogue":
				this.equippedBody = iservice.getPremade(9);
				this.equippedPrimary = iservice.getPremade(10);
				this.equippedSecondary = iservice.getPremade(11);
				this.currency = 150 + currencyMetabonus;
				this.equipTypes = new ArrayList<String>(Arrays.asList(new String[] {"headMedium", "bodyMedium", "bow", "dagger", "back", "neck"}));
				this.abilities.add(new Ability(0, "Steady Shot", 0, 1f, 10, 1, "lowHealth", "Attack", "Power", "Fire off a basic shot."));
				this.abilities.add(new Ability(1, "Deadeye", 10, 1.6f, 0, 1, "backCenter", "Attack", "Power", "Shoot the back center enemy with unerring precision. This attack cannot be dodged.", new Flags("hit")));	
				this.abilities.add(new Ability(2, "Shadowmeld", 20, 8f, 0, 1, "self", "Buff", "Armor", "Become one with the shadows, making it harder for enemies to hit you. Dramatically increases your dodge and critical ratings for one round.", new Buff("shadowmeld", 2, new String[]{"critRating", "dodgeRating"})));
				this.abilities.add(new Ability(4, "Assassinate", 40, 2.6f, 15, 1, "highHealth", "Attack", "Power", "Attack an enemy with a deadly strike. Your critical multiplier is doubled for this attack.", new Flags("critDoubleDamage")));
				this.abilities.add(new Ability(6, "Blade Dance", 80, 0.8f, 20, 8, "lowHealth", "Attack", "Power", "Unleash a deadly flurry of attacks."));
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
				this.equippedBody = iservice.getPremade(12);
				this.equippedPrimary = iservice.getPremade(13);
				this.equippedSecondary = iservice.getPremade(14);
				this.currency = 100 + currencyMetabonus;
				this.equipTypes = new ArrayList<String>(Arrays.asList(new String[] {"headHeavy", "bodyHeavy", "sword", "shield", "back", "neck"}));		
				this.abilities.add(new Ability(0, "Straight Slash", 0, 1f, 25, 1, "frontCenter", "Attack", "Power", "Make a basic attack."));
				this.abilities.add(new Ability(1, "Heavy Strike", 8, 1.6f, 35, 1, "frontRight", "Attack", "Power", "Attack with a mighty blow. 150% of Armor Penetration applies.", new Flags("highPen")));
				this.abilities.add(new Ability(2, "Stunning Bash", 16, 1.4f, 20, 1, "frontLeft", "Attack", "Power", "Attack with a stunning shield strike. Stuns normal enemies for 1 round.", null, new Flags("stun")));
				this.abilities.add(new Ability(4, "Wide Sweep", 32, 1.8f, 30, 1, "frontRow", "Attack", "Power", "Attack all enemies in a row."));
				this.abilities.add(new Ability(6, "Living Fortress", 64, 5f, 0, 1, "self", "Buff", "Armor", "Dramatically increases armor and provites immunity to critical hits until end of combat.", new Flags("fortress", -1), new Buff("livingFortress", "armor")));
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
				this.equippedBody = iservice.getPremade(6);
				this.equippedPrimary = iservice.getPremade(7);
				this.equippedSecondary = iservice.getPremade(8);
				this.currency = 100 + currencyMetabonus;
				this.equipTypes = new ArrayList<String>(Arrays.asList(new String[] {"headLight", "bodyLight", "staff", "spellbook", "back", "neck"}));
				this.abilities.add(new Ability(0, "Zap", 0, 1.2f, 55, 1, "random", "Attack", "Power", "Cast a basic spell.", new Flags("magic")));
				this.abilities.add(new Ability(1, "Ice Shards", 16, 0.75f, 15, 3, "random", "Attack", "Power", "Conjure and launch 3 frozen knives.", new Flags("magic")));
				this.abilities.add(new Ability(2, "Flame Cone", 32, 0.9f, 30,  1, "cone", "Attack", "Power", "Blast outward with a spreading cone of fire.", new Flags("magic")));
				this.abilities.add(new Ability(4, "Death Circuit", 64, 1.2f, 70, 5, "backCenter", "Chain", "Power", "Shock the back center enemy with a bolt of lightning that then jumps up to 4 times.", new Flags("magic")));
				this.abilities.add(new Ability(6, "Extinction Event", 128, 0.6f, 30, 20, "random", "Attack", "Power", "Call down a cataclysmic rain of 20 meteorites.", new Flags("magic")));
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
	}

	public PlayerCharacter(PlayerCharacterAWS pc) {
		ObjectMapper mapper = new ObjectMapper();
		Set<Ability> abilities = new HashSet<>();
		if(pc.getAbilities() != null) {
			for(String ability: pc.getAbilities()) {
				try {
					abilities.add(mapper.readValue(ability, Ability.class));
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		Set<Item> inventory = new HashSet<>();
		if(pc.getInventory() != null) {
			for(String item: pc.getInventory()) {
				try {
					inventory.add(mapper.readValue(item, Item.class));
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.id = pc.getId();
		this.location = pc.getLocation();
		this.dungeonBoard = pc.getDungeonBoard();
		this.currentDungeon = pc.getCurrentDungeon();
		this.currentShop = pc.getCurrentShop();
		this.day = pc.getDay();
		this.ate = pc.isAte();
		this.name = pc.getName();
		this.characterClass = pc.getCharacterClass();
		this.experience = pc.getExperience();
		this.level = pc.getLevel();
		this.attributePoints = pc.getAttributePoints();
		this.currency = pc.getCurrency();
		this.metacurrency = pc.getMetacurrency();
		this.abilities = abilities;
		this.inventory = inventory;
		this.equipTypes = pc.getEquipTypes();
		try {
			this.equippedHead = mapper.readValue(pc.getEquippedHead(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedBody = mapper.readValue(pc.getEquippedBody(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedBack = mapper.readValue(pc.getEquippedBack(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedNeck = mapper.readValue(pc.getEquippedNeck(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedPrimary = mapper.readValue(pc.getEquippedPrimary(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedSecondary = mapper.readValue(pc.getEquippedSecondary(), Item.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
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

	public int getMetacurrency() {
		return metacurrency;
	}

	public void setMetacurrency(int metacurrency) {
		this.metacurrency = metacurrency;
	}

	public Set<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(Set<Ability> abilities) {
		this.abilities = abilities;
	}
	
	public Set<Item> getInventory() {
		return inventory;
	}

	public void setInventory(Set<Item> inventory) {
		this.inventory = inventory;
	}

	public List<String> getEquipTypes() {
		return equipTypes;
	}

	public void setEquipTypes(List<String> equipTypes) {
		this.equipTypes = equipTypes;
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
				equipTypes, equippedBack, equippedBody, equippedHead, equippedNeck, equippedPrimary, equippedSecondary,
				experience, healthBonus, healthRegenBonus, id, intelligence, intelligenceBonus, inventory, level,
				location, metacurrency, name, powerBonus, strength, strengthBonus);
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
				&& energyRegenBonus == other.energyRegenBonus && Objects.equals(equipTypes, other.equipTypes)
				&& Objects.equals(equippedBack, other.equippedBack) && Objects.equals(equippedBody, other.equippedBody)
				&& Objects.equals(equippedHead, other.equippedHead) && Objects.equals(equippedNeck, other.equippedNeck)
				&& Objects.equals(equippedPrimary, other.equippedPrimary)
				&& Objects.equals(equippedSecondary, other.equippedSecondary) && experience == other.experience
				&& healthBonus == other.healthBonus && healthRegenBonus == other.healthRegenBonus
				&& Objects.equals(id, other.id) && intelligence == other.intelligence
				&& intelligenceBonus == other.intelligenceBonus && Objects.equals(inventory, other.inventory)
				&& level == other.level && Objects.equals(location, other.location)
				&& metacurrency == other.metacurrency && Objects.equals(name, other.name)
				&& powerBonus == other.powerBonus && strength == other.strength && strengthBonus == other.strengthBonus;
	}

	@Override
	public String toString() {
		return "PlayerCharacter [id=" + id + ", location=" + location + ", dungeonBoard=" + dungeonBoard
				+ ", currentDungeon=" + currentDungeon + ", currentShop=" + currentShop + ", day=" + day + ", ate="
				+ ate + ", name=" + name + ", characterClass=" + characterClass + ", experience=" + experience
				+ ", level=" + level + ", attributePoints=" + attributePoints + ", currency=" + currency
				+ ", metacurrency=" + metacurrency + ", abilities=" + abilities + ", inventory=" + inventory
				+ ", equipTypes=" + equipTypes + ", equippedHead=" + equippedHead + ", equippedBody=" + equippedBody
				+ ", equippedBack=" + equippedBack + ", equippedNeck=" + equippedNeck + ", equippedPrimary="
				+ equippedPrimary + ", equippedSecondary=" + equippedSecondary + ", constitution=" + constitution
				+ ", strength=" + strength + ", dexterity=" + dexterity + ", intelligence=" + intelligence
				+ ", constitutionBonus=" + constitutionBonus + ", strengthBonus=" + strengthBonus + ", dexterityBonus="
				+ dexterityBonus + ", intelligenceBonus=" + intelligenceBonus + ", powerBonus=" + powerBonus
				+ ", healthBonus=" + healthBonus + ", healthRegenBonus=" + healthRegenBonus + ", armorPenBonus="
				+ armorPenBonus + ", armorBonus=" + armorBonus + ", dodgeRatingBonus=" + dodgeRatingBonus
				+ ", critRatingBonus=" + critRatingBonus + ", energyBonus=" + energyBonus + ", energyRegenBonus="
				+ energyRegenBonus + ", currentHealth=" + currentHealth + ", currentEnergy=" + currentEnergy + "]";
	}
	
	
}
