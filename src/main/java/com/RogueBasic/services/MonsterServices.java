package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.MonsterDao;
import com.datastax.driver.core.Session;

public class MonsterServices {
	public MonsterDao dao;
	private static final Logger log = LogManager.getLogger(MonsterServices.class);

	public MonsterServices(Session session) {
		super();
		dao = new MonsterDao(session);
	}
}
