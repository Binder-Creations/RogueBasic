package com.RogueBasic.enums;

public enum CassandraTable {
	PLAYER(" (id uuid PRIMARY KEY, name text, password text, characterIds set<UUID>, metacurrency int, constitutionMetabonus int, strengthMetabonus int, intelligenceMetabonus int, dexterityMetabonus int, currencyMetabonus int)"),
	PLAYERBYNAME(" (name text PRIMARY KEY, id uuid)"),
	PLAYERCHARACTERAWS(" (id uuid PRIMARY KEY, location text, dungeonBoard set<UUID>, currentDungeon uuid, currentShop uuid, ate boolean, name text, characterClass text, experience int, level int, attributePoints int, currency int,  metacurrency int, abilities set<text>, inventory set<text>, equipTypes list<text>, equippedHead text, equippedBody text, equippedBack text, equippedNeck text, equippedPrimary text, equippedSecondary text, constitution int, strength int, dexterity int, intelligence int, currentHealth int, currentEnergy int)"),
	DUNGEONAWS(" (id uuid PRIMARY KEY, name text, description text, theme text, prefixMod text, postfixMod text, floorCount int, floors set<text>, challengeRating int, miniboss boolean, boss boolean, reward int, rewardSet set<text>, questCompleted boolean, rewardClaimed boolean, currentFloor int, currentRoom int)"),
	SHOPAWS(" (id uuid PRIMARY KEY, inventory set<text>)");
	
	private final String statement;
	
	CassandraTable(String statement){
		this.statement = statement;
	}
	
	public String statement() {
		return this.statement;
	}
}
