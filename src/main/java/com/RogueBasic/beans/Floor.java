package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue", name = "floor")
public class Floor {
	
	@PartitionKey private UUID id;
	private int level;
	private int xLength;
	private int yLength;
	private UUID previousFloorId;
	private UUID nextFloorId;
	private UUID dungeonId;
	private Set<UUID> roomIds;
	
	public Floor() {}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXLength() {
		return xLength;
	}

	public void setXLength(int xLength) {
		this.xLength = xLength;
	}

	public int getYLength() {
		return yLength;
	}

	public void setYLength(int yLength) {
		this.yLength = yLength;
	}

	public UUID getPreviousFloorId() {
		return previousFloorId;
	}

	public void setPreviousFloorId(UUID previousFloorId) {
		this.previousFloorId = previousFloorId;
	}

	public UUID getNextFloorId() {
		return nextFloorId;
	}
	
	public void setNextFloorId(UUID nextFloorId) {
		this.nextFloorId = nextFloorId;
	}
	
	public UUID getDungeonId() {
		return dungeonId;
	}
	
	public void setDungeonId(UUID dungeonId) {
		this.dungeonId = dungeonId;
	}
	
	public Set<UUID> getRoomIds() {
		return roomIds;
	}
	
	public void setRoomIds(Set<UUID> roomIds) {
		this.roomIds = roomIds;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dungeonId, id, level, nextFloorId, previousFloorId, roomIds, xLength, yLength);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Floor other = (Floor) obj;
		return Objects.equals(dungeonId, other.dungeonId) && Objects.equals(id, other.id) && level == other.level
				&& Objects.equals(nextFloorId, other.nextFloorId)
				&& Objects.equals(previousFloorId, other.previousFloorId) && Objects.equals(roomIds, other.roomIds)
				&& xLength == other.xLength && yLength == other.yLength;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", level=" + level + ", xLength=" + xLength + ", yLength=" + yLength
				+ ", previousFloorId=" + previousFloorId + ", nextFloorId=" + nextFloorId + ", dungeonId=" + dungeonId
				+ ", roomIds=" + roomIds + "]";
	}
	
}
