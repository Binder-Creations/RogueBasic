package com.RogueBasic.data;

import com.RogueBasic.beans.UsableItem;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface UsableAccessor {
	@Query("SELECT * FROM usable")
	Result<UsableItem> getAll();
}
