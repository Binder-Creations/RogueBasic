package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Item;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class ItemDao {
	Session session;
	MappingManager manager;
	Mapper<Item> mapper;
	ItemAccessor accessor;
	
	public ItemDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Item.class);
		this.accessor = manager.createAccessor(ItemAccessor.class);
	}
	
	public Item findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Item> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Item item) {
		mapper.save(item);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
