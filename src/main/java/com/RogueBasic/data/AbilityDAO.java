package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Ability;
import com.datastax.oss.driver.api.core.CqlSession;

public class AbilityDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(AbilityDao.class);	
	
	public AbilityDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Ability findById(UUID id) {
		log.trace("AbilityDao.findById() calling CassandraOperations.selectOne() and returning Ability");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Ability.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Ability> getAll() {
		log.trace("AbilityDao.findById() calling CassandraOperations.select() and returning List<Ability>");
		try {
			return template.select("select * from player", Ability.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Ability player) {
		log.trace("AbilityDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("AbilityDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Ability.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
