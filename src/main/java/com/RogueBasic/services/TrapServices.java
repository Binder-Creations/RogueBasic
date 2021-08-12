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
import com.RogueBasic.beans.Trap;
import com.RogueBasic.data.TrapDao;
import com.datastax.oss.driver.api.core.CqlSession;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

public class TrapServices {
	public TrapDao dao;
	private static final Logger log = LogManager.getLogger(TrapServices.class);
	
	public TrapServices(CqlSession session) {
		super();
		this.dao = new TrapDao(session);
	}
	
	public UUID generate(Dungeon dungeon, int level){
		List<Trap> trapPool;
	
		log.trace("TrapServices.generate() calling genMV()");
		int trapValue = genTV(dungeon.getChallengeRating(), level);	
		log.trace("TrapServices.generate() calling genTrapPool()");
		trapPool = genTrapPool(trapValue, dungeon.getTheme());
		log.trace("TrapServices.generate() calling genEncounter()");
		
		log.trace("TrapServices.generate() returning UUID");
		return trapPool.get(ThreadLocalRandom.current().nextInt(trapPool.size())).getId();
	}

	private int genTV(int challengeRating, int level) {
		int modifier = 1+(2*level);
		int base = 2*challengeRating;
		int trapValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		log.trace("TrapServices.genTV() returning int");
		return trapValue > 1 ? trapValue : 1;
	}
	
	private List<Trap> genTrapPool(int trapValue, String theme){
		int lowerBound = trapValue > 50 ? 25 : trapValue/2;
		int upperBound = trapValue;
		log.trace("TrapServices.genTrapPool() calling TrapDao.getAll()");
		List<Trap> pool = new ArrayList<>();
			   dao.getAll()
			   .stream()
			   .filter((t)->t.getTheme().equals(theme) && t.getLevel()>=lowerBound && t.getLevel() <= upperBound)
			   .collect(Collectors.toList());
		log.trace("TrapServices.genTrapPool() returning List<Trap>");
		return pool.size() != 0
				? pool
				: dao.getAll()
				   .stream()
				   .filter((t)->t.getTheme().equals(theme))
				   .collect(Collectors.toList());	
	}
}
