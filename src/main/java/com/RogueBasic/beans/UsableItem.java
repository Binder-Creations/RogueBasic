package com.RogueBasic.beans;

import java.util.Objects;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue", name = "usable")
public class UsableItem extends Item{
	private String action;
	
	public UsableItem() {};
	
	public UsableItem(String name, String description, int cost, double weight) {
		super(name, description, cost, weight);
		this.action = null;
	}
	
	public UsableItem(String name, String description, int cost, double weight, String action) {
		super(name, description, cost, weight);
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(getCost(), getDescription(), getId(), getName(), getWeight(), action);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsableItem other = (UsableItem) obj;
		return Objects.equals(getId(), other.getId()) && getCost() == other.getCost() && getDescription() == other.getDescription() 
		&& getName() == other.getName() && getWeight() == other.getWeight() && getAction() == other.getAction();
	}

	@Override
	public String toString() {
		return "Usable [id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", cost=" + getCost() + ", weight="
				+ getWeight() + "]";
	}
}
