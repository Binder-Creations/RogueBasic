package com.RogueBasic.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerByName;
import com.datastax.oss.driver.api.core.CqlSession;

@Component
public class PlayerByNameDao {
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private CassandraOperations cassandraTemplate;	
	
	public PlayerByNameDao(CqlSession session) {
		super();
	}

	public Player findByName(String name) {
		try {
			PlayerByName player = cassandraTemplate.selectOne(Query.query(Criteria.where("name").is(name)), PlayerByName.class);
			return player != null
					? playerDao.findById(player.getId())
					: null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean save(Player player) {
		try {
			cassandraTemplate.insert(new PlayerByName(player.getName(), player.getId()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
