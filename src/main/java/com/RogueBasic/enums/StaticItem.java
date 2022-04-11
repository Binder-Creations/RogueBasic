package com.RogueBasic.enums;

import com.RogueBasic.beans.Item;
import com.RogueBasic.util.JsonUtilities;

public enum StaticItem {
	GOLD("gold"),
	SOUL("soul"),
	RATIONS("rations"),
	WINE("wine"),
	HEALTH_POTION("healthPotion"),
	ENERGY_POTION("energyPotion");
	
	private String name;
	
	StaticItem(String name){
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Item getItem() {
		return JsonUtilities.getItem(this.name);
	}
	
	public Item getItem(int count) {
		Item item = JsonUtilities.getItem(this.name);
		item.setCount(count);
		return item;
	}
}
