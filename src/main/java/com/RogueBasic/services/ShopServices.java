package com.RogueBasic.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.enums.EquipmentType;
import com.RogueBasic.enums.StaticItem;

@Component
public class ShopServices {
	@Autowired
	private PlayerCharacterDao playerCharacterDao;
	@Autowired
	private ItemServices itemServices;
	
	
	public ShopServices() {}
	
	public Set<Item> genInventory(UUID shopID, UUID pcId) {
		Set<Item> inventory = new HashSet<>();
		PlayerCharacter pc = playerCharacterDao.findById(pcId);
		
		pc.setCurrentShop(shopID);
		playerCharacterDao.save(pc);

		inventory.add(StaticItem.HEALTH_POTION.getItem());
		inventory.add(StaticItem.ENERGY_POTION.getItem());
		inventory.add(StaticItem.RATIONS.getItem());
		inventory.add(StaticItem.WINE.getItem());
		for(int i = 0; i < 23; i++) {
			Item item = itemServices.genEquipment(EquipmentType.fromStrings(pc.getEquipTypes()), pc.getLevel());
			inventory.add(item);
		}
		
		return inventory;
	}
	
	
}
