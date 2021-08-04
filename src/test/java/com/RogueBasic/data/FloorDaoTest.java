package com.RogueBasic.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.Floor;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.driver.core.Session;

public class FloorDaoTest {
	
	private static CassandraUtilities cu;
	private static FloorDao dao;
	private static Floor tester;
	private static Floor tester2;
	private static Session session;
	
	@BeforeAll
	private static void setUp() {
		
		tester = new Floor();
		tester.setId(UUID.randomUUID());
		tester2 = new Floor();
		tester2.setId(UUID.randomUUID());
		
		session = new CassandraConnector().connect();
		cu = new CassandraUtilities(session);
		
		//Initialized tables are required to construct our dao
		cu.dropAllTables();
		cu.initialize();
		dao = new FloorDao(session);	

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
		List<Floor> testList = dao.getAll();
		
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
		Floor tester3 = new Floor();
		tester3.setId(UUID.randomUUID());
		
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
