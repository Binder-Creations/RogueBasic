package com.RogueBasic.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.ShopDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

public class ShopServices {
	public ShopDao dao;
	private static final Logger log = LogManager.getLogger(ShopServices.class);

	public ShopServices(CqlSession session) {
		super();
		dao = new ShopDao(session);
	}
	
	public Set<Item> genInventoryCache(UUID shopID, UUID pcId) {
		Set<Item> inventory = new HashSet<>();
		CqlSession session = CassandraConnector.getSession();
		PlayerCharacterDao pcDao = new PlayerCharacterDao(session);
		ItemServices iService = new ItemServices(session);
		PlayerCharacter pc = pcDao.findById(pcId);
		
		pc.setCurrentShop(shopID);
		pcDao.save(pc);
		String[] exceptions = null;
		if(pc.getCharacterClass().equals("Rogue")) {
			String[] rogueExceptions = {"headLight", "headHeavy", "bodyLight", "bodyHeavy", "sword", "staff", "shield", "spellbook"};
			exceptions = rogueExceptions;
		}
		if(pc.getCharacterClass().equals("Wizard")) {
			String[] wizardExceptions = {"headMedium", "headHeavy", "bodyMedium", "bodyHeavy", "sword", "bow", "shield", "dagger"};
			exceptions = wizardExceptions;
		}
		if(pc.getCharacterClass().equals("Warrior"))  {
			String[] warriorExceptions = {"headLight", "headMedium", "bodyLight", "bodyMedium", "bow", "staff", "dagger", "spellbook"};
			exceptions = warriorExceptions;
		}
		
		for(int i = 0; i < 23; i++) {
			Item item = iService.genEquipment(exceptions, pc.getLevel());
			inventory.add(item);
		}
		
		return inventory;
	}
	
	
}
