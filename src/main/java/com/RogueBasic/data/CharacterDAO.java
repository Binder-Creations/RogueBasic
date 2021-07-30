package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.PlayerCharacter;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class CharacterDao {
	private MappingManager manager;
	private Mapper<PlayerCharacter> mapper;
	private CharacterAccessor accessor;
	private static final Logger log = LogManager.getLogger(CharacterDao.class);	
	
	public CharacterDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(PlayerCharacter.class);
			this.accessor = manager.createAccessor(CharacterAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlayerCharacter findById(UUID id) {
		log.trace("CharacterDao.findById() calling Mapper.get() and returning Character");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PlayerCharacter> getAll() {
		log.trace("CharacterDao.findById() calling CharacterAccessor.getAll() and returning List<Character>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public void save(PlayerCharacter player) {
		log.trace("CharacterDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	 
	public void deleteById(UUID id) {
		log.trace("CharacterDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
