package com.RogueBasic.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
	private long id;
	private String name;
	private String description;
	private int cost;
	private double weight;
	
	public Item(long id, String name, String description, int cost, double weight) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.weight = weight;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
	@Override
	public int hashCode() {
		return Objects.hash(cost, description, id, name, weight);
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
		return cost == other.cost && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost + ", weight="
				+ weight + "]";
	}
	
	
}
