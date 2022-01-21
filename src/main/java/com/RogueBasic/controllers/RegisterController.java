package com.RogueBasic.controllers;

import java.util.UUID;

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
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
public class RegisterController {
	
	@GetMapping("/register")
	public String register(@CookieValue(value="player_id", defaultValue="0") String playerId, @CookieValue(value="player_name", required=false) String playerName, Model model) {
		if(playerId.equals("0") || playerName == null) {
			model.addAttribute("register", new Register());
			if(playerName == null && !playerId.equals("0")) {
				model.addAttribute("temp", true);
			}
			return "register";
		} else {
			return "redirect:/home";
		}
	}
	
	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute Register register, @CookieValue(value="player_id", defaultValue="0") String playerId, Model model, final RedirectAttributes redirectAttributes) {
		CqlSession session = CassandraConnector.getSession();
		PlayerDao pdao = new PlayerDao(session);
		Player player = pdao.findByName(register.getName());
		if(player == null) {
			if(!playerId.equals("0")) {
				Player tempPlayer = pdao.findById(UUID.fromString(playerId));
				player = new Player(register.getName(), register.getPassword(), tempPlayer);
			} else {
				player = new Player(register.getName(), register.getPassword());
			}
			pdao.firstSave(player);
		    redirectAttributes.addFlashAttribute("name", player.getName());
			return "redirect:/welcome";
		} else {
			model.addAttribute("failure", true);
			return "register";
		}
	}
	
    @PostMapping("/register/{id}")
    public ResponseEntity newPlayer(@PathVariable String id) {
    	PlayerDao pDao = new PlayerDao(CassandraConnector.getSession());
    	Player tempPlayer = new Player(id);
    	pDao.firstSave(tempPlayer);
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