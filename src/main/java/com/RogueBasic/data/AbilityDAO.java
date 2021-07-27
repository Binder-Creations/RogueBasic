package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Ability;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class AbilityDao {
	Session session;
	MappingManager manager;
	Mapper<Ability> mapper;
	AbiltyAccessor accessor;
	
	public AbilityDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Ability.class);
		this.accessor = manager.createAccessor(AbiltyAccessor.class);
	}
	
	public Ability findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Ability> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Ability ability) {
		mapper.save(ability);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
