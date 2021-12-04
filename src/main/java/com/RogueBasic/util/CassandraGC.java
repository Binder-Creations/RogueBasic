package com.RogueBasic.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.Shop;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.data.ShopDao;

public class CassandraGC implements Runnable{
	private Thread t;
	private boolean collect;
	private static final Logger log = LogManager.getLogger(CassandraGC.class);
	
	public void run() {
 	    Boolean initialized = false;
	    
 	    log.debug("Running CassandraGC thread");
	    try {
	        while(true) {
	        	if(initialized) {
	        		Thread.sleep(86400000);
	        	}
	        	
	        	initialized = true;
	    		DungeonDao dDao = new DungeonDao(CassandraConnector.connect());
	     	    PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.connect());
	     	    PlayerDao pDao = new PlayerDao(CassandraConnector.connect());
	     	    ShopDao sDao = new ShopDao(CassandraConnector.connect());
	    	    List<Dungeon> dungeons = dDao.getAll();
	    	    List<PlayerCharacter> characters = pcDao.getAll();
	    	    List<Player> players = pDao.getAll();
	    	    List<Shop> shops = sDao.getAll();
	    	    
	    	    int counter = 0;
	    	    for(PlayerCharacter character : characters) {
    	    		this.collect = true;
    	    		for (Player player : players) {
		    			if(player.getCharacterIds().contains(character.getId()))
		    				this.collect = false;
    	    		}
    	    		if(this.collect) {
    	    			pcDao.deleteById(character.getId());
    	    			counter++;
    	    		}
	    	    }
	    	    log.debug(counter > 0 ? "CassandraGC removed " + counter + " unused PlayerCharacters" : "CassandraGC found no unused PlayerCharacters to remove");
	    	    
	    	    List<PlayerCharacter> charactersPostGC = pcDao.getAll();
	    	    
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
    	    			dDao.deleteById(dungeon.getId());
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
    	    			sDao.deleteById(shop.getId());
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
	          t.start ();
	       }
		}
}
