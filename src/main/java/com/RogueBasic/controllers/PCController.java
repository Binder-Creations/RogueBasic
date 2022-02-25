package com.RogueBasic.controllers;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.PcDeleteRequest;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;

@RestController
@RequestMapping("/pc")
public class PCController {
	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private PlayerCharacterDao playerCharacterDao;

    @GetMapping("/{id}")
    public PlayerCharacter getPlayerCharacter(@PathVariable String id) {
    	return playerCharacterDao.findById(UUID.fromString(id));
    }

	@PutMapping
    public ResponseEntity<String> updatePlayerCharacter(@RequestBody PlayerCharacter pc) {
    	playerCharacterDao.save(pc);
        return ResponseEntity.ok().build();
    }
    
	@DeleteMapping
    public ResponseEntity<String> deletePlayerCharacter(@RequestBody PcDeleteRequest pcdr) {
    	try {
    		Player p = playerDao.findById(pcdr.getPlayerId());
    		Set<UUID> characterIds = p.getCharacterIds();
    		characterIds.remove(pcdr.getCharacterId());
    		p.setCharacterIds(characterIds);
    		p.setMetacurrency(p.getMetacurrency() + pcdr.getMetacurrency());
    		playerDao.save(p);
    		playerCharacterDao.deleteById(pcdr.getCharacterId());
    		return ResponseEntity.ok().build();
    	}
    	catch(Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}
    	
    }
}