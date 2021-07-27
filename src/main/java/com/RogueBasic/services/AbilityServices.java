package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.AbilityDao;
import com.datastax.driver.core.Session;

public class AbilityServices {
	public AbilityDao dao;
	private static final Logger log = LogManager.getLogger(AbilityServices.class);

	public AbilityServices(Session session) {
		super();
		dao = new AbilityDao(session);
	}
}
