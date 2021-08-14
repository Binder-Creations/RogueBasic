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

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

public class PlayerCharacterDaoTest {
	
	private static CassandraUtilities cu;
	private static PlayerCharacterDao dao;
	private static PlayerCharacter tester;
	private static PlayerCharacter tester2;
	private static CqlSession session;
	
	@BeforeAll
	private static void setUp() {
		session = CassandraConnector.connect();
		tester = new PlayerCharacter(UUID.randomUUID(), "test", "Warrior", 1, 1, 1, 1);
		tester2 = new PlayerCharacter(UUID.randomUUID(), "test2", "Rogue", 2, 2, 2, 2);
		cu = new CassandraUtilities(session);
		
		//Initialized tables are required to construct our dao
		cu.dropAllTables();
		cu.initialize();
		dao = new PlayerCharacterDao(session);
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
		List<PlayerCharacter> testList = dao.getAll();
		
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
		PlayerCharacter tester3 = new PlayerCharacter(UUID.randomUUID(), "test3", "Wizard", 3, 3, 3, 3);
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
