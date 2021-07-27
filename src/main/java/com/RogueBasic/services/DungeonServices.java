package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.DungeonDao;
import com.datastax.driver.core.Session;

public class DungeonServices {
	public DungeonDao dao;
	private static final Logger log = LogManager.getLogger(DungeonServices.class);

	public DungeonServices(Session session) {
		super();
		dao = new DungeonDao(session);
	}
}
