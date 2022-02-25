package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.RogueBasic.beans.Monster;

import io.netty.util.internal.ThreadLocalRandom;

public enum MonsterType {
	ARCHER("archer", Position.BACK_CENTER, 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.1f));
				monster.setArmor(Math.round((float)monster.getArmor()*1.1f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			}),
	ROGUE("rogue", Position.FRONT_CENTER, 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*0.7f));
				monster.setArmor(Math.round((float)monster.getArmor()*0.7f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.9f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			}),
	WARRIOR("warrior", Position.FRONT_CENTER, 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.5f));
				monster.setPower(Math.round((float)monster.getPower()*0.7f));
				monster.setArmor(Math.round((float)monster.getArmor()*1.5f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*0.8f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*0.7f));
				return monster;
			}),
	WIZARD("wizard", Position.BACK_CENTER, 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*0.6f));
				monster.setPower(Math.round((float)monster.getPower()*2.2f));
				monster.setArmor(Math.round((float)monster.getArmor()*0.6f));
				return monster;
			});
	
	private final String type;
	private final Position defaultPosition;
	private final Function<Monster, Monster> adjustStats;
	
	MonsterType(String type, Position defaultPosition, Function<Monster, Monster> adjustStats){
		this.type = type;
		this.defaultPosition = defaultPosition;
		this.adjustStats = adjustStats;
	}
	
	public String type() {
		return this.type;
	}
	
	public Position defaultPosition() {
		return this.defaultPosition;
	}
	
	public Monster adjustStats(Monster monster) {
		return this.adjustStats.apply(monster);
	}
	
	public static MonsterType randomFrontType() {
		List<MonsterType> frontTypes = new ArrayList<>();
		for(MonsterType type: MonsterType.values()) {
			if(type.defaultPosition().front()) {
				frontTypes.add(type);
			}
		}
		return frontTypes.get(ThreadLocalRandom.current().nextInt(frontTypes.size()));
	}
	
	public static MonsterType randomBackType() {
		List<MonsterType> backTypes = new ArrayList<>();
		for(MonsterType type: MonsterType.values()) {
			if(!type.defaultPosition().front()) {
				backTypes.add(type);
			}
		}
		return backTypes.get(ThreadLocalRandom.current().nextInt(backTypes.size()));
	}
	
	public static MonsterType randomType() {	
		return MonsterType.values()[ThreadLocalRandom.current().nextInt(MonsterType.values().length)];
	}
}
