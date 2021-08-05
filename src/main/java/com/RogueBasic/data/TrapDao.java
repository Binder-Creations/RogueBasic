package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Trap;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class TrapDao {
	private MappingManager manager;
	private Mapper<Trap> mapper;
	private TrapAccessor accessor;
	private static final Logger log = LogManager.getLogger(TrapDao.class);	
	
	public TrapDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Trap.class);
			this.accessor = manager.createAccessor(TrapAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Trap findById(UUID id) {
		log.trace("TrapDao.findById() calling Mapper.get() and returning Trap");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Trap> getAll() {
		log.trace("TrapDao.findById() calling TrapAccessor.getAll() and returning List<Trap>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Trap trap) {
		log.trace("TrapDao.getAll() calling Mapper.save()");
		try {
			mapper.save(trap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("TrapDao.save() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
