package com.RogueBasic.data;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.RogueBasic.beans.Item;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class ItemDao {
	private MappingManager manager;
	private Mapper<Item> mapper;
	private ItemAccessor accessor;
	private static final Logger log = LogManager.getLogger(ItemDao.class);	
	
	public ItemDao(Session session) {
		super();
		try {
			this.manager = new MappingManager(session);
			this.mapper = manager.mapper(Item.class);
			this.accessor = manager.createAccessor(ItemAccessor.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Item findById(UUID id) {
		log.trace("ItemDao.findById() calling Mapper.get() and returning Item");
		try {
			return mapper.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Item> getAll() {
		log.trace("ItemDao.getAll() calling ItemAccessor.getAll() and returning List<Item>");
		try {
			return accessor.getAll().all();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	  
	public boolean save(Item player) {
		log.trace("ItemDao.findById() calling Mapper.save()");
		try {
			mapper.save(player);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean deleteById(UUID id) {
		log.trace("ItemDao.save() calling Mapper.delete()");
		try {
			mapper.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
