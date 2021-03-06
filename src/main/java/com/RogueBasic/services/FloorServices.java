package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;

@Component
public class FloorServices {
	@Autowired
	RoomServices roomServices;
	
	public FloorServices() {}
	
	public Set<Floor> generate(Dungeon dungeon) {
		List<Floor> floors = new ArrayList<>();
		Set<Floor> floorSet = new HashSet<>();
		
		int floorCount = dungeon.getFloorCount();
		
		for(int i = 0; i<floorCount; i++) {
			Floor floor = new Floor();
			floor.setId(UUID.randomUUID());
			floors.add(floor);
		}
		
		for(int i = 0; i< floors.size(); i++) {
			Floor floor = floors.get(i);
			
			floor.setLevel(i+1);
			floor.setDungeonId(dungeon.getId());
			genXAndY(floor, dungeon.getChallengeRating());
			
			if(i > 0)
				floor.setPreviousFloorId(floors.get(i-1).getId());
			if(i<floors.size()-1)
				floor.setNextFloorId(floors.get(i+1).getId());
			
			floor.setRooms(roomServices.generate(dungeon, floor));
			floorSet.add(floor);
		}
		
		return floorSet;
	}
	
	public void genXAndY(Floor floor, int challengeRating) {
		int modifier = 1 + challengeRating/6 + floor.getLevel()/4;
		modifier = modifier > 4 ? 4 : modifier;
		int base = 3;
		floor.setXLength(base + ThreadLocalRandom.current().nextInt(modifier));
		floor.setYLength(base + ThreadLocalRandom.current().nextInt(modifier));
	}
}
