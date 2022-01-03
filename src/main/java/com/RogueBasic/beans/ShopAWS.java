package com.RogueBasic.beans;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table
public class ShopAWS {
	@PrimaryKey private UUID id;
	private Set<String> inventoryCache;
	
	public ShopAWS() {}

	public ShopAWS(Shop shop) {
		ObjectMapper mapper = new ObjectMapper();
		Set<String> inventoryCache = new HashSet<>();
		if(shop.getInventoryCache() != null) {
			for(Item item: shop.getInventoryCache()) {
				try {
					inventoryCache.add(mapper.writeValueAsString(item));
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
	public Set<String> getInventoryCache() {
		return inventoryCache;
	}
	public void setInventoryCache(Set<String> inventoryCache) {
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
		ShopAWS other = (ShopAWS) obj;
		return Objects.equals(id, other.id) && Objects.equals(inventoryCache, other.inventoryCache);
	}
	@Override
	public String toString() {
		return "ShopAWS [id=" + id + ", inventoryCache=" + inventoryCache + "]";
	}
	
}
