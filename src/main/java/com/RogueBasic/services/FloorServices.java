package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.FloorDao;
import com.datastax.driver.core.Session;

public class FloorServices {
	public FloorDao dao;
	private static final Logger log = LogManager.getLogger(FloorServices.class);

	public FloorServices(Session session) {
		super();
		dao = new FloorDao(session);
	}
}
