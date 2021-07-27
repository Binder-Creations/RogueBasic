package com.RogueBasic.data;

import com.RogueBasic.beans.Floor;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface FloorAccessor {
	@Query("SELECT * FROM floor")
	Result<Floor> getAll();
}
