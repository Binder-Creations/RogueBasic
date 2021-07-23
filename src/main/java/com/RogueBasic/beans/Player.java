package com.RogueBasic.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.RogueBasic.util.RogueUtilities;

public class Player {

	private long id;
	private String userName;
	private String password;
	private List<Long> characterIds;
	
	public Player(String userName, String password) {
		super();
		this.id = RogueUtilities.randIdPlayer();
		this.userName = userName;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Long> getCharacterIds() {
		return characterIds;
	}
	public void setCharacterIds(List<Long> characterIds) {
		this.characterIds = characterIds;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(characterIds, id, password, userName);
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
		return Objects.equals(characterIds, other.characterIds) && id == other.id
				&& Objects.equals(password, other.password) && Objects.equals(userName, other.userName);
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", userName=" + userName +", password=" + password
				+ ", characters=" + characterIds + "]";
	}
	
}
