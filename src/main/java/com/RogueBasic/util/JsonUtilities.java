package com.RogueBasic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtilities {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static Item getItem(String itemName) {
		try {	
			return mapper.readValue(Files.readString(Paths.get("src/main/resources/json/items/" + itemName + ".json")), Item.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Ability> getMonsterAbilities(String type){
		try {
			return mapper.readValue(Files.readString(Paths.get("src/main/resources/json/monsterAbilities/"+type+".json")), new TypeReference<List<Ability>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static PlayerCharacter getBasePlayerCharacter(String characterClass) {
		try {
			return mapper.readValue(Files.readString(Paths.get("src/main/resources/json/classes/" + characterClass.toLowerCase() + ".json")), PlayerCharacter.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
