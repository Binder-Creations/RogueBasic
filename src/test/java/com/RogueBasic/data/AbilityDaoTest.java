package com.RogueBasic.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.Ability;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class AbilityDaoTest {
	
	private static CassandraUtilities cu;
	private static AbilityDao dao;
	private static Ability tester;
	private static Ability tester2;
	private static CqlSession session;
	
	@BeforeAll
	private static void setUp() {
		session = CassandraConnector.connect();
		tester = new Ability("test", "test", 1, 1, "test", "test");
		tester2 = new Ability("test2", "test2", 2, 2, "test2", "test2");
		cu = new CassandraUtilities(session);
		
		//Initialized tables are required to construct our dao
		cu.dropAllTables();
		cu.initialize();
		dao = new AbilityDao(session);
	}
	
	@BeforeEach
	private void before() {
		dao.truncate();
		dao.save(tester);
		dao.save(tester2);
	}
	
	@Test
	public void findByIdTest() {
		//the object returned by findByID should equal our tester
		assertEquals(tester, dao.findById(tester.getId()));
	}
	
	@Test
	public void getAllTest() {
		List<Ability> testList = dao.getAll();
		
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
		Ability tester3 = new Ability("test3", "test3", 3, 3, "test3", "test3");
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
