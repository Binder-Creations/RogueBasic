package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.RogueBasic.enums.CharacterClass;
import com.RogueBasic.enums.StaticItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerCharacter {
	private UUID id;
	private String location;
	private Set<UUID> dungeonBoard;
	private UUID currentDungeon;
	private UUID currentShop;
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
	private int currentHealth;
	private int currentEnergy;
	
	public PlayerCharacter() {}

	public PlayerCharacter(String name, CharacterClass characterClass, int constitution, int strength, int dexterity, int intelligence, int currencyMetabonus) {
		super();
		PlayerCharacter classBase = characterClass.getBase();
		this.id = UUID.randomUUID();
		this.location = "Town";
		this.name = name;
		this.characterClass = characterClass.getName();
		this.currency = 150 + currencyMetabonus;
		this.constitution = constitution;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
		this.level = 1;
		this.currentHealth = -1;
		this.currentEnergy = -1;
		this.inventory = new HashSet<>();
		this.inventory.add(StaticItem.HEALTH_POTION.getItem());
		this.inventory.add(StaticItem.ENERGY_POTION.getItem());
		this.inventory.add(StaticItem.RATIONS.getItem(2));
		this.inventory.add(StaticItem.WINE.getItem(2));
		this.equippedBody = classBase.getEquippedBody();
		this.equippedPrimary = classBase.getEquippedPrimary();
		this.equippedSecondary = classBase.getEquippedSecondary();
		this.equipTypes = classBase.getEquipTypes();
		this.abilities = classBase.getAbilities();
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
		return Objects.hash(abilities, ate, attributePoints, characterClass, constitution, currency, currentDungeon,
				currentEnergy, currentHealth, currentShop, dexterity, dungeonBoard, equipTypes, equippedBack,
				equippedBody, equippedHead, equippedNeck, equippedPrimary, equippedSecondary, experience, id,
				intelligence, inventory, level, location, metacurrency, name, strength);
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
		return Objects.equals(abilities, other.abilities) && ate == other.ate
				&& attributePoints == other.attributePoints && Objects.equals(characterClass, other.characterClass)
				&& constitution == other.constitution && currency == other.currency
				&& Objects.equals(currentDungeon, other.currentDungeon) && currentEnergy == other.currentEnergy
				&& currentHealth == other.currentHealth && Objects.equals(currentShop, other.currentShop)
				&& dexterity == other.dexterity && Objects.equals(dungeonBoard, other.dungeonBoard)
				&& Objects.equals(equipTypes, other.equipTypes) && Objects.equals(equippedBack, other.equippedBack)
				&& Objects.equals(equippedBody, other.equippedBody) && Objects.equals(equippedHead, other.equippedHead)
				&& Objects.equals(equippedNeck, other.equippedNeck)
				&& Objects.equals(equippedPrimary, other.equippedPrimary)
				&& Objects.equals(equippedSecondary, other.equippedSecondary) && experience == other.experience
				&& Objects.equals(id, other.id) && intelligence == other.intelligence
				&& Objects.equals(inventory, other.inventory) && level == other.level
				&& Objects.equals(location, other.location) && metacurrency == other.metacurrency
				&& Objects.equals(name, other.name) && strength == other.strength;
	}

	@Override
	public String toString() {
		return "PlayerCharacter [id=" + id + ", location=" + location + ", dungeonBoard=" + dungeonBoard
				+ ", currentDungeon=" + currentDungeon + ", currentShop=" + currentShop + ", ate=" + ate + ", name="
				+ name + ", characterClass=" + characterClass + ", experience=" + experience + ", level=" + level
				+ ", attributePoints=" + attributePoints + ", currency=" + currency + ", metacurrency=" + metacurrency
				+ ", abilities=" + abilities + ", inventory=" + inventory + ", equipTypes=" + equipTypes
				+ ", equippedHead=" + equippedHead + ", equippedBody=" + equippedBody + ", equippedBack=" + equippedBack
				+ ", equippedNeck=" + equippedNeck + ", equippedPrimary=" + equippedPrimary + ", equippedSecondary="
				+ equippedSecondary + ", constitution=" + constitution + ", strength=" + strength + ", dexterity="
				+ dexterity + ", intelligence=" + intelligence + ", currentHealth=" + currentHealth + ", currentEnergy="
				+ currentEnergy + "]";
	}
	
}
