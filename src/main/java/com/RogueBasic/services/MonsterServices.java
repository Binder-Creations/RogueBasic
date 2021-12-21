package com.RogueBasic.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Buff;
import com.RogueBasic.beans.Flags;
import com.RogueBasic.beans.Monster;

public class MonsterServices {
	private static final Logger log = LogManager.getLogger(MonsterServices.class);
	
	public MonsterServices() {
		super();
	}
	
	public static Set<Monster> generate(int challengeRating, String theme, int level, boolean boss, boolean miniboss){
		Set<Monster> monsters = new HashSet<>();
		int encounterValue = Math.round((6f+(float)challengeRating*3f)*(0.9f + (float)level/10f)*(boss ? 1.2f : 1f)*(miniboss ? 1.1f : 1f));
		int encounterBase = encounterValue;
		int monsterMinimum = Math.round((float)encounterValue/6f);
		List<String> backPositions = new ArrayList<>();
		backPositions.add("backLeft");
		backPositions.add("backCenter");
		backPositions.add("backRight");
		List<String> frontPositions = new ArrayList<>();
		frontPositions.add("frontLeft");
		frontPositions.add("frontCenter");
		frontPositions.add("frontRight");
		boolean bossGenerated = false;
		boolean minibossGenerated = false;
		
		while (encounterValue >= monsterMinimum) {
			Monster monster = new Monster();
			monster.setId(UUID.randomUUID());
			int monsterValue = 0;
			
			if(boss && !bossGenerated) {
				monster.setBoss(true);
				bossGenerated = true;
			}
			if(miniboss && !minibossGenerated) {
				monster.setMiniboss(true);
				minibossGenerated = true;
			}
			
			monsterValue = monster.isBoss()
					? ThreadLocalRandom.current().nextInt(monsterMinimum*3, encounterBase+1)
					: monster.isMiniboss()
						? ThreadLocalRandom.current().nextInt(monsterMinimum*2, Math.round((float)encounterBase*1.5f)+1)
						: ThreadLocalRandom.current().nextInt(monsterMinimum, (Math.round((float)encounterBase/3f)+1));
			
			encounterValue -= monsterValue;
			
			monster.setVariant(monster.isBoss() ? 1 : ThreadLocalRandom.current().nextInt(1,3));
			genSpecies(theme, monster);
			genTypePosition(monster, backPositions, frontPositions);
			String modifier = genStatsModifier(monster, monsterValue);
			genName(monster, modifier);
			genAbilities(monster);
			
			monsters.add(monster);
		}
		
		return monsters;
	}

	private static void genSpecies(String theme, Monster monster) {
		switch(theme) {
		case "Cave":
			monster.setSpecies(ThreadLocalRandom.current().nextBoolean()
				? "goblin"
				: "ogre");
			break;
		case "Castle":
			monster.setSpecies(ThreadLocalRandom.current().nextBoolean()
				? "human1"
				: "human2");
			break;
		case "Arcane":
			monster.setSpecies(ThreadLocalRandom.current().nextBoolean()
				? "skeleton"
				: "golem");
			break;
		}	
	}

