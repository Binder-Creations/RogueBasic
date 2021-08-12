package com.RogueBasic.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.datastax.oss.driver.api.core.CqlSession;

public class PlayerServices {
	public PlayerDao dao;
	private static final Logger log = LogManager.getLogger(PlayerServices.class);

	public PlayerServices(CqlSession session) {
		super();
		dao = new PlayerDao(session);
	}
	
	public UUID login(String name, String password) {
		
		//finds the Player matching the userName and password of the login attempt
		//or null if none found
		log.trace("PlayerServices.login() calls PlayerDAO.getAll()");
		Player p = dao.getAll()
				.stream()
				.filter(x -> x.getName()==name&&x.getPassword()==password)
				.findFirst()
				.orElse(null);
		
		
		if(p!=null) log.info("Logging in Player \"" + name + "\".");
		
		//returns the id of the matching Player, or null if no match
		log.trace("PlayerServices.login() returns long id (null on fail)");
		return p != null ? p.getId() : null;
	}
	
	public UUID register(String name, String password) {
		
		//tries to find a Player matching the Username of the registration attempt
		log.trace("PlayerServices.register() calls PlayerDAO.getAll()");
		Boolean b = dao.getAll()
				.stream()
				.anyMatch(x -> x.getName()==name);
		
		if(b==false) log.info("Registering Player \"" + name + "\".");
		
		Player p = new Player(name, password);
		
		log.trace("PlayerServices.register() calls PlayerDAO.save()");
		dao.save(p);
		
		//registers a new player if name available; otherwise returns null
		log.trace("PlayerServices.register() returns long id (null on fail)");
		return b == false ? p.getId() : null;
	}
}
