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
public class RegisterController {
	
	@GetMapping("/register")
	public String dungeon(Model model) {
		model.addAttribute("register", new Register());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute Register register, Model model, final RedirectAttributes redirectAttributes) {
		CqlSession session = CassandraConnector.getSession();
		PlayerDao pdao = new PlayerDao(session);
		Player player = pdao.findByName(register.getName());
		if(player == null) {
			player = new Player(register.getName(), register.getPassword());
			pdao.save(player);
		    redirectAttributes.addFlashAttribute("player", player);
			return "redirect:/home";
		} else {
			model.addAttribute("failure", true);
			return "register";
		}
	}
	
}

class Register{
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