package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;

@Controller
public class HomeController {
	@Autowired
	private PlayerDao playerDao;
	
	@GetMapping("/home")
	public String home(@CookieValue(value="player_id", required=false) String playerId, @CookieValue(value="player_name", required=false) String playerName, Model model) {
		if(playerName == null) {
			model.addAttribute("name", "Temporary User");
			model.addAttribute("temp", true);
		} else {
			model.addAttribute("name", playerName);
		}
		if(playerId != null) {
			Player player = playerDao.findById(UUID.fromString(playerId));
			if(player != null) {
				model.addAttribute("has_characters", player.getCharacterIds() != null);
			} else {
				playerId = null;
			}
		}
		if(playerName == null) {
			model.addAttribute("temp", true);
		}
		return playerId == null 
				? "redirect:/login"
				: "home";
	}

}