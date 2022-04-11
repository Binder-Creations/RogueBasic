package com.RogueBasic.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.atLeast;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Room;

@ExtendWith(MockitoExtension.class)
public class FloorServicesTest {
	@Mock
	Dungeon dungeon;
	
	@Mock
	RoomServices roomServices;
	
	@InjectMocks
	FloorServices floorServices;
	
	@Test
	public void generateTest() {
		Set<Room> roomSet = new HashSet<>();
		for(int i = 0; i < 3; i++) {
			roomSet.add(mock(Room.class));
		}
		when(roomServices.generate(any(Dungeon.class), any(Floor.class))).thenReturn(roomSet);
		
		UUID testUUID = UUID.randomUUID();
		when(dungeon.getFloorCount()).thenReturn(2);
		when(dungeon.getId()).thenReturn(testUUID);
		when(dungeon.getChallengeRating()).thenReturn(2);
		
		Set<Floor> floorSet = floorServices.generate(dungeon);
		for(Floor floor: floorSet) {
			assertNotNull(floor);
			assumeTrue(floor != null);
			assertTrue(floor.getDungeonId().equals(testUUID));
			assertNotNull(floor.getLevel());
			assertNotNull(floor.getRooms());
			assertTrue(floor.getXLength() >= 3);
			assertTrue(floor.getYLength() >= 3);
			assumeTrue(floor.getRooms() != null);
			for(Room room : floor.getRooms()) {
				assertTrue(room != null);
			}
		}
		
		verify(dungeon, atLeast(1)).getFloorCount();
		verify(dungeon, atLeast(1)).getId();
		verify(dungeon, atLeast(1)).getChallengeRating();
	}
}
