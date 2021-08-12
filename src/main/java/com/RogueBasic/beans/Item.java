package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Item {
	
	@PrimaryKey private UUID id;
	private String name;
	private String description;
	private int cost;
	private double weight;
	private String action;
	
	public Item() {}
	
	public Item(String name, String description, int cost, double weight, String action) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.weight = weight;
		this.action = action;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public int hashCode() {
		return Objects.hash(action, cost, description, id, name, weight);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(action, other.action) && cost == other.cost
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost + ", weight="
				+ weight + ", action=" + action + "]";
	}
	
	
}
