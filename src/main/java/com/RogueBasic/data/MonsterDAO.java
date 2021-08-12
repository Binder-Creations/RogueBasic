package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Monster;
import com.datastax.oss.driver.api.core.CqlSession;

public class MonsterDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(MonsterDao.class);	
	
	public MonsterDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Monster findById(UUID id) {
		log.trace("MonsterDao.findById() calling CassandraOperations.selectOne() and returning Monster");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Monster.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Monster> getAll() {
		log.trace("MonsterDao.findById() calling CassandraOperations.select() and returning List<Monster>");
		try {
			return template.select("select * from monster", Monster.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Monster monster) {
		log.trace("MonsterDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(monster);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("MonsterDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Monster.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
