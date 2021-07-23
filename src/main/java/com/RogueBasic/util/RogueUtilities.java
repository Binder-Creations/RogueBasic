package com.RogueBasic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RogueUtilities {
	private static Random rand = new Random();
	private static final Logger log = LogManager.getLogger(RogueUtilities.class);
	
	public static long randIdCharacter() {
		while(true) {
			long tempId = rand.nextLong();
			List<Long> ids = new ArrayList<Long>();
			//List<Long> ids = CharacterDAO.getCharacters().stream().map|something|;
			if(ids.stream().anyMatch(l->l.equals(tempId))) 
				continue;
			log.trace("Long id <-- RogueUtilities.randIdCharacter()");
			return tempId;
		}
	}
	
	public static long randIdPlayer() {
		while(true) {
			long tempId = rand.nextLong();
			List<Long> ids = new ArrayList<Long>();
			//List<Long> ids = PlayerDAO.getPlayers().stream().map|something|;
			if(ids.stream().anyMatch(l->l.equals(tempId))) 
				continue;
			log.trace("Long id <-- RogueUtilities.randIdPlayer()");
			return tempId;
		}
	}
	
	public static long randIdDungeon() {
		while(true) {
			long tempId = rand.nextLong();
			List<Long> ids = new ArrayList<Long>();
			//List<Long> ids = DungeonDAO.getDungeons().stream().map|something|;
			if(ids.stream().anyMatch(l->l.equals(tempId))) 
				continue;
			log.trace("Long id <-- RogueUtilities.randIdDungeon()");
			return tempId;
		}
	}
}
