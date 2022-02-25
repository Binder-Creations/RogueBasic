package com.RogueBasic.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.enums.DungeonModifier;
import com.RogueBasic.enums.DungeonTheme;
import com.RogueBasic.enums.EquipmentType;

@Component
public class DungeonServices {
	@Autowired
	private DungeonDao dungeonDao;
	@Autowired
	private PlayerCharacterDao playerCharacterDao;
	@Autowired
	private ItemServices itemServices;
	@Autowired
	private FloorServices floorServices;
	private static final Logger log = LogManager.getLogger(DungeonServices.class);
	
	public DungeonServices() {}
	
	//creates an unsaved, framed dungeon (with floors/rooms yet to be generated)
	public Dungeon generateShell(UUID pcId) {
		if(pcId == null) {
			log.warn("passed null pcId; returning null");
			return null;
		}
		
		PlayerCharacter pc = playerCharacterDao.findById(pcId);
		Dungeon dungeon = new Dungeon();
		DungeonTheme theme = DungeonTheme.randomTheme();
		dungeon.setTheme(theme.theme());
		dungeon.setId(UUID.randomUUID());
		dungeon.setChallengeRating(genChallengeRating(pc.getLevel()));
		dungeon.setFloorCount(genFloorCount(dungeon.getChallengeRating()));		
		genBossMiniboss(dungeon);
		genNameModsDescription(dungeon, theme);
		dungeon.setReward((int)Math.round(100*(1+(0.1*(dungeon.getChallengeRating()-1)))*(1+(0.25*(dungeon.getFloorCount()-1)))*(dungeon.isMiniboss() ? 1.34 : 1)*(dungeon.isBoss() ? 1.67 : 1)));
		Set<Item> rewardSet = new HashSet<>();
		for(int i = 0; i < 3; i++) {
			rewardSet.add(itemServices.genEquipment(EquipmentType.fromStrings(pc.getEquipTypes()), dungeon.getChallengeRating() + dungeon.getFloorCount()*2));
		}
		dungeon.setRewardSet(rewardSet);
		dungeonDao.save(dungeon);
		return dungeon;
	}
	
	//completes a framed dungeon by adding floors and rooms, and saves it to the database
	public Dungeon addFloors(Dungeon dungeon) {
		dungeon.setFloors(floorServices.generate(dungeon));
		dungeonDao.save(dungeon);
		return dungeon;
	}
	
	public int genChallengeRating(int level) {
		//Calculates a pseudorandom challengeRating based on PC level, which should be 0 or greater
		int modifier = 1+2*level/5;
		modifier = modifier > 0 ? modifier : 1;
		modifier = modifier > 3 ? 3 : modifier;
		int challengeRating = level - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		log.trace("DungeonServices.genChallengeRating() returning int");
		return challengeRating > 0 ? challengeRating :1;
	}
	
	public int genFloorCount(int challengeRating) {
		//Calculates a pseudorandom floorCount based on challengeRating, which should be between 1 and 6
		int modifier = 1+(challengeRating/4);
		modifier = modifier > 4 ? 4 : modifier;
		int base = 1+(challengeRating/8);
		int floorCount = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		log.trace("DungeonServices.genFloorCount() returning int");
		return floorCount > 0
				? floorCount < 7
					? floorCount
						: 6
				: 1;
	}
	
	public void genBossMiniboss(Dungeon dungeon) {
		int modifier = dungeon.getChallengeRating() + ThreadLocalRandom.current().nextInt(12);
		
		//Generates a boss if modifier > 15 to avoid boss encounters at the lowest levels
		if(modifier > 15 && ThreadLocalRandom.current().nextInt(99)<14+modifier)
			dungeon.setBoss(true);
		
		//If the boss flag has been set and the dungeon only has one floor, the boss takes precedent
		//and miniboss is not flagged
		dungeon.setMiniboss(ThreadLocalRandom.current().nextInt(99)<26+(2*modifier)
							  ? dungeon.getFloorCount() == 1 && dungeon.isBoss()
							     ? false
							     : true
							  : false);
	}
	
	public void genNameModsDescription(Dungeon dungeon, DungeonTheme theme) {	
		StringBuffer name = new StringBuffer();
		StringBuffer description = new StringBuffer();
		DungeonModifier prefix = DungeonModifier.randomPrefix();
		DungeonModifier postfix = DungeonModifier.randomPostfix();
		String noun = theme.randomNoun();

		name.append(prefix.nameModifier(theme));
		name.append(theme.randomAdjective());
		name.append(noun);
		name.append(postfix.nameModifier(theme));
		
		description.append(prefix.descriptionModifier(theme));
		description.append(String.format(theme.randomBaseDescription(), noun.toLowerCase()));
		char c = description.charAt(1);
		description.insert(0, c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'
				         ? "An"
				         : "A");
		description.append(postfix.descriptionModifier(theme));
		
		dungeon.setName(name.toString());
		dungeon.setDescription(description.toString());
	}
}
