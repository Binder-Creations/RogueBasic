package com.RogueBasic.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnector {

    private Cluster cluster;
    private Session session;
    private static final Logger log = LogManager.getLogger(CassandraConnector.class);
    
    public Session connect() {
    	try {
    		Cluster cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .withPort(9042)
                .withoutJMXReporting()
               // .withProtocolVersion(ProtocolVersion.V3)
                .build();
    	log.debug("Cassandra cluster created.");
        this.session = cluster.connect("rogue");
        
        log.debug("Cassandra session created, keyspace: " + session.getLoggedKeyspace());
        return this.session;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        if(session != null) {
        	log.debug("Closing active Cassandra session.");
        	session.close();
        } else {
        	log.debug("Cannot find an active Cassandra session to close.");
        }
    	if(cluster != null) {
    		log.debug("Closing active Cassandra cluster.");
    		cluster.close();
    	} else {
    		log.debug("Cannot find an active Cassandra cluster to close.");
    	}
        
    }
}