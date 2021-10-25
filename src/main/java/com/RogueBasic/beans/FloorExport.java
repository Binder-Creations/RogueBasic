package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.data.RoomDao;
import com.RogueBasic.util.CassandraConnector;


public class FloorExport {
	
	private UUID id;
	private int level;
	private int xLength;
	private int yLength;
	private UUID previousFloorId;
	private UUID nextFloorId;
	private UUID dungeonId;
	private Set<RoomExport> rooms;
	
	public FloorExport() {}

	public FloorExport(Floor floor) {
		super();
		RoomDao rDao = new RoomDao(CassandraConnector.getSession());
		
		this.id = floor.getId();
		this.level = floor.getLevel();
		this.xLength = floor.getXLength();
		this.yLength = floor.getYLength();
		this.previousFloorId = floor.getPreviousFloorId();
		this.nextFloorId = floor.getNextFloorId();
		this.dungeonId = floor.getDungeonId();
		this.rooms = new HashSet<>();
		floor.getRoomIds().forEach(id->this.rooms.add(new RoomExport(rDao.findById(id))));
	}

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

	public Set<RoomExport> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomExport> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dungeonId, id, level, nextFloorId, previousFloorId, rooms, xLength, yLength);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloorExport other = (FloorExport) obj;
		return Objects.equals(dungeonId, other.dungeonId) && Objects.equals(id, other.id) && level == other.level
				&& Objects.equals(nextFloorId, other.nextFloorId)
				&& Objects.equals(previousFloorId, other.previousFloorId) && Objects.equals(rooms, other.rooms)
				&& xLength == other.xLength && yLength == other.yLength;
	}

	@Override
	public String toString() {
		return "FloorExport [id=" + id + ", level=" + level + ", xLength=" + xLength + ", yLength=" + yLength
				+ ", previousFloorId=" + previousFloorId + ", nextFloorId=" + nextFloorId + ", dungeonId=" + dungeonId
				+ ", rooms=" + rooms + "]";
	}
	
}
