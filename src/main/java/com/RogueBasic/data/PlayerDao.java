package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.Player;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class PlayerDao {
	MappingManager manager = new MappingManager(new CassandraConnector().getSession());
    Mapper<Player> mapper = manager.mapper(Player.class);
    
	public Player findById(UUID playerId) {
		return mapper.get(playerId);
	};
	
	public List<Player> getAll() {
		return null;
	}
	
	  
	public void save(Player player) {
		mapper.save(player);
		return;
	}
	
	 
	public void deleteById(UUID playerId) {
		mapper.delete(playerId);
		return;
	}
}
