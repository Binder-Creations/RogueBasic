package com.RogueBasic.controllers;

import java.util.Set;
import java.util.UUID;

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
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/pc")
public class PCController {


    @GetMapping("/{id}")
    public PlayerCharacter getPlayerCharacter(@PathVariable String id) {
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	return pcDao.findById(UUID.fromString(id));
    }

    @PutMapping
    public ResponseEntity updatePlayerCharacter(@RequestBody PlayerCharacter pc) {
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	pcDao.save(pc);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping
    public ResponseEntity deletePlayerCharacter(@RequestBody PcDeleteRequest pcdr) {
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	PlayerDao pDao = new PlayerDao(CassandraConnector.getSession());
    	try {
    		Player p = pDao.findById(pcdr.getPlayerId());
    		Set<UUID> characterIds = p.getCharacterIds();
    		characterIds.remove(pcdr.getCharacterId());
    		p.setCharacterIds(characterIds);
    		p.setMetacurrency(p.getMetacurrency() + pcdr.getMetacurrency());
    		pDao.save(p);
    		pcDao.deleteById(pcdr.getCharacterId());
    		return ResponseEntity.ok().build();
    	}
    	catch(Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}
    	
    }
}