package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.data.ItemDao;
import com.RogueBasic.util.CassandraConnector;
import com.datastax.oss.driver.api.core.CqlSession;

public class ShopExport {
	private UUID id;
	private Map<UUID, Integer> inventory;
	private Set<Item> inventoryCache;
	
	public ShopExport(Shop shop) {
		CqlSession session = CassandraConnector.getSession();
		ItemDao idao = new ItemDao(session);
		
		this.id = shop.getId();
		this.inventory = shop.getInventory();
		if(this.inventory != null) {
			this.inventoryCache = new HashSet<>();
			shop.getInventory().forEach((id,count)->this.inventoryCache.add(idao.findById(id)));
		}
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

	public Set<Item> getInventoryCache() {
		return inventoryCache;
	}

	public void setInventoryCache(Set<Item> inventoryCache) {
		this.inventoryCache = inventoryCache;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, inventory, inventoryCache);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShopExport other = (ShopExport) obj;
		return Objects.equals(id, other.id) && Objects.equals(inventory, other.inventory)
				&& Objects.equals(inventoryCache, other.inventoryCache);
	}

	@Override
	public String toString() {
		return "ShopExport [id=" + id + ", inventory=" + inventory + ", inventoryCache=" + inventoryCache + "]";
	}

	

	
		
}
