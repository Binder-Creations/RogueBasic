package com.RogueBasic.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterAWS;
import com.RogueBasic.util.CassandraUtilities;

@Component
public class PlayerCharacterDao {
	@Autowired
	private CassandraOperations cassandraTemplate;
	@Autowired
	private CassandraUtilities cassandraUtilities;	
	
	public PlayerCharacterDao() {}
	
	public PlayerCharacter findById(UUID id) {
		return new PlayerCharacter(this.cassandraUtilities.findById(id, PlayerCharacterAWS.class));
	}
	
	public List<PlayerCharacter> getAll() {
		List<PlayerCharacterAWS> rawList = cassandraUtilities.getAll(PlayerCharacterAWS.class);
		List<PlayerCharacter> convertedList = new ArrayList<>();
		for(PlayerCharacterAWS playerCharacterAWS : rawList) {
			convertedList.add(new PlayerCharacter(playerCharacterAWS));
		}
		return convertedList;
	}
	  
	public boolean save(PlayerCharacter playerCharacter) {
		try {
			cassandraTemplate.insert(new PlayerCharacterAWS(playerCharacter));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		try {
			cassandraTemplate.delete(Query.query(Criteria.where("id").is(id)), PlayerCharacterAWS.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		cassandraTemplate.truncate(PlayerCharacterAWS.class);
	}
}
