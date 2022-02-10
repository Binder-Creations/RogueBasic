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
	
	public Set<Item> genInventory(UUID shopID, UUID pcId) {
		Set<Item> inventory = new HashSet<>();
		CqlSession session = CassandraConnector.getSession();
		PlayerCharacterDao pcDao = new PlayerCharacterDao(session);
		ItemServices iService = new ItemServices(session);
		PlayerCharacter pc = pcDao.findById(pcId);
		
		pc.setCurrentShop(shopID);
		pcDao.save(pc);

		for(int i = 0; i < 23; i++) {
			Item item = iService.genEquipment(pc.getEquipTypes(), pc.getLevel());
			inventory.add(item);
		}
		for(int i = 2; i < 6 ;i++) {
			inventory.add(iService.getPremade(i));
		}
		
		return inventory;
	}
	
	
}
