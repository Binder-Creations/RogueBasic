package com.RogueBasic.beans;

import java.util.Objects;
import java.util.UUID;

public class PcDeleteRequest {
	UUID playerId;
	UUID characterId;
	int metacurrency;
	
	public PcDeleteRequest() {}

	public UUID getPlayerId() {
		return playerId;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public UUID getCharacterId() {
		return characterId;
	}

	public void setCharacterId(UUID characterId) {
		this.characterId = characterId;
	}

	public int getMetacurrency() {
		return metacurrency;
	}

	public void setMetacurrency(int metacurrency) {
		this.metacurrency = metacurrency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(characterId, metacurrency, playerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PcDeleteRequest other = (PcDeleteRequest) obj;
		return Objects.equals(characterId, other.characterId) && metacurrency == other.metacurrency
				&& Objects.equals(playerId, other.playerId);
	}

	@Override
	public String toString() {
		return "PcDeleteRequest [playerId=" + playerId + ", characterId=" + characterId + ", metacurrency="
				+ metacurrency + "]";
	}
	
	
}
