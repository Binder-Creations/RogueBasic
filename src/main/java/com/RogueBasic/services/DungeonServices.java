package com.RogueBasic.services;

import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.data.DungeonDAO;

public class DungeonServices {
	public DungeonDAO dao = new DungeonDAO();
	private static final Logger log = LogManager.getLogger(DungeonServices.class);
	Random rand = new Random();
	
	public UUID generate() {
		String theme = randTheme();
		String name = randName(theme);
		String description = "";
		int floorCount = 0;
		int challengeRating = 0;
		boolean boss = false;
		boolean miniBoss = false;
		Dungeon d = new Dungeon(name, description, theme, floorCount, challengeRating, boss, miniBoss);
		return d.getId();
	}
	
	private String randTheme() {
		String[] themes = {"Cave", "Castle", "Arcane"};
		return themes[rand.nextInt(themes.length)];
	}
	
	private String randName(String theme) {
		switch (theme) {
			case "Cave":
				String[] cavePrefix = {"Abandoned", "Desolate", "Bloodrent"};
				String[] caveAdjective = {"Dank", "Dark", "Crystal"};
				String[] caveNoun = {"Hollow", "Cave", "Recess"};
				String[] cavePostfix = {"of Spiders", "of Despair", "of the Lost"};
				return (rand.nextInt(2) == 1 ? cavePrefix[rand.nextInt(cavePrefix.length)] + " " : "") 
						+ caveAdjective[rand.nextInt(caveAdjective.length)] + " "
						+ caveNoun[rand.nextInt(caveNoun.length)]
						+ (rand.nextInt(2) == 1 ? " " + cavePostfix[rand.nextInt(cavePostfix.length)] : "");
			case "Castle":
				String[] castlePrefix = {"Stonehewn", "Imposing", "Crumbling"};
				String[] castleAdjective = {"Knightly", "Fortified", "Bloodstone"};
				String[] castleNoun = {"Bastion", "Castle", "Bastilla"};
				String[] castlePostfix = {"of the Tyrant", "of Iron", "of the Dark Legion"};
				return (rand.nextInt(2) == 1 ? castlePrefix[rand.nextInt(castlePrefix.length)] + " " : "") 
						+ castleAdjective[rand.nextInt(castleAdjective.length)] + " "
						+ castleNoun[rand.nextInt(castleNoun.length)]
						+ (rand.nextInt(2) == 1 ? " " + castlePostfix[rand.nextInt(castlePostfix.length)] : "");
			case "Arcane":
				String[] arcanePrefix = {"Manawept", "Etherial", "Haunted"};
				String[] arcaneAdjective = {"Wizard's", "Arcane", "Cultist"};
				String[] arcaneNoun = {"Laboratory", "Ritual Site", "Tower"};
				String[] arcanePostfix = {"of Ley Crossings", "of Demon Summoning", "of Planar Instability"};
				return (rand.nextInt(2) == 1 ? arcanePrefix[rand.nextInt(arcanePrefix.length)] + " " : "") 
						+ arcaneAdjective[rand.nextInt(arcaneAdjective.length)] + " "
						+ arcaneNoun[rand.nextInt(arcaneNoun.length)]
						+ (rand.nextInt(2) == 1 ? " " + arcanePostfix[rand.nextInt(arcanePostfix.length)] : "");
			default:
				return null;
		}
	}
}
