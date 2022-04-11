package com.RogueBasic.util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.enums.CharacterClass;
import com.RogueBasic.enums.MonsterType;
import com.RogueBasic.enums.StaticItem;

public class JsonUtilitiesTest {
	
	@Test
	public void getItemTest() {
		Item item = JsonUtilities.getItem(StaticItem.ENERGY_POTION.getName());
		assertNotNull(item);
		assertNotNull(item.getId());
		assertNull(JsonUtilities.getItem("notAnItem"));
	}
	
	@Test
	public void getMonsterAbilitiesTest() {
		List<Ability> abilities = JsonUtilities.getMonsterAbilities(MonsterType.ARCHER.getType());
		assertNotNull(abilities);
		assumeTrue(abilities != null);
		for(Ability ability: abilities) {
			assertNotNull(ability);
		}
		assertNull(JsonUtilities.getMonsterAbilities("notAType"));
	}
	
	@Test
	public void getBasePlayerCharacter() {
		PlayerCharacter playerCharacter = JsonUtilities.getBasePlayerCharacter(CharacterClass.ROGUE.getName());
		assertNotNull(playerCharacter);	
	}
}
