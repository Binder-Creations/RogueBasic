package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Player;

@Component
public class PlayerDao {
	@Autowired
	private CassandraOperations cassandraTemplate;	
	
	public PlayerDao() {}
	
	public Player findById(UUID id) {
		try {
			return cassandraTemplate.selectOne(Query.query(Criteria.where("id").is(id)), Player.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getAll() {
		try {
			return cassandraTemplate.select("select * from player", Player.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Player player) {
		try {
			cassandraTemplate.insert(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		try {
			cassandraTemplate.delete(Query.query(Criteria.where("id").is(id)), Player.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		cassandraTemplate.truncate(Player.class);
	}
}
