package com.RogueBasic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.DAO;

public class RogueUtilities {
	private static Random rand = new Random();
	private static final Logger log = LogManager.getLogger(RogueUtilities.class);
	
	public static long newId(DAO dao) {
		while(true) {
			long tempId = rand.nextLong();
			//if(dao.getIds().stream().anyMatch(l->l.equals(tempId))) 
			//	continue;
			log.trace("RogueUtilities.randIdCharacter() returns long id");
			return tempId;
		}	
	}
}
