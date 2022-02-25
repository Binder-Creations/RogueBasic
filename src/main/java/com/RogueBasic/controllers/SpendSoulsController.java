package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.RogueBasic.beans.Player;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.enums.MetacurrencyPurchase;

@Controller
public class SpendSoulsController {
	@Autowired
	private PlayerDao playerDao;
	
	@GetMapping("/spend_souls")
	public String newCharacter(@CookieValue(value="player_id", required=false) String playerId, Model model) {
		if(playerId == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("spendSouls", new SpendSouls(playerDao.findById(UUID.fromString(playerId))));
			return "spend_souls";
		}
	}
	
	@PostMapping("/spend_souls")
	public void newCharacterSubmit(@CookieValue(value="player_id", required=false) String playerId, @RequestParam("purchase") String purchase,  @RequestParam("cost") String cost, Model model) {
		Player player = playerDao.findById(UUID.fromString(playerId));
		MetacurrencyPurchase.fromString(purchase).purchase(player, cost);
		playerDao.save(player);		
		model.addAttribute("spendSouls", new SpendSouls(playerDao.findById(UUID.fromString(playerId))));
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
	private int conCost;
	private int strCost;
	private int dexCost;
	private int intCost;
	private int goldCost;
	
	public SpendSouls() {}
	
	public SpendSouls(Player player) {
		super();
		this.metacurrency = player.getMetacurrency();
		this.constitutionMetabonus = player.getConstitutionMetabonus();
		this.strengthMetabonus = player.getStrengthMetabonus();
		this.dexterityMetabonus = player.getDexterityMetabonus();
		this.intelligenceMetabonus = player.getIntelligenceMetabonus();
		this.currencyMetabonus = player.getCurrencyMetabonus();
		this.conCost = calcCost(player.getConstitutionMetabonus());
		this.strCost = calcCost(player.getStrengthMetabonus());
		this.dexCost = calcCost(player.getDexterityMetabonus());
		this.intCost = calcCost(player.getIntelligenceMetabonus());
		this.goldCost = calcCost(player.getCurrencyMetabonus()/25);
	}

	private int calcCost(int attribute) {
		return attribute == 0 
			? 3
			: attribute == 1
				? 5
				: attribute*5;
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

	public int getConCost() {
		return conCost;
	}

	public void setConCost(int conCost) {
		this.conCost = conCost;
	}

	public int getStrCost() {
		return strCost;
	}

	public void setStrCost(int strCost) {
		this.strCost = strCost;
	}

	public int getDexCost() {
		return dexCost;
	}

	public void setDexCost(int dexCost) {
		this.dexCost = dexCost;
	}

	public int getIntCost() {
		return intCost;
	}

	public void setIntCost(int intCost) {
		this.intCost = intCost;
	}

	public int getGoldCost() {
		return goldCost;
	}

	public void setGoldCost(int goldCost) {
		this.goldCost = goldCost;
	}
	
}