package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.services.ItemServices;
import com.RogueBasic.services.ShopServices;
import com.RogueBasic.util.CassandraConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Shop {
	private UUID id;
	private Set<Item> inventoryCache;
	
	public Shop () {}
	
	public Shop(UUID pcId) {
		ShopServices services = new ShopServices(CassandraConnector.connect());
		this.id = UUID.randomUUID();
		this.inventoryCache = services.genInventoryCache(this.id, pcId);
	}
	
	public Shop(ShopAWS shop) {
		ObjectMapper mapper = new ObjectMapper();
		Set<Item> inventoryCache = new HashSet<>();
		if(shop.getInventoryCache() != null) {
			for(String item: shop.getInventoryCache()) {
				try {
					inventoryCache.add(mapper.readValue(item, Item.class));
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.id = shop.getId();
		this.inventoryCache = inventoryCache;
		
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Set<Item> getInventoryCache() {
		return inventoryCache;
	}

	public void setInventoryCache(Set<Item> inventoryCache) {
		this.inventoryCache = inventoryCache;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, inventoryCache);
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
		return Objects.equals(id, other.id) && Objects.equals(inventoryCache, other.inventoryCache);
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", inventory=" + inventoryCache + "]";
	}

	

	
		
}
