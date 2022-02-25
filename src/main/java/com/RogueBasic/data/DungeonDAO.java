package com.RogueBasic.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.DungeonAWS;
import com.RogueBasic.util.CassandraUtilities;

@Component
public class DungeonDao {
	@Autowired
	private CassandraOperations cassandraTemplate;
	@Autowired
	private CassandraUtilities cassandraUtilities;	
	
	public DungeonDao() {}
	
	public Dungeon findById(UUID id) {
		return new Dungeon(cassandraUtilities.findById(id, DungeonAWS.class));
	}
	
	public List<Dungeon> getAll() {
		List<DungeonAWS> rawList = cassandraUtilities.getAll(DungeonAWS.class);
		List<Dungeon> convertedList = new ArrayList<>();
		for(DungeonAWS dungeonAWS : rawList) {
			convertedList.add(new Dungeon(dungeonAWS));
		}
		return convertedList;
	}
	  
	public boolean save(Dungeon dungeon) {
		try {
			cassandraTemplate.insert(new DungeonAWS(dungeon));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		try {
			cassandraTemplate.delete(Query.query(Criteria.where("id").is(id)), DungeonAWS.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		cassandraTemplate.truncate(DungeonAWS.class);
	}
}
