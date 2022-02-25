package com.RogueBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.util.CassandraUtilities;

@Component
public class Initialize {
	@Autowired
	CassandraUtilities cassandraUtilities;
	
	Initialize(){}
	
	public void initialize() {
		cassandraUtilities.dropAllTables();
		cassandraUtilities.initialize();		
	}
}
