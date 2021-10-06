package com.RogueBasic.beans;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Shop {
	@PrimaryKey private UUID id;
	private Map<UUID, Integer> inventory;
	
	public Shop() {
		this.id = UUID.randomUUID();
	}
	
	public Shop(ShopExport shop) {
		this.id = shop.getId();
		this.inventory = shop.getInventory();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Map<UUID, Integer> getInventory() {
		return inventory;
	}

	public void setInventory(Map<UUID, Integer> inventory) {
		this.inventory = inventory;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, inventory);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		return Objects.equals(id, other.id) && Objects.equals(inventory, other.inventory);
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", inventory=" + inventory + "]";
	}

	

	
		
}
