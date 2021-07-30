package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.UsableItem;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class UsableDao {
	private MappingManager manager;
	private Mapper<UsableItem> mapper;
	private UsableAccessor accessor;
	private static final Logger log = LogManager.getLogger(UsableDao.class);	
	
	public UsableDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(UsableItem.class);
			this.accessor = manager.createAccessor(UsableAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public UsableItem findById(UUID id) {
		log.trace("UsableDao.findById() calling Mapper.get() and returning Usable");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<UsableItem> getAll() {
		log.trace("UsableDao.findById() calling UsableAccessor.getAll() and returning List<Usable>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public void save(UsableItem player) {
		log.trace("UsableDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	 
	public void deleteById(UUID id) {
		log.trace("UsableDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
