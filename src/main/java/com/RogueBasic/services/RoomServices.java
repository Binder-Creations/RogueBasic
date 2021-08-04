package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.RoomDao;
import com.datastax.driver.core.Session;

public class RoomServices {
	public RoomDao dao;
	private static final Logger log = LogManager.getLogger(RoomServices.class);
	private Session session;
	private Queue<Room> queue;
	
	public RoomServices(Session session) {
		super();
		this.session = session;
		this.dao = new RoomDao(session);
		this.queue = new LinkedList<>();
	}
	
	public Set<UUID> generate (Dungeon dungeon, Floor floor) {
		MonsterServices ms = new MonsterServices(session);
		ItemServices is = new ItemServices(session);
		
		List<Room> rooms = new ArrayList<>();
		Set<UUID> ids = new HashSet<>();
		boolean stairsGenerated = false;
		int xLength = floor.getXLength();
		int yLength = floor.getYLength();
		genStart(xLength, yLength);
		
		while(!queue.isEmpty()) {
			Room room = queue.poll();
			
			if(stairsGenerated == false) {
				List<Character> connections = genConnections(room, rooms, xLength, yLength);
				int newConnections = genNewConnectionsInt(room, connections);	
				genConnectedRooms(room, connections, newConnections);
				stairsGenerated = stairsCheck(room, rooms, xLength, yLength);			
				genBossMiniboss(room, dungeon, floor.getLevel());
				room.setMonsterIds(ms.generate(dungeon, floor.getLevel()));
				room.setItemIds(is.generate(dungeon, floor.getLevel(), room.isMiniboss(), room.isBoss(), room.getMonsterIds() != null, room.getTrapId() != null));
				rooms.add(room);
				ids.add(room.getId());
			}
		}
		
		rooms.forEach((r)->dao.save(r));
		return ids;
	}
	
	private void genStart(int xLength, int yLength) {
		Room room = new Room();
		room.setId(UUID.randomUUID());
		room.setxCoord(ThreadLocalRandom.current().nextInt(xLength)+1);
		room.setyCoord(ThreadLocalRandom.current().nextInt(yLength)+1);	
		room.setStairsPrevious(true);
		queue.add(room);
	}
	
	private List<Character> genConnections(Room room, List<Room> rooms, int xLength, int yLength){
		List<Character> connections = new ArrayList<>();
		if(room.getxCoord()>1 
				&& !rooms.stream()
				.anyMatch((r)->r.getxCoord()==room.getxCoord()-1 
							&& r.getyCoord()==room.getyCoord())) {
			connections.add('W');
		} else {
			if (room.getxCoord()>1 && ThreadLocalRandom.current().nextInt(2)==0) {
				Room westRoom = rooms.stream()
						.filter((r)->r.getxCoord()==room.getxCoord()-1 
						&& r.getyCoord()==room.getyCoord())
						.findFirst()
						.orElse(null);
				room.setWestRoomId(westRoom.getId());
				westRoom.setEastRoomId(room.getId());
			}
		}
		if(room.getxCoord()<xLength 
				&& !rooms.stream()
				.anyMatch((r)->r.getxCoord()==room.getxCoord()+1 
							&& r.getyCoord()==room.getyCoord())) {
			connections.add('E');
		} else {
			if (room.getxCoord()<xLength && ThreadLocalRandom.current().nextInt(2)==0) {
				Room eastRoom = rooms.stream()
						.filter((r)->r.getxCoord()==room.getxCoord()+1 
						&& r.getyCoord()==room.getyCoord())
						.findFirst()
						.orElse(null);
				room.setEastRoomId(eastRoom.getId());
				eastRoom.setWestRoomId(room.getId());
			}
		}
		if(room.getyCoord()>1 
				&& !rooms.stream()
				.anyMatch((r)->r.getxCoord()==room.getxCoord() 
							&& r.getyCoord()==room.getyCoord()-1)) {
			connections.add('S');
		} else {
			if (room.getyCoord()>1 && ThreadLocalRandom.current().nextInt(2)==0) {
				Room southRoom = rooms.stream()
						.filter((r)->r.getxCoord()==room.getxCoord() 
						&& r.getyCoord()==room.getyCoord()-1)
						.findFirst()
						.orElse(null);
				room.setSouthRoomId(southRoom.getId());
				southRoom.setNorthRoomId(room.getId());
			}
		}
		if(room.getyCoord()<yLength
				&& !rooms.stream()
				.anyMatch((r)->r.getxCoord()==room.getxCoord() 
							&& r.getyCoord()==room.getyCoord()+1)) {
			connections.add('N');
		} else {
			if (room.getyCoord()<yLength && ThreadLocalRandom.current().nextInt(2)==0) {
				Room northRoom = rooms.stream()
						.filter((r)->r.getxCoord()==room.getxCoord() 
						&& r.getyCoord()==room.getyCoord()+1)
						.findFirst()
						.orElse(null);
				room.setNorthRoomId(northRoom.getId());
				northRoom.setSouthRoomId(room.getId());
			}
		}
		return connections;
	}
	
