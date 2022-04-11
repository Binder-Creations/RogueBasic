package com.RogueBasic.util;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.Shop;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.data.ShopDao;

@Component
public class CassandraGC implements Runnable{
	private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private static final Logger log = LogManager.getLogger(CassandraGC.class);
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private PlayerCharacterDao playerCharacterDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private DungeonDao dungeonDao;
	private boolean collect;
	
	public CassandraGC(){}
	
	public static void start() {
		log.debug("Starting CassandraGC");
		executor.scheduleAtFixedRate(new CassandraGC(), 0, 1, TimeUnit.DAYS);
	}
	
	public void run() {
 	    log.debug("Running CassandraGC");
	    List<Dungeon> dungeons = dungeonDao.getAll();
	    List<PlayerCharacter> characters = playerCharacterDao.getAll();
	    List<Player> players = playerDao.getAll();
	    List<Shop> shops = shopDao.getAll();
	    
	    int counter = 0;
	    for(PlayerCharacter character : characters) {
	    	this.collect = true;
    		for (Player player : players) {
    			if(player.getCharacterIds() != null && player.getCharacterIds().contains(character.getId()))
    				this.collect = false;
    		}
    		if(this.collect) {
    			playerCharacterDao.deleteById(character.getId());
    			counter++;
    		}
	    }
	    log.debug(counter > 0 ? "CassandraGC removed " + counter + " unused PlayerCharacters" : "CassandraGC found no unused PlayerCharacters to remove");
	    
	    List<PlayerCharacter> charactersPostGC = playerCharacterDao.getAll();
	    
	    counter = 0;
	    for(Dungeon dungeon : dungeons) {
    		this.collect = true;
    		for (PlayerCharacter character : charactersPostGC) {
    			if(character.getCurrentDungeon() != null && character.getCurrentDungeon().equals(dungeon.getId()))
    				this.collect = false;
    			if(character.getDungeonBoard() != null) {
	    			character.getDungeonBoard().forEach(id -> {
	    				if(id.equals(dungeon.getId())) {
	    					this.collect = false;
	    				}
	    			});
    			};
    		}
    		if(this.collect) {
    			dungeonDao.deleteById(dungeon.getId());
    			counter++;
    		}
	    }
	    log.debug(counter > 0 ? "CassandraGC removed " + counter + " unused Dungeons" : "CassandraGC found no unused Dungeons to remove");
	    
	    counter = 0;
	    for(Shop shop : shops) {
    		this.collect = true;
    		for (PlayerCharacter character : charactersPostGC) {
    			if(character.getCurrentShop() != null && character.getCurrentShop().equals(shop.getId()))
    				this.collect = false;
    		}
    		if(this.collect) {
    			shopDao.deleteById(shop.getId());
    			counter++;
    		}
	    }
	    log.debug(counter > 0 ? "CassandraGC removed " + counter + " unused Shops" : "CassandraGC found no unused Shops to remove");
	}
}
