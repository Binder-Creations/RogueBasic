package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Monster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class MonsterDao {
	private MappingManager manager;
	private Mapper<Monster> mapper;
	private MonsterAccessor accessor;
	private static final Logger log = LogManager.getLogger(MonsterDao.class);	
	
	public MonsterDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Monster.class);
			this.accessor = manager.createAccessor(MonsterAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Monster findById(UUID id) {
		log.trace("MonsterDao.findById() calling Mapper.get() and returning Monster");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Monster> getAll() {
		log.trace("MonsterDao.findById() calling MonsterAccessor.getAll() and returning List<Monster>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Monster player) {
		log.trace("MonsterDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("MonsterDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
