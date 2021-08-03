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

	public RoomServices(Session session) {
		super();
		dao = new RoomDao(session);
	}
	
	public Set<UUID> generate (Dungeon dungeon, Floor floor) {
		List<Room> rooms = new ArrayList<>();
		Set<UUID> ids = new HashSet<>();
		Queue<Room> genQueue = new LinkedList<>();
		boolean stairsGenerated = false;
		
		int xLength = floor.getXLength();
		int yLength = floor.getYLength();
		
		Room startRoom = new Room();
		startRoom.setId(UUID.randomUUID());
		startRoom.setxCoord(ThreadLocalRandom.current().nextInt(xLength)+1);
		startRoom.setyCoord(ThreadLocalRandom.current().nextInt(yLength)+1);	
		startRoom.setStairsPrevious(true);
		genQueue.add(startRoom);
		
		while(!genQueue.isEmpty()) {
			Room room = genQueue.poll();
			
			if(stairsGenerated == false) {
				List<Character> potentailNewConnections = new ArrayList<>();
				if(room.getxCoord()>1 
						&& !rooms.stream()
						.anyMatch((r)->r.getxCoord()==room.getxCoord()-1 
									&& r.getyCoord()==room.getyCoord())) {
					potentailNewConnections.add('W');
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
					potentailNewConnections.add('E');
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
					potentailNewConnections.add('S');
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
					potentailNewConnections.add('N');
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
				
				int numberOfNewConnections = room.isStairsNext() 
						? 0
						: genQueue.isEmpty()
								? 1 + ThreadLocalRandom.current().nextInt(potentailNewConnections.size()+1)
							    : ThreadLocalRandom.current().nextInt(potentailNewConnections.size() + 1);
				if(potentailNewConnections.size() > 0) {	   
					for(int i = 0; i < numberOfNewConnections; i++) {
						int rand = ThreadLocalRandom.current().nextInt(potentailNewConnections.size()); 
						Room newRoom = new Room();
						newRoom.setId(UUID.randomUUID());
						
						switch(potentailNewConnections.get(rand)) {
							case 'W':
								room.setWestRoomId(newRoom.getId());
								newRoom.setEastRoomId(room.getId());
								newRoom.setxCoord(room.getxCoord()-1);
								newRoom.setyCoord(room.getyCoord());
								genQueue.add(newRoom);
								break;
							case 'E':
								room.setEastRoomId(newRoom.getId());
								newRoom.setWestRoomId(room.getId());
								newRoom.setxCoord(room.getxCoord()+1);
								newRoom.setyCoord(room.getyCoord());
								genQueue.add(newRoom);
								break;
							case 'S':
								room.setSouthRoomId(newRoom.getId());
								newRoom.setNorthRoomId(room.getId());
								newRoom.setxCoord(room.getxCoord());
								newRoom.setyCoord(room.getyCoord()-1);
								genQueue.add(newRoom);
								break;
							case 'N':
								room.setNorthRoomId(newRoom.getId());
								newRoom.setSouthRoomId(room.getId());
								newRoom.setxCoord(room.getxCoord());
								newRoom.setyCoord(room.getyCoord()+1);
								genQueue.add(newRoom);
								break;
						}
						
						potentailNewConnections.remove(rand);
						if (potentailNewConnections.size() == 0)
							break;
					}
				}
				
				if(stairsGenerated == false && rooms.size()>(xLength*yLength)/3) {
					if(stairsGenerated = ThreadLocalRandom.current().nextInt(101)<(100*rooms.size())/(2*xLength*yLength)) {
						stairsGenerated = true;
						room.setStairsNext(true);
					}
				}
				
				rooms.add(room);
				ids.add(room.getId());
			}
		}
		
		rooms.forEach((r)->dao.save(r));
		
		return ids;
	}
	
}