	private static void genTypePosition(Monster monster, List<String> backPositions, List<String> frontPositions) {
		if(monster.isBoss()) {
			switch(monster.getSpecies()) {
				case "goblin":
					monster.setType("archer");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
				case "ogre":
					monster.setType("warrior");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
				case "human1":
					monster.setType("wizard");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
				case "human2":
					monster.setType("rogue");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
				case "skeleton":
					monster.setType("wizard");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
				case "golem":
					monster.setType("warrior");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
			}
			return;
		}
		if(monster.isMiniboss()) {
			switch(monster.getSpecies()) {
				case "goblin":
					monster.setType("wizard");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
				case "ogre":
					monster.setType("rogue");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
				case "human1":
					monster.setType("warrior");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
				case "human2":
					monster.setType("wizard");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
				case "skeleton":
					monster.setType("warrior");
					monster.setPosition("frontCenter");
					frontPositions.remove("frontCenter");
					break;
				case "golem":
					monster.setType("archer");
					monster.setPosition("backCenter");
					backPositions.remove("backCenter");
					break;
			}
			return;
		}
		
		List<String> types = new ArrayList<>();
		if(backPositions.size() > 0) {
			types.add("archer");
			types.add("wizard");
		}
		if(frontPositions.size() > 0) {
			types.add("rogue");
			types.add("warrior");
		}
		
		monster.setType(types.get(ThreadLocalRandom.current().nextInt(types.size())));
		switch(monster.getType()) {
		case "archer":
			monster.setPosition(backPositions.get(ThreadLocalRandom.current().nextInt(backPositions.size())));
			backPositions.remove(monster.getPosition());
			break;
		case "wizard":
			monster.setPosition(backPositions.get(ThreadLocalRandom.current().nextInt(backPositions.size())));
			backPositions.remove(monster.getPosition());
			break;
		case "rogue":
			monster.setPosition(frontPositions.get(ThreadLocalRandom.current().nextInt(frontPositions.size())));
			frontPositions.remove(monster.getPosition());
			break;
		case "warrior":
			monster.setPosition(frontPositions.get(ThreadLocalRandom.current().nextInt(frontPositions.size())));
			frontPositions.remove(monster.getPosition());
			break;
		}
	}
	
	private static String genStatsModifier(Monster monster, int monsterValue) {
		String modifier = null;
		
		int monsterLevel = monster.isBoss()
				? Math.round((float)monsterValue/2f)
				: monster.isMiniboss() 
					? Math.round((float)monsterValue/1.5f)
					: monsterValue;
		monster.setLevel((monsterLevel - 1) >= 0 ? monsterLevel -1 : monsterLevel);
		
		float healthMod = 1f;
		float powerMod = 1f;
		float armorMod = 1f;
		float dodgeMod = 1f;
		float critMod = 1f;
		float baseHealth = monster.isBoss() ? 14f : monster.isMiniboss() ? 12f : 10f;
		float basePower = 10f;
		float baseArmor = 20f;
		float baseDodge = 8f;
		float baseCrit = 8f;
		
		int roll = ThreadLocalRandom.current().nextInt(40);
		switch(roll) {
			case 0:
				modifier = "Robust";
				healthMod = healthMod*1.35f;
				break;
			case 1:
				modifier = "Potent";
				powerMod = powerMod*1.35f;
				break;
			case 2:
				modifier = "Armored";
				armorMod = armorMod*1.35f;
				break;
			case 3:
				modifier = "Nimble";
				dodgeMod = dodgeMod*1.35f;
				break;
			case 4:
				modifier = "Keen";
				critMod = critMod*1.35f;
				break;
			case 5:
				modifier = "Hulking";
				healthMod = healthMod*1.2f;
				powerMod = powerMod*1.2f;
				break;
			case 6:
				modifier = "Resillent";
				healthMod = healthMod*1.2f;
				armorMod = armorMod*1.2f;
				break;
			case 7:
				modifier = "Athletic";
				healthMod = healthMod*1.2f;
				dodgeMod = dodgeMod*1.2f;
				break;
			case 8:
				modifier = "Martial";
				healthMod = healthMod*1.2f;
				critMod = critMod*1.2f;
				break;
			case 9:
				modifier = "Metallic";
				powerMod = powerMod*1.2f;
				armorMod = armorMod*1.2f;
				break;
			case 10:
				modifier = "Alacritous";
				powerMod = powerMod*1.2f;
				dodgeMod = dodgeMod*1.2f;
				break;
			case 11:
				modifier = "Deadly";
				powerMod = powerMod*1.2f;
				critMod = critMod*1.2f;
				break;
			case 12:
				modifier = "Defensive";
				armorMod = armorMod*1.2f;
				dodgeMod = dodgeMod*1.2f;
				break;
			case 13:
				modifier = "Honed";
				armorMod = armorMod*1.2f;
				critMod = critMod*1.2f;
				break;
			case 14:
				modifier = "Agile";
				dodgeMod = dodgeMod*1.2f;
				critMod = critMod*1.2f;
				break;
			default:
				break;
		}
		
		switch(monster.getSpecies()) {
			case "goblin":
				healthMod = healthMod*0.7f;
				powerMod = powerMod*1.2f;
				armorMod = armorMod*0.8f;
				dodgeMod = dodgeMod*1.4f;
				critMod = critMod*1.2f;
				break;
			case "ogre":
				healthMod = healthMod*1.5f;
				powerMod = powerMod*1.3f;
				dodgeMod = dodgeMod*0.4f;
				critMod = critMod*0.7f;
				break;
			case "human1":
				break;
			case "human2":
				break;
			case "skeleton":
				healthMod = healthMod*0.8f;
				powerMod = powerMod*1.4f;
				armorMod = armorMod*0.7f;
				critMod = critMod*1.4f;
				break;
			case "golem":
				healthMod = healthMod*1.3f;
				powerMod = powerMod*0.7f;
				armorMod = armorMod*1.5f;
				critMod = critMod*0.6f;
				break;
		}
		
		switch(monster.getType()) {
			case "wizard":
				healthMod = healthMod*0.6f;
				powerMod = powerMod*2.2f;
				armorMod = armorMod*0.6f;
				break;
			case "rogue":
				healthMod = healthMod*0.7f;
				armorMod = armorMod*0.7f;
				critMod = critMod*1.3f;
				dodgeMod = dodgeMod*1.7f;
				break;
			case "archer":
				healthMod = healthMod*1.2f;
				armorMod = armorMod*1.1f;
				powerMod = powerMod*1.1f;
				break;
			case "warrior":
				healthMod = healthMod*1.5f;
				armorMod = armorMod*1.5f;
				critMod = critMod*0.8f;
				dodgeMod = dodgeMod*0.8f;
				powerMod = powerMod*0.8f;	
				break;
		}
		
		monster.setHealth(Math.round(monsterValue*baseHealth*healthMod));
		monster.setCurrentHealth(-1);
		monster.setPower(Math.round(monsterValue*basePower*powerMod));
		monster.setArmor(Math.round(monsterValue*baseArmor*armorMod));
		monster.setDodgeRating(Math.round(monsterValue*baseDodge*dodgeMod));
		monster.setCritRating(Math.round(monsterValue*baseCrit*critMod));
		
		return modifier;
	}
	
	private static void genName(Monster monster, String modifier) {
		String type = null;
		
		if(monster.isBoss()) {
			switch(monster.getSpecies()) {
				case "goblin":
					type = "Bigboss";
					break;
				case "golem":
					type = "Multi-Defense Platform";
					break;
				case "human1":
					type = "Ascended One";
					break;
				case "human2":
					type = "True Master";
					break;
				case "ogre":
					type = "Crystal-Fused";
					break;
				case "skeleton":
					type = "Hellflame Scion";
					break;
			}			
		}
		if (monster.isMiniboss()) {
			switch(monster.getSpecies()) {
				case "goblin":
					type = "Skulldancer";
					break;
				case "golem":
					type = "Retrofitted Borer";
					break;
				case "human1":
					type = "Royal Guard";
					break;
				case "human2":
					type = "Archmage";
					break;
				case "ogre":
					type = "Shadow-Stolen";
					break;
				case "skeleton":
					type = "Immortal";
					break;
			}	
		}
		if(!monster.isBoss() && !monster.isMiniboss()) {
			List<String> types = new ArrayList<>();
			switch(monster.getSpecies()) {
				case "goblin":
					types.addAll(Arrays.asList("Sharpeye", "Spelltinger", "Dogshanker", "Tallgob"));
					break;
				case "golem":
					types.addAll(Arrays.asList("Quarrier", "Atmokineter", "Dissembler", "Compactor"));
					break;
				case "human1":
					types.addAll(Arrays.asList("Bowman", "Stoic", "Knave", "Knight"));
					break;
				case "human2":
					types.addAll(Arrays.asList("Ranger", "Mage", "Monk", "Paladin"));
					break;
				case "ogre":
					types.addAll(Arrays.asList("Boulderer", "Crysogre", "Reaver", "Ogre Heavy"));
					break;
				case "skeleton":
					types.addAll(Arrays.asList("Stalker", "Necromancer", "Deathdealer", "Blackguard"));
					break;
			}
			switch(monster.getType()) {
				case "archer":
					type = types.get(0);
					break;
				case "wizard":
					type = types.get(1);
					break;
				case "rogue":
					type = types.get(2);
					break;
				case "warrior":
					type = types.get(3);
					break;
				}
		}
		monster.setName((modifier != null ? modifier + " " : "") + type);
	}
	
