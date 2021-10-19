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
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Item;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.FloorDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.MonsterDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.RoomDao;
import com.RogueBasic.data.TrapDao;
import com.RogueBasic.util.RogueUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class DungeonServices {
	private CqlSession session;
	private DungeonDao dao;
	private RogueUtilities ru;
	private static final Logger log = LogManager.getLogger(DungeonServices.class);
	
	public DungeonServices(CqlSession session) {
		super();
		this.session = session;
		this.dao = new DungeonDao(session);
		this.ru = new RogueUtilities();
	}
	
	//creates an unsaved, framed dungeon (with floors/rooms yet to be generated)
	public Dungeon generateShell(UUID pcId) {
		if(pcId == null) {
			log.debug("passed null pcId; returning null");
			return null;
		}
		
		PlayerCharacterDao pcDao = new PlayerCharacterDao(session);
		PlayerCharacter pc = pcDao.findById(pcId);
		Dungeon dungeon = new Dungeon();
		dungeon.setId(UUID.randomUUID());
		
		//Calls various private methods in this class to:
		//Calculate a challenge rating from a range based on PC level;
		//Calculate a floor count from a range based on challenge rating;
		//Select a random dungeon theme;
		log.trace("DungeonServices.generate() calling genChallengeRating()");
		dungeon.setChallengeRating(genChallengeRating(pc.getLevel()));
		log.trace("DungeonServices.generate() calling genFloorCount()");
		dungeon.setFloorCount(genFloorCount(dungeon.getChallengeRating()));
		log.trace("DungeonServices.generate() calling genTheme()");
		dungeon.setTheme(genTheme());
		log.trace("DungeonServices.generate() calling genBossMiniboss()");
		genBossMiniboss(dungeon);
		log.trace("DungeonServices.generate() calling genNameModsDescription()");
		if(genNameModsDescription(dungeon) == false) {
			return null;
		}
		dungeon.setReward((int)Math.round(100*(1+(0.1*(dungeon.getChallengeRating()-1)))*(1+(0.25*(dungeon.getFloorCount()-1)))*(dungeon.isMiniboss() ? 1.34 : 1)*(dungeon.isBoss() ? 1.67 : 1)));
		
		this.dao.save(dungeon);
		
		log.trace("DungeonServices.generate() returning UUID" );
		return dungeon;
	}
	
	//completes a framed dungeon by adding floors and rooms, and saves it to the database
	public Dungeon addFloors(Dungeon dungeon) {
		FloorServices fs = new FloorServices(session);
		
		//Calls FloorServices->RoomServices->Monster/Item/Equipment/TrapServices
		//to generate the contents of the dungeon, before saving it to our database
		//and returning the id
		log.trace("DungeonServices.addFloors() calling FloorServices.generate()");
		dungeon.setFloorIds(fs.generate(dungeon));
		log.trace("DungeonServices.addFloors() calling DungeonDao.save()");
		dao.save(dungeon);
		
		//log our dungeon and all of its enumerated objects
		if(log.isDebugEnabled()) {
			log.debug(dungeon.toString());
			FloorDao fdao = new FloorDao(session);
			RoomDao rdao = new RoomDao(session);
			MonsterDao mdao = new MonsterDao(session);
			ItemDao idao = new ItemDao(session);
			TrapDao tdao = new TrapDao(session);
			
			for(UUID id: dungeon.getFloorIds()) {
				Floor floor = fdao.findById(id);
				log.debug(floor.toString());
				for(UUID rid: floor.getRoomIds()) {
					Room room = rdao.findById(rid);
					log.debug(room.toString());
					if(room.getMonsterIds()!=null) {
						for(UUID u : room.getMonsterIds()) {
							log.debug(mdao.findById(u).toString());
						}
					}
//					if(room.getLoot()!=null) {
//						for(UUID u : room.getLoot()) {
//							log.debug(idao.findById(u).toString());
//						}
//					}
					if(room.getTrapId()!=null) {
						log.debug(tdao.findById(room.getTrapId()).toString());
					}
				}
			}
		}
		log.trace("DungeonServices.addFloors() returning UUID" );
		return dungeon;
	}
	
	public int genChallengeRating(int level) {
		//Calculating a pseudorandom challengeRating based on PC level, which should be 0 or greater
		int modifier = 1+2*level/5;
		modifier = modifier > 0 ? modifier : 1;
		modifier = modifier > 3 ? 3 : modifier;
		int challengeRating = level - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
		log.trace("DungeonServices.genChallengeRating() returning int");
		return challengeRating > 0 ? challengeRating :0;
	}
	
	public int genFloorCount(int challengeRating) {
		//Calculating a pseudorandom floorCount based on challengeRating, which should be between 1 and 6
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
	
	private String genTheme() {
		//Selects a pseudoranom theme from our ThemeList document
		log.trace("DungeonServices.genTheme calling RogueUtilities.readFileToList() for ThemeList.rbt");
		List<String> themes = ru.readFileToList("source/themes/themeList.rbt");
		log.trace("DungeonServices.genTheme returning String");
		return themes.get(ThreadLocalRandom.current().nextInt(themes.size()));
	}
	
	public void genBossMiniboss(Dungeon dungeon) {
		int modifier = dungeon.getChallengeRating() + ThreadLocalRandom.current().nextInt(12);
		
		//We only generate a boss if modifier > 15 to avoid boss encounters at the lowest levels
		if(modifier > 15 && ThreadLocalRandom.current().nextInt(99)<14+modifier)
			dungeon.setBoss(true);
		
		//If we have set the boss flag and the dungeon only has one floor, the boss takes precedent
		//and miniboss is not flagged
		dungeon.setMiniboss(ThreadLocalRandom.current().nextInt(99)<26+(2*modifier)
							  ? dungeon.getFloorCount() == 1 && dungeon.isBoss()
							     ? false
							     : true
							  : false);
	}
	
	public boolean genNameModsDescription(Dungeon dungeon) {	
		//Reads our theme document for the arrays of potential name and description components
		//which our name and description will be pseudorandomly assembled from
		log.trace("DungeonServices.genNameModsDescription() calling RogueUtilities.readFileToArrays()");
		List<String[]> components = ru.readFileToArrays("source/themes/"+dungeon.getTheme() + ".rbt");
		
		if(components == null) {
			log.debug("failed to read theme file; returning null");
			return false;
		}
		
		StringBuffer name = new StringBuffer();
		StringBuffer description = new StringBuffer();
		
		//the first roll determines the prefix cluster (name, modifier, description)
		//which are applied to the dungeon, if any
		int roll = ThreadLocalRandom.current().nextInt(11);
			switch(roll) {
				case 0:
					dungeon.setPrefixMod("traps-10");
					name.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
					description.append(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)]);
					break;
				case 1:
					dungeon.setPrefixMod("traps-20");
					name.append(components.get(0)[ThreadLocalRandom.current().nextInt(components.get(0).length)]);
					description.append(components.get(6)[ThreadLocalRandom.current().nextInt(components.get(6).length)]);
					break;
				case 2:
					dungeon.setPrefixMod("traps-30");
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
				default:
					break;
			}
		
		//An adjective and noun are selected for the name, as well as a corresponding
		//description section which has the noun inserted into a placeholder
		name.append(components.get(2)[ThreadLocalRandom.current().nextInt(components.get(2).length)]);
		String noun = components.get(3)[ThreadLocalRandom.current().nextInt(components.get(3).length)];	
		name.append(noun);
		description.append(String.format(components.get(8)[ThreadLocalRandom.current().nextInt(components.get(8).length)], noun.toLowerCase()));
		
		
		//If the first letter of the first word of our description is a vowel,
		//insert "An" to start the description; otherwise, "A"
		char c = description.charAt(1);
		description.insert(0, c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'
				         ? "An"
				         : "A");
		
		//the second roll determines the postfix cluster (name, modifier, description)
		//which are applied to the dungeon, if any
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
		
		log.trace("DungeonServices.genNameModsDescription() returning boolean");
		return true;
	}
	

	
}
