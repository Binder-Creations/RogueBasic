package com.RogueBasic.util;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraUtilities {
	private CqlSession session;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(CassandraUtilities.class);
	private String keyspace;
	
	public CassandraUtilities (CqlSession session){
		super();
		this.session = session;
		this.ru = new RogueUtilities();
		this.keyspace = "rogue";
	}
	
	public void initialize() {
		//creates the tables required for our database, from the Tables.rbt document
		log.trace("CassandraUtilities.initialize() calling RogueUtilities.readFileToList() for Tables.rbt");
		ru.readFileToList("source/tables.rbt")
		  .forEach((s)->session.execute("CREATE TABLE IF NOT EXISTS " + this.keyspace + "." + s));
		log.debug("Database tables created");
	}

	public void dropAllTables() {
		//drops all of our database's tables, as listed in TableList.rbt
		Pattern name = Pattern.compile("(\\w+)\\s+");
		log.trace("CassandraUtilities.dropAllTables() calling RogueUtilities.readFileToList()");
		ru.readFileToList("source/tables.rbt")
		  .forEach((s)->{
			  Matcher m = name.matcher(s);
			  if(m.find())
				  session.execute("DROP TABLE IF EXISTS "  + this.keyspace + "." + m.group(1));});
		log.debug("Database tables dropped");
	}
	
}
