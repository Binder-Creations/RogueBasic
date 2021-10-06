package com.RogueBasic.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.ItemDao;
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
	
	public Map<UUID, Integer> genInventory(UUID pcId) {
		Map<UUID, Integer> inventory = new HashMap<>();
		CqlSession session = CassandraConnector.getSession();
		PlayerCharacterDao pcdao = new PlayerCharacterDao(session);
		ItemDao idao = new ItemDao(session);
		ItemServices iservice = new ItemServices(session);
		PlayerCharacter pc = pcdao.findById(pcId);
		
		String[] exceptions;
		if(pc.getCharacterClass() == "Rogue") {
			String[] rogueExceptions = {"headLight", "headHeavy", "bodyLight", "bodyHeavy", "sword", "staff", "shield", "spellbook"};
			exceptions = rogueExceptions;
		}
		if(pc.getCharacterClass() == "Wizard") {
			String[] wizardExceptions = {"headMedium", "headHeavy", "bodyMedium", "bodyHeavy", "sword", "bow", "shield", "dagger"};
			exceptions = wizardExceptions;
		} else {
			String[] warriorExceptions = {"headLight", "headMedium", "bodyLight", "bodyMedium", "bow", "staff", "dagger", "spellbook"};
			exceptions = warriorExceptions;
		}
		
		inventory.put(UUID.fromString("d822106a-e753-40db-898d-d438d1592baa"), 3);
		inventory.put(UUID.fromString("ab0d75df-8457-4dde-adfe-77c9b37d262d"), 5);
		
		for(int i = 0; i < 23; i++) {
			Item item = iservice.genEquipment(exceptions, pc.getLevel());
			idao.save(item);
			inventory.put(item.getId(), 1);
		}
		
		return inventory;
	}
	
	
}
