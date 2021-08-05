package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Room;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class RoomDao {
	private MappingManager manager;
	private Mapper<Room> mapper;
	private RoomAccessor accessor;
	private static final Logger log = LogManager.getLogger(RoomDao.class);	
	
	public RoomDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Room.class);
			this.accessor = manager.createAccessor(RoomAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Room findById(UUID id) {
		log.trace("RoomDao.findById() calling Mapper.get() and returning Room");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Room> getAll() {
		log.trace("RoomDao.getAll() calling RoomAccessor.getAll() and returning List<Room>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Room room) {
		log.trace("RoomDao.findById() calling Mapper.save()");
		try {
			mapper.save(room);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("RoomDao.save() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
