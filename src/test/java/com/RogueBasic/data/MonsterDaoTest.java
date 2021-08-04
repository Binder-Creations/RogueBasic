package com.RogueBasic.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.Monster;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.driver.core.Session;

public class MonsterDaoTest {
	
	private static CassandraUtilities cu;
	private static MonsterDao dao;
	private static Monster tester;
	private static Monster tester2;
	private static Session session;
	private static Set<UUID> testSet;
	
	@BeforeAll
	private static void setUp() {
		testSet = new HashSet<UUID>();
		testSet.add(UUID.randomUUID());
		testSet.add(UUID.randomUUID());
		tester = new Monster("test", "test", "test", true, true, 1, 1, 1, 1, 1, 1, testSet);
		tester2 = new Monster("test2", "test2","test2", false, false, 2, 2, 2, 2, 2, 2, testSet);
	
		session = new CassandraConnector().connect();
		cu = new CassandraUtilities(session);
		
		//Initialized tables are required to construct our dao
		cu.dropAllTables();
		cu.initialize();
		dao = new MonsterDao(session);	

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
		assertEquals(dao.findById(tester.getId()), tester);
	}
	
	@Test
	public void getAllTest() {
		List<Monster> testList = dao.getAll();
		
		//asserts for each object that the getAll method's list has returned 
		//each object which should be in the table
		assertEquals(tester, testList.stream()
				.filter(x->x.getId().equals(tester.getId()))
				.findFirst()
				.orElse(null));
		assertEquals(tester2, testList.stream()
				.filter(x->x.getId().equals(tester2.getId()))
				.findFirst()
				.orElse(null));
	}
	
	@Test
	public void saveTest() {
		Monster tester3 = new Monster("test3", "test3", "test3", true, true, 3, 3, 3, 3, 3, 3, testSet);
		dao.save(tester3);
		assertEquals(tester3, dao.findById(tester3.getId()));
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