	private static void genAbilities(Monster monster) {
		Set<Ability> abilities = new HashSet<>();
		List<Character> abilityChars = new ArrayList<>();
		abilityChars.addAll(Arrays.asList('A','B','C','D','E','F'));
		
		for(int i = 0; i < (monster.isBoss() ? 3 : monster.isMiniboss() ? 2 : 1); i++) {
			Character abilityChar = abilityChars.get(ThreadLocalRandom.current().nextInt(abilityChars.size()));
			abilityChars.remove(abilityChar);
			
			switch(monster.getType()){
				case "archer":
					switch(abilityChar) {
						case 'A':
							abilities.add(new Ability(0, "Precise Shot", 0, 2.6f, 10, 1, "pc", "Attack", null, "shoots you with unerring accuracy.", new Flags("hit")));
							break;
						case 'B':
							abilities.add(new Ability(0, "Envenomed Strike", 0, 0.8f, 0, 1, "pc", "Debuff", null, "inflicts you with a long-lasting poison.", null, null, null, new Buff("poison", -1, "poison")));
							break;
						case 'C':
							abilities.add(new Ability(0, "Take Aim", 0, 3f, 0, 1, "self", "Buff", null, "locks you in their sights.", new Flags("hit", -1), new Buff("takeAim", -1, "critRating")));
							break;
						case 'D':
							abilities.add(new Ability(0, "Triple Shot", 0, 0.9f, 30, 3, "pc", "Attack", null, "fires three quick shots at you."));
							break;
						case 'E':
							abilities.add(new Ability(0, "Soul Sight", 0, 3f, 20, 1, "pc", "Attack", null, "launches an ephemeral attack upon your soul directly.", new Flags("attackEnergy")));
							break;
						case 'F':
							abilities.add(new Ability(0, "Pin Down", 0, 5f, 0, 1, "pc", "Debuff", null, "shoots at your feet, pinning you to the ground.", null, null, null, new Buff("pinDown", 3, new String[] {"dodgeRating", "critRating"})));
							break;
					}
					break;
				case "wizard":
					switch(abilityChar) {
						case 'A':
							abilities.add(new Ability(0, "Gather Power", 0, 15f, 0, 1, "self", "Buff", null, "gathers magical energy in preparation for a powerful spell.", new Buff("gatherPower", 2, "power")));
							break;
						case 'B':
							abilities.add(new Ability(0, "Fireball", 0, 1.8f, 0, 1, "pc", "Debuff", null, "throws a ball of sticky, long-burning flame at you.", null, null, null, new Buff("fireball", 3, "burn")));
							break;
						case 'C':
							abilities.add(new Ability(0, "Knit Together", 0, 4f, 15, 1, "allMonsters", "Heal", null, "releases a wave of rapid-healing magic."));
							break;
						case 'D':
							abilities.add(new Ability(0, "Regeneration", 0, 1.2f, 0, 1, "allMonsters", "Buff", null, "wraps their allies in long-lasting healing magic.", new Buff("regeneration", 4, "regenerate")));
							break;
						case 'E':
							abilities.add(new Ability(0, "Arcane Barrage", 0, 1.2f, 25, 3, "pc", "Attack", null, "conjures three unavoidable missles to magically seek you out.", new Flags("magic")));
							break;
						case 'F':
							abilities.add(new Ability(0, "Deep Chill", 0, 2f, 0, 1, "pc", "Debuff", null, "blasts you with bone-penetrating cold.", null, null, null, new Buff("deepChill", 4, new String[] {"strength", "dexterity"})));
							break;
					}
					break;
				case "rogue":
					switch(abilityChar) {
						case 'A':
							abilities.add(new Ability(0, "Vital Strike", 0, 1.2f, 10, 1, "pc", "Attack", null, "launches a vicious attack seeking your most vulnerable opening.", new Flags("crit")));
							break;
						case 'B':
							abilities.add(new Ability(0, "Hasten", 0, 5f, 0, 1, "self", "Buff", null, "concentrates, and their movements speed up.", new Buff("hasten", 4, new String[] {"dodgeRating", "critRating"})));
							break;
						case 'C':
							abilities.add(new Ability(0, "Seamseek", 0, 1.5f, 15, 1, "pc", "Attack", null, "bypasses your armor with a carefully-placed attack.", new Flags("fullPen")));
							break;
						case 'D':
							abilities.add(new Ability(0, "Lacerate", 0, 0.9f, 0, 1, "pc", "Debuff", null, "opens your artery, causing your to bleed profusely.", null, null, null, new Buff("lacerate", "bleed")));
							break;
						case 'E':
							abilities.add(new Ability(0, "Flurry", 0, 0.7f, 25, 4, "pc", "Attack", null, "attacks you four times in a blur of motion."));
							break;
						case 'F':
							abilities.add(new Ability(0, "Perfect Dodge", 0, 1f, 0, 1, "self", "Buff", null, "readies themself to dodge your next attack.", new Flags("dodge", 2)));
							break;
					}
					break;
				case "warrior":
					switch(abilityChar) {
						case 'A':
							abilities.add(new Ability(0, "Fortify", 0, 8f, 0, 1, "self", "Buff", null, "enhances their defenses.", new Buff("fortify", -1, "armor")));
							break;
						case 'B':
							abilities.add(new Ability(0, "Rallying Cry", 0, 4f, 0, 1, "allMonsters", "Buff", null, "bolsters the morale of their allies.", new Buff("rallyingCry", 4, "power")));
							break;
						case 'C':
							abilities.add(new Ability(0, "Sunder", 0, 6f, 0, 1, "pc", "Debuff", null, "pries open your armor, making you vulnerable to further attacks.", null, null, null, new Buff("sunder", 4, "armor")));
							break;
						case 'D':
							abilities.add(new Ability(0, "Dazing Blow", 0, 7f, 20, 1, "pc", "Debuff", null, "disorients you with a heavy, blunt strike.", null, null, null, new Buff("dazingBlow", 3, "power")));
							break;
						case 'E':
							abilities.add(new Ability(0, "Inner Reserve", 0, 6f, 0, 1, "self", "Heal", null, "draws deeply from their inner resillence to shrug off wounds."));
							break;
						case 'F':
							abilities.add(new Ability(0, "Power Strike", 0, 3f, 50, 1, "pc", "Attack", null, "makes a shatteringly powerful attack."));
							break;
					}
					break;
			}
		}
		
		monster.setAbilities(abilities);
	}
	
//	public Set<UUID> generate(Dungeon dungeon, int level, boolean boss, boolean miniboss){
//		Set<UUID> ids = new HashSet<>();
//		List<Monster> minibossPool = null;
//		List<Monster> bossPool = null;
//		List<Monster> monsterPool;
//		List<Monster> encounter;
//		
//		log.trace("MonsterServices.generate() calling genMV()");
//		int monsterValue = genMV(dungeon.getChallengeRating(), level);	
//		if(boss) {
//			log.trace("MonsterServices.generate() calling genMinibossPool()");
//			minibossPool = genMinibossPool(monsterValue, dungeon.getTheme());
//		}
//		if(miniboss) { 
//			log.trace("MonsterServices.generate() calling genBossPool()");
//			bossPool = genBossPool(monsterValue, dungeon.getTheme());
//		}
//		log.trace("MonsterServices.generate() calling genMonsterPool()");
//		monsterPool = genMonsterPool(monsterValue, dungeon.getTheme());
//		log.trace("MonsterServices.generate() calling genEncounter()");
//		encounter = genEncounter(monsterValue, minibossPool, bossPool, monsterPool);
//		encounter.forEach((m)->ids.add(m.getId()));
//		
//		log.trace("MonsterServices.generate() returning Set<UUID>");
//		return ids.size() > 0 ? ids : null;
//	}
//
//	private int genMV(int challengeRating, int level) {
//		int modifier = 2+(challengeRating/2)+(level);
//		int base = 3*challengeRating;
//		int monsterValue = base - modifier + ThreadLocalRandom.current().nextInt(3*modifier);
//		log.trace("MonsterServices.genMV() returning int");
//		return monsterValue > 2 ? monsterValue : 2;
//	}
//	
//	private List<Monster> genMinibossPool(int monsterValue, String theme){
//		int lowerBound = monsterValue > 60 ? 30 : monsterValue/2;
//		int upperBound = monsterValue;
//		log.trace("MonsterServices.genMinibossPool calling MonsterDao.getAll()");
//		List<Monster> pool = dao.getAll()
//			   .stream()
//			   .filter((m)->m.isMiniboss() && m.getTheme().equals(theme) && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
//			   .collect(Collectors.toList());
//		log.trace("MonsterServices.genMinibossPool() returning List<Monster>");
//		return pool.size() != 0
//				? pool
//				: dao.getAll()
//				   .stream()
//				   .filter((m)->m.isMiniboss() && m.getTheme().equals(theme))
//				   .collect(Collectors.toList());
//	}
//	
//	private List<Monster> genBossPool(int monsterValue, String theme){
//		int lowerBound = monsterValue > 60 ? 45 : (3*monsterValue)/4;
//		int upperBound = monsterValue;
//		log.trace("MonsterServices.genBossPool() calling MonsterDao.getAll()");
//		List<Monster> pool = dao.getAll()
//			   .stream()
//			   .filter((m)->m.isBoss() && m.getTheme().equals(theme) && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
//			   .collect(Collectors.toList());
//		log.trace("MonsterServices.genBossPool() returning List<Monster>");
//		return pool.size() != 0
//				? pool
//				: dao.getAll()
//				   .stream()
//				   .filter((m)->m.isBoss() && m.getTheme().equals(theme))
//				   .collect(Collectors.toList());	
//	}
//	
//	private List<Monster> genMonsterPool(int monsterValue, String theme){
//		int lowerBound = monsterValue > 60 ? 10 : monsterValue/6;
//		int upperBound = monsterValue;
//		log.trace("MonsterServices.genMonsterPool() calling MonsterDao.getAll()");
//		List<Monster> pool = new ArrayList<>();
//			   dao.getAll()
//			   .stream()
//			   .filter((m)->!m.isBoss() && !m.isMiniboss() && m.getTheme().equals(theme) && m.getLevel()>=lowerBound && m.getLevel() <= upperBound)
//			   .collect(Collectors.toList());
//		log.trace("MonsterServices.genMonsterPool() returning List<Monster>");
//		return pool.size() != 0
//				? pool
//				: dao.getAll()
//				   .stream()
//				   .filter((m)->!m.isBoss() && !m.isMiniboss() && m.getTheme().equals(theme))
//				   .collect(Collectors.toList());	
//	}
//	
//	private List<Monster> genEncounter(int monsterValue, List<Monster> minibossPool, List<Monster> bossPool,
//			List<Monster> monsterPool) {
//		List<Monster> encounter = new ArrayList<>();
//		int smallestLevel = monsterValue;
//		for(Monster m: monsterPool) {
//			if(m.getLevel()<smallestLevel)
//				smallestLevel = m.getLevel();
//		}
//		if (minibossPool != null) {
//			Monster miniboss = minibossPool.get(ThreadLocalRandom.current().nextInt(minibossPool.size()));
//			encounter.add(miniboss);
//			monsterValue -= miniboss.getLevel();
//		}
//		
//		if (bossPool != null) {
//			Monster boss = bossPool.get(ThreadLocalRandom.current().nextInt(bossPool.size()));
//			encounter.add(boss);
//			monsterValue -= boss.getLevel();
//		}
//		
//		while(monsterValue >= smallestLevel && encounter.size()<4) {
//			List<Monster> newPool = new ArrayList<>();
//			for(Monster m: monsterPool) {
//				if(m.getLevel()<=monsterValue)
//					newPool.add(m);
//			}
//			monsterPool = newPool;
//			Monster selection = monsterPool.get(ThreadLocalRandom.current().nextInt(monsterPool.size()));
//			encounter.add(selection);
//			monsterValue -= selection.getLevel();
//		}
//		
//		log.trace("MonsterServices.genEncounter() returning List<Monster>");
//		return encounter;
//	}
}
