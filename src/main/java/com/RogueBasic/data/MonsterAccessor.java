package com.RogueBasic.data;

import com.RogueBasic.beans.Monster;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface MonsterAccessor {
	@Query("SELECT * FROM monster")
	Result<Monster> getAll();
}
