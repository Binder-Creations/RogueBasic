package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Monster;
import com.RogueBasic.data.MonsterDao;
import com.datastax.driver.core.Session;

public class MonsterServices {
	public MonsterDao dao;
	private static final Logger log = LogManager.getLogger(MonsterServices.class);
	
	public MonsterServices(Session session) {
		super();
		this.dao = new MonsterDao(session);
	}
	
	public Set<UUID> generate(Dungeon dungeon, int level){
		Set<UUID> ids = new HashSet<>();
		List<Monster> minibossPool = null;
		List<Monster> bossPool = null;
		List<Monster> monsterPool;
		List<Monster> encounter;
		
		int monsterValue = genMV(dungeon.getChallengeRating(), level);	
		if(dungeon.isMiniboss())
			minibossPool = genMinibossPool(monsterValue, dungeon.getTheme());
		if(dungeon.isBoss()) 
			bossPool = genBossPool(monsterValue, dungeon.getTheme());	
		monsterPool = genMonsterPool(monsterValue, dungeon.getTheme());
		encounter = genEncounter(monsterValue, minibossPool, bossPool, monsterPool);
		encounter.forEach((m)->ids.add(m.getId()));
		
		return ids;
	}

	private int genMV(int challengeRating, int level) {
		int modifier = 2+(challengeRating/2)+(level);
		int base = 3*challengeRating;
		int monsterValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		return monsterValue > 2 ? monsterValue : 2;
	}
	
	private List<Monster> genMinibossPool(int monsterValue, String theme){
		int lowerBound = monsterValue > 60 ? 30 : monsterValue/2;
		int upperBound = monsterValue;
		List<Monster> pool = dao.getAll()
			   .stream()
			   .filter((m)->m.isMiniboss() && m.getTheme() == theme && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
			   .collect(Collectors.toList());
		return pool != null 
				? pool
				: dao.getAll()
				   .stream()
				   .filter((m)->m.isMiniboss() && m.getTheme() == theme)
				   .collect(Collectors.toList());
	}
	
	private List<Monster> genBossPool(int monsterValue, String theme){
		int lowerBound = monsterValue > 60 ? 45 : (3*monsterValue)/4;
		int upperBound = monsterValue;
		List<Monster> pool = dao.getAll()
			   .stream()
			   .filter((m)->m.isBoss() && m.getTheme() == theme && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
			   .collect(Collectors.toList());
		return pool != null 
				? pool
				: dao.getAll()
				   .stream()
				   .filter((m)->m.isBoss() && m.getTheme() == theme)
				   .collect(Collectors.toList());	
	}
	
	private List<Monster> genMonsterPool(int monsterValue, String theme){
		int lowerBound = monsterValue > 60 ? 15 : monsterValue/4;
		int upperBound = (2*monsterValue)/3;
		List<Monster> pool = dao.getAll()
			   .stream()
			   .filter((m)-> !m.isBoss() && !m.isMiniboss() && m.getTheme() == theme && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
			   .collect(Collectors.toList());
		return pool != null 
				? pool
				: dao.getAll()
				   .stream()
				   .filter((m)->!m.isBoss() && !m.isMiniboss() && m.getTheme() == theme)
				   .collect(Collectors.toList());	
	}
	
	private List<Monster> genEncounter(int monsterValue, List<Monster> minibossPool, List<Monster> bossPool,
			List<Monster> monsterPool) {
		List<Monster> encounter = new ArrayList<>();
		int smallestLevel = monsterValue;
		for(Monster m: monsterPool) {
			if(m.getLevel()<smallestLevel)
				smallestLevel = m.getLevel();
		}
		
		if (minibossPool != null) {
			Monster miniboss = minibossPool.get(ThreadLocalRandom.current().nextInt(minibossPool.size()));
			encounter.add(miniboss);
			monsterValue -= miniboss.getLevel();
		}
		
		if (bossPool != null) {
			Monster boss = minibossPool.get(ThreadLocalRandom.current().nextInt(bossPool.size()));
			encounter.add(boss);
			monsterValue -= boss.getLevel();
		}
		
		while(monsterValue >= smallestLevel) {
			List<Monster> newPool = new ArrayList<>();
			for(Monster m: monsterPool) {
				if(m.getLevel()<=monsterValue)
					newPool.add(m);
			monsterPool = newPool;
			Monster selection = monsterPool.get(ThreadLocalRandom.current().nextInt(monsterPool.size()));
			encounter.add(selection);
			monsterValue -= selection.getLevel();
			}
		}
		
		return encounter;
	}
}
