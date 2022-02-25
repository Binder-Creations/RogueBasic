package com.RogueBasic.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;

@Component
public class PlayerServices {
	@Autowired
	private PlayerDao playerDao;
	private static final Logger log = LogManager.getLogger(PlayerServices.class);

	public PlayerServices() {}
	
	public UUID login(String name, String password) {
		//finds the Player matching the userName and password of the login attempt
		//or null if none found
		Player p = playerDao.getAll()
				.stream()
				.filter(x -> x.getName()==name&&x.getPassword()==password)
				.findFirst()
				.orElse(null);
		
		if(p!=null) log.info("Logging in Player \"" + name + "\".");
		return p != null ? p.getId() : null;
	}
	
	public UUID register(String name, String password) {	
		//tries to find a Player matching the Username of the registration attempt
		log.trace("PlayerServices.register() calls PlayerDAO.getAll()");
		Boolean b = playerDao.getAll()
				.stream()
				.anyMatch(x -> x.getName()==name);
		
		if(!b) log.info("Registering Player \"" + name + "\".");
		
		Player p = new Player(name, password);
		
		playerDao.save(p);
		//registers a new player if name available; otherwise returns null
		return b == false ? p.getId() : null;
	}
}
