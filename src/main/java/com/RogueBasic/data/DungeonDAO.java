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

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.DungeonAWS;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class DungeonDao {
	private CassandraOperations template;
	private CassandraUtilities cu;
	private static final Logger log = LogManager.getLogger(DungeonDao.class);	
	
	public DungeonDao(CqlSession session) {
		super();
		try {
			this.template = new CassandraTemplate(session);
			this.cu = new CassandraUtilities(session);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Dungeon findById(UUID id) {
		log.trace("DungeonDao.findById() calling CassandraOperations.selectOne() and returning Dungeon");
		return new Dungeon(cu.findById(id, DungeonAWS.class));
	}
	
	public List<Dungeon> getAll() {
		log.trace("DungeonDao.findById() calling CassandraOperations.select() and returning List<Dungeon>");
		try {
			List<DungeonAWS> dungeonsAWS = template.select("select * from dungeonAWS", DungeonAWS.class);
			List<Dungeon> dungeons = new ArrayList<>();
			for(DungeonAWS dungeonAWS: dungeonsAWS) {
				dungeons.add(new Dungeon(dungeonAWS));
			}
			return dungeons;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Dungeon dungeon) {
		log.trace("DungeonDao.findById() calling CassandraOperations.insert()");
		try {
			template.insert(new DungeonAWS(dungeon));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("DungeonDao.save() calling CassandraOperations.delete()");
		try {
			template.delete(Query.query(Criteria.where("id").is(id)), DungeonAWS.class);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void truncate() {
		template.truncate(DungeonAWS.class);
	}
}
