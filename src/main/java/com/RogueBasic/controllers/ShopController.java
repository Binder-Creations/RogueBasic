package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Shop;
import com.RogueBasic.data.ShopDao;
import com.RogueBasic.util.CassandraConnector;

@RestController
@RequestMapping("/shop")
public class ShopController {


    @GetMapping("/{id}")
    public Shop getShop(@PathVariable String id) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	return sDao.findById(UUID.fromString(id));
    }
    
    @GetMapping("/new/{pcId}")
    public Shop newShop(@PathVariable String pcId) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	Shop shop = new Shop(UUID.fromString(pcId));
    	sDao.save(shop);
    	return shop;
    }

    @PutMapping
    public ResponseEntity updateShop(@RequestBody Shop shop) {
    	ShopDao sDao = new ShopDao(CassandraConnector.getSession());
    	sDao.save(shop);
        return ResponseEntity.ok().build();
    }
}