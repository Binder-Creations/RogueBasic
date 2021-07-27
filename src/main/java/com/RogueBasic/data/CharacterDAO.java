package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import com.RogueBasic.beans.PlayerCharacter;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class CharacterDao {
	Session session;
	MappingManager manager;
	Mapper<PlayerCharacter> mapper;
	CharacterAccessor accessor;
	
	public CharacterDao(Session session) {
		super();
		this.session = session;
		this.manager = new MappingManager(session);
		this.mapper = manager.mapper(PlayerCharacter.class);
		this.accessor = manager.createAccessor(CharacterAccessor.class);
	}
	
	public PlayerCharacter findById(UUID id) {
		return mapper.get(id);
	};
	
	public List<PlayerCharacter> getAll() {
		return accessor.getAll().all();
	}
	
	  
	public void save(PlayerCharacter character) {
		mapper.save(character);
		return;
	}
	
	 
	public void deleteById(UUID id) {
		mapper.delete(id);
		return;
	}
}
