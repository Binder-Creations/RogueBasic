package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Dungeon {
	
	@PrimaryKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private String prefixMod;
	private String postfixMod;
	private int floorCount;
	private Set<UUID> floorIds;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;

	
	public Dungeon() {}
	
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
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getPrefixMod() {
		return prefixMod;
	}

	public void setPrefixMod(String prefixMod) {
		this.prefixMod = prefixMod;
	}

	public String getPostfixMod() {
		return postfixMod;
	}

	public void setPostfixMod(String postfixMod) {
		this.postfixMod = postfixMod;
	}

	public int getFloorCount() {
		return floorCount;
	}
	
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
	
	public Set<UUID> getFloorIds() {
		return floorIds;
	}
	
	public void setFloorIds(Set<UUID> floorIds) {
		this.floorIds = floorIds;
	}
	
	public int getChallengeRating() {
		return challengeRating;
	}
	
	public void setChallengeRating(int challengeRating) {
		this.challengeRating = challengeRating;
	}
	
	public boolean isMiniboss() {
		return miniboss;
	}
	
	public void setMiniboss(boolean miniboss) {
		this.miniboss = miniboss;
	}
	
	public boolean isBoss() {
		return boss;
	}
	
	public void setBoss(boolean boss) {
		this.boss = boss;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boss, challengeRating, description, floorCount, floorIds, id, miniboss, name, postfixMod,
				prefixMod, theme);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dungeon other = (Dungeon) obj;
		return boss == other.boss && challengeRating == other.challengeRating
				&& Objects.equals(description, other.description) && floorCount == other.floorCount
				&& Objects.equals(floorIds, other.floorIds) && Objects.equals(id, other.id)
				&& miniboss == other.miniboss && Objects.equals(name, other.name)
				&& Objects.equals(postfixMod, other.postfixMod) && Objects.equals(prefixMod, other.prefixMod)
				&& Objects.equals(theme, other.theme);
	}
	
	@Override
	public String toString() {
		return "Dungeon [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", prefixMod=" + prefixMod + ", postfixMod=" + postfixMod + ", floorCount=" + floorCount
				+ ", floorIds=" + floorIds + ", challengeRating=" + challengeRating + ", miniboss=" + miniboss
				+ ", boss=" + boss + "]";
	}
	
	
}
