package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.data.RoomDAO;

public class RoomServices {
	public RoomDAO dao = new RoomDAO();
	private static final Logger log = LogManager.getLogger(RoomServices.class);
	
}
