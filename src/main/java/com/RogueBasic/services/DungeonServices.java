package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.driver.core.Session;

public class DungeonServices {
	private Session session;
	private DungeonDao dDao;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(DungeonServices.class);
	
	public DungeonServices(Session session) {
		super();
		this.session = session;
		this.dDao = new DungeonDao(session);
		this.ru = new RogueUtilities();
	}
	
	public Dungeon generate(UUID pcId) {
		if(pcId != null) {
			PlayerCharacterDao pcDao = new PlayerCharacterDao(session);
			PlayerCharacter pc = pcDao.findById(pcId);
			String theme = genTheme();
			String name = genName(theme);
			return new Dungeon(name, genDescription(name), theme, 0, 0, false, false);
		} else {
			log.debug("passed null pcId; returning null");
			return null;
		}
	}
	
	private String genTheme() {
		String[] themes = {"Cave", "Castle", "Arcane"};
		return themes[ThreadLocalRandom.current().nextInt(themes.length)];
	}
	
	public String genName (String theme) {
		
		List<String[]> components = ru.readFileToArrays(theme, ".rbt");
		StringBuilder sb = new StringBuilder();
		
		if(components == null) {
			return null;
		}
		
		if(ThreadLocalRandom.current().nextInt(3) > 1) 
			sb.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
		sb.append(components.get(1)[ThreadLocalRandom.current().nextInt(components.get(1).length)])
		  .append(components.get(2)[ThreadLocalRandom.current().nextInt(components.get(2).length)]);
		if(ThreadLocalRandom.current().nextInt(3) > 1) 
			sb.append(components.get(3)[ThreadLocalRandom.current().nextInt(components.get(3).length)]);
		
		return sb.toString();
	}
	
	private String genDescription(String name) {
		return null;
	}
}
