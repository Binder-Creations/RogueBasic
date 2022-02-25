package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.List;

public enum Position {
	FRONT_LEFT("frontLeft", true),
	FRONT_CENTER("frontCenter", true),
	FRONT_RIGHT("frontRight", true),
	BACK_LEFT("backLeft", false),
	BACK_CENTER("backCenter", false),
	BACK_RIGHT("backRight", false);
	
	private final String position;
	private final boolean front;
	
	Position(String position, boolean front){
		this.position = position;
		this.front = front;
	}
	
	public String position() {
		return this.position;
	}
	
	public boolean front() {
		return this.front;
	}
	
	public static List<Position> allFront(){
		List<Position> frontPositions = new ArrayList<>();
		for(Position position: Position.values()) {
			if(position.front()) {
				frontPositions.add(position);
			}
		}
		return frontPositions;
	}
	
	public static List<Position> allBack(){
		List<Position> backPositions = new ArrayList<>();
		for(Position position: Position.values()) {
			if(!position.front()) {
				backPositions.add(position);
			}
		}
		return backPositions;
	}
}
