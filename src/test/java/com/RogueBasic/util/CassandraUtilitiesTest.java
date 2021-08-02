package com.RogueBasic.util;

import com.RogueBasic.data.*;
import com.datastax.driver.core.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.RogueBasic.beans.*;

public class CassandraUtilitiesTest {
	
	private static Session session;
	private static CassandraUtilities cu;
	private static AbilityDao aDao;
	private static DungeonDao dDao;
	private static EquipmentDao eDao;
	private static FloorDao fDao;
	private static ItemDao iDao;
	private static MonsterDao mDao;
	private static PlayerDao pDao;
	private static PlayerCharacterDao pcDao;
	private static RoomDao rDao;
	private static Ability a;
	private static Dungeon d;
	private static Equipment e;
	private static Floor f;
	private static Item i;
	private static Monster m;
	private static Player p;
	private static PlayerCharacter pc;
	private static Room r;
	
	@BeforeAll
	private static void setUp() {
		
		session = new CassandraConnector().connect();
		cu = new CassandraUtilities(session);
		aDao = new AbilityDao(session);
		dDao = new DungeonDao(session);
		eDao = new EquipmentDao(session);
		fDao = new FloorDao(session);
		iDao = new ItemDao(session);
		mDao = new MonsterDao(session);
		pDao = new PlayerDao(session);
		pcDao = new PlayerCharacterDao(session);
		rDao = new RoomDao(session);
		String t = "test";
		UUID u = UUID.randomUUID();
		Set<UUID> su = new HashSet<>();
		su.add(u);
		su.add(u);
		a = new Ability(t, t, 1, 1, t, t);
		d = new Dungeon();
		e = new Equipment(t, t, 1, 1, t, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		f = new Floor(1, 1, 1, u, u, u);
		i = new Item(t, t, 1, 1, t);
		m = new Monster(t, t, true, true, 1, 1, 1, 1, 1, 1, su);
		p = new Player(t, t);
		pc = new PlayerCharacter(u, t, 1, 1, 1, 1);
		r = new Room(u, u, 1, 1, u, u, u, u, u, su, su);
		
	}
	
	@Test
	public void initializeTest() {
		
		cu.initialize();
		aDao.save(a);
		assertEquals(a, aDao.findById(a.getId()));
		dDao.save(d);
		assertEquals(d, dDao.findById(d.getId()));
		eDao.save(e);
		assertEquals(e.toString(), eDao.findById(e.getId()).toString());
		fDao.save(f);
		assertEquals(f, fDao.findById(f.getId()));
		iDao.save(i);
		assertEquals(i, iDao.findById(i.getId()));
		mDao.save(m);
		assertEquals(m, mDao.findById(m.getId()));
		pDao.save(p);
		assertEquals(p, pDao.findById(p.getId()));
		pcDao.save(pc);
		assertEquals(pc, pcDao.findById(pc.getId()));
		rDao.save(r);
		assertEquals(r, rDao.findById(r.getId()));
	}
	
	@Test
	public void dropAllTablesTest() {
		
		cu.dropAllTables();
		assertFalse(aDao.save(a));
		assertFalse(dDao.save(d));
		assertFalse(eDao.save(e));
		assertFalse(iDao.save(i));
		assertFalse(mDao.save(m));
		assertFalse(pDao.save(p));
		assertFalse(pcDao.save(pc));
		assertFalse(rDao.save(r));
	}
}
