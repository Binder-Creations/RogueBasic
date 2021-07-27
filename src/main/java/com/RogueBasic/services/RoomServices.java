package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.RoomDao;
import com.datastax.driver.core.Session;

public class RoomServices {
	public RoomDao dao;
	private static final Logger log = LogManager.getLogger(RoomServices.class);

	public RoomServices(Session session) {
		super();
		dao = new RoomDao(session);
	}
}
