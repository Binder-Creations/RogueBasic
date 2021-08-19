package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerByName;
import com.datastax.oss.driver.api.core.CqlSession;

public class PlayerDao {
	private CassandraOperations template;
	private static final Logger log = LogManager.getLogger(PlayerDao.class);	
	
	public PlayerDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Player findById(UUID id) {
		log.trace("PlayerDao.findById() calling CassandraOperations.selectOne() and returning Player");
		try {
			return template.selectOne(Query.query(Criteria.where("id").is(id)), Player.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Player findByName(String name) {
		log.trace("PlayerDao.findByName() calling CassandraOperations.selectOne() and returning Player");
		try {
			PlayerByName player = template.selectOne(Query.query(Criteria.where("name").is(name)), PlayerByName.class);
			return player != null
					? template.selectOne(Query.query(Criteria.where("id").is(player.getId())), Player.class)
					: null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getAll() {
		log.trace("PlayerDao.getAll() calling CassandraOperations.select() and returning List<Player>");
		try {
			return template.select("select * from player", Player.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Player player) {
		log.trace("PlayerDao.save() calling CassandraOperations.insert()");
		try {
			template.insert(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean firstSave(Player player ) {
		log.trace("PlayerDao.firstSave() calling CassandraOperations.insert()");
		try {
			template.insert(player);
			template.insert(new PlayerByName(player.getName(), player.getId()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("PlayerDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), Player.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		log.trace("PlayerDao.truncate() calling CassandraOperations.truncate()");
		template.truncate(Player.class);
	}
}
