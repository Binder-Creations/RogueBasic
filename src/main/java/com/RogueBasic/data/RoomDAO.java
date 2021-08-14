package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Room;
import com.datastax.oss.driver.api.core.CqlSession;

public class RoomDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(RoomDao.class);	
	
	public RoomDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Room findById(UUID id) {
		log.trace("RoomDao.findById() calling CassandraOperations.selectOne() and returning Room");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Room.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Room> getAll() {
		log.trace("RoomDao.findById() calling CassandraOperations.select() and returning List<Room>");
		try {
			return template.select("select * from room", Room.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Room room) {
		log.trace("RoomDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(room);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("RoomDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Room.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		template.truncate(Room.class);
	}
}
