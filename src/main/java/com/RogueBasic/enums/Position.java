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
	
	public String getPosition() {
		return this.position;
	}
	
	public boolean isFront() {
		return this.front;
	}
	
	public static List<Position> getAllFront(){
		List<Position> frontPositions = new ArrayList<>();
		for(Position position: Position.values()) {
			if(position.isFront()) {
				frontPositions.add(position);
			}
		}
		return frontPositions;
	}
	
	public static List<Position> getAllBack(){
		List<Position> backPositions = new ArrayList<>();
		for(Position position: Position.values()) {
			if(!position.isFront()) {
				backPositions.add(position);
			}
		}
		return backPositions;
	}
}
