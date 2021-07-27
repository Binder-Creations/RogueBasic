package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.UsableItem;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class UsableDao {
	Session session;
	MappingManager manager;
	Mapper<UsableItem> mapper;
	UsableAccessor accessor;
	
	public UsableDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(UsableItem.class);
		this.accessor = manager.createAccessor(UsableAccessor.class);
	}
	
	public UsableItem findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<UsableItem> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(UsableItem usable) {
		mapper.save(usable);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
