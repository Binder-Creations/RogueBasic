package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.CharacterDao;
import com.datastax.driver.core.Session;

public class CharacterServices {
	public CharacterDao dao;
	private static final Logger log = LogManager.getLogger(CharacterServices.class);

	public CharacterServices(Session session) {
		super();
		dao = new CharacterDao(session);
	}
}
