package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;

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
	private Flags selfFlags;
	private Flags targetFlags;
	private Set<Buff> buffs;
	private Set<Buff> debuffs;
	
	public Ability() {}

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

	public Flags getSelfFlags() {
		return selfFlags;
	}

	public void setSelfFlags(Flags selfFlags) {
		this.selfFlags = selfFlags;
	}

	public Flags getTargetFlags() {
		return targetFlags;
	}

	public void setTargetFlags(Flags targetFlags) {
		this.targetFlags = targetFlags;
	}

	public Set<Buff> getBuffs() {
		return buffs;
	}

	public void setBuffs(Set<Buff> buffs) {
		this.buffs = buffs;
	}

	public Set<Buff> getDebuffs() {
		return debuffs;
	}

	public void setDebuffs(Set<Buff> debuffs) {
		this.debuffs = debuffs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(buffs, cost, debuffs, description, factor, hits, icon, level, modifier, name, selfFlags,
				target, targetFlags, type);
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
				&& Objects.equals(description, other.description) && factor == other.factor && hits == other.hits
				&& Objects.equals(icon, other.icon) && level == other.level
				&& Float.floatToIntBits(modifier) == Float.floatToIntBits(other.modifier)
				&& Objects.equals(name, other.name) && Objects.equals(selfFlags, other.selfFlags)
				&& Objects.equals(target, other.target) && Objects.equals(targetFlags, other.targetFlags)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Ability [level=" + level + ", name=" + name + ", cost=" + cost + ", modifier=" + modifier + ", factor="
				+ factor + ", hits=" + hits + ", target=" + target + ", type=" + type + ", icon=" + icon
				+ ", description=" + description + ", selfFlags=" + selfFlags + ", targetFlags=" + targetFlags
				+ ", buffs=" + buffs + ", debuffs=" + debuffs + "]";
	}
	
	
	
}
