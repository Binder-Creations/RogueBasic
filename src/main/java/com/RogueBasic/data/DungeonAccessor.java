package com.RogueBasic.data;

import com.RogueBasic.beans.Dungeon;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface DungeonAccessor {
	@Query("SELECT * FROM dungeon")
	Result<Dungeon> getAll();
}
