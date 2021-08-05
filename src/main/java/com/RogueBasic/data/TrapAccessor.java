package com.RogueBasic.data;
import com.RogueBasic.beans.Trap;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface TrapAccessor {
	@Query("SELECT * FROM trap")
	Result<Trap> getAll();
}
