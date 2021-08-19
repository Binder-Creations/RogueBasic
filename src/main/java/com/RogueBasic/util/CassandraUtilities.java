package com.RogueBasic.util;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Equipment;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Monster;
import com.RogueBasic.beans.Trap;
import com.RogueBasic.data.EquipmentDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.MonsterDao;
import com.RogueBasic.data.TrapDao;
import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CassandraUtilities {
	private CqlSession session;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(CassandraUtilities.class);
	
	public CassandraUtilities (CqlSession session){
		super();
		this.session = session;
		this.ru = new RogueUtilities();
	}
	
	public void initialize() {
		//creates the tables required for our database, from the Tables.rbt document
		log.trace("CassandraUtilities.initialize() calling RogueUtilities.readFileToList() for Tables.rbt");
		ru.readFileToList("source/tables.rbt")
		  .forEach((s)->session.execute("CREATE TABLE IF NOT EXISTS " + s));
		log.debug("Database tables created");
	}
	
	public void populate() {
		ObjectMapper mapper = new ObjectMapper();
		MonsterDao mdao = new MonsterDao(session);
		ItemDao idao = new ItemDao(session);
		EquipmentDao edao = new EquipmentDao(session);
		TrapDao tdao = new TrapDao(session);
		
		//populates our tables with predefined game objects:
		//Monsters, Items, Equipment, Traps
		//Each from their respectively named document
		log.trace("CassandraUtilities.populate() calling RogueUtilities.readFileToList() for Monsters.rbt");
		for(String s : ru.readFileToList("source/Monsters.rbt")) {
			try {
				Monster m = mapper.readValue(s, Monster.class);
				m.setId(UUID.randomUUID());
				log.trace("CassandraUtilities.populate() calling MonsterDao.save()");
				mdao.save(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.trace("CassandraUtilities.populate() calling RogueUtilities.readFileToList() for Items.rbt");
		for(String s : ru.readFileToList("source/items.rbt")) {
			try {
				Item i = mapper.readValue(s, Item.class);
				i.setId(UUID.randomUUID());
				log.trace("CassandraUtilities.populate() calling ItemDao.save()");
				idao.save(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.trace("CassandraUtilities.populate() calling RogueUtilities.readFileToList() for Equipment.rbt");
		for(String s : ru.readFileToList("source/equipment.rbt")) {
			try {
				Equipment eq = mapper.readValue(s, Equipment.class);
				eq.setId(UUID.randomUUID());
				log.trace("CassandraUtilities.populate() calling EquipmentDao.save()");
				edao.save(eq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.trace("CassandraUtilities.populate() calling RogueUtilities.readFileToList() for Traps.rbt");
		for(String s : ru.readFileToList("source/traps.rbt")) {
			try {
				Trap t = mapper.readValue(s, Trap.class);
				t.setId(UUID.randomUUID());
				log.trace("CassandraUtilities.populate() calling TrapDao.save()");
				tdao.save(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.debug("Database tables populated");
	}

	public void dropAllTables() {
		//drops all of our database's tables, as listed in TableList.rbt
		log.trace("CassandraUtilities.dropAllTables() calling RogueUtilities.readFileToList()");
		ru.readFileToList("source/tableList.rbt")
		  .forEach((s)->session.execute("DROP TABLE IF EXISTS " + s));
		log.debug("Database tables dropped");
	}
	
}
