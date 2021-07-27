package com.RogueBasic.util;

import com.datastax.driver.core.Session;

public class CassandraUtilities {
	private Session session; 
	
	public CassandraUtilities (Session session){
		super();
		this.session = session;
	}
	
	public void initialize() {
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.PLAYER (id uuid PRIMARY KEY, userName text, password text, characterIds set<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)");
	
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.CHARACTER (id uuid PRIMARY KEY, playerId uuid, name text, characterClass text, experience int, level int, currency int,  abilityIds set<UUID>, inventory map<uuid, int>, constitution int, strength int, dexterity int, intelligence int, constitutionBonus int, strengthBonus int, dexterityBonus int, intelligenceBonus int, powerBonus int, healthBonus int, healthRegenBonus int, encumberanceBonus int, carryCapacityBonus int, dodgeRatingBonus int, critRatingBonus int, energyBonus int, energyRegenBonus int, armorBonus int, currentHealth int, currentEnergy int, currentEncumberance int, currentCarryCapacity int)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.ABILITY (id uuid PRIMARY KEY, name text, description text, level int, energyCost int, area text)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.DUNGEON (id uuid PRIMARY KEY, name text, description text, theme text, floorCount int, floors set<UUID>, challengeRating int, miniboss boolean, boss boolean)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.FLOOR (id uuid PRIMARY KEY, level int, xLength int, yLength int, previousFloorId uuid, nextFloorId uuid, dungeonId uuid, roomIds set<UUID>)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.ROOM (id uuid PRIMARY KEY, floorID uuid, dungeonId uuid, xCoord int, yCoord int, northRoomId uuid, southRoomId uuid, eastRoomId uuid, westRoomId uuid, trapId uuid, itemIds set<UUID>, monsterIds set<UUID>)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.ITEM (id uuid PRIMARY KEY, name text, description text, cost int, weight double)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.USABLE (id uuid PRIMARY KEY, name text, description text, cost int, weight double)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.EQUIPMENT (id uuid PRIMARY KEY, name text, description text, cost int, weight double, constitutionBonus int, strengthBonus int, dexterityBonus int, intelligenceBonus int, powerBonus int, healthBonus int, healthRegenBonus int, encumberanceBonus int, carryCapacityBonus int, dodgeRatingBonus int, critRatingBonus int, energyBonus int, energyRegenBonus int, armorBonus int)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue_basic.MONSTER (id uuid PRIMARY KEY, name text, description text, boss boolean, miniBoss boolean, level int, health int, power int, armor int, dodgeRating int, critRating int, abilityIds set<UUID>)");
	}
	
	public void populate() {
		
	}
	
	public void dropAllTables() {
		session.execute("DROP TABLE IF EXISTS rogue_basic.PLAYER");
		session.execute("DROP TABLE IF EXISTS rogue_basic.CHARACTER");
		session.execute("DROP TABLE IF EXISTS rogue_basic.ABILITY");
		session.execute("DROP TABLE IF EXISTS rogue_basic.DUNGEON");
		session.execute("DROP TABLE IF EXISTS rogue_basic.FLOOR");
		session.execute("DROP TABLE IF EXISTS rogue_basic.ROOM");
		session.execute("DROP TABLE IF EXISTS rogue_basic.ITEM");
		session.execute("DROP TABLE IF EXISTS rogue_basic.USABLE");
		session.execute("DROP TABLE IF EXISTS rogue_basic.EQUIPMENT");
		session.execute("DROP TABLE IF EXISTS rogue_basic.MONSTER");
	}
}
