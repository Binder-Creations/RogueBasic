package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Equipment;
import com.datastax.oss.driver.api.core.CqlSession;

public class EquipmentDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(EquipmentDao.class);	
	
	public EquipmentDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Equipment findById(UUID id) {
		log.trace("EquipmentDao.findById() calling CassandraOperations.selectOne() and returning Equipment");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Equipment.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Equipment> getAll() {
		log.trace("EquipmentDao.findById() calling CassandraOperations.select() and returning List<Equipment>");
		try {
			return template.select("select * from equipment", Equipment.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Equipment equipment) {
		log.trace("EquipmentDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(equipment);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("EquipmentDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Equipment.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
