package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.DungeonExport;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/dungeon")
public class DungeonController {

    @GetMapping("/{id}")
    public Dungeon getDungeon(@PathVariable String id) {
    	DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
    	return dDao.findById(UUID.fromString(id));
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

    //returns a finished dungeon from a shell
    @GetMapping("/complete/{id}")
    public DungeonExport getCompleteDungeon(@PathVariable String id) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
        DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
        
    	return new DungeonExport(ds.addFloors(dDao.findById(UUID.fromString(id))));
    }
    
    @GetMapping("/convert/{id}")
    public DungeonExport getConvertedDungeon(@PathVariable String id) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
        DungeonDao dDao = new DungeonDao(CassandraConnector.getSession());
        
    	return new DungeonExport(dDao.findById(UUID.fromString(id)));
    }
}