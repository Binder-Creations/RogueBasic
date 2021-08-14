package com.RogueBasic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String dungeon(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginSubmit(@ModelAttribute Login login, Model model, final RedirectAttributes redirectAttributes) {
		CqlSession session = CassandraConnector.getSession();
		PlayerDao pdao = new PlayerDao(session);
		Player player = pdao.findByName(login.getName());
		if(player != null && player.getPassword().equals(login.getPassword())) {
			redirectAttributes.addFlashAttribute("player", player);
			return "redirect:/home";
		} else {
			model.addAttribute("failure", true);
			return "login";
		}
	}
	
}

class Login{
	private String name;
	private String password;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}