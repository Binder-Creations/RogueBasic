package com.RogueBasic.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Shop;
import com.RogueBasic.data.ShopDao;
import com.RogueBasic.services.ShopServices;

@RestController
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ShopServices shopServices;


    @GetMapping("/{id}")
    public Shop getShop(@PathVariable String id) {
    	return shopDao.findById(UUID.fromString(id));
    }
    
    @GetMapping("/new/{pcId}")
    public Shop newShop(@PathVariable String pcId) {
    	Shop shop = new Shop();
    	shop.setId(UUID.randomUUID());
    	shop.setInventory(shopServices.genInventory(shop.getId(), UUID.fromString(pcId)));
    	shopDao.save(shop);
    	return shop;
    }

	@PutMapping
    public ResponseEntity<String> updateShop(@RequestBody Shop shop) {
    	shopDao.save(shop);
        return ResponseEntity.ok().build();
    }
}