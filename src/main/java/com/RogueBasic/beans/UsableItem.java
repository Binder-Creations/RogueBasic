package com.RogueBasic.beans;

import java.util.Objects;

import com.RogueBasic.functions.Use;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue_basic", name = "usable")
public class UsableItem extends Item{
	private Use use;
	
	public UsableItem(String name, String description, int cost, double weight) {
		super(name, description, cost, weight);
		this.use = null;
	}
	
	public UsableItem(String name, String description, int cost, double weight, Use use) {
		super(name, description, cost, weight);
		this.use = use;
	}

	public Use getUse() {
		return use;
	}
	
	public void setUse(Use use) {
		this.use = use;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getCost(), getDescription(), getId(), getName(), getWeight(), use);
		return result;
	}
	
	@Override
	public String toString() {
		return "Usable [id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", cost=" + getCost() + ", weight="
				+ getWeight() + "]";
	}
}
