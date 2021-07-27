package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

import com.RogueBasic.functions.Use;

public class Ability {
	private UUID id;
	private String name;
	private String description;
	private int level;
	private int energyCost;
	private String area;
	private Use use;
	
	
	//constructor for monster abilities
	public Ability(String name, String description, Use use) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.use = use;
	}
	
	//constructor for character abilities
	public Ability(String name, String description, int level, int energyCost, String area, Use use) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.level = level;
		this.energyCost = energyCost;
		this.area = area;
		this.use = use;
	}

	public UUID getId() {
		return id;
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
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getEnergyCost() {
		return energyCost;
	}
	
	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public Use getUse() {
		return use;
	}
	
	public void setUse(Use use) {
		this.use = use;
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, description, energyCost, id, level, name);
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
		return Objects.equals(area, other.area) && Objects.equals(description, other.description)
				&& energyCost == other.energyCost && id == other.id && level == other.level
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Ability [id=" + id + ", name=" + name + ", description=" + description + ", level=" + level
				+ ", energyCost=" + energyCost + ", area=" + area +"]";
	}
	
	
}
