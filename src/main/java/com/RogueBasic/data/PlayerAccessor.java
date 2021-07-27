package com.RogueBasic.data;
import com.RogueBasic.beans.Player;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface PlayerAccessor {
	@Query("SELECT * FROM player")
	Result<Player> getAll();
}
