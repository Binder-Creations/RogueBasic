package com.RogueBasic.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Shop;
import com.RogueBasic.beans.ShopAWS;

@Component
public class ShopDao {
	@Autowired
	private CassandraOperations cassandraTemplate;	
	
	public ShopDao() {}
	
	public Shop findById(UUID id) {
		try {
			return new Shop(cassandraTemplate.selectOne(Query.query(Criteria.where("id").is(id)), ShopAWS.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Shop> getAll() {
		try {
			List<ShopAWS> shopsAWS = cassandraTemplate.select("select * from shopAWS", ShopAWS.class);
			List<Shop> shops = new ArrayList<>();
			for(ShopAWS shopAWS: shopsAWS) {
				shops.add(new Shop(shopAWS));
			}
			return shops;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Shop shop) {
		try {
			cassandraTemplate.insert(new ShopAWS(shop));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		try {
			cassandraTemplate.delete(Query.query(Criteria.where("id").is(id)), ShopAWS.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		cassandraTemplate.truncate(ShopAWS.class);
	}
}
