package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import com.RogueBasic.data.RoomDAO;
import com.RogueBasic.util.RogueUtilities;

public class Room {
	private UUID id;
	private UUID floorId;
	private UUID dungeonId;
	private int xCoord;
	private int yCoord;
	private UUID northRoomId;
	private UUID southRoomId;
	private UUID eastRoomId;
	private UUID westRoomId;
	private UUID trapId;
	private UUID itemIds[];
	private UUID monsterIds[];
	
	public Room(UUID floorId, UUID dungeonId, int xCoord, int yCoord, UUID northRoomId, UUID southRoomId, UUID eastRoomId, UUID westRoomId,
			UUID trapId, UUID[] itemIds, UUID[] monsterIds) {
		super();
		this.id = UUID.randomUUID();
		this.floorId = floorId;
		this.dungeonId = dungeonId;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.northRoomId = northRoomId;
		this.southRoomId = southRoomId;
		this.eastRoomId = eastRoomId;
		this.westRoomId = westRoomId;
		this.trapId = trapId;
		this.itemIds = itemIds;
		this.monsterIds = monsterIds;
	}

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
	
	public UUID[] getItemIds() {
		return itemIds;
	}
	
	public void setItemIds(UUID[] itemIds) {
		this.itemIds = itemIds;
	}
	
	public UUID[] getMonsterIds() {
		return monsterIds;
	}
	
	public void setMonsterIds(UUID[] monsterIds) {
		this.monsterIds = monsterIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(itemIds);
		result = prime * result + Arrays.hashCode(monsterIds);
		result = prime * result + Objects.hash(dungeonId, eastRoomId, floorId, id, northRoomId, southRoomId, trapId,
				westRoomId, xCoord, yCoord);
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
		Room other = (Room) obj;
		return dungeonId == other.dungeonId && eastRoomId == other.eastRoomId && floorId == other.floorId
				&& id == other.id && Arrays.equals(itemIds, other.itemIds)
				&& Arrays.equals(monsterIds, other.monsterIds) && northRoomId == other.northRoomId
				&& southRoomId == other.southRoomId && trapId == other.trapId && westRoomId == other.westRoomId
				&& xCoord == other.xCoord && yCoord == other.yCoord;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", floorId=" + floorId + ", dungeonId=" + dungeonId + ", xCoord=" + xCoord
				+ ", yCoord=" + yCoord + ", northRoomId=" + northRoomId + ", southRoomId=" + southRoomId
				+ ", eastRoomId=" + eastRoomId + ", westRoomId=" + westRoomId + ", trapId=" + trapId + ", itemIds="
				+ Arrays.toString(itemIds) + ", monsterIds=" + Arrays.toString(monsterIds) + "]";
	}
	
}
