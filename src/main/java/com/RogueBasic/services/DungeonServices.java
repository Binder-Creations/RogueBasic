package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.driver.core.Session;

public class DungeonServices {
	private Session session;
	private DungeonDao dDao;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(DungeonServices.class);
	
	public DungeonServices(Session session) {
		super();
		this.session = session;
		this.dDao = new DungeonDao(session);
		this.ru = new RogueUtilities();
	}
	
	public Dungeon generate(UUID pcId) {
		if(pcId == null) {
			log.debug("passed null pcId; returning null");
			return null;
		}
		
		FloorServices fs = new FloorServices(session);
		PlayerCharacterDao pcDao = new PlayerCharacterDao(session);
		PlayerCharacter pc = pcDao.findById(pcId);
		Dungeon dungeon = new Dungeon();
		
		dungeon.setId(UUID.randomUUID());
		dungeon.setChallengeRating(genChallengeRating(pc.getLevel()));
		dungeon.setFloorCount(genFloorCount(dungeon.getChallengeRating()));
		dungeon.setTheme(genTheme());
		genBossMinibossFlags(dungeon);
		if(genNameDescriptionFlags(dungeon) == false) {
			return null;
		}
		
		dungeon.setFloorIds(fs.generate(dungeon));

		
		return dungeon;
	}
	
	public int genChallengeRating(int level) {
		int modifier = 1+2*level/5;
		modifier = modifier > 0 ? modifier : 1;
		modifier = modifier > 3 ? 3 : modifier;
		int challengeRating = level - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		challengeRating = challengeRating > 0 ? challengeRating : 0;
		return challengeRating;
	}
	
	public int genFloorCount(int challengeRating) {
		int modifier = 1+(challengeRating/4);
		modifier = modifier > 4 ? 4 : modifier;
		int base = 1+(challengeRating/8);
		int floorCount = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		floorCount = floorCount > 0 ? floorCount : 1;
		return floorCount;
	}
	
	private String genTheme() {
		String[] themes = {"Cave", "Castle", "Arcane"};
		return themes[ThreadLocalRandom.current().nextInt(themes.length)];
	}
	
	public void genBossMinibossFlags(Dungeon dungeon) {
		int modifier = dungeon.getChallengeRating() + ThreadLocalRandom.current().nextInt(11);
		
		if(modifier < 6)
			return;
		
		if(modifier > 15 && ThreadLocalRandom.current().nextInt(99)<14+modifier)
			dungeon.setBoss(true);
		
		dungeon.setMiniboss(ThreadLocalRandom.current().nextInt(99)<34+modifier
							  ? dungeon.getFloorCount() == 1 && dungeon.isBoss()
							     ? false
							     : true
							  : false);
	}
	
	public boolean genNameDescriptionFlags (Dungeon dungeon) {	
		List<String[]> components = ru.readFileToArrays(dungeon.getTheme(), ".rbt");
		
		if(components == null) {
			log.debug("failed to read theme file; returning null");
			return false;
		}
		
		StringBuilder name = new StringBuilder();
		StringBuilder description = new StringBuilder();
		
		int roll = ThreadLocalRandom.current().nextInt(11);
		if(roll < 6) {
			switch(roll) {
				case 0:
					dungeon.setPrefixMod("trap-10");
					name.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
					description.append(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)]);
					break;
				case 1:
					dungeon.setPrefixMod("trap-20");
					name.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
					description.append(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)]);
					break;
				case 2:
					dungeon.setPrefixMod("trap-30");
					name.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
					description.append(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)]);
					break;
				case 3:
					dungeon.setPrefixMod("loot-10");
					name.append(components.get(1)[ThreadLocalRandom.current().nextInt(components.get(1).length)]);
					description.append(components.get(7)[ThreadLocalRandom.current().nextInt(components.get(7).length)]);
					break;
				case 4:
					dungeon.setPrefixMod("loot-20");
					name.append(components.get(1)[ThreadLocalRandom.current().nextInt(components.get(1).length)]);
					description.append(components.get(7)[ThreadLocalRandom.current().nextInt(components.get(7).length)]);
					break;
				case 5:
					dungeon.setPrefixMod("loot-30");
					name.append(components.get(1)[ThreadLocalRandom.current().nextInt(components.get(1).length)]);
					description.append(components.get(7)[ThreadLocalRandom.current().nextInt(components.get(7).length)]);
					break;
			}
		}
		
		String adjective = components.get(2)[ThreadLocalRandom.current().nextInt(components.get(2).length)];
		String noun = components.get(3)[ThreadLocalRandom.current().nextInt(components.get(3).length)];	
		name.append(adjective).append(noun);
		description.append(String.format(components.get(8)[ThreadLocalRandom.current().nextInt(components.get(8).length)], noun.toLowerCase()));
		
		char c = description.charAt(1);
		String initial = c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'
				         ? "An"
				         : "A";
		description.insert(0, initial);
		
		roll = ThreadLocalRandom.current().nextInt(23);
		if(roll < 6) {
			switch(roll) {
				case 0:
					dungeon.setPostfixMod("health-10");
					name.append(components.get(4)[ThreadLocalRandom.current().nextInt(components.get(4).length)]);
					description.append(components.get(9)[ThreadLocalRandom.current().nextInt(components.get(9).length)]);
					break;
				case 1:
					dungeon.setPostfixMod("health-20");
					name.append(components.get(4)[ThreadLocalRandom.current().nextInt(components.get(4).length)]);
					description.append(components.get(9)[ThreadLocalRandom.current().nextInt(components.get(9).length)]);
					break;
				case 2:
					dungeon.setPostfixMod("health-30");
					name.append(components.get(4)[ThreadLocalRandom.current().nextInt(components.get(4).length)]);
					description.append(components.get(9)[ThreadLocalRandom.current().nextInt(components.get(9).length)]);
					break;
				case 3:
					dungeon.setPostfixMod("damage-10");
					name.append(components.get(5)[ThreadLocalRandom.current().nextInt(components.get(5).length)]);
					description.append(components.get(10)[ThreadLocalRandom.current().nextInt(components.get(10).length)]);
					break;
				case 4:
					dungeon.setPostfixMod("damage-20");
					name.append(components.get(5)[ThreadLocalRandom.current().nextInt(components.get(5).length)]);
					description.append(components.get(10)[ThreadLocalRandom.current().nextInt(components.get(10).length)]);
					break;
				case 5:
					dungeon.setPostfixMod("damage-30");
					name.append(components.get(5)[ThreadLocalRandom.current().nextInt(components.get(5).length)]);
					description.append(components.get(10)[ThreadLocalRandom.current().nextInt(components.get(10).length)]);
					break;
			}
		}
		
		dungeon.setName(name.toString());
		dungeon.setDescription(description.toString());
		
		return true;
	}
	

	
}
