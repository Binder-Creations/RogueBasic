package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PostMapping("/character_select")
	public String characterSelectPost(@RequestParam("character_id") String characterId, HttpServletResponse response) {
		Cookie idCookie = new Cookie("character_id", characterId);
		idCookie.setPath("/");
		response.addCookie(idCookie);
		return "redirect:/";
	}

}