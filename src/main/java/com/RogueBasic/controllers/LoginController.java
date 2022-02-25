package com.RogueBasic.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerByNameDao;

@Controller
public class LoginController {
	@Autowired
	private PlayerByNameDao playerByNameDao;
	
	@GetMapping("/login")
	public String dungeon(@CookieValue(value="player_id", required=false) String playerId, @CookieValue(value="player_name", required=false) String playerName, @RequestParam(name="logout", required=false) String logout, Model model, HttpServletResponse response) {
		if(logout != null || (playerId != null && playerName == null)) {
			Cookie nameCookie = new Cookie("player_name", null);
			Cookie idCookie = new Cookie("player_id", null);
			nameCookie.setPath("/");
			idCookie.setPath("/");
			nameCookie.setMaxAge(0);
			idCookie.setMaxAge(0);
			response.addCookie(nameCookie);
			response.addCookie(idCookie);
			model.addAttribute("login", new Login());
			return "login";
		} else {
			if(playerId == null) {
				model.addAttribute("login", new Login());
				return "login";
			} else {
				return "redirect:/home";
			}
		}
	}
	
	@PostMapping("/login")
	public String loginSubmit(@ModelAttribute Login login, Model model, HttpServletResponse response) {
		Player player = playerByNameDao.findByName(login.getName());
		if(player != null && player.getPassword().equals(login.getPassword())) {
			Cookie nameCookie = new Cookie("player_name", player.getName());
			Cookie idCookie = new Cookie("player_id", player.getId().toString());
			nameCookie.setPath("/");
			idCookie.setPath("/");
			if(login.isRemember()) {
				nameCookie.setMaxAge(2592000);
				idCookie.setMaxAge(2592000);
			}
			response.addCookie(nameCookie);
			response.addCookie(idCookie);
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
	private boolean remember;
	
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

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}
	
	
}