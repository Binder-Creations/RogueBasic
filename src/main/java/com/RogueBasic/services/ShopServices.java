package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.AbilityDao;
import com.datastax.oss.driver.api.core.CqlSession;

public class ShopServices {
	public AbilityDao dao;
	private static final Logger log = LogManager.getLogger(ShopServices.class);

	public ShopServices(CqlSession session) {
		super();
		dao = new AbilityDao(session);
	}
}
