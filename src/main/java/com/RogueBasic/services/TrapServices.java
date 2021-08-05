package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.TrapDao;
import com.datastax.driver.core.Session;

public class TrapServices {
	public TrapDao dao;
	private static final Logger log = LogManager.getLogger(TrapServices.class);

	public TrapServices(Session session) {
		super();
		dao = new TrapDao(session);
	}
}
