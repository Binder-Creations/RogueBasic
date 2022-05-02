package com.RogueBasic.beans;

import java.util.Set;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.RogueBasic.util.PasswordUtilities;

@Table
public class Player {

	@PrimaryKey private UUID id;
	private String name;
	private ByteBuffer passwordHash;
	private ByteBuffer salt;
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
		this.salt = PasswordUtilities.getSalt();
		this.passwordHash = PasswordUtilities.hash(password, this.salt);
	}
	
	public Player(String id) {
		super ();
		this.id = UUID.fromString(id);
		this.name = "Temporary User";
		this.salt = PasswordUtilities.getSalt();
		this.passwordHash = PasswordUtilities.hash(id, this.salt);	
	}
	
	public Player(String name, String password, Player tempPlayer) {
		this(name, password);
		this.characterIds = tempPlayer.getCharacterIds();
		this.metacurrency = tempPlayer.getMetacurrency();
		this.constitutionMetabonus = tempPlayer.getConstitutionMetabonus();
		this.strengthMetabonus = tempPlayer.getStrengthMetabonus();
		this.dexterityMetabonus = tempPlayer.getDexterityMetabonus();
		this.intelligenceMetabonus = tempPlayer.getIntelligenceMetabonus();
		this.currencyMetabonus = tempPlayer.getCurrencyMetabonus();
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

	public ByteBuffer getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(ByteBuffer passwordHash) {
		this.passwordHash = passwordHash;
	}

	public ByteBuffer getSalt() {
		return salt;
	}

	public void setSalt(ByteBuffer salt) {
		this.salt = salt;
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
				intelligenceMetabonus, metacurrency, name, passwordHash, salt, strengthMetabonus);
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
				&& Objects.equals(id, other.id) && intelligenceMetabonus == other.intelligenceMetabonus
				&& metacurrency == other.metacurrency && Objects.equals(name, other.name)
				&& Objects.equals(passwordHash, other.passwordHash) && Objects.equals(salt, other.salt)
				&& strengthMetabonus == other.strengthMetabonus;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", passwordHash=" + passwordHash + ", salt=" + salt
				+ ", characterIds=" + characterIds + ", metacurrency=" + metacurrency + ", constitutionMetabonus="
				+ constitutionMetabonus + ", strengthMetabonus=" + strengthMetabonus + ", intelligenceMetabonus="
				+ intelligenceMetabonus + ", dexterityMetabonus=" + dexterityMetabonus + ", currencyMetabonus="
				+ currencyMetabonus + "]";
	}


}