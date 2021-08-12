package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Trap {
	@PrimaryKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private int level;
	private String action;
	
	public Trap() {}

	public Trap(String name, String description, String theme, int level, String action) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
		this.theme = theme;
		this.level = level;
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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, description, id, level, name, theme);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trap other = (Trap) obj;
		return Objects.equals(action, other.action) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && level == other.level && Objects.equals(name, other.name)
				&& Objects.equals(theme, other.theme);
	}

	@Override
	public String toString() {
		return "Trap [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme + ", level="
				+ level + ", action=" + action + "]";
	}

	
		
}
