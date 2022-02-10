package com.RogueBasic.util;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Query;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Item;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.select.Selector;
import com.datastax.oss.driver.shaded.guava.common.base.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public <T> T findById(UUID id, Class<T> entityClass){
		CassandraTemplate template = new CassandraTemplate(this.session);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		Select select = QueryBuilder.selectFrom(entityClass.getSimpleName())
			.json().selector(Selector.all())
			.whereColumn("id").isEqualTo(QueryBuilder.literal(id));
			T entity = null;
			try {
				entity = mapper.readValue(template.selectOne(select.build(), String.class), entityClass);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return entity;
	}
	
}
