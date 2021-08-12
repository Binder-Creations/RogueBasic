package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Item;
import com.datastax.oss.driver.api.core.CqlSession;

public class ItemDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(ItemDao.class);	
	
	public ItemDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Item findById(UUID id) {
		log.trace("ItemDao.findById() calling CassandraOperations.selectOne() and returning Item");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Item> getAll() {
		log.trace("ItemDao.findById() calling CassandraOperations.select() and returning List<Item>");
		try {
			return template.select("select * from item", Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Item item) {
		log.trace("ItemDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("ItemDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Item.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
