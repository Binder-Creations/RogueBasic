package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table
public class PlayerCharacterAWS {
	@PrimaryKey private UUID id;
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
	private Set<String> abilities;
	private Set<String> inventory;
	private List<String> equipTypes;
	private String equippedHead;
	private String equippedBody;
	private String equippedBack;
	private String equippedNeck;
	private String equippedPrimary;
	private String equippedSecondary;
	private int constitution;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int currentHealth;
	private int currentEnergy;
	
	public PlayerCharacterAWS() {}
	
	public PlayerCharacterAWS(PlayerCharacter pc) {
		ObjectMapper mapper = new ObjectMapper();
		Set<String> abilities = new HashSet<>();
		if(pc.getAbilities() != null) {
			for(Ability ability: pc.getAbilities()) {
				try {
					abilities.add(mapper.writeValueAsString(ability));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		Set<String> inventory = new HashSet<>();
		if(pc.getInventory() != null) {
			for(Item item: pc.getInventory()) {
				try {
					inventory.add(mapper.writeValueAsString(item));
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
			this.equippedHead = mapper.writeValueAsString(pc.getEquippedHead());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedBody = mapper.writeValueAsString(pc.getEquippedBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedBack = mapper.writeValueAsString(pc.getEquippedBack());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedNeck = mapper.writeValueAsString(pc.getEquippedNeck());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedPrimary = mapper.writeValueAsString(pc.getEquippedPrimary());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.equippedSecondary = mapper.writeValueAsString(pc.getEquippedSecondary());
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

	public Set<String> getAbilities() {
		return abilities;
	}

	public void setAbilities(Set<String> abilities) {
		this.abilities = abilities;
	}

	public Set<String> getInventory() {
		return inventory;
	}

	public void setInventory(Set<String> inventory) {
		this.inventory = inventory;
	}

	public List<String> getEquipTypes() {
		return equipTypes;
	}

	public void setEquipTypes(List<String> equipTypes) {
		this.equipTypes = equipTypes;
	}

	public String getEquippedHead() {
		return equippedHead;
	}

	public void setEquippedHead(String equippedHead) {
		this.equippedHead = equippedHead;
	}

	public String getEquippedBody() {
		return equippedBody;
	}

	public void setEquippedBody(String equippedBody) {
		this.equippedBody = equippedBody;
	}

	public String getEquippedBack() {
		return equippedBack;
	}

	public void setEquippedBack(String equippedBack) {
		this.equippedBack = equippedBack;
	}

	public String getEquippedNeck() {
		return equippedNeck;
	}

	public void setEquippedNeck(String equippedNeck) {
		this.equippedNeck = equippedNeck;
	}

	public String getEquippedPrimary() {
		return equippedPrimary;
	}

	public void setEquippedPrimary(String equippedPrimary) {
		this.equippedPrimary = equippedPrimary;
	}

	public String getEquippedSecondary() {
		return equippedSecondary;
	}

	public void setEquippedSecondary(String equippedSecondary) {
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
		PlayerCharacterAWS other = (PlayerCharacterAWS) obj;
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
		return "PlayerCharacterAWS [id=" + id + ", location=" + location + ", dungeonBoard=" + dungeonBoard
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