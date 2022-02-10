package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table
public class DungeonAWS {

	@PrimaryKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private String prefixMod;
	private String postfixMod;
	private int floorCount;
	private Set<String> floors;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;
	private int reward;
	private Set<String> rewardSet;
	private boolean questCompleted;
	private boolean rewardClaimed;
	private int currentFloor;
	private int currentRoom;
	
	public DungeonAWS() {}
	
	public DungeonAWS(Dungeon dungeon) {
		ObjectMapper mapper = new ObjectMapper();
		Set<String> floors = new HashSet<>();
		if(dungeon.getFloors() != null) {
			for(Floor floor: dungeon.getFloors()) {
				try {
					floors.add(mapper.writeValueAsString(floor));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		Set<String> rewardSet = new HashSet<>();
		if(dungeon.getRewardSet() != null) {
			for(Item item: dungeon.getRewardSet()) {
				try {
					rewardSet.add(mapper.writeValueAsString(item));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.id = dungeon.getId();
		this.name = dungeon.getName();
		this.description = dungeon.getDescription();
		this.theme = dungeon.getTheme();
		this.prefixMod = dungeon.getPrefixMod();
		this.postfixMod = dungeon.getPostfixMod();
		this.floorCount = dungeon.getFloorCount();
		this.floors = floors;
		this.challengeRating = dungeon.getChallengeRating();
		this.miniboss = dungeon.isMiniboss();
		this.boss = dungeon.isBoss();
		this.reward = dungeon.getReward();
		this.rewardSet = rewardSet;
		this.questCompleted = dungeon.isQuestCompleted();
		this.rewardClaimed = dungeon.isRewardClaimed();
		this.currentFloor = dungeon.getCurrentFloor();
		this.currentRoom = dungeon.getCurrentRoom();
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

	public Set<String> getFloors() {
		return floors;
	}

	public void setFloors(Set<String> floors) {
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

	public Set<String> getRewardSet() {
		return rewardSet;
	}

	public void setRewardSet(Set<String> rewardSet) {
		this.rewardSet = rewardSet;
	}

	public boolean isQuestCompleted() {
		return questCompleted;
	}

	public void setQuestCompleted(boolean questCompleted) {
		this.questCompleted = questCompleted;
	}

	public boolean isRewardClaimed() {
		return rewardClaimed;
	}

	public void setRewardClaimed(boolean rewardClaimed) {
		this.rewardClaimed = rewardClaimed;
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
				miniboss, name, postfixMod, prefixMod, questCompleted, reward, rewardClaimed, rewardSet, theme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DungeonAWS other = (DungeonAWS) obj;
		return boss == other.boss && challengeRating == other.challengeRating && currentFloor == other.currentFloor
				&& currentRoom == other.currentRoom && Objects.equals(description, other.description)
				&& floorCount == other.floorCount && Objects.equals(floors, other.floors)
				&& Objects.equals(id, other.id) && miniboss == other.miniboss && Objects.equals(name, other.name)
				&& Objects.equals(postfixMod, other.postfixMod) && Objects.equals(prefixMod, other.prefixMod)
				&& questCompleted == other.questCompleted && reward == other.reward
				&& rewardClaimed == other.rewardClaimed && Objects.equals(rewardSet, other.rewardSet)
				&& Objects.equals(theme, other.theme);
	}

	@Override
	public String toString() {
		return "DungeonAWS [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", prefixMod=" + prefixMod + ", postfixMod=" + postfixMod + ", floorCount=" + floorCount + ", floors="
				+ floors + ", challengeRating=" + challengeRating + ", miniboss=" + miniboss + ", boss=" + boss
				+ ", reward=" + reward + ", rewardSet=" + rewardSet + ", questCompleted=" + questCompleted
				+ ", rewardClaimed=" + rewardClaimed + ", currentFloor=" + currentFloor + ", currentRoom=" + currentRoom
				+ "]";
	}
	
}
