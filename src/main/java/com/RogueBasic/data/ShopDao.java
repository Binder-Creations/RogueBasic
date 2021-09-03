package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Shop;
import com.datastax.oss.driver.api.core.CqlSession;

public class ShopDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(ShopDao.class);	
	
	public ShopDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Shop findById(UUID id) {
		log.trace("ShopDao.findById() calling CassandraOperations.selectOne() and returning Shop");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Shop> getAll() {
		log.trace("ShopDao.findById() calling CassandraOperations.select() and returning List<Shop>");
		try {
			return template.select("select * from shop", Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Shop shop) {
		log.trace("ShopDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(shop);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("ShopDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Shop.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		template.truncate(Shop.class);
	}
}
