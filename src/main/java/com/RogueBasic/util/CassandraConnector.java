package com.RogueBasic.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.oss.driver.api.core.CqlSession;

public class CassandraConnector {

    private static CqlSession session;
    private static final Logger log = LogManager.getLogger(CassandraConnector.class);
    
    public static CqlSession connect() {
    	try {
    		session = CqlSession.builder().withKeyspace("rogue").build();
    		return session;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public static CqlSession getSession() {
        return session;
    }
    
    public static void close() {
        if(session != null) {
        	log.debug("Closing active Cassandra session.");
        	session.close();
        } else {
        	log.debug("Cannot find an active Cassandra session to close.");
        }       
    }
}