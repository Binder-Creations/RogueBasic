package com.RogueBasic.beans;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Room {
	@PrimaryKey private UUID id;
	private UUID floorId;
	private UUID dungeonId;
	private int xCoord;
	private int yCoord;
	private boolean stairsPrevious;
	private boolean stairsNext;
	private UUID northRoomId;
	private UUID southRoomId;
	private UUID eastRoomId;
	private UUID westRoomId;
	private UUID trapId;
	private Map<UUID, Integer> loot;
	private Set<UUID> monsterIds;
	private boolean miniboss;
	private boolean boss;
	
	public Room() {}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getFloorId() {
		return floorId;
	}

	public void setFloorId(UUID floorId) {
		this.floorId = floorId;
	}

	public UUID getDungeonId() {
		return dungeonId;
	}

	public void setDungeonId(UUID dungeonId) {
		this.dungeonId = dungeonId;
	}

	public int getxCoord() {
		return xCoord;
	}
	
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	
	public int getyCoord() {
		return yCoord;
	}
	
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	public boolean isStairsPrevious() {
		return stairsPrevious;
	}

	public void setStairsPrevious(boolean stairsPrevious) {
		this.stairsPrevious = stairsPrevious;
	}

	public boolean isStairsNext() {
		return stairsNext;
	}

	public void setStairsNext(boolean stairsNext) {
		this.stairsNext = stairsNext;
	}

	public UUID getNorthRoomId() {
		return northRoomId;
	}
	
	public void setNorthRoomId(UUID northRoomId) {
		this.northRoomId = northRoomId;
	}
	
	public UUID getSouthRoomId() {
		return southRoomId;
	}
	
	public void setSouthRoomId(UUID southRoomId) {
		this.southRoomId = southRoomId;
	}
	
	public UUID getEastRoomId() {
		return eastRoomId;
	}
	
	public void setEastRoomId(UUID eastRoomId) {
		this.eastRoomId = eastRoomId;
	}
	
	public UUID getWestRoomId() {
		return westRoomId;
	}
	
	public void setWestRoomId(UUID westRoomId) {
		this.westRoomId = westRoomId;
	}
	
	public UUID getTrapId() {
		return trapId;
	}
	
	public void setTrapId(UUID trapId) {
		this.trapId = trapId;
	}
	

	public Map<UUID, Integer> getLoot() {
		return loot;
	}

	public void setLoot(Map<UUID, Integer> loot) {
		this.loot = loot;
	}

	public Set<UUID> getMonsterIds() {
		return monsterIds;
	}
	
	public void setMonsterIds(Set<UUID> monsterIds) {
		this.monsterIds = monsterIds;
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
		return Objects.hash(boss, dungeonId, eastRoomId, floorId, id, loot, miniboss, monsterIds, northRoomId,
				southRoomId, stairsNext, stairsPrevious, trapId, westRoomId, xCoord, yCoord);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return boss == other.boss && Objects.equals(dungeonId, other.dungeonId)
				&& Objects.equals(eastRoomId, other.eastRoomId) && Objects.equals(floorId, other.floorId)
				&& Objects.equals(id, other.id) && Objects.equals(loot, other.loot) && miniboss == other.miniboss
				&& Objects.equals(monsterIds, other.monsterIds) && Objects.equals(northRoomId, other.northRoomId)
				&& Objects.equals(southRoomId, other.southRoomId) && stairsNext == other.stairsNext
				&& stairsPrevious == other.stairsPrevious && Objects.equals(trapId, other.trapId)
				&& Objects.equals(westRoomId, other.westRoomId) && xCoord == other.xCoord && yCoord == other.yCoord;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", floorId=" + floorId + ", dungeonId=" + dungeonId + ", xCoord=" + xCoord
				+ ", yCoord=" + yCoord + ", stairsPrevious=" + stairsPrevious + ", stairsNext=" + stairsNext
				+ ", northRoomId=" + northRoomId + ", southRoomId=" + southRoomId + ", eastRoomId=" + eastRoomId
				+ ", westRoomId=" + westRoomId + ", trapId=" + trapId + ", loot=" + loot + ", monsterIds=" + monsterIds
				+ ", miniboss=" + miniboss + ", boss=" + boss + "]";
	}
	
}