	private int genNewConnectionsInt(Room room, List<Character> connections) {
		return room.isStairsNext()
		? 0
		: queue.isEmpty()
				? 1 + ThreadLocalRandom.current().nextInt(connections.size()+1)
			    : ThreadLocalRandom.current().nextInt(connections.size() + 1);
	}
	
	private void genConnectedRooms(Room room, List<Character> connections, int newConnections) {
		if(connections.size() > 0) {	   
			for(int i = 0; i < newConnections; i++) {
				int rand = ThreadLocalRandom.current().nextInt(connections.size()); 
				Room newRoom = new Room();
				newRoom.setId(UUID.randomUUID());
				
				switch(connections.get(rand)) {
					case 'W':
						room.setWestRoomId(newRoom.getId());
						newRoom.setEastRoomId(room.getId());
						newRoom.setxCoord(room.getxCoord()-1);
						newRoom.setyCoord(room.getyCoord());
						queue.add(newRoom);
						break;
					case 'E':
						room.setEastRoomId(newRoom.getId());
						newRoom.setWestRoomId(room.getId());
						newRoom.setxCoord(room.getxCoord()+1);
						newRoom.setyCoord(room.getyCoord());
						queue.add(newRoom);
						break;
					case 'S':
						room.setSouthRoomId(newRoom.getId());
						newRoom.setNorthRoomId(room.getId());
						newRoom.setxCoord(room.getxCoord());
						newRoom.setyCoord(room.getyCoord()-1);
						queue.add(newRoom);
						break;
					case 'N':
						room.setNorthRoomId(newRoom.getId());
						newRoom.setSouthRoomId(room.getId());
						newRoom.setxCoord(room.getxCoord());
						newRoom.setyCoord(room.getyCoord()+1);
						queue.add(newRoom);
						break;
				}
				
				connections.remove(rand);
				if (connections.size() == 0)
					break;
			}
		}
	}
	
	private boolean stairsCheck(Room room, List<Room> rooms, int xLength, int yLength) {
		if( queue.isEmpty()
				|| ( rooms.size()>(xLength*yLength)/3
				&& ThreadLocalRandom.current().nextInt(101)<(100*rooms.size())/(2*xLength*yLength))) {
					room.setStairsNext(true);
					return true;
			}
		return false;
	}
	
	private void genBossMiniboss(Room room, Dungeon dungeon, int level) {
		int floorCount = dungeon.getFloorCount();

		room.setBoss(dungeon.isBoss()
			  ? room.isStairsNext()
			     ? level == floorCount
			        ? true
			        : false
			     : false
			  : false);
		
		room.setMiniboss(dungeon.isMiniboss()
			  ? room.isStairsNext()
				 ? dungeon.isBoss()
			        ? level == 1+(floorCount/2)
			           ? true
			           : false
			        : level == floorCount
			           ? true
			           : false
			      : false
			  : false);
	}
	
}