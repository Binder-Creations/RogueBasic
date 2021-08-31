package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Room;
import com.RogueBasic.data.RoomDao;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/room")
public class RoomController {


    @GetMapping("/{id}")
    public Room getRoom(@PathVariable String id) {
    	RoomDao rDao = new RoomDao(CassandraConnector.getSession());
    	return rDao.findById(UUID.fromString(id));
    }

    @PutMapping
    public ResponseEntity updateRoom(@RequestBody Room room) {
    	RoomDao rDao = new RoomDao(CassandraConnector.getSession());
    	rDao.save(room);
        return ResponseEntity.ok().build();
    }
}