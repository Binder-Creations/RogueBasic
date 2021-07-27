package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Equipment;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class EquipmentDao {
	Session session;
	MappingManager manager;
	Mapper<Equipment> mapper;
	EquipmentAccessor accessor;
	
	public EquipmentDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(Equipment.class);
		this.accessor = manager.createAccessor(EquipmentAccessor.class);
	}
	
	public Equipment findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<Equipment> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(Equipment equipment) {
		mapper.save(equipment);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
