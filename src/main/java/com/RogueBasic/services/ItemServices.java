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
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.ItemDao;
import com.datastax.oss.driver.api.core.CqlSession;

public class ItemServices {
	private ItemDao dao;
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	public ItemServices(CqlSession session) {
		super();
		dao = new ItemDao(session);
	}

	public void generate(Dungeon dungeon, Room room, int level){
		List<Item> lootPool;
		List<Item> loot;
		Set<UUID> itemIds = new HashSet<>();
		
		int lootValue = genLV(dungeon.getChallengeRating(), level, room.isMiniboss(), room.isBoss(), room.getMonsterIds() != null, room.getTrapId() != null);	
		if (lootValue == 0)
			return;
		
		lootPool = genLootPool(lootValue);
		loot = genLoot(lootValue, lootPool);
		
		for (Item i : loot) {
			itemIds.add(i.getId());
		}
		
		room.setItemIds(itemIds);
	}
	
	private int genLV(int challengeRating, int level, boolean miniboss, boolean boss, boolean monsters,
			boolean trapped) {
		int modifier = 10*(2+(challengeRating/2)+(level));
		int base = boss 
				? challengeRating*60
				: miniboss
					? challengeRating*45
					: monsters
						? challengeRating*30
						: trapped
							? challengeRating*20
							:challengeRating*10;
		int lootValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		return lootValue > 0 ? lootValue : 0;
	}
	
	private List<Item> genLootPool(int lootValue){
		int lowerBound = lootValue > 60 ? 20 : lootValue/3;
		List<Item> pool = dao.getAll();
		pool.stream()
			   .filter((i)-> i.getCost()>=lowerBound && i.getCost() <= lootValue)
			   .collect(Collectors.toList());
		return pool.size() != 0 
				? pool
				: dao.getAll();
	}
	
	private List<Item> genLoot(int lootValue, List<Item> lootPool) {
		List<Item> loot = new ArrayList<>();
		int smallestCost = lootValue;
		
		for(Item i: lootPool) {
			if(i.getCost()<smallestCost)
				smallestCost = i.getCost();
		}
		
		while(lootValue >= smallestCost) {
			List<Item> newPool = new ArrayList<>();
			for(Item i: lootPool) {
				if(i.getCost()<=lootValue)
					newPool.add(i);
			}
			lootPool = newPool;
			if (lootPool.size()==0) {
				break;
			}
			Item selection = lootPool.get(ThreadLocalRandom.current().nextInt(lootPool.size()));
			loot.add(selection);
			lootValue -= selection.getCost();
		}

		
		return loot;
	}

}
