package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue_basic", name = "dungeon")
public class Dungeon {
	
	@PartitionKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private int floorCount;
	private Set<UUID> floors;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;
	
	public Dungeon() {}
	
	public Dungeon(String name, String description, String theme, int floorCount, int challengeRating,
			boolean miniboss, boolean boss) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.theme = theme;
		this.floorCount = floorCount;
		this.challengeRating = challengeRating;
		this.miniboss = miniboss;
		this.boss = boss;
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
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public int getFloorCount() {
		return floorCount;
	}
	
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
	
	public Set<UUID> getFloors() {
		return floors;
	}
	
	public void setFloors(Set<UUID> floors) {
		this.floors = floors;
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
		return Objects.hash(boss, challengeRating, description, floorCount, floors, id, miniboss, name, theme);
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
				&& Objects.equals(floors, other.floors) && Objects.equals(id, other.id) && miniboss == other.miniboss
				&& Objects.equals(name, other.name) && Objects.equals(theme, other.theme);
	}
	
	@Override
	public String toString() {
		return "Dungeon [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", floorCount=" + floorCount + ", floors=" + floors + ", challengeRating=" + challengeRating
				+ ", miniboss=" + miniboss + ", boss=" + boss + "]";
	}
	
	
}
