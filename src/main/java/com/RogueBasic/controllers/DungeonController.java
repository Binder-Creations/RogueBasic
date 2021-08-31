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
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/dungeon")
public class DungeonController {

	//returns a list of 3 unsaved dungeon shells for use on the quest board
    @GetMapping("/{id}")
    public List<Dungeon> getDungeon(@PathVariable String id) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
    	List<Dungeon> dungeons = new ArrayList<>();
    	dungeons.add(ds.generateShell(UUID.fromString(id)));
    	dungeons.add(ds.generateShell(UUID.fromString(id)));
    	dungeons.add(ds.generateShell(UUID.fromString(id)));
    	return dungeons;
    }

    //returns a single saved, finished dungeon from a shell
    @GetMapping
    public Dungeon getCompleteDungeon(@RequestBody Dungeon dungeon) {
    	DungeonServices ds = new DungeonServices(CassandraConnector.getSession());
        return ds.addFloors(dungeon);
    }
}