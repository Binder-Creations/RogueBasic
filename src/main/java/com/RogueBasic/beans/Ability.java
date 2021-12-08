package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;

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
	private Flags pcFlags;
	private Flags monsterFlags;
	private Flags allMonstersFlags;
	private Set<Buff> buffs;
	private Set<Buff> debuffs;;
	
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
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description);
		this.pcFlags = pcFlags;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags);
		this.monsterFlags = monsterFlags;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags, Flags allMonstersFlags) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, monsterFlags);
		this.allMonstersFlags = allMonstersFlags;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Set<Buff> buffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description);
		this.buffs = buffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Set<Buff> buffs, Set<Buff> debuffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, buffs);
		this.debuffs = debuffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Set<Buff> buffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags);
		this.buffs = buffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags, Set<Buff> buffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, monsterFlags);
		this.buffs = buffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags, Flags allMonstersFlags, Set<Buff> buffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, monsterFlags, allMonstersFlags);
		this.buffs = buffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Set<Buff> buffs, Set<Buff> debuffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, buffs);
		this.debuffs = debuffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags, Set<Buff> buffs, Set<Buff> debuffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, monsterFlags, buffs);
		this.debuffs = debuffs;
	}
	
	public Ability(int level, String name, int cost, float modifier, int factor, int hits, String target, String type, String icon, String description, Flags pcFlags, Flags monsterFlags, Flags allMonstersFlags, Set<Buff> buffs, Set<Buff> debuffs) {
		this(level, name, cost, modifier, factor, hits, target, type, icon, description, pcFlags, monsterFlags, allMonstersFlags, buffs);
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

	public Flags getPcFlags() {
		return pcFlags;
	}

	public void setPcFlags(Flags pcFlags) {
		this.pcFlags = pcFlags;
	}

	public Flags getMonsterFlags() {
		return monsterFlags;
	}

	public void setMonsterFlags(Flags monsterFlags) {
		this.monsterFlags = monsterFlags;
	}

	public Flags getAllMonstersFlags() {
		return allMonstersFlags;
	}

	public void setAllMonstersFlags(Flags allMonstersFlags) {
		this.allMonstersFlags = allMonstersFlags;
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
		return Objects.hash(allMonstersFlags, buffs, cost, debuffs, description, factor, hits, icon, level, modifier,
				monsterFlags, name, pcFlags, target, type);
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
		return Objects.equals(allMonstersFlags, other.allMonstersFlags) && Objects.equals(buffs, other.buffs)
				&& cost == other.cost && Objects.equals(debuffs, other.debuffs)
				&& Objects.equals(description, other.description) && factor == other.factor && hits == other.hits
				&& Objects.equals(icon, other.icon) && level == other.level
				&& Float.floatToIntBits(modifier) == Float.floatToIntBits(other.modifier)
				&& Objects.equals(monsterFlags, other.monsterFlags) && Objects.equals(name, other.name)
				&& Objects.equals(pcFlags, other.pcFlags) && Objects.equals(target, other.target)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Ability [level=" + level + ", name=" + name + ", cost=" + cost + ", modifier=" + modifier + ", factor="
				+ factor + ", hits=" + hits + ", target=" + target + ", type=" + type + ", icon=" + icon
				+ ", description=" + description + ", pcFlags=" + pcFlags + ", monsterFlags=" + monsterFlags
				+ ", allMonstersFlags=" + allMonstersFlags + ", buffs=" + buffs + ", debuffs=" + debuffs + "]";
	}
	
	
	
}
