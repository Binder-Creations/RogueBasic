package com.RogueBasic.enums;

public enum SystemConstant {
	USERNAME("AWS_MCS_SPRING_APP_USERNAME"),
	PASSWORD("AWS_MCS_SPRING_APP_PASSWORD");
	
	private final String constant;
	
	SystemConstant(String constant){
		this.constant = constant;
	}
	
	public String constant() {
		return System.getenv(this.constant);
	}
}
