package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Floor;
import com.datastax.oss.driver.api.core.CqlSession;

public class FloorDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(FloorDao.class);	
	
	public FloorDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Floor findById(UUID id) {
		log.trace("FloorDao.findById() calling CassandraOperations.selectOne() and returning Floor");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Floor.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Floor> getAll() {
		log.trace("FloorDao.findById() calling CassandraOperations.select() and returning List<Floor>");
		try {
			return template.select("select * from floor", Floor.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Floor floor) {
		log.trace("FloorDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(floor);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("FloorDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Floor.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
