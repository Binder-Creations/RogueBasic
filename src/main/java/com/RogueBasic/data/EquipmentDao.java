package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Equipment;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class EquipmentDao {
	private MappingManager manager;
	private Mapper<Equipment> mapper;
	private EquipmentAccessor accessor;
	private static final Logger log = LogManager.getLogger(EquipmentDao.class);	
	
	public EquipmentDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Equipment.class);
			this.accessor = manager.createAccessor(EquipmentAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Equipment findById(UUID id) {
		log.trace("EquipmentDao.findById() calling Mapper.get() and returning Equipment");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Equipment> getAll() {
		log.trace("EquipmentDao.findById() calling EquipmentAccessor.getAll() and returning List<Equipment>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public void save(Equipment player) {
		log.trace("EquipmentDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	 
	public void deleteById(UUID id) {
		log.trace("EquipmentDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
