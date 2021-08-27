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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
public class NewCharacterController {
	
	@GetMapping("/new_character")
	public String newCharacter(@CookieValue(value="player_id", defaultValue="0") String playerId, Model model) {
		if(playerId.equals("0")) {
			return "redirect:/login";
		} else {
			PlayerDao pdao = new PlayerDao(CassandraConnector.getSession());
			model.addAttribute("newCharacter", new NewCharacter(pdao.findById(UUID.fromString(playerId))));
			return "new_character";
		}
	}
	
	@PostMapping("/new_character")
	public String newCharacterSubmit(@CookieValue(value="player_id", defaultValue="0") String playerId, @ModelAttribute NewCharacter newCharacter, Model model) {
		CqlSession session = CassandraConnector.getSession();
		PlayerDao pdao = new PlayerDao(session);
		PlayerCharacterDao pcdao = new PlayerCharacterDao(session);
		Player player = pdao.findById(UUID.fromString(playerId));
		PlayerCharacter pc = new PlayerCharacter(newCharacter.getName(), newCharacter.getCharacterClass(), Integer.parseInt(newCharacter.getConstitution()) + player.getConstitutionMetabonus(), Integer.parseInt(newCharacter.getStrength()) + player.getStrengthMetabonus(), Integer.parseInt(newCharacter.getDexterity()) + player.getDexterityMetabonus(), Integer.parseInt(newCharacter.getIntelligence()) + player.getIntelligenceMetabonus(), player.getCurrencyMetabonus());
		Set<UUID> pcIds = player.getCharacterIds() == null
				? new HashSet<UUID>()
				: player.getCharacterIds();
		pcIds.add(pc.getId());
		player.setCharacterIds(pcIds);
		pdao.save(player);
		pcdao.save(pc);
		
		return "redirect:/character_select";
	}
	
}

class NewCharacter{
	private String name;
	private String characterClass;
	private String constitution;
	private String strength;
	private String dexterity;
	private String intelligence;
	private int constitutionMetabonus;
	private int strengthMetabonus;
	private int dexterityMetabonus;
	private int intelligenceMetabonus;
	private int currencyMetabonus;
	
	public NewCharacter() {}
	
	public NewCharacter(Player player) {
		this.constitutionMetabonus = player.getConstitutionMetabonus();
		this.strengthMetabonus = player.getStrengthMetabonus();
		this.dexterityMetabonus = player.getDexterityMetabonus();
		this.intelligenceMetabonus = player.getIntelligenceMetabonus();
		this.currencyMetabonus = player.getCurrencyMetabonus();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getDexterity() {
		return dexterity;
	}
	public void setDexterity(String dexterity) {
		this.dexterity = dexterity;
	}
	public String getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(String intelligence) {
		this.intelligence = intelligence;
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