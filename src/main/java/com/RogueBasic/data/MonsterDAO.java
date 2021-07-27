package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Monster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class MonsterDao {
	Session session;
	MappingManager manager;
	Mapper<Monster> mapper;
	MonsterAccessor accessor;
	
	public MonsterDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Monster.class);
		this.accessor = manager.createAccessor(MonsterAccessor.class);
	}
	
	public Monster findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Monster> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Monster monster) {
		mapper.save(monster);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
