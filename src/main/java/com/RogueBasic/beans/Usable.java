package com.RogueBasic.beans;

import java.util.Objects;

import com.RogueBasic.functions.Use;

public class Usable extends Item{
	private Use use;
	
	public Usable(long id, String name, String description, int cost, double weight) {
		super(id, name, description, cost, weight);
		this.use = null;
	}
	
	public Usable(long id, String name, String description, int cost, double weight, Use use) {
		super(id, name, description, cost, weight);
		this.use = use;
	}

	public Use getUse() {
		return use;
	}
	public void setUse(Use use) {
		this.use = use;
	}
	
	
//	public void use() {
//		if(use != null) {
//			use.use();
//		}
//	}
}
