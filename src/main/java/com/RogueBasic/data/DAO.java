package com.RogueBasic.data;

import java.util.List;

public interface DAO<T> {
	public T getById(long id);
	public List<T> getAll();
	public List<Long> getIds();
}
