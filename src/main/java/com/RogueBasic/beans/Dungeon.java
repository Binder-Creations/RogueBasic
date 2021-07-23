package com.RogueBasic.beans;

import java.util.Arrays;
import java.util.Objects;

public class Dungeon {
	private long id;
	private String name;
	private String description;
	private String theme;
	private int floorCount;
	private long[] floors;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;
	
	
	
	public Dungeon(long id, String name, String description, String theme, int floorCount, int challengeRating,
			boolean miniboss, boolean boss) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.theme = theme;
		this.floorCount = floorCount;
		this.challengeRating = challengeRating;
		this.miniboss = miniboss;
		this.boss = boss;
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
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public int getFloorCount() {
		return floorCount;
	}
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
	public long[] getFloors() {
		return floors;
	}
	public void setFloors(long[] floors) {
		this.floors = floors;
	}
	public int getChallengeRating() {
		return challengeRating;
	}
	public void setChallengeRating(int challengeRating) {
		this.challengeRating = challengeRating;
	}
	public boolean isMiniboss() {
		return miniboss;
	}
	public void setMiniboss(boolean miniboss) {
		this.miniboss = miniboss;
	}
	public boolean isBoss() {
		return boss;
	}
	public void setBoss(boolean boss) {
		this.boss = boss;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(floors);
		result = prime * result
				+ Objects.hash(boss, challengeRating, description, floorCount, id, miniboss, name, theme);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dungeon other = (Dungeon) obj;
		return boss == other.boss && challengeRating == other.challengeRating
				&& Objects.equals(description, other.description) && floorCount == other.floorCount
				&& Arrays.equals(floors, other.floors) && id == other.id && miniboss == other.miniboss
				&& Objects.equals(name, other.name) && Objects.equals(theme, other.theme);
	}
	
	@Override
	public String toString() {
		return "Dungeon [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", floorCount=" + floorCount + ", floors=" + Arrays.toString(floors) + ", challengeRating="
				+ challengeRating + ", miniboss=" + miniboss + ", boss=" + boss + "]";
	}
	
	
}
