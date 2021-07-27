package com.RogueBasic.data;

import com.RogueBasic.beans.PlayerCharacter;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface CharacterAccessor {
	@Query("SELECT * FROM character")
	Result<PlayerCharacter> getAll();
}
