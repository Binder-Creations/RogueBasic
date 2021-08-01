package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class DungeonDao {
	private MappingManager manager;
	private Mapper<Dungeon> mapper;
	private DungeonAccessor accessor;
	private static final Logger log = LogManager.getLogger(DungeonDao.class);	
	
	public DungeonDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Dungeon.class);
			this.accessor = manager.createAccessor(DungeonAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Dungeon findById(UUID id) {
		log.trace("DungeonDao.findById() calling Mapper.get() and returning Dungeon");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Dungeon> getAll() {
		log.trace("DungeonDao.findById() calling DungeonAccessor.getAll() and returning List<Dungeon>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Dungeon player) {
		log.trace("DungeonDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("DungeonDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
