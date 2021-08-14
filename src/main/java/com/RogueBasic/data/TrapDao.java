package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Trap;
import com.datastax.oss.driver.api.core.CqlSession;

public class TrapDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(TrapDao.class);	
	
	public TrapDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Trap findById(UUID id) {
		log.trace("TrapDao.findById() calling CassandraOperations.selectOne() and returning Trap");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Trap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Trap> getAll() {
		log.trace("TrapDao.findById() calling CassandraOperations.select() and returning List<Trap>");
		try {
			return template.select("select * from trap", Trap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Trap trap) {
		log.trace("TrapDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(trap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("TrapDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Trap.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		template.truncate(Trap.class);
	}
}
