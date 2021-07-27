package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.ItemDao;
import com.datastax.driver.core.Session;

public class ItemServices {
	public ItemDao dao;
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	public ItemServices(Session session) {
		super();
		dao = new ItemDao(session);
	}
}
