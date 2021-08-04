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
import com.RogueBasic.data.ItemDao;
import com.datastax.driver.core.Session;

public class ItemServices {
	public ItemDao dao;
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	public ItemServices(Session session) {
		super();
		dao = new ItemDao(session);
	}

	public Set<UUID> generate(Dungeon dungeon, int level, boolean miniboss, boolean boss, boolean monsters, boolean trapped){
		Set<UUID> ids = new HashSet<>();
		List<Item> lootPool;
		List<Item> loot;
		
		int lootValue = genLV(dungeon.getChallengeRating(), level, miniboss, boss, monsters, trapped);	
		if (lootValue == 0)
			return null;
		
		lootPool = genLootPool(lootValue);
		loot = genLoot(lootValue, lootPool);
		loot.forEach((i)->ids.add(i.getId()));
		
		return ids;
	}

	private int genLV(int challengeRating, int level, boolean miniboss, boolean boss, boolean monsters,
			boolean trapped) {
		int modifier = 2+(challengeRating/2)+(level);
		int base = boss 
				? challengeRating*6
				: miniboss
					? challengeRating*4
					: monsters
						? challengeRating*3
						: trapped
							? challengeRating*2
							:challengeRating;
		int lootValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		return lootValue > 0 ? lootValue : 0;
	}
	
	private List<Item> genLootPool(int lootValue){
		int lowerBound = lootValue > 60 ? 20 : lootValue/3;
		List<Item> pool = dao.getAll()
			   .stream()
			   .filter((i)-> i.getCost()>=lowerBound && i.getCost() <= lootValue)
			   .collect(Collectors.toList());
		return pool != null 
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
			lootPool = newPool;
			Item selection = lootPool.get(ThreadLocalRandom.current().nextInt(lootPool.size()));
			loot.add(selection);
			lootValue -= selection.getCost();
			}
		}
		
		return loot;
	}

}
