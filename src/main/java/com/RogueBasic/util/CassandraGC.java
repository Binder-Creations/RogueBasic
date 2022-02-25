package com.RogueBasic.util;

import java.util.List;

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
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private PlayerCharacterDao playerCharacterDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private DungeonDao dungeonDao;
	private Thread t;
	private boolean collect;
	private static final Logger log = LogManager.getLogger(CassandraGC.class);
	
	public CassandraGC(){}
	
	public void run() {
 	    Boolean initialized = false;
 	    log.debug("Running CassandraGC thread");
	    try {
	        while(true) {
	        	if(initialized) {
	        		Thread.sleep(86400000);
	        	}
	        	
	        	initialized = true;
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
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    log.debug("CassandraGC thread exiting.");
	}
	
	public void start() {
		log.debug("Starting CassandraGC thread");
	    if (t == null) {
	          t = new Thread (this, "CassandraGC");
	          t.start();
	       }
		}
}
