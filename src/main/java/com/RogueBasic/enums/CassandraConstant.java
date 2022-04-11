package com.RogueBasic.enums;

public enum CassandraConstant {
	KEYSPACE("rogue");
	
	private final String constant;
	
	CassandraConstant(String constant){
		this.constant = constant;
	}
	
	public String get() {
		return this.constant;
	}
}
