package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Floor;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class FloorDao {
	private MappingManager manager;
	private Mapper<Floor> mapper;
	private FloorAccessor accessor;
	private static final Logger log = LogManager.getLogger(FloorDao.class);	
	
	public FloorDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Floor.class);
			this.accessor = manager.createAccessor(FloorAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Floor findById(UUID id) {
		log.trace("FloorDao.findById() calling Mapper.get() and returning Floor");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Floor> getAll() {
		log.trace("FloorDao.findById() calling FloorAccessor.getAll() and returning List<Floor>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Floor player) {
		log.trace("FloorDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("FloorDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
