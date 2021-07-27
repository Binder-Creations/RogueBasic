package com.RogueBasic.data;
import com.RogueBasic.beans.Room;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface RoomAccessor {
	@Query("SELECT * FROM room")
	Result<Room> getAll();
}
