package com.RogueBasic.data;

import com.RogueBasic.beans.Item;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface ItemAccessor {
	@Query("SELECT * FROM item")
	Result<Item> getAll();
}
