package com.RogueBasic.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.data.ItemDao;
import com.RogueBasic.services.ItemServices;
import com.RogueBasic.util.CassandraConnector;


@Table
public class PlayerCharacter {
	
	@PrimaryKey private UUID id;
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
	private UUID equippedHead;
	private UUID equippedBody;
	private UUID equippedBack;
	private UUID equippedNeck;
	private UUID equippedPrimary;
	private UUID equippedSecondary;
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
		this.inventory.put(UUID.fromString("d822106a-e753-40db-898d-d438d1592baa"), 1);
		this.inventory.put(UUID.fromString("ab0d75df-8457-4dde-adfe-77c9b37d262d"), 2);
		switch(characterClass) {
			case "Rogue":
				this.equippedBody = UUID.fromString("4013e206-1991-458b-b998-7c8536ad4af3");
				this.equippedPrimary = UUID.fromString("4eff0337-cae1-42c1-b1fe-6ac397f12c09");
				this.equippedSecondary = UUID.fromString("5fb043b1-745d-4243-b22a-e8ecc69ddb81");
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
				this.equippedBody = UUID.fromString("914f823f-2691-4f39-bd8f-27381ccfb556");
				this.equippedPrimary = UUID.fromString("2d139761-0d80-4be1-88dd-436c2c76c0a0");
				this.equippedSecondary = UUID.fromString("9413a77b-a76a-4462-8e9e-a05b6a4e479f");
				this.currency = 100 + currencyMetabonus;
				this.abilities = new HashSet<>();
				this.abilities.add(new Ability(1, "Heavy Strike", 8, 160, 1, "Power", "Attack the front right enemy with a mighty blow. 150% of Armor Penetration applies to this attack."));
				this.abilities.add(new Ability(2, "Stunning Bash", 16, 140, 1, "Power", "Attack the front left enemy with a stunning shield strike. A non-boss enemy hit by this attack is stunned for 1 round."));
				this.abilities.add(new Ability(4, "Wide Sweep", 32, 180, 1, "Power", "Attack all front-row enemies."));
				this.abilities.add(new Ability(6, "Living Fortress", 64, 500, 1, "Armor", "Dramatically increases armor and provites immunity to critical hits until end of combat."));
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
				this.equippedBody = UUID.fromString("111e072e-0c47-49b3-8cd6-7ddbffd025ea");
				this.equippedPrimary = UUID.fromString("a3fc0e39-2baf-4729-9b14-28b6a8528d53");
				this.equippedSecondary = UUID.fromString("d32c3382-3752-4244-9bf0-78d7a93946a5");
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
		ItemDao id = new ItemDao(CassandraConnector.getSession());
		for(int i = 0; i < 30; i++) {
			String[] exceptions = {};
			Item item = iss.genEquipment(exceptions, 8);
			id.save(item);
			inventory.put(item.getId(), 1);
		}
	}
	
	public PlayerCharacter(PlayerCharacterExport pc) {
		this.id = pc.getId();
		this.location = pc.getLocation();
		if(pc.getDungeonBoard() != null) {
			this.dungeonBoard = new HashSet<>();
			pc.getDungeonBoard().forEach(dungeon -> this.dungeonBoard.add(dungeon.getId()));
		}
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
		this.abilities = pc.getAbilities();
		this.inventory = pc.getInventory();
		this.equippedHead = pc.getEquippedHead() != null
			? pc.getEquippedHead().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
		this.equippedBody = pc.getEquippedBody() != null
			? pc.getEquippedBody().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
		this.equippedBack = pc.getEquippedBack() != null
			? pc.getEquippedBack().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
		this.equippedNeck = pc.getEquippedNeck() != null
			? pc.getEquippedNeck().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
		this.equippedPrimary = pc.getEquippedPrimary() != null
			? pc.getEquippedPrimary().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
		this.equippedSecondary = pc.getEquippedSecondary() != null
			? pc.getEquippedSecondary().getId()
			: UUID.fromString("00000000-0000-0000-0000-000000000000");
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

	public Map<UUID,Integer> getInventory() {
		return inventory;
	}
	
	public void setInventory(Map<UUID,Integer> inventory) {
		this.inventory = inventory;
	}
	
	public UUID getEquippedHead() {
		return equippedHead;
	}

	public void setEquippedHead(UUID equippedHead) {
		this.equippedHead = equippedHead;
	}

	public UUID getEquippedBody() {
		return equippedBody;
	}

	public void setEquippedBody(UUID equippedBody) {
		this.equippedBody = equippedBody;
	}

	public UUID getEquippedBack() {
		return equippedBack;
	}

	public void setEquippedBack(UUID equippedBack) {
		this.equippedBack = equippedBack;
	}

	public UUID getEquippedNeck() {
		return equippedNeck;
	}

	public void setEquippedNeck(UUID equippedNeck) {
		this.equippedNeck = equippedNeck;
	}

	public UUID getEquippedPrimary() {
		return equippedPrimary;
	}

	public void setEquippedPrimary(UUID equippedPrimary) {
		this.equippedPrimary = equippedPrimary;
	}

	public UUID getEquippedSecondary() {
		return equippedSecondary;
	}

	public void setEquippedSecondary(UUID equippedSecondary) {
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
				healthBonus, healthRegenBonus, id, intelligence, intelligenceBonus, inventory, level, location, name,
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
				&& level == other.level && Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& powerBonus == other.powerBonus && strength == other.strength && strengthBonus == other.strengthBonus;
	}
	
	@Override
	public String toString() {
		return "PlayerCharacter [id=" + id + ", location=" + location + ", dungeonBoard=" + dungeonBoard
				+ ", currentDungeon=" + currentDungeon + ", currentShop=" + currentShop + ", day=" + day + ", ate="
				+ ate + ", name=" + name + ", characterClass=" + characterClass + ", experience=" + experience
				+ ", level=" + level + ", attributePoints=" + attributePoints + ", currency=" + currency
				+ ", abilities=" + abilities + ", inventory=" + inventory + ", equippedHead=" + equippedHead
				+ ", equippedBody=" + equippedBody + ", equippedBack=" + equippedBack + ", equippedNeck=" + equippedNeck
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
