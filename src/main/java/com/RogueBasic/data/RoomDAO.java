package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Room;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class RoomDao {
	Session session;
	MappingManager manager;
	Mapper<Room> mapper;
	RoomAccessor accessor;
	
	public RoomDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Room.class);
		this.accessor = manager.createAccessor(RoomAccessor.class);
	}
	
	public Room findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Room> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Room room) {
		mapper.save(room);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
