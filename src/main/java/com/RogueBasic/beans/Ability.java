package com.RogueBasic.beans;

import java.util.Objects;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "ability")
public class Ability {
	private int level;
	private String name;
	private int cost;
	private int modifier;
	private int hits;
	private String type;
	private String description;
	
	public Ability() {}
	
	public Ability(int level, String name, int cost, int modifier, int hits, String type, String description) {
		super();
		this.level = level;
		this.name = name;
		this.cost = cost;
		this.modifier = modifier;
		this.hits = hits;
		this.type = type;
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, description, hits, level, modifier, name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ability other = (Ability) obj;
		return cost == other.cost && Objects.equals(description, other.description) && hits == other.hits
				&& level == other.level && modifier == other.modifier && Objects.equals(name, other.name)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Ability [level=" + level + ", name=" + name + ", cost=" + cost + ", modifier=" + modifier + ", hits="
				+ hits + ", type=" + type + ", description=" + description + "]";
	}
	
	
	
}
