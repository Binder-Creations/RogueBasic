package com.RogueBasic.beans;

import java.util.Set;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Player {

	@PrimaryKey private UUID id;
	private String name;
	private String password;
	private Set<UUID> characterIds;
	private int metacurrency;
	private int constitutionMetabonus;
	private int strengthMetabonus;
	private int intelligenceMetabonus;
	private int dexterityMetabonus;
	private int currencyMetabonus;
	
	public Player() {}
	
	public Player(String name, String password) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
		this.password = password;
		this.metacurrency = 0;
		this.constitutionMetabonus = 0;
		this.strengthMetabonus = 0;
		this.intelligenceMetabonus = 0;
		this.dexterityMetabonus = 0;
		this.currencyMetabonus = 0;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getMetacurrency() {
		return metacurrency;
	}

	public void setMetacurrency(int metacurrency) {
		this.metacurrency = metacurrency;
	}

	public Set<UUID> getCharacterIds() {
		return characterIds;
	}
	
	public void setCharacterIds(Set<UUID> characterIds) {
		this.characterIds = characterIds;
	}
	
	public int getConstitutionMetabonus() {
		return constitutionMetabonus;
	}

	public void setConstitutionMetabonus(int constitutionMetabonus) {
		this.constitutionMetabonus = constitutionMetabonus;
	}

	public int getStrengthMetabonus() {
		return strengthMetabonus;
	}

	public void setStrengthMetabonus(int strengthMetabonus) {
		this.strengthMetabonus = strengthMetabonus;
	}

	public int getIntelligenceMetabonus() {
		return intelligenceMetabonus;
	}

	public void setIntelligenceMetabonus(int intelligenceMetabonus) {
		this.intelligenceMetabonus = intelligenceMetabonus;
	}

	public int getDexterityMetabonus() {
		return dexterityMetabonus;
	}

	public void setDexterityMetabonus(int dexterityMetabonus) {
		this.dexterityMetabonus = dexterityMetabonus;
	}

	public int getCurrencyMetabonus() {
		return currencyMetabonus;
	}

	public void setCurrencyMetabonus(int currencyMetabonus) {
		this.currencyMetabonus = currencyMetabonus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterIds, constitutionMetabonus, currencyMetabonus, dexterityMetabonus, id,
				intelligenceMetabonus, metacurrency, password, strengthMetabonus, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(characterIds, other.characterIds) && constitutionMetabonus == other.constitutionMetabonus
				&& currencyMetabonus == other.currencyMetabonus && dexterityMetabonus == other.dexterityMetabonus
				&& Objects.equals(id,other.id) && intelligenceMetabonus == other.intelligenceMetabonus
				&& metacurrency == other.metacurrency && Objects.equals(password, other.password)
				&& strengthMetabonus == other.strengthMetabonus && Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", password=" + password + ", metacurrency="
				+ metacurrency + ", characterIds=" + characterIds + ", constitutionMetabonus=" + constitutionMetabonus
				+ ", strengthMetabonus=" + strengthMetabonus + ", intelligenceMetabonus=" + intelligenceMetabonus
				+ ", dexterityMetabonus=" + dexterityMetabonus + ", currencyMetabonus=" + currencyMetabonus + "]";
	}
	
}
