package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/dungeon")
public class DungeonController {

    @GetMapping("/{id}")
    public Dungeon getDungeon(@PathVariable String id) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
    	DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
    	Dungeon dungeon = dDao.findById(UUID.fromString(id));
    	return dungeon.getFloors() != null ? dungeon : ds.addFloors(dungeon);
    }
	
	//returns a list of 3 dungeon shells for use on the quest board
    @GetMapping("/new/{pcId}")
    public List<Dungeon> getNewDungeon(@PathVariable String pcId) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
    	List<Dungeon> dungeons = new ArrayList<>();
    	dungeons.add(ds.generateShell(UUID.fromString(pcId)));
    	dungeons.add(ds.generateShell(UUID.fromString(pcId)));
    	dungeons.add(ds.generateShell(UUID.fromString(pcId)));
    	return dungeons;
    }

    @GetMapping("/getBoard/{pcId}")
    public List<Dungeon> getDungeonBoard(@PathVariable String pcId) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
    	
    	List<Dungeon> dungeons = new ArrayList<>();
    	pcDao.findById(UUID.fromString(pcId)).getDungeonBoard().forEach(dungeonId ->{
    		dungeons.add(dDao.findById(dungeonId));
    	});
    	return dungeons;
    }

    @PutMapping
    public ResponseEntity updateDungeon(@RequestBody Dungeon dungeon) {
    	DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
    	dDao.save(dungeon);
        return ResponseEntity.ok().build();
    }
}