package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import com.RogueBasic.data.DungeonDAO;
import com.RogueBasic.util.RogueUtilities;

public class Floor {
	private UUID id;
	private int level;
	private int xLength;
	private int yLength;
	private UUID previousFloorId;
	private UUID nextFloorId;
	private UUID dungeonId;
	private UUID[] roomIds;
	
	public Floor(int level, int xLength, int yLength, UUID previousFloorId, UUID nextFloorId, UUID dungeonId,
			UUID[] roomIds) {
		super();
		this.id = UUID.randomUUID();
		this.level = level;
		this.xLength = xLength;
		this.yLength = yLength;
		this.previousFloorId = previousFloorId;
		this.nextFloorId = nextFloorId;
		this.dungeonId = dungeonId;
		this.roomIds = roomIds;
	}

	public UUID getId() {
		return id;
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
	
	public UUID[] getRoomIds() {
		return roomIds;
	}
	
	public void setRoomIds(UUID[] roomIds) {
		this.roomIds = roomIds;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(roomIds);
		result = prime * result + Objects.hash(dungeonId, id, level, nextFloorId, previousFloorId, xLength, yLength);
		return result;
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
		return dungeonId == other.dungeonId && id == other.id && yLength == other.yLength && level == other.level
				&& nextFloorId == other.nextFloorId && previousFloorId == other.previousFloorId
				&& Arrays.equals(roomIds, other.roomIds) && xLength == other.xLength;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", level=" + level + ", xLength=" + xLength + ", yLength=" + yLength + ", previousFloorId="
				+ previousFloorId + ", nextFloorId=" + nextFloorId + ", dungeonId=" + dungeonId + ", roomIds="
				+ Arrays.toString(roomIds) + "]";
	}
	
}
