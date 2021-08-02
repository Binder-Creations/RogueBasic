package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "rogue", name = "dungeon")
public class Dungeon {
	
	@PartitionKey private UUID id;
	private String name;
	private String description;
	private String theme;
	private int floorCount;
	private Set<UUID> floors;
	private int challengeRating;
	private boolean miniboss;
	private boolean boss;
	private boolean treasureBonus1;
	private boolean treasureBonus2;
	private boolean treasureBonus3;
	private boolean trapBonus1;
	private boolean trapBonus2;
	private boolean trapBonus3;
	private boolean enemyHealth1;
	private boolean enemyHealth2;
	private boolean enemyHealth3;
	private boolean enemyDamage1;
	private boolean enemyDamage2;
	private boolean enemyDamage3;
	
	public Dungeon() {}
	
//	public Dungeon(String name, String description, String theme, int floorCount, int challengeRating,
//			boolean miniboss, boolean boss) {
//		super();
//		this.id = UUID.randomUUID();
//		this.name = name;
//		this.description = description;
//		this.theme = theme;
//		this.floorCount = floorCount;
//		this.challengeRating = challengeRating;
//		this.miniboss = miniboss;
//		this.boss = boss;
//	}
	
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
	
	public int getFloorCount() {
		return floorCount;
	}
	
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
	
	public Set<UUID> getFloors() {
		return floors;
	}
	
	public void setFloors(Set<UUID> floors) {
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
	
	public boolean isTreasureBonus1() {
		return treasureBonus1;
	}

	public void setTreasureBonus1(boolean treasureBonus1) {
		this.treasureBonus1 = treasureBonus1;
	}

	public boolean isTreasureBonus2() {
		return treasureBonus2;
	}

	public void setTreasureBonus2(boolean treasureBonus2) {
		this.treasureBonus2 = treasureBonus2;
	}

	public boolean isTreasureBonus3() {
		return treasureBonus3;
	}

	public void setTreasureBonus3(boolean treasureBonus3) {
		this.treasureBonus3 = treasureBonus3;
	}

	public boolean isTrapBonus1() {
		return trapBonus1;
	}

	public void setTrapBonus1(boolean trapBonus1) {
		this.trapBonus1 = trapBonus1;
	}

	public boolean isTrapBonus2() {
		return trapBonus2;
	}

	public void setTrapBonus2(boolean trapBonus2) {
		this.trapBonus2 = trapBonus2;
	}

	public boolean isTrapBonus3() {
		return trapBonus3;
	}

	public void setTrapBonus3(boolean trapBonus3) {
		this.trapBonus3 = trapBonus3;
	}

	public boolean isEnemyHealth1() {
		return enemyHealth1;
	}

	public void setEnemyHealth1(boolean enemyHealth1) {
		this.enemyHealth1 = enemyHealth1;
	}

	public boolean isEnemyHealth2() {
		return enemyHealth2;
	}

	public void setEnemyHealth2(boolean enemyHealth2) {
		this.enemyHealth2 = enemyHealth2;
	}

	public boolean isEnemyHealth3() {
		return enemyHealth3;
	}

	public void setEnemyHealth3(boolean enemyHealth3) {
		this.enemyHealth3 = enemyHealth3;
	}

	public boolean isEnemyDamage1() {
		return enemyDamage1;
	}

	public void setEnemyDamage1(boolean enemyDamage1) {
		this.enemyDamage1 = enemyDamage1;
	}

	public boolean isEnemyDamage2() {
		return enemyDamage2;
	}

	public void setEnemyDamage2(boolean enemyDamage2) {
		this.enemyDamage2 = enemyDamage2;
	}

	public boolean isEnemyDamage3() {
		return enemyDamage3;
	}

	public void setEnemyDamage3(boolean enemyDamage3) {
		this.enemyDamage3 = enemyDamage3;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boss, challengeRating, description, enemyDamage1, enemyDamage2, enemyDamage3, enemyHealth1,
				enemyHealth2, enemyHealth3, floorCount, floors, id, miniboss, name, theme, trapBonus1, trapBonus2,
				trapBonus3, treasureBonus1, treasureBonus2, treasureBonus3);
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
				&& Objects.equals(description, other.description) && enemyDamage1 == other.enemyDamage1
				&& enemyDamage2 == other.enemyDamage2 && enemyDamage3 == other.enemyDamage3
				&& enemyHealth1 == other.enemyHealth1 && enemyHealth2 == other.enemyHealth2
				&& enemyHealth3 == other.enemyHealth3 && floorCount == other.floorCount
				&& Objects.equals(floors, other.floors) && Objects.equals(id, other.id) && miniboss == other.miniboss
				&& Objects.equals(name, other.name) && Objects.equals(theme, other.theme)
				&& trapBonus1 == other.trapBonus1 && trapBonus2 == other.trapBonus2 && trapBonus3 == other.trapBonus3
				&& treasureBonus1 == other.treasureBonus1 && treasureBonus2 == other.treasureBonus2
				&& treasureBonus3 == other.treasureBonus3;
	}
	
	@Override
	public String toString() {
		return "Dungeon [id=" + id + ", name=" + name + ", description=" + description + ", theme=" + theme
				+ ", floorCount=" + floorCount + ", floors=" + floors + ", challengeRating=" + challengeRating
				+ ", miniboss=" + miniboss + ", boss=" + boss + ", treasureBonus1=" + treasureBonus1
				+ ", treasureBonus2=" + treasureBonus2 + ", treasureBonus3=" + treasureBonus3 + ", trapBonus1="
				+ trapBonus1 + ", trapBonus2=" + trapBonus2 + ", trapBonus3=" + trapBonus3 + ", enemyHealth1="
				+ enemyHealth1 + ", enemyHealth2=" + enemyHealth2 + ", enemyHealth3=" + enemyHealth3 + ", enemyDamage1="
				+ enemyDamage1 + ", enemyDamage2=" + enemyDamage2 + ", enemyDamage3=" + enemyDamage3 + "]";
	}
	
	
}
