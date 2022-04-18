package com.RogueBasic.util;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtilities {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static Item getItem(String itemName) {
		try {	
			return mapper.readValue(new ClassPathResource("json/items/"+itemName+".json").getInputStream(), Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Ability> getMonsterAbilities(String type){
		try {
			return mapper.readValue(new ClassPathResource("json/monsterAbilities/"+type+".json").getInputStream(), new TypeReference<List<Ability>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PlayerCharacter getBasePlayerCharacter(String characterClass) {
		try {
			return mapper.readValue(new ClassPathResource("json/classes/"+characterClass.toLowerCase()+".json").getInputStream(), PlayerCharacter.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
