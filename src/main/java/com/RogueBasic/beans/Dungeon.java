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
	private Set<Floor> floors;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;
	private int reward;
	private int currentFloor;
	private int currentRoom;
	
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
	
	public Set<Floor> getFloors() {
		return floors;
	}

	public void setFloors(Set<Floor> floors) {
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

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(int currentRoom) {
		this.currentRoom = currentRoom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boss, challengeRating, currentFloor, currentRoom, description, floorCount, floors, id,
				miniboss, name, postfixMod, prefixMod, reward, theme);
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
		return boss == other.boss && challengeRating == other.challengeRating && currentFloor == other.currentFloor
				&& currentRoom == other.currentRoom && Objects.equals(description, other.description)
				&& floorCount == other.floorCount && Objects.equals(floors, other.floors)
				&& Objects.equals(id, other.id) && miniboss == other.miniboss && Objects.equals(name, other.name)
				&& Objects.equals(postfixMod, other.postfixMod) && Objects.equals(prefixMod, other.prefixMod)
				&& reward == other.reward && Objects.equals(theme, other.theme);
	}
	
	@Override
	public String toString() {
		return "DungeonExport [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", prefixMod=" + prefixMod + ", postfixMod=" + postfixMod + ", floorCount=" + floorCount + ", floors="
				+ floors + ", challengeRating=" + challengeRating + ", miniboss=" + miniboss + ", boss=" + boss
				+ ", reward=" + reward + ", currentFloor=" + currentFloor + ", currentRoom=" + currentRoom + "]";
	}
	
	
}
