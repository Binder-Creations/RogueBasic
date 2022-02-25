package com.RogueBasic.enums;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

import com.RogueBasic.beans.Player;

public enum MetacurrencyPurchase {
	CONSTITUTION("Constitution", (player, cost) -> {
		player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
		player.setConstitutionMetabonus(player.getConstitutionMetabonus()+1);
	}),
	STRENGTH("Strength", (player, cost) -> {
		player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
		player.setStrengthMetabonus(player.getStrengthMetabonus()+1);
	}),
	DEXTERITY("Dexterity", (player, cost) -> {
		player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
		player.setDexterityMetabonus(player.getDexterityMetabonus()+1);
	}),
	INTELLIGENCE("Intelligence", (player, cost) -> {
		player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
		player.setIntelligenceMetabonus(player.getIntelligenceMetabonus()+1);
	}),
	CURRRENCY("Currency", (player, cost) -> {
		player.setMetacurrency(player.getMetacurrency() - Integer.parseInt(cost));
		player.setCurrencyMetabonus(player.getCurrencyMetabonus()+25);
	});
	
	private final String type;
	private final BiConsumer<Player, String> purchase;
	
	MetacurrencyPurchase(String type, BiConsumer<Player, String> purchase){
		this.type = type;
		this.purchase = purchase;
	}
	
	public String type() {
		return this.type;
	}
	
	public void purchase(Player player, String cost){
		this.purchase.accept(player, cost);
	}
	
	public static MetacurrencyPurchase fromString(String purchaseType) {
		return Stream.of(MetacurrencyPurchase.values())
		.filter(purchase -> purchase.type().equals(purchaseType))
		.findFirst().orElse(null);
	}
}
