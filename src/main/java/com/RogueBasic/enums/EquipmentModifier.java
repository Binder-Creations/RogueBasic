package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.RogueBasic.beans.Item;

import io.netty.util.internal.ThreadLocalRandom;

public enum EquipmentModifier {
	MIGHT (true, true, " of Might", 
			(item, statSum) -> {
				item.setStrengthBonus(statSum);
				return item;
			}),
	GRACE (true, true, " of Grace", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum);
				return item;
			}),
	CLARITY (true, true, " of Clarity", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum);
				return item;
			}),
	PROWESS (true, true, " of Prowess", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setStrengthBonus(statSum - item.getDexterityBonus());				
				return item;
			}),
	FORCE (true, true, " of Force", 
			(item, statSum) -> {
				item.setStrengthBonus(statSum/2);
				item.setIntelligenceBonus(statSum - item.getStrengthBonus());				
				return item;
			}),
	CUNNING (true, true, " of Cunning", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setDexterityBonus(statSum - item.getIntelligenceBonus());			
				return item;
			}),
	SUNDERING (true, false, " of Sundering", 
			(item, statSum) -> {
				item.setArmorPenBonus(statSum*5);
				return item;
			}),
	EXPLOITATION (true, false, " of Exploitation", 
			(item, statSum) -> {
				item.setCritRatingBonus(statSum*5);
				return item;
			}),
	CAPACITY (true, false, " of Capacity", 
			(item, statSum) -> {
				item.setEnergyBonus(statSum*10);
				return item;
			}),
	PERSISTENCE (true, false, " of Persistence", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setEnergyBonus((statSum - item.getStrengthBonus())*10);
				return item;
			}),
	DOMINANCE (true, false, " of Dominance", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setArmorPenBonus((statSum - item.getStrengthBonus())*5);
				return item;
			}),
	VICTORY (true, false, " of Victory", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setCritRatingBonus((statSum - item.getStrengthBonus())*5);
				return item;
			}),
	DISSECTION (true, false, " of Dissection", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setArmorPenBonus((statSum - item.getDexterityBonus())*5);
				return item;
			}),
	PRECISION (true, false, " of Precision", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setCritRatingBonus((statSum - item.getDexterityBonus())*5);
				return item;
			}),
	AGILITY (true, false, " of Agility", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setEnergyBonus((statSum - item.getDexterityBonus())*10);
				return item;
			}),
	TACTICS (true, false, " of Tactics", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setArmorPenBonus((statSum - item.getIntelligenceBonus())*5);		
				return item;
			}),
	GENIUS (true, false, " of Genius", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setEnergyBonus((statSum - item.getIntelligenceBonus())*10);
				return item;
			}),
	WIT (true, false, " of Wit", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setCritRatingBonus((statSum - item.getIntelligenceBonus())*5);
				return item;
			}),
	WELLNESS (false, true, " of Wellness", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum);
				return item;
			}),
	HARDINESS (false, true, " of Hardiness", 
			(item, statSum) -> {
				item.setHealthBonus(statSum*8);
				return item;
			}),
	REGENERATION (false, true, " of Regeneration", 
			(item, statSum) -> {
				item.setHealthRegenBonus(statSum);
				return item;
			}),
	FORTIFICATION (false, true, " of Fortification", 
			(item, statSum) -> {
				item.setArmorBonus(statSum*5);
				return item;
			}),
	EVASION (false, true, " of Evasion", 
			(item, statSum) -> {
				item.setDodgeRatingBonus(statSum*5);
				return item;
			}),
	INSIGHT (false, true, " of Insight", 
			(item, statSum) -> {
				item.setEnergyRegenBonus((statSum*3)/2);
				return item;
			}),
	FITNESS (false, true, " of Fitness", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setStrengthBonus(statSum - item.getConstitutionBonus());
				return item;
			}),
	NIMBLENESS (false, true, " of Nimbleness", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setDexterityBonus(statSum - item.getConstitutionBonus());	
				return item;
			}),
	CONSIDERATION (false, true, " of Consideration", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setIntelligenceBonus(statSum - item.getConstitutionBonus());				
				return item;
			}),
	VITALITY (false, true, " of Vitality", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setHealthBonus((statSum - item.getConstitutionBonus())*8);
				return item;
			}),
	MENDING (false, true, " of Mending", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setHealthRegenBonus(statSum - item.getConstitutionBonus());
				return item;
			}),
	RESILLIENCE (false, true, " of Resillience", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setArmorBonus((statSum - item.getConstitutionBonus())*5);
				return item;
			}),
	MARATHON (false, true, " of Marathon", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setDodgeRatingBonus((statSum - item.getConstitutionBonus())*5);
				return item;
			}),
	ATRITION (false, true, " of Atrition", 
			(item, statSum) -> {
				item.setConstitutionBonus(statSum/2);
				item.setEnergyRegenBonus(((statSum - item.getConstitutionBonus())*3)/2);
				return item;
			}),
	HALENESS (false, true, " of Haleness", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setHealthBonus((statSum - item.getStrengthBonus())*10);
				return item;
			}),
	TROLL (false, true, " of the Troll", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setHealthRegenBonus(statSum - item.getStrengthBonus());	
				return item;
			}),
	FORTRESS (false, true, " of the Fortress", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setArmorBonus((statSum - item.getStrengthBonus())*5);		
				return item;
			}),
	DERVISH (false, true, " of the Dervish", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setDodgeRatingBonus((statSum - item.getStrengthBonus())*5);
				return item;
			}),
	MOMENTUM (false, true, " of Momentum", 
			(item, statSum) -> {
				item.setStrengthBonus(Math.round(statSum/2));
				item.setEnergyRegenBonus(((statSum - item.getStrengthBonus())*3)/2);
				return item;
			}),
	REBOUNDING (false, true, " of Rebounding", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setHealthBonus((statSum - item.getDexterityBonus())*8);
				return item;
			}),
	CYCLING (false, true, " of Cycling", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setHealthRegenBonus(statSum - item.getDexterityBonus());
				return item;
			}),
	SCALES (false, true, " of Scales", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setHealthBonus((statSum - item.getDexterityBonus())*5);	
				return item;
			}),
	DANCING (false, true, " of Dancing", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setDodgeRatingBonus((statSum - item.getDexterityBonus())*5);	
				return item;
			}),
	ALACRITY (false, true, " of Alacrity", 
			(item, statSum) -> {
				item.setDexterityBonus(statSum/2);
				item.setEnergyRegenBonus(((statSum - item.getDexterityBonus())*3)/2);
				return item;
			}),
	WARMAGE (false, true, " of the Warmage", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setHealthBonus((statSum - item.getIntelligenceBonus())*8);
				return item;
			}),
	CONTINUITY (false, true, " of Continuity", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setHealthRegenBonus(statSum - item.getIntelligenceBonus());
				return item;
			}),
	TURTLE (false, true, " of the Turtle", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setArmorBonus((statSum - item.getIntelligenceBonus())*5);
				return item;
			}),
	FOX (false, true, " of the Fox", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setDodgeRatingBonus((statSum - item.getIntelligenceBonus())*5);
				return item;
			}),
	ACADEMIA (false, true, " of Academia", 
			(item, statSum) -> {
				item.setIntelligenceBonus(statSum/2);
				item.setEnergyRegenBonus(((statSum - item.getIntelligenceBonus())*3)/2);
				return item;
			});
	
	private final boolean offense;
	private final boolean defense;
	private final String suffix;
	private final BiFunction<Item, Integer, Item> statAdjust;
	
	EquipmentModifier(boolean offense, boolean defense, String suffix, BiFunction<Item, Integer, Item> statAdjust) {
		this.offense = offense;
		this.defense = defense;
		this.suffix = suffix;
		this.statAdjust = statAdjust;
	}
	
	public boolean isOffense() {
		return this.offense;
	}
	
	public boolean isDefense() {
		return this.defense;
	}
	
	public String getSuffix() {
		return this.suffix;
	}
	
	public Item modifyStats(Item item, Integer statSum) {
		return this.statAdjust.apply(item, statSum);
	}
	
	public static EquipmentModifier getRandomOffense(){
		List<EquipmentModifier> offenseModifiers = new ArrayList<>();
		for(EquipmentModifier modifier: EquipmentModifier.values()) {
			if(modifier.isOffense()) {
				offenseModifiers.add(modifier);
			}
		}
		return offenseModifiers.get(ThreadLocalRandom.current().nextInt(offenseModifiers.size()));
	}
	
	public static EquipmentModifier getRandomDefense(){
		List<EquipmentModifier> defenseModifiers = new ArrayList<>();
		for(EquipmentModifier modifier: EquipmentModifier.values()) {
			if(modifier.isDefense()) {
				defenseModifiers.add(modifier);
			}
		}
		return defenseModifiers.get(ThreadLocalRandom.current().nextInt(defenseModifiers.size()));
	}
}
