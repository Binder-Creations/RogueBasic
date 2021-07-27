package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.MonsterDAO;

public class MonsterServices {
	public MonsterDAO dao = new MonsterDAO();
	private static final Logger log = LogManager.getLogger(MonsterServices.class);

	
}
