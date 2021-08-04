package com.RogueBasic.util;

import com.datastax.driver.core.Session;

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
		
	}
	
	public void dropAllTables() {
		ru.readFileToList("TableList", ".rbt")
		  .forEach((s)->session.execute("DROP TABLE IF EXISTS " + s));
	}
	
}
