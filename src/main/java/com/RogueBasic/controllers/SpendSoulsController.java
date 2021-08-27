package com.RogueBasic.controllers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;

@Controller
public class SpendSoulsController {
	
	@GetMapping("/spend_souls")
	public String newCharacter(@CookieValue(value="player_id", defaultValue="0") String playerId, Model model) {
		PlayerDao pdao = new PlayerDao(CassandraConnector.getSession());
		if(playerId.equals("0")) {
			return "redirect:/login";
		} else {
			model.addAttribute("spendSouls", new SpendSouls(pdao.findById(UUID.fromString(playerId))));
			return "spend_souls";
		}
	}
	
	@PostMapping("/spend_souls")
	public void newCharacterSubmit(@CookieValue(value="player_id", defaultValue="0") String playerId, @RequestParam("purchase") String purchase,  @RequestParam("cost") String cost, Model model) {
		PlayerDao pdao = new PlayerDao(CassandraConnector.getSession());
		Player player = pdao.findById(UUID.fromString(playerId));
		switch(purchase) {
			case "Constitution":
				player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
				player.setConstitutionMetabonus(player.getConstitutionMetabonus()+1);
				break;
			case "Strength":
				player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
				player.setStrengthMetabonus(player.getStrengthMetabonus()+1);
				break;
			case "Dexterity":
				player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
				player.setDexterityMetabonus(player.getDexterityMetabonus()+1);
				break;
			case "Intelligence":
				player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
				player.setIntelligenceMetabonus(player.getIntelligenceMetabonus()+1);
				break;
			case "Currency":
				player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
				player.setCurrencyMetabonus(player.getCurrencyMetabonus()+25);
				break;
		}
		
		pdao.save(player);		
		model.addAttribute("spendSouls", new SpendSouls(pdao.findById(UUID.fromString(playerId))));
		return;
	}
	
}

class SpendSouls{
	private int metacurrency;
	private int constitutionMetabonus;
	private int strengthMetabonus;
	private int dexterityMetabonus;
	private int intelligenceMetabonus;
	private int currencyMetabonus;
	
	public SpendSouls() {}
	
	public SpendSouls(Player player) {
		super();
		this.metacurrency = player.getMetacurrency();
		this.constitutionMetabonus = player.getConstitutionMetabonus();
		this.strengthMetabonus = player.getStrengthMetabonus();
		this.dexterityMetabonus = player.getDexterityMetabonus();
		this.intelligenceMetabonus = player.getIntelligenceMetabonus();
		this.currencyMetabonus = player.getCurrencyMetabonus();
	}

	public int getMetacurrency() {
		return metacurrency;
	}

	public void setMetacurrency(int metacurrency) {
		this.metacurrency = metacurrency;
	}

	public int getConstitutionMetabonus() {
		return constitutionMetabonus;
	}

	public void setConstitutionMetabonus(int constitutionMetabonus) {
		this.constitutionMetabonus = constitutionMetabonus;
	}

	public int getStrengthMetabonus() {
		return strengthMetabonus;
	}

	public void setStrengthMetabonus(int strengthMetabonus) {
		this.strengthMetabonus = strengthMetabonus;
	}

	public int getDexterityMetabonus() {
		return dexterityMetabonus;
	}

	public void setDexterityMetabonus(int dexterityMetabonus) {
		this.dexterityMetabonus = dexterityMetabonus;
	}

	public int getIntelligenceMetabonus() {
		return intelligenceMetabonus;
	}

	public void setIntelligenceMetabonus(int intelligenceMetabonus) {
		this.intelligenceMetabonus = intelligenceMetabonus;
	}

	public int getCurrencyMetabonus() {
		return currencyMetabonus;
	}

	public void setCurrencyMetabonus(int currencyMetabonus) {
		this.currencyMetabonus = currencyMetabonus;
	}
}