package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.PlayerCharacterDao;
import com.datastax.oss.driver.api.core.CqlSession;

public class PlayerCharacterServices {
	public PlayerCharacterDao dao;
	private static final Logger log = LogManager.getLogger(PlayerCharacterServices.class);

	public PlayerCharacterServices(CqlSession session) {
		super();
		dao = new PlayerCharacterDao(session);
	}
}
