package com.RogueBasic.services;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;

public class PlayerServices {
	public PlayerDao dao = new PlayerDao();
	private Logger log = LogManager.getLogger(PlayerServices.class);

	public UUID login(String userName, String password) {
		
		//finds the Player matching the userName and password of the login attempt
		//or null if none found
		log.trace("PlayerServices.login() calls PlayerDAO.getAll()");
		Player p = dao.getAll()
				.stream()
				.filter(x -> x.getUserName()==userName&&x.getPassword()==password)
				.findFirst()
				.orElse(null);
		
		//returns the id of the matching Player, or null if no match
		log.trace("PlayerServices.login() returns long id (null on fail)");
		return p != null ? p.getId() : null;
	}
	
	public UUID register(String userName, String password) {
		
		//
		log.trace("PlayerServices.login() calls PlayerDAO.getAll()");
		Player p = dao.getAll()
				.stream()
				.filter(x -> x.getUserName()==userName)
				.findFirst()
				.orElse(null);
		
		log.trace("PlayerServices.register() returns long id (null on fail)");
		return p !=null ? null : new Player(userName, password).getId();
	}
}
