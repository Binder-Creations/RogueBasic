package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/dungeon")
public class DungeonController {
	@Autowired
	private DungeonServices dungeonServices;
	@Autowired
	private DungeonDao dungeonDao;
	@Autowired
	private PlayerCharacterDao playerCharacterDao;
	
    @GetMapping("/{id}")
    public Dungeon getDungeon(@PathVariable String id) {
    	Dungeon dungeon = dungeonDao.findById(UUID.fromString(id));
    	if(dungeon.getFloors() == null || dungeon.getFloors().size() == 0) {
    		return dungeonServices.addFloors(dungeon);
    	}
    	return dungeon;
    }
	
	//returns a list of 3 dungeon shells for use on the quest board
    @GetMapping("/new/{pcId}")
    public List<Dungeon> getNewDungeon(@PathVariable String pcId) {
    	List<Dungeon> dungeons = new ArrayList<>();
    	dungeons.add(dungeonServices.generateShell(UUID.fromString(pcId)));
    	dungeons.add(dungeonServices.generateShell(UUID.fromString(pcId)));
    	dungeons.add(dungeonServices.generateShell(UUID.fromString(pcId)));
    	return dungeons;
    }

    @GetMapping("/getBoard/{pcId}")
    public List<Dungeon> getDungeonBoard(@PathVariable String pcId) {
    	List<Dungeon> dungeons = new ArrayList<>();
    	playerCharacterDao.findById(UUID.fromString(pcId)).getDungeonBoard().forEach(dungeonId ->{
    		dungeons.add(dungeonDao.findById(dungeonId));
    	});
    	return dungeons;
    }

	@PutMapping
    public ResponseEntity<String> updateDungeon(@RequestBody Dungeon dungeon) {
    	dungeonDao.save(dungeon);
        return ResponseEntity.ok().build();
    }
}