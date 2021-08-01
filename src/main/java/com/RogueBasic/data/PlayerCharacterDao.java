package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.PlayerCharacter;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class PlayerCharacterDao {
	private MappingManager manager;
	private Mapper<PlayerCharacter> mapper;
	private PlayerCharacterAccessor accessor;
	private static final Logger log = LogManager.getLogger(PlayerCharacterDao.class);	
	
	public PlayerCharacterDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(PlayerCharacter.class);
			this.accessor = manager.createAccessor(PlayerCharacterAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlayerCharacter findById(UUID id) {
		log.trace("PlayerCharacterDao.findById() calling Mapper.get() and returning PlayerCharacter");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PlayerCharacter> getAll() {
		log.trace("PlayerCharacterDao.findById() calling PlayerCharacterAccessor.getAll() and returning List<PlayerCharacter>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(PlayerCharacter player) {
		log.trace("PlayerCharacterDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("PlayerCharacterDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
