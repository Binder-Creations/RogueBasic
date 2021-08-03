package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.data.FloorDao;
import com.datastax.driver.core.Session;

public class FloorServices {
	public Session session;
	public FloorDao dao;
	private static final Logger log = LogManager.getLogger(FloorServices.class);

	public FloorServices(Session session) {
		super();
		this.session = session;
		this.dao = new FloorDao(session);
	}
	
	public Set<UUID> generate(Dungeon dungeon) {
		RoomServices rs = new RoomServices(session);
		List<Floor> floors = new ArrayList<>();
		Set<UUID> ids = new HashSet<>();
		
		int floorCount = dungeon.getFloorCount();
		
		for(int i = 0; i<floorCount; i++) {
			Floor floor = new Floor();
			floor.setId(UUID.randomUUID());
			floors.add(floor);
		}
		
		for(int i = 0; i< floors.size(); i++) {
			Floor floor = floors.get(i);
			ids.add(floor.getId());
			
			floor.setLevel(i+1);
			floor.setDungeonId(dungeon.getId());
			genXAndY(floor, dungeon.getChallengeRating());
			
			if(i > 0)
				floor.setPreviousFloorId(floors.get(i-1).getId());
			if(i<floors.size()-1)
				floor.setNextFloorId(floors.get(i+1).getId());
			
			floor.setRoomIds(rs.generate(dungeon, floor));
			
			dao.save(floor);
			
		}
		
		return ids;
	}
	
	public void genXAndY(Floor floor, int challengeRating) {
		int modifier = 2 + challengeRating/6 + floor.getLevel()/4;
		modifier = modifier > 5 ? 5 : modifier;
		int base = 3;
		floor.setXLength(base + ThreadLocalRandom.current().nextInt(modifier));
		floor.setYLength(base + ThreadLocalRandom.current().nextInt(modifier));
	}
}
