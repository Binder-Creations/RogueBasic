package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerByNameDao;
import com.RogueBasic.data.PlayerDao;

@Controller
public class RegisterController {
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private PlayerByNameDao playerByNameDao;
	
	@GetMapping("/register")
	public String register(@CookieValue(value="player_id", required=false) String playerId, @CookieValue(value="player_name", required=false) String playerName, Model model) {
		if(playerId == null || playerName == null) {
			model.addAttribute("register", new Register());
			if(playerName == null && playerId != null) {
				model.addAttribute("temp", true);
			}
			return "register";
		} else {
			return "redirect:/home";
		}
	}
	
	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute Register register, @CookieValue(value="player_id", required=false) String playerId, Model model, final RedirectAttributes redirectAttributes) {
		Player player = playerByNameDao.findByName(register.getName());
		if(player == null) {
			if(playerId != null) {
				player = new Player(register.getName(), register.getPassword(), playerDao.findById(UUID.fromString(playerId)));
			} else {
				player = new Player(register.getName(), register.getPassword());
			}
			playerByNameDao.save(player);
			playerDao.save(player);
		    redirectAttributes.addFlashAttribute("name", player.getName());
			return "redirect:/welcome";
		} else {
			model.addAttribute("failure", true);
			return "register";
		}
	}
	
	@PostMapping("/register/{id}")
    public ResponseEntity<String> newPlayer(@PathVariable String id) {
    	Player tempPlayer = new Player(id);
    	playerByNameDao.save(tempPlayer);
    	return ResponseEntity.ok().build();
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