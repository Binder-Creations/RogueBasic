package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterExport;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/pc")
public class PCController {


    @GetMapping("/{id}")
    public PlayerCharacterExport getPlayerCharacter(@PathVariable String id) {
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	return new PlayerCharacterExport(pcDao.findById(UUID.fromString(id)));
    }

    @PutMapping
    public ResponseEntity updatePlayerCharacter(@RequestBody PlayerCharacterExport pc) {
    	PlayerCharacterDao pcDao = new PlayerCharacterDao(CassandraConnector.getSession());
    	pcDao.save(new PlayerCharacter(pc));
        return ResponseEntity.ok().build();
    }
}