package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Dungeon;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class DungeonDao {
	Session session;
	MappingManager manager;
	Mapper<Dungeon> mapper;
	DungeonAccessor accessor;
	
	public DungeonDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Dungeon.class);
		this.accessor = manager.createAccessor(DungeonAccessor.class);
	}
	
	public Dungeon findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Dungeon> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Dungeon dungeon) {
		mapper.save(dungeon);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
