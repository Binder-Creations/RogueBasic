package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue_basic", name = "room")
public class Room {
	@PartitionKey private UUID id;
	private UUID floorId;
	private UUID dungeonId;
	private int xCoord;
	private int yCoord;
	private UUID northRoomId;
	private UUID southRoomId;
	private UUID eastRoomId;
	private UUID westRoomId;
	private UUID trapId;
	private Set<UUID> itemIds;
	private Set<UUID> monsterIds;
	
	public Room() {}
	
	public Room(UUID floorId, UUID dungeonId, int xCoord, int yCoord, UUID northRoomId, UUID southRoomId, UUID eastRoomId, UUID westRoomId,
			UUID trapId, Set<UUID> itemIds, Set<UUID> monsterIds) {
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
	
	public Set<UUID> getItemIds() {
		return itemIds;
	}
	
	public void setItemIds(Set<UUID> itemIds) {
		this.itemIds = itemIds;
	}
	
	public Set<UUID> getMonsterIds() {
		return monsterIds;
	}
	
	public void setMonsterIds(Set<UUID> monsterIds) {
		this.monsterIds = monsterIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dungeonId, eastRoomId, floorId, id, itemIds, monsterIds, northRoomId, southRoomId, trapId,
				westRoomId, xCoord, yCoord);
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
		return Objects.equals(dungeonId, other.dungeonId) && Objects.equals(eastRoomId, other.eastRoomId)
				&& Objects.equals(floorId, other.floorId) && Objects.equals(id, other.id)
				&& Objects.equals(itemIds, other.itemIds) && Objects.equals(monsterIds, other.monsterIds)
				&& Objects.equals(northRoomId, other.northRoomId) && Objects.equals(southRoomId, other.southRoomId)
				&& Objects.equals(trapId, other.trapId) && Objects.equals(westRoomId, other.westRoomId)
				&& xCoord == other.xCoord && yCoord == other.yCoord;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", floorId=" + floorId + ", dungeonId=" + dungeonId + ", xCoord=" + xCoord
				+ ", yCoord=" + yCoord + ", northRoomId=" + northRoomId + ", southRoomId=" + southRoomId
				+ ", eastRoomId=" + eastRoomId + ", westRoomId=" + westRoomId + ", trapId=" + trapId + ", itemIds="
				+ itemIds + ", monsterIds=" + monsterIds + "]";
	}
	
}
