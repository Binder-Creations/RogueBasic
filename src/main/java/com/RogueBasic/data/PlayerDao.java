package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Player;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class PlayerDao {
	private MappingManager manager;
	private Mapper<Player> mapper;
	private PlayerAccessor accessor;
	private static final Logger log = LogManager.getLogger(PlayerDao.class);	
	
	public PlayerDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Player.class);
			this.accessor = manager.createAccessor(PlayerAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player findById(UUID id) {
		log.trace("PlayerDao.findById() calling Mapper.get() and returning Player");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getAll() {
		log.trace("PlayerDao.findById() calling PlayerAccessor.getAll() and returning List<Player>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Player player) {
		log.trace("PlayerDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("PlayerDao.findById() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
