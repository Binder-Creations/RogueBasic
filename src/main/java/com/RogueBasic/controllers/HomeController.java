package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String home(@CookieValue(value="player_id", defaultValue="0") String playerId, @CookieValue(value="player_name", defaultValue="null") String playerName, Model model) {
		PlayerDao pdao = new PlayerDao(CassandraConnector.getSession());
		model.addAttribute("name", playerName);
		if(!playerId.equals("0"))
			model.addAttribute("has_characters", pdao.findById(UUID.fromString(playerId)).getCharacterIds() != null);
		return playerId.equals("0") 
				? "redirect:/login"
				: "home";
	}

}