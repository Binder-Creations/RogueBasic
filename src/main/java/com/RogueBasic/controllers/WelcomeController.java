package com.RogueBasic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.RogueBasic.beans.Player;

@Controller
public class WelcomeController {
	
	@GetMapping("/welcome")
	public String welcome(@ModelAttribute("player") Player player, Model model) {
		return "welcome";
	}
	
}