package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.MonsterDao;
import com.RogueBasic.data.TrapDao;
import com.RogueBasic.util.CassandraConnector;


public class RoomExport {
	private UUID id;
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
	private Trap trap;
	private Map<UUID, Integer> loot;
	private Set<Item> lootCache;
	private Set<Monster> monsters;
	private boolean miniboss;
	private boolean boss;
	private boolean cleared;
	
	public RoomExport() {}
	
	public RoomExport(Room room) {
		super();
		
		this.id = room.getId();
		this.floorId = room.getFloorId();
		this.dungeonId = room.getDungeonId();
		this.xCoord = room.getxCoord();
		this.yCoord = room.getyCoord();
		this.stairsPrevious = room.isStairsPrevious();
		this.stairsNext = room.isStairsNext();
		this.northRoomId = room.getNorthRoomId();
		this.southRoomId = room.getSouthRoomId();
		this.eastRoomId = room.getEastRoomId();
		this.westRoomId = room.getWestRoomId();
		if(room.getTrapId() != null) {
			TrapDao tDao = new TrapDao(CassandraConnector.getSession());
			this.trap = tDao.findById(room.getTrapId());
		}
		this.loot = room.getLoot();
		if(this.loot != null) {
			ItemDao iDao = new ItemDao(CassandraConnector.getSession());
			this.lootCache = new HashSet<>();
			this.loot.forEach((k,v)->lootCache.add(iDao.findById(k)));
		}
		if(room.getMonsterIds() != null) {
			MonsterDao mDao = new MonsterDao(CassandraConnector.getSession());
			this.monsters = new HashSet<>();
			room.getMonsterIds().forEach(id->this.monsters.add(mDao.findById(id)));
		}		
		this.miniboss = room.isMiniboss();
		this.boss = room.isBoss();
		this.cleared = room.isCleared();
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

	public Trap getTrap() {
		return trap;
	}

	public void setTrap(Trap trap) {
		this.trap = trap;
	}

	public Map<UUID, Integer> getLoot() {
		return loot;
	}

	public void setLoot(Map<UUID, Integer> loot) {
		this.loot = loot;
	}

	public Set<Item> getLootCache() {
		return lootCache;
	}

	public void setLootCache(Set<Item> lootCache) {
		this.lootCache = lootCache;
	}

	public Set<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(Set<Monster> monsters) {
		this.monsters = monsters;
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
	
	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boss, cleared, dungeonId, eastRoomId, floorId, id, loot, lootCache, miniboss, monsters,
				northRoomId, southRoomId, stairsNext, stairsPrevious, trap, westRoomId, xCoord, yCoord);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomExport other = (RoomExport) obj;
		return boss == other.boss && cleared == other.cleared && Objects.equals(dungeonId, other.dungeonId)
				&& Objects.equals(eastRoomId, other.eastRoomId) && Objects.equals(floorId, other.floorId)
				&& Objects.equals(id, other.id) && Objects.equals(loot, other.loot)
				&& Objects.equals(lootCache, other.lootCache) && miniboss == other.miniboss
				&& Objects.equals(monsters, other.monsters) && Objects.equals(northRoomId, other.northRoomId)
				&& Objects.equals(southRoomId, other.southRoomId) && stairsNext == other.stairsNext
				&& stairsPrevious == other.stairsPrevious && Objects.equals(trap, other.trap)
				&& Objects.equals(westRoomId, other.westRoomId) && xCoord == other.xCoord && yCoord == other.yCoord;
	}

	@Override
	public String toString() {
		return "RoomExport [id=" + id + ", floorId=" + floorId + ", dungeonId=" + dungeonId + ", xCoord=" + xCoord
				+ ", yCoord=" + yCoord + ", stairsPrevious=" + stairsPrevious + ", stairsNext=" + stairsNext
				+ ", northRoomId=" + northRoomId + ", southRoomId=" + southRoomId + ", eastRoomId=" + eastRoomId
				+ ", westRoomId=" + westRoomId + ", trap=" + trap + ", loot=" + loot + ", lootCache=" + lootCache
				+ ", monsters=" + monsters + ", miniboss=" + miniboss + ", boss=" + boss + ", cleared=" + cleared + "]";
	}
	
}