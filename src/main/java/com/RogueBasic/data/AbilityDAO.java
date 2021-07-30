package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Ability;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class AbilityDao {
	private MappingManager manager;
	private Mapper<Ability> mapper;
	private AbilityAccessor accessor;
	private static final Logger log = LogManager.getLogger(AbilityDao.class);	
	
	public AbilityDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Ability.class);
			this.accessor = manager.createAccessor(AbilityAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Ability findById(UUID id) {
		log.trace("AbilityDao.findById() calling Mapper.get() and returning Ability");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Ability> getAll() {
		log.trace("AbilityDao.findById() calling AbilityAccessor.getAll() and returning List<Ability>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public void save(Ability player) {
		log.trace("AbilityDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	 
	public void deleteById(UUID id) {
		log.trace("AbilityDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
