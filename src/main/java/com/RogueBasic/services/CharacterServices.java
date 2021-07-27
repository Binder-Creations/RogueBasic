package com.RogueBasic.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.CharacterDAO;

public class CharacterServices {
	public CharacterDAO dao = new CharacterDAO();
	private static final Logger log = LogManager.getLogger(CharacterServices.class);

}
