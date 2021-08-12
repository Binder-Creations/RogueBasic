package com.RogueBasic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String home(@ModelAttribute("player") Player player, Model model) {
		return player.getName() != null 
				? "home"
				: "redirect:/login";
	}
	
}