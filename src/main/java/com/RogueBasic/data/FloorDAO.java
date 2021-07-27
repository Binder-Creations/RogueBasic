package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Floor;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class FloorDao {
	Session session;
	MappingManager manager;
	Mapper<Floor> mapper;
	FloorAccessor accessor;
	
	public FloorDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Floor.class);
		this.accessor = manager.createAccessor(FloorAccessor.class);
	}
	
	public Floor findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Floor> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Floor floor) {
		mapper.save(floor);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
