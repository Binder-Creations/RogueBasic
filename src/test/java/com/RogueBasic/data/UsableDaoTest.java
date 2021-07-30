package com.RogueBasic.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.UsableItem;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.driver.core.Session;

public class UsableDaoTest {
	
	private static CassandraUtilities cu;
	private static UsableDao dao;
	private static UsableItem tester;
	private static UsableItem tester2;
	private static Session session;
	
	@BeforeAll
	private static void setUp() {
		
		tester = new UsableItem("test", "test", 1, 1, "test");
		tester2 = new UsableItem("test2", "test2", 2, 2, "test2");
	
		session = new CassandraConnector().connect();
		cu = new CassandraUtilities(session);
		
		//Initialized tables are required to construct our dao
		cu.initialize();
		dao = new UsableDao(session);	

	}
	
	@BeforeEach
	private void reset() {
		
		//drops any previous data and creates the blank tables needed for each test
		cu.dropAllTables();
		cu.initialize();
		
		//writes tester and tester2 to our database for future testing
		dao.save(tester);
		dao.save(tester2);
	}
	
	@Test
	public void findByIdTest() {
		//the object returned by findByID should equal our tester
		assertEquals(dao.findById(tester.getId()).toString(), tester.toString());
	}
	
	@Test
	public void getAllTest() {
		List<UsableItem> testList = dao.getAll();
		
		//asserts for each object that the getAll method's list has returned 
		//each object which should be in the table
		assertEquals(tester.toString(), testList.stream()
				.filter(x->x.getId().equals(tester.getId()))
				.findFirst()
				.orElse(null).toString());
		assertEquals(tester2.toString(), testList.stream()
				.filter(x->x.getId().equals(tester2.getId()))
				.findFirst()
				.orElse(null).toString());
	}
	
	@Test
	public void saveTest() {
		UsableItem tester3 = new UsableItem("test3", "test3", 3, 3, "test3");
		dao.save(tester3);
		assertEquals(tester3.toString(), dao.findById(tester3.getId()).toString());
	}
	
	@Test
	public void deleteByIdTest() {
		dao.deleteById(tester.getId());
		
		//If tester has been deleted, it should return null while
		//the undeleted tester2 returns not null
		assertNull(dao.findById(tester.getId()));
		assertNotNull(dao.findById(tester2.getId()));
	}
	
	
	
	@AfterAll
	private static void close() {
		session.close();
	}
}
