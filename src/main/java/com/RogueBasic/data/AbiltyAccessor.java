package com.RogueBasic.data;

import com.RogueBasic.beans.Ability;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface AbiltyAccessor {
	@Query("SELECT * FROM ability")
	Result<Ability> getAll();
}
