package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.ItemDAO;

public class ItemServices {
	public ItemDAO dao = new ItemDAO();
	private static final Logger log = LogManager.getLogger(ItemServices.class);

	
}
