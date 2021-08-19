package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class PlayerByName{
	@PrimaryKey private String name;
	private UUID id;
	
	public PlayerByName() {}
	
	public PlayerByName(String name, UUID id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerByName other = (PlayerByName) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "PlayerByName [name=" + name + ", id=" + id + "]";
	}
	
	
}
