package com.RogueBasic.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.Monster;
import com.RogueBasic.enums.DungeonTheme;
import com.RogueBasic.enums.MonsterSpecies;
import com.RogueBasic.enums.MonsterType;
import com.RogueBasic.enums.Position;

public class MonsterServicesTest {
	@Test
	public void generateTest() {
		for(DungeonTheme theme: DungeonTheme.values()) {
			Set<Monster> monsterSetNoBosses = MonsterServices.generate(2, theme.getTheme(), 2, false, false);
			assertFalse(monsterSetNoBosses.stream().anyMatch(monster -> monster.isBoss() || monster.isMiniboss()));
			Set<Monster> monsterSetMiniboss = MonsterServices.generate(2, theme.getTheme(), 2, false, true);
			assertTrue(monsterSetMiniboss.stream().anyMatch(monster -> monster.isMiniboss()));
			Set<Monster> monsterSetBoss = MonsterServices.generate(2, theme.getTheme(), 2, true, false);
			assertTrue(monsterSetBoss.stream().anyMatch(monster -> monster.isBoss()));
			Set<Set<Monster>> setOfMonsterSets = new HashSet<>();
			setOfMonsterSets.add(monsterSetNoBosses);
			setOfMonsterSets.add(monsterSetMiniboss);
			setOfMonsterSets.add(monsterSetBoss);
			
			for(Set<Monster> monsterSet: setOfMonsterSets) {
				assertNotNull(monsterSet);
				assumeTrue(monsterSet != null);
				for(Monster monster: monsterSet) {
					assertTrue(
						Stream.of(MonsterSpecies.values())
						.anyMatch(species -> species.getSpecies().equals(monster.getSpecies()))
							);
					assertTrue(
							Stream.of(MonsterType.values())
							.anyMatch(type -> type.getType().equals(monster.getType()))
							);
					assertTrue(
							Stream.of(Position.values())
							.anyMatch(position -> position.getPosition().equals(monster.getPosition()))
							);					
					assertTrue(monster.getVariant() >= 1);
					assertTrue(monster.getLevel() >= 0);
					assertTrue(monster.getCurrentHealth() == -1);
					assertTrue(monster.getHealth() > 0);
					assertTrue(monster.getArmor() > 0);
					assertTrue(monster.getPower() > 0);
					assertTrue(monster.getDodgeRating() > 0);
					assertTrue(monster.getCritRating() > 0);
					assertNotNull(monster.getName());
					assertNotNull(monster.getAbilities());
					assumeTrue(monster.getAbilities() != null);
					assertTrue(
							monster.isBoss() 
								? monster.getAbilities().size() == 3 
								: monster.isMiniboss()
									? monster.getAbilities().size() == 2
									: monster.getAbilities().size() == 1
							);
				}
				
			}
			
		}
	}
}
