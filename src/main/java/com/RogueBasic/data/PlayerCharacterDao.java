package com.RogueBasic.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterAWS;
import com.datastax.oss.driver.api.core.CqlSession;

public class PlayerCharacterDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(PlayerCharacterDao.class);	
	
	public PlayerCharacterDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlayerCharacter findById(UUID id) {
		log.trace("PlayerCharacterDao.findById() calling CassandraOperations.selectOne() and returning PlayerCharacter");
		try {
			return new PlayerCharacter(template.selectOne(Query.query(Criteria.where("id").is(id)), PlayerCharacterAWS.class));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PlayerCharacter> getAll() {
		log.trace("PlayerCharacterDao.findById() calling CassandraOperations.select() and returning List<PlayerCharacter>");
		try {
			List<PlayerCharacterAWS> playerCharactersAWS = template.select("select * from playerCharacter", PlayerCharacterAWS.class);
			List<PlayerCharacter> playerCharacters = new ArrayList<>();
			for(PlayerCharacterAWS playerCharacterAWS: playerCharactersAWS) {
				playerCharacters.add(new PlayerCharacter(playerCharacterAWS));
			}
			return playerCharacters;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(PlayerCharacter playerCharacter) {
		log.trace("PlayerCharacterDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(new PlayerCharacterAWS(playerCharacter));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("PlayerCharacterDao.deleteById() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), PlayerCharacterAWS.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		template.truncate(PlayerCharacterAWS.class);
	}
}
