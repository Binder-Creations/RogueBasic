package com.RogueBasic.enums;

import java.util.function.Function;

import com.RogueBasic.beans.Monster;

import io.netty.util.internal.ThreadLocalRandom;

public enum MonsterModifier {
	NONE("", 
			monster -> {
				return monster;
			}),
	ROBUST("Robust ", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.35f));
				return monster;
			}),
	POTENT("Potent ", 
			monster -> {
				monster.setPower(Math.round((float)monster.getPower()*1.35f));
				return monster;
			}),
	ARMORED("Armored ", 
			monster -> {
				monster.setArmor(Math.round((float)monster.getArmor()*1.35f));
				return monster;
			}),
	NIMBLE("Nimble ", 
			monster -> {
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.35f));
				return monster;
			}),
	KEEN("Keen ", 
			monster -> {
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.35f));
				return monster;
			}),
	HULKING("Hulking ", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.2f));
				monster.setPower(Math.round((float)monster.getPower()*1.2f));
				return monster;
			}),
	RESILLIENT("Resillient ", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.2f));
				monster.setArmor(Math.round((float)monster.getArmor()*1.2f));
				return monster;
			}),
	ATHLETIC("Athletic ", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.2f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.2f));
				return monster;
			}),
	MARTIAL("Martial ", 
			monster -> {
				monster.setHealth(Math.round((float)monster.getHealth()*1.2f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));				return monster;
			}),
	METALLIC("Metallic ", 
			monster -> {
				monster.setPower(Math.round((float)monster.getPower()*1.2f));
				monster.setArmor(Math.round((float)monster.getArmor()*1.2f));
				return monster;
			}),
	ALACRITOUS("Alacritous ", 
			monster -> {
				monster.setPower(Math.round((float)monster.getPower()*1.2f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.2f));
				return monster;
			}),
	DEADLY("Deadly ", 
			monster -> {
				monster.setPower(Math.round((float)monster.getPower()*1.2f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			}),
	DEFENSIVE("Defensive ", 
			monster -> {
				monster.setArmor(Math.round((float)monster.getArmor()*1.2f));
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.2f));
				return monster;
			}),
	HONED("Honed ", 
			monster -> {
				monster.setArmor(Math.round((float)monster.getArmor()*1.2f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			}),
	AGILE("Agile ", 
			monster -> {
				monster.setDodgeRating(Math.round((float)monster.getDodgeRating()*1.2f));
				monster.setCritRating(Math.round((float)monster.getCritRating()*1.2f));
				return monster;
			});
	
	private final String modifier;
	private final Function<Monster, Monster> adjustStats;
	
	MonsterModifier(String modifier, Function<Monster, Monster> adjustStats){
		this.modifier = modifier;
		this.adjustStats = adjustStats;
	}
	
	public String getModifier() {
		return this.modifier;
	}
	
	public Monster modifyStats(Monster monster) {
		return this.adjustStats.apply(monster);
	}
	
	public static MonsterModifier getRandomModifier() {
		if(ThreadLocalRandom.current().nextInt(9) >= 4) {
			return MonsterModifier.NONE;
		} else {
			return MonsterModifier.values()[ThreadLocalRandom.current().nextInt(1, MonsterModifier.values().length)];
		}
	}
}
