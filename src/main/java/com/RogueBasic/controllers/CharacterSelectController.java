package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;

@Controller
public class CharacterSelectController {
	
	@GetMapping("/character_select")
	public String characterSelect(@CookieValue(value="player_id", defaultValue="0") String playerId, Model model) {
		if (playerId.equals("0")){
			return "redirect:/login";
		}
		
		PlayerDao pdao = new PlayerDao(CassandraConnector.getSession());
		
		if(pdao.findById(UUID.fromString(playerId)).getCharacterIds() == null) {
			return "redirect:/home";
		}
		
		PlayerCharacterDao pcdao = new PlayerCharacterDao(CassandraConnector.getSession());	
		List<PlayerCharacter> characters = new ArrayList<>();
		pdao.findById(UUID.fromString(playerId))
			.getCharacterIds()
			.stream()
			.forEach((pc)->characters.add(pcdao.findById(pc)));	
		model.addAttribute("characters", characters);
		
		return "character_select";
	}

}