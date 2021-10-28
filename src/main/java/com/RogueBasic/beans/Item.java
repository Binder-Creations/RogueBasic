package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "item")
public class Item {
	
	private UUID id;
	private String name;
	private String description;
	private String type;
	private String rarity;
	private String image;
	private int cost;
	private String actionType;
	private int actionValue;
	private int constitutionBonus;
	private int strengthBonus;
	private int dexterityBonus;
	private int intelligenceBonus;
	private int powerBonus;
	private int healthBonus;
	private int healthRegenBonus;
	private int armorBonus;
	private int armorPenBonus;
	private int dodgeRatingBonus;
	private int critRatingBonus;
	private int energyBonus;
	private int energyRegenBonus;
	
	public Item() {}
	
	public Item(String name, String description, String type, String rarity, String image, int cost, String actionType, int actionValue) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.type = type;
		this.rarity = rarity;
		this.image = image;
		this.cost = cost;
		this.actionType = actionType;
		this.actionValue = actionValue;
	}
	
	public Item(UUID id, String name, String description, String type, String rarity, String image, int cost, String actionType, int actionValue) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.rarity = rarity;
		this.image = image;
		this.cost = cost;
		this.actionType = actionType;
		this.actionValue = actionValue;
	}
	
	public Item(String name, String description, String type, String rarity, String image, int cost, String actionType, int actionValue, int constitutionBonus, int strengthBonus, int dexterityBonus, int intelligenceBonus, int powerBonus, int healthBonus, int healthRegenBonus, int armorBonus, int armorPenBonus, int dodgeRatingBonus, int critRatingBonus, int energyBonus, int energyRegenBonus) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.type = type;
		this.rarity = rarity;
		this.image = image;
		this.cost = cost;
		this.actionType = actionType;
		this.actionValue = actionValue;
		this.constitutionBonus = constitutionBonus;
		this.strengthBonus = strengthBonus;
		this.dexterityBonus = dexterityBonus;
		this.intelligenceBonus = intelligenceBonus;
		this.powerBonus = powerBonus;
		this.healthBonus = healthBonus;
		this.healthRegenBonus = healthRegenBonus;
		this.dodgeRatingBonus = dodgeRatingBonus;
		this.critRatingBonus = critRatingBonus;
		this.energyBonus = energyBonus;
		this.energyRegenBonus = energyRegenBonus;
		this.armorBonus = armorBonus;
		this.armorPenBonus = armorPenBonus;
	}
	
	public Item(UUID id, String name, String description, String type, String rarity, String image, int cost, String actionType, int actionValue, int constitutionBonus, int strengthBonus, int dexterityBonus, int intelligenceBonus, int powerBonus, int healthBonus, int healthRegenBonus, int armorBonus, int armorPenBonus, int dodgeRatingBonus, int critRatingBonus, int energyBonus, int energyRegenBonus) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.rarity = rarity;
		this.image = image;
		this.cost = cost;
		this.actionType = actionType;
		this.actionValue = actionValue;
		this.constitutionBonus = constitutionBonus;
		this.strengthBonus = strengthBonus;
		this.dexterityBonus = dexterityBonus;
		this.intelligenceBonus = intelligenceBonus;
		this.powerBonus = powerBonus;
		this.healthBonus = healthBonus;
		this.healthRegenBonus = healthRegenBonus;
		this.dodgeRatingBonus = dodgeRatingBonus;
		this.critRatingBonus = critRatingBonus;
		this.energyBonus = energyBonus;
		this.energyRegenBonus = energyRegenBonus;
		this.armorBonus = armorBonus;
		this.armorPenBonus = armorPenBonus;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
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

	public int getArmorBonus() {
		return armorBonus;
	}

	public void setArmorBonus(int armorBonus) {
		this.armorBonus = armorBonus;
	}

	public int getArmorPenBonus() {
		return armorPenBonus;
	}

	public void setArmorPenBonus(int armorPenBonus) {
		this.armorPenBonus = armorPenBonus;
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

	@Override
	public int hashCode() {
		return Objects.hash(actionType, actionValue, armorBonus, armorPenBonus, constitutionBonus, cost,
				critRatingBonus, description, dexterityBonus, dodgeRatingBonus, energyBonus, energyRegenBonus,
				healthBonus, healthRegenBonus, id, image, intelligenceBonus, name, powerBonus, rarity, strengthBonus,
				type);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(actionType, other.actionType) && actionValue == other.actionValue
				&& armorBonus == other.armorBonus && armorPenBonus == other.armorPenBonus
				&& constitutionBonus == other.constitutionBonus && cost == other.cost
				&& critRatingBonus == other.critRatingBonus && Objects.equals(description, other.description)
				&& dexterityBonus == other.dexterityBonus && dodgeRatingBonus == other.dodgeRatingBonus
				&& energyBonus == other.energyBonus && energyRegenBonus == other.energyRegenBonus
				&& healthBonus == other.healthBonus && healthRegenBonus == other.healthRegenBonus
				&& Objects.equals(id, other.id) && Objects.equals(image, other.image)
				&& intelligenceBonus == other.intelligenceBonus && Objects.equals(name, other.name)
				&& powerBonus == other.powerBonus && Objects.equals(rarity, other.rarity)
				&& strengthBonus == other.strengthBonus && Objects.equals(type, other.type);
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type + ", rarity="
				+ rarity + ", image=" + image + ", cost=" + cost + ", actionType=" + actionType + ", actionValue="
				+ actionValue + ", constitutionBonus=" + constitutionBonus + ", strengthBonus=" + strengthBonus
				+ ", dexterityBonus=" + dexterityBonus + ", intelligenceBonus=" + intelligenceBonus + ", powerBonus="
				+ powerBonus + ", healthBonus=" + healthBonus + ", healthRegenBonus=" + healthRegenBonus
				+ ", armorBonus=" + armorBonus + ", armorPenBonus=" + armorPenBonus + ", dodgeRatingBonus="
				+ dodgeRatingBonus + ", critRatingBonus=" + critRatingBonus + ", energyBonus=" + energyBonus
				+ ", energyRegenBonus=" + energyRegenBonus + "]";
	}
	
	
}
