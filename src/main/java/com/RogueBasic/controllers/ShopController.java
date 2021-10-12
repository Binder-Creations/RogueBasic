package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.PlayerCharacterExport;
import com.RogueBasic.beans.Shop;
import com.RogueBasic.beans.ShopExport;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.ShopDao;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/shop")
public class ShopController {


    @GetMapping("/{id}")
    public ShopExport getShop(@PathVariable String id) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	return new ShopExport(sDao.findById(UUID.fromString(id)));
    }
    
    @GetMapping("/new/{pcId}")
    public ShopExport newShop(@PathVariable String pcId) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	Shop shop = new Shop(UUID.fromString(pcId));
    	sDao.save(shop);
    	return new ShopExport(shop);
    }

    @PutMapping
    public ResponseEntity updateShop(@RequestBody ShopExport shop) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	sDao.save(new Shop(shop));
        return ResponseEntity.ok().build();
    }
}