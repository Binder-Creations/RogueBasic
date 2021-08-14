package com.RogueBasic.beans;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class PlayerByName{
	@PrimaryKey private String name;
	private UUID id;
	private String password;
	private Set<UUID> characterIds;
	private int metacurrency;
	private int constitutionMetabonus;
	private int strengthMetabonus;
	private int intelligenceMetabonus;
	private int dexterityMetabonus;
	private int currencyMetabonus;
	
	public PlayerByName() {}
	
	public PlayerByName(Player player) {
		this.name = player.getName();
		this.id = player.getId();
		this.password = player.getPassword();
		this.characterIds = player.getCharacterIds();
		this.metacurrency = player.getMetacurrency();
		this.constitutionMetabonus = player.getConstitutionMetabonus();
		this.strengthMetabonus = player.getStrengthMetabonus();
		this.intelligenceMetabonus = player.getIntelligenceMetabonus();
		this.dexterityMetabonus = player.getDexterityMetabonus();
		this.currencyMetabonus = player.getCurrencyMetabonus();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UUID> getCharacterIds() {
		return characterIds;
	}

	public void setCharacterIds(Set<UUID> characterIds) {
		this.characterIds = characterIds;
	}

	public int getMetacurrency() {
		return metacurrency;
	}

	public void setMetacurrency(int metacurrency) {
		this.metacurrency = metacurrency;
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
				intelligenceMetabonus, metacurrency, name, password, strengthMetabonus);
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
		return Objects.equals(characterIds, other.characterIds) && constitutionMetabonus == other.constitutionMetabonus
				&& currencyMetabonus == other.currencyMetabonus && dexterityMetabonus == other.dexterityMetabonus
				&& Objects.equals(id, other.id) && intelligenceMetabonus == other.intelligenceMetabonus
				&& metacurrency == other.metacurrency && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && strengthMetabonus == other.strengthMetabonus;
	}

	@Override
	public String toString() {
		return "PlayerByName [name=" + name + ", id=" + id + ", password=" + password + ", characterIds=" + characterIds
				+ ", metacurrency=" + metacurrency + ", constitutionMetabonus=" + constitutionMetabonus
				+ ", strengthMetabonus=" + strengthMetabonus + ", intelligenceMetabonus=" + intelligenceMetabonus
				+ ", dexterityMetabonus=" + dexterityMetabonus + ", currencyMetabonus=" + currencyMetabonus + "]";
	}
	
	
}
