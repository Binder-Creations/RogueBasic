package com.RogueBasic.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.beans.Monster;
import com.RogueBasic.enums.DungeonTheme;
import com.RogueBasic.enums.MonsterModifier;
import com.RogueBasic.enums.MonsterSpecies;
import com.RogueBasic.enums.MonsterType;
import com.RogueBasic.enums.Position;
import com.RogueBasic.util.RogueUtilities;

public class MonsterServices {
	
	public MonsterServices() {}
	
	public static Set<Monster> generate(int challengeRating, String theme, int level, boolean boss, boolean miniboss){
		Set<Monster> monsters = new HashSet<>();
		int encounterValue = Math.round((6f+(float)challengeRating*3f)*(0.9f + (float)level/10f)*(boss ? 1.2f : 1f)*(miniboss ? 1.1f : 1f));
		int encounterBase = encounterValue;
		int monsterMinimum = Math.round((float)encounterValue/6f);
		List<Position> frontPositions = Position.allFront();
		List<Position> backPositions = Position.allBack();
		boolean bossGenerated = false;
		boolean minibossGenerated = false;
		
		while (encounterValue >= monsterMinimum) {
			Monster monster = new Monster();
			MonsterSpecies species = DungeonTheme.fromString(theme).randomSpecies();
			MonsterModifier modifier = MonsterModifier.randomModifier();
			String name = modifier.modifier();
			MonsterType type = null;
			Position position = null;
			monster.setSpecies(species.species());
			
			if(boss && !bossGenerated) {
				monster.setBoss(true);
				bossGenerated = true;
				type = species.bossType();
				position = type.defaultPosition();
			}
			
			if(miniboss && !minibossGenerated) {
				monster.setMiniboss(true);
				minibossGenerated = true;
				monster.setType(type.type());
				position = type.defaultPosition();
			}
			
			if(!monster.isBoss() && !monster.isMiniboss()) {
				if(frontPositions.size() == 0) {
					type = MonsterType.randomBackType();
					position = backPositions.get(ThreadLocalRandom.current().nextInt(backPositions.size()));
				} else if (backPositions.size() == 0) {
					type = MonsterType.randomFrontType();
					position = frontPositions.get(ThreadLocalRandom.current().nextInt(frontPositions.size()));
				} else {
					type = MonsterType.randomType();
					if(type.defaultPosition().front()) {
						position = frontPositions.get(ThreadLocalRandom.current().nextInt(frontPositions.size()));
					} else {
						position = backPositions.get(ThreadLocalRandom.current().nextInt(backPositions.size()));
					}
				}
			}
			if(position.front()) {
				frontPositions.remove(position);
			} else {
				backPositions.remove(position);
			}
			monster.setType(type.type());
			monster.setPosition(position.position());
			monster.setVariant(monster.isBoss() ? 1 : ThreadLocalRandom.current().nextInt(1,3));
			int monsterValue = monster.isBoss()
					? ThreadLocalRandom.current().nextInt(monsterMinimum*3, encounterBase+1)
					: monster.isMiniboss()
						? ThreadLocalRandom.current().nextInt(monsterMinimum*2, Math.round((float)encounterBase*1.5f)+1)
						: ThreadLocalRandom.current().nextInt(monsterMinimum, (Math.round((float)encounterBase/3f)+1));
			int monsterLevel = monster.isBoss()
					? Math.round((float)monsterValue/2f)
					: monster.isMiniboss() 
						? Math.round((float)monsterValue/1.5f)
						: monsterValue;
			monster.setLevel(monsterLevel >= 1 ? monsterLevel -1 : monsterLevel);
			monster.setCurrentHealth(-1);
			monster.setHealth(monster.getHealth()*monsterValue);
			monster.setPower(monster.getPower()*monsterValue);
			monster.setArmor(monster.getArmor()*monsterValue);
			monster.setDodgeRating(monster.getDodgeRating()*monsterValue);
			monster.setCritRating(monster.getCritRating()*monsterValue);
			
			modifier.adjustStats(type.adjustStats(species.adjustStats(monster)));
			
			encounterValue -= monsterValue;
			
			if(monster.isBoss()) {
				name += species.bossName();
			} else if (monster.isMiniboss()) {
				name += species.minibossName();
			} else {
				name += species.nameByType(type);
			}
			monster.setName(name);
			genAbilities(monster);
			monsters.add(monster);
		}
		
		return monsters;
	}
	
	private static void genAbilities(Monster monster) {
		Set<Ability> abilities = new HashSet<>();
		List<Ability> abilityPool = RogueUtilities.getMonsterAbilities(monster.getType());
		for(int i = 0; i < (monster.isBoss() ? 3 : monster.isMiniboss() ? 2 : 1); i++) {
			Ability ability = abilityPool.get(ThreadLocalRandom.current().nextInt(abilityPool.size()));
			abilityPool.remove(ability);
			abilities.add(ability);
		}
		monster.setAbilities(abilities);
	}
}
