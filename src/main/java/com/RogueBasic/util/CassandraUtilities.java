package com.RogueBasic.util;

import java.util.UUID;

import com.RogueBasic.beans.Equipment;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Monster;
import com.RogueBasic.beans.Trap;
import com.RogueBasic.data.EquipmentDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.MonsterDao;
import com.RogueBasic.data.TrapDao;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CassandraUtilities {
	private Session session;
	private RogueUtilities ru;
	
	public CassandraUtilities (Session session){
		super();
		this.session = session;
		this.ru = new RogueUtilities();
	}
	
	public void initialize() {
		ru.readFileToList("Initialize", ".rbt")
		  .forEach((s)->session.execute("CREATE TABLE IF NOT EXISTS " + s));
	}
	
	public void populate() {
		ObjectMapper mapper = new ObjectMapper();
		MonsterDao mdao = new MonsterDao(session);
		ItemDao idao = new ItemDao(session);
		EquipmentDao edao = new EquipmentDao(session);
		TrapDao tdao = new TrapDao(session);
		
		for(String s : ru.readFileToList("Monsters", ".rbt")) {
			try {
				Monster m = mapper.readValue(s, Monster.class);
				m.setId(UUID.randomUUID());
				mdao.save(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(String s : ru.readFileToList("Items", ".rbt")) {
			try {
				Item i = mapper.readValue(s, Item.class);
				i.setId(UUID.randomUUID());
				idao.save(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(String s : ru.readFileToList("Equipment", ".rbt")) {
			try {
				Equipment eq = mapper.readValue(s, Equipment.class);
				eq.setId(UUID.randomUUID());
				edao.save(eq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(String s : ru.readFileToList("Traps", ".rbt")) {
			try {
				Trap t = mapper.readValue(s, Trap.class);
				t.setId(UUID.randomUUID());
				tdao.save(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dropAllTables() {
		ru.readFileToList("TableList", ".rbt")
		  .forEach((s)->session.execute("DROP TABLE IF EXISTS " + s));
	}
	
}
