package com.RogueBasic.enums;

public enum SystemEnv {
	USERNAME("AWS_MCS_SPRING_APP_USERNAME"),
	PASSWORD("AWS_MCS_SPRING_APP_PASSWORD");
	
	private final String constant;
	
	SystemEnv(String constant){
		this.constant = constant;
	}
	
	public String get() {
		return System.getenv(this.constant);
	}
}
