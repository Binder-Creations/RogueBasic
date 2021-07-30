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
                + "rogue.PLAYER (id uuid PRIMARY KEY, name text, password text, characterIds set<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)");
	
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.CHARACTER (id uuid PRIMARY KEY, playerId uuid, name text, characterClass text, experience int, level int, currency int,  abilityIds set<UUID>, inventory map<uuid, int>, constitution int, strength int, dexterity int, intelligence int, constitutionBonus int, strengthBonus int, dexterityBonus int, intelligenceBonus int, powerBonus int, healthBonus int, healthRegenBonus int, encumberanceBonus int, carryCapacityBonus int, dodgeRatingBonus int, critRatingBonus int, energyBonus int, energyRegenBonus int, armorBonus int, currentHealth int, currentEnergy int, currentEncumberance int, currentCarryCapacity int)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.ABILITY (id uuid PRIMARY KEY, name text, description text, level int, energyCost int, area text, action text)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.DUNGEON (id uuid PRIMARY KEY, name text, description text, theme text, floorCount int, floors set<UUID>, challengeRating int, miniboss boolean, boss boolean)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.FLOOR (id uuid PRIMARY KEY, level int, xLength int, yLength int, previousFloorId uuid, nextFloorId uuid, dungeonId uuid, roomIds set<UUID>)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.ROOM (id uuid PRIMARY KEY, floorID uuid, dungeonId uuid, xCoord int, yCoord int, northRoomId uuid, southRoomId uuid, eastRoomId uuid, westRoomId uuid, trapId uuid, itemIds set<UUID>, monsterIds set<UUID>)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.ITEM (id uuid PRIMARY KEY, name text, description text, cost int, weight double)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.USABLE (id uuid PRIMARY KEY, name text, description text, cost int, weight double, action text)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.EQUIPMENT (id uuid PRIMARY KEY, name text, description text, cost int, weight double, constitutionBonus int, strengthBonus int, dexterityBonus int, intelligenceBonus int, powerBonus int, healthBonus int, healthRegenBonus int, encumberanceBonus int, carryCapacityBonus int, dodgeRatingBonus int, critRatingBonus int, energyBonus int, energyRegenBonus int, armorBonus int)");
		
		session.execute("CREATE TABLE IF NOT EXISTS "
                + "rogue.MONSTER (id uuid PRIMARY KEY, name text, description text, boss boolean, miniBoss boolean, level int, health int, power int, armor int, dodgeRating int, critRating int, abilityIds set<UUID>)");
	}
	
	public void populate() {
		
	}
	
	public void dropAllTables() {
		session.execute("DROP TABLE IF EXISTS rogue.PLAYER");
		session.execute("DROP TABLE IF EXISTS rogue.CHARACTER");
		session.execute("DROP TABLE IF EXISTS rogue.ABILITY");
		session.execute("DROP TABLE IF EXISTS rogue.DUNGEON");
		session.execute("DROP TABLE IF EXISTS rogue.FLOOR");
		session.execute("DROP TABLE IF EXISTS rogue.ROOM");
		session.execute("DROP TABLE IF EXISTS rogue.ITEM");
		session.execute("DROP TABLE IF EXISTS rogue.USABLE");
		session.execute("DROP TABLE IF EXISTS rogue.EQUIPMENT");
		session.execute("DROP TABLE IF EXISTS rogue.MONSTER");
	}
	
}
