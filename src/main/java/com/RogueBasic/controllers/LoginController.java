package com.RogueBasic.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String dungeon(@CookieValue(value="player_id", defaultValue="0") String playerId, @RequestParam(name = "logout", defaultValue = "false") String logout, Model model, HttpServletResponse response) {
		if(logout.equals("true")) {
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
			if(playerId.equals("0")) {
				model.addAttribute("login", new Login());
				return "login";
			} else {
				return "redirect:/home";
			}
		}
	}
	
	@PostMapping("/login")
	public String loginSubmit(@ModelAttribute Login login, Model model, HttpServletResponse response) {
		CqlSession session = CassandraConnector.getSession();
		PlayerDao pdao = new PlayerDao(session);
		Player player = pdao.findByName(login.getName());
		if(player != null && player.getPassword().equals(login.getPassword())) {
			Cookie nameCookie = new Cookie("player_name", player.getName());
			Cookie idCookie = new Cookie("player_id", player.getId().toString());
			nameCookie.setPath("/");
			idCookie.setPath("/");
			if(login.isRemember()) {
				nameCookie.setMaxAge(30 * 24 * 60 * 60);
				idCookie.setMaxAge(30 * 24 * 60 * 60);
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