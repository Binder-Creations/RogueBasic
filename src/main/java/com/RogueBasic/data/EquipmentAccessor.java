package com.RogueBasic.data;

import com.RogueBasic.beans.Equipment;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface EquipmentAccessor {
	@Query("SELECT * FROM equipment")
	Result<Equipment> getAll();
}
