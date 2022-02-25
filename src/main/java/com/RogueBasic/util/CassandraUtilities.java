package com.RogueBasic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Component;

import com.RogueBasic.enums.CassandraConstant;
import com.RogueBasic.enums.CassandraTable;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.select.Selector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CassandraUtilities {
	@Autowired
	private CqlSession session;
	@Autowired
	private CassandraOperations cassandraTemplate;
	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger log = LogManager.getLogger(CassandraUtilities.class);
	
	public CassandraUtilities (){}
	
	public void initialize() {
		for(CassandraTable table: CassandraTable.values()) {
			String tableName = CassandraConstant.KEYSPACE.constant() + "." + table.name();
			session.execute("CREATE TABLE IF NOT EXISTS "  + tableName + table.statement());
			log.debug("Table created: " + tableName);
		}
		log.info("All tables created");
	}

	public void dropAllTables() {
		for(CassandraTable table: CassandraTable.values()) {
			String tableName = CassandraConstant.KEYSPACE.constant() + "." + table.name();
			session.execute("DROP TABLE IF EXISTS "  + tableName);
			log.debug("Table dropped: " + tableName);
		}
		log.info("All tables dropped");
	}
	
	public <T> T findById(UUID id, Class<T> entityClass){
		this.mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		Select select = QueryBuilder.selectFrom(entityClass.getSimpleName())
			.json().selector(Selector.all())
			.whereColumn("id").isEqualTo(QueryBuilder.literal(id));
			String rawEntity = cassandraTemplate.selectOne(select.build(), String.class);
			T entity = null;
			try {
				if(rawEntity != null) {
					entity = this.mapper.readValue(rawEntity, entityClass);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return entity;
	}
	
	public <T> List<T> getAll(Class<T> entityClass){
		this.mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		Select select = QueryBuilder.selectFrom(entityClass.getSimpleName())
			.json().selector(Selector.all());
			List<String> rawEntityList = cassandraTemplate.select(select.build(), String.class);
			List<T> entityList = new ArrayList<>();
			try {
				if(rawEntityList != null) {
					for(String rawEntity : rawEntityList) {
						entityList.add(this.mapper.readValue(rawEntity, entityClass));
					}
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return entityList;
	}	
	
}
