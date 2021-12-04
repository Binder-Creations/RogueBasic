package com.RogueBasic.beans;

import java.util.Objects;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "ability")
public class Ability {
	private int level;
	private String name;
	private int cost;
	private float modifier;
	private int factor;
	private int hits;
	private String target;
	private String type;
	private String icon;
	private String description;
	private String flags;
	private String buffs;
	private String debuffs;
	
	public Ability() {}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description) {
		super();
		this.level = level;
		this.name = name;
		this.cost = cost;
		this.modifier = modifier;
		this.factor = factor;
		this.hits = hits;
		this.target = target;
		this.type = type;
		this.icon = icon;
		this.description = description;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, String flags) {
		super();
		this.level = level;
		this.name = name;
		this.cost = cost;
		this.modifier = modifier;
		this.factor = factor;
		this.hits = hits;
		this.target = target;
		this.type = type;
		this.icon = icon;
		this.description = description;
		this.flags = flags;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, String buffs, String debuffs) {
		super();
		this.level = level;
		this.name = name;
		this.cost = cost;
		this.modifier = modifier;
		this.factor = factor;
		this.hits = hits;
		this.target = target;
		this.type = type;
		this.icon = icon;
		this.description = description;
		this.buffs = buffs;
		this.debuffs = debuffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, String flags, String buffs, String debuffs) {
		super();
		this.level = level;
		this.name = name;
		this.cost = cost;
		this.modifier = modifier;
		this.factor = factor;
		this.hits = hits;
		this.target = target;
		this.type = type;
		this.icon = icon;
		this.description = description;
		this.flags = flags;
		this.buffs = buffs;
		this.debuffs = debuffs;
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

	public float getModifier() {
		return modifier;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}
	
	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getBuffs() {
		return buffs;
	}

	public void setBuffs(String buffs) {
		this.buffs = buffs;
	}

	public String getDebuffs() {
		return debuffs;
	}

	public void setDebuffs(String debuffs) {
		this.debuffs = debuffs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(buffs, cost, debuffs, description, factor, flags, hits, icon, level, modifier, name, target,
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
		Ability other = (Ability) obj;
		return Objects.equals(buffs, other.buffs) && cost == other.cost && Objects.equals(debuffs, other.debuffs)
				&& Objects.equals(description, other.description) && factor == other.factor
				&& Objects.equals(flags, other.flags) && hits == other.hits && Objects.equals(icon, other.icon)
				&& level == other.level && Float.floatToIntBits(modifier) == Float.floatToIntBits(other.modifier)
				&& Objects.equals(name, other.name) && Objects.equals(target, other.target)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Ability [level=" + level + ", name=" + name + ", cost=" + cost + ", modifier=" + modifier + ", factor="
				+ factor + ", hits=" + hits + ", target=" + target + ", type=" + type + ", icon=" + icon
				+ ", description=" + description + ", flags=" + flags + ", buffs=" + buffs + ", debuffs=" + debuffs
				+ "]";
	}
	
	
	
}
