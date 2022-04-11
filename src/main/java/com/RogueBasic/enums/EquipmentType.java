package com.RogueBasic.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import com.RogueBasic.beans.Item;

import io.netty.util.internal.ThreadLocalRandom;

public enum EquipmentType {
	HEAD_LIGHT("headLight", false, true, 11, 
			(item, statSum) -> {
				item.setArmorBonus((statSum*4)/3);
				return item;
			},
			new String[] {"Motheaten ", "Frayed ", "Tattered "}, 
			new String[] {"Linen ", "Layered ", "Well-Woven "},
			new String[] {"Silken ", "Embroidered ", "Fine "}, 
			new String[] {"Archmage's ", "Gold-Threaded ", "Ley-Attuned "}, 
			new String[] {"Hood", "Cowl", "Hat", "Cap", "Headdress", "Chappeau"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	HEAD_MEDIUM("headMedium", false, true, 11, 
			(item, statSum) -> {
				item.setArmorBonus(statSum*2);
				return item;
			},
			new String[] {"Mottled ", "Worn ", "Rough "}, 
			new String[] {"Heavy ", "Oiled ", "Supple "},
			new String[] {"Darkened ", "Treated ", "Fine "}, 
			new String[] {"Masterwork ", "Shadowed ", "Displacer-Hide "}, 
			new String[] {"Leather Cowl", "Leather Helm", "Combat Mask", "Headcap", "Light Helm"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, ominous %s", " exceptional %s"}
			),
	HEAD_HEAVY("headHeavy", false, true, 15, 
			(item, statSum) -> {
				item.setArmorBonus(statSum*3);
				return item;
			},
			new String[] {"Rusty ", "Dented ", "Marred "}, 
			new String[] {"Steel ", "Sturdy ", "Burnished "},
			new String[] {"Polished ", "Crested ", "Tempered "}, 
			new String[] {"Masterwork ", "Glowing ", "Adamantine "}, 
			new String[] {"Armet", "Bascinet", "Kettle Hat", "Full Helm", "Sallet", "Visored Helm"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	BODY_LIGHT("bodyLight", false, true, 11, 
			(item, statSum) -> {
				item.setArmorBonus((statSum*5)/2);
				return item;
			},
			new String[] {"Motheaten ", "Frayed ", "Tattered "}, 
			new String[] {"Linen ", "Layered ", "Well-Woven "},
			new String[] {"Silken ", "Embroidered ", "Fine "}, 
			new String[] {"Archmage's ", "Gold-Threaded ", "Ley-Attuned "}, 
			new String[] {"Vestment", "Robe", "Vest", "Tunic", "Garb", "Attire"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	BODY_MEDIUM("bodyMedium", false, true, 12, 
			(item, statSum) -> {
				item.setArmorBonus(statSum*4);
				return item;
			},
			new String[] {"Mottled ", "Worn ", "Rough "}, 
			new String[] {"Heavy ", "Oiled ", "Supple "},
			new String[] {"Darkened ", "Treated ", "Fine "}, 
			new String[] {"Masterwork ", "Shadowed ", "Displacer-Hide "}, 
			new String[] {"Leather Armor", "Strapped Armor", "Studded Leather Armor", "Hide Armor", "Light Chainmail"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, ominous %s", " exceptional %s"}
			),
	BODY_HEAVY("bodyHeavy", false, true, 12, 
			(item, statSum) -> {
				item.setArmorBonus(statSum*6);
				return item;
			},
			new String[] {"Rusty ", "Dented ", "Marred "}, 
			new String[] {"Steel ", "Sturdy ", "Burnished "},
			new String[] {"Polished ", "Heraldic ", "Tempered "}, 
			new String[] {"Masterwork ", "Glowing ", "Adamantine "}, 
			new String[] {"Half-Plate Armor", "Full-Plate Armor", "Breastplate", "Scale Mail", "Heavy Chainmail"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	STAFF("staff", true, false, 41, 
			(item, statSum) -> {
				item.setPowerBonus((statSum*5)/2);
				return item;
			},
			new String[] {"Decayed ", "Dim ", "Depleted "}, 
			new String[] {"Humming ", "Infused ", "Attuned "},
			new String[] {"Potent ", "Glowing ", "Runed "}, 
			new String[] {"Masterwork ", "Radiant ", "Ley-Attuned "}, 
			new String[] {"Staff", "Rod", "Scepter", "Caduceus", "Wand", "Shillelagh"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average craft", " well-worn, well-proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	SPELLBOOK("spellbook", true, false, 20, 
			(item, statSum) -> {
				item.setPowerBonus((statSum*4)/3);
				return item;
			},
			new String[] {"Motheaten ", "Sparse ", "Depleted "}, 
			new String[] {"Commonprint ", "Well-Written ", "Indexed "},
			new String[] {"Potent ", "Comprehensive ", "Annotated "}, 
			new String[] {"Archmage's ", "Glowing ", "Ley-Attuned "}, 
			new String[] {"Book", "Spellbook", "Grimoire", "Tome", "Encyclopedia"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	BOW("bow", true, false, 13, 
			(item, statSum) -> {
				item.setPowerBonus(statSum*2);
				return item;
			},
			new String[] {"Awkward ", "Flimsy ", "Worn "}, 
			new String[] {"Taut ", "Polished ", "Elm "},
			new String[] {"Honed ", "Strengthened ", "Treated "}, 
			new String[] {"Masterwork ", "Glowing ", "Ebonwood "}, 
			new String[] {"Bow", "Crossbow", "Recurve Bow", "Short Bow", "Long Bow"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average craft", " well-worn, well-proven %s"}, 
			new String[] {" expertly crafted %s", " finely-honed, powerful %s", " exceptional %s"}
			),
	DAGGER("dagger", true, false, 15, 
			(item, statSum) -> {
				item.setPowerBonus(statSum);
				return item;
			},
			new String[] {"Dull ", "Rusty ", "Chipped "}, 
			new String[] {"Steel ", "Glinting ", "Sharp "},
			new String[] {"Honed ", "Razor-Edged ", "Deadly "}, 
			new String[] {"Masterwork ", "Infused ", "Gem-Studded "}, 
			new String[] {"Dagger", "Knife", "Poniard", "Tanto", "Stiletto", "Shortblade"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average craft", " well-worn, well-proven %s"}, 
			new String[] {" expertly crafted %s", " wicked, glinting %s", " exceptional %s"}
			),
	SWORD("sword", true, false, 12, 
			(item, statSum) -> {
				item.setPowerBonus((statSum*3)/2);
				return item;
			},
			new String[] {"Dull ", "Rusty ", "Brittle "}, 
			new String[] {"Glinting ", "Sharp ", "Steel "},
			new String[] {"Honed ", "Razor-Edged ", "Tempered "}, 
			new String[] {"Masterwork ", "Glowing ", "Mithril "}, 
			new String[] {"Sword", "Blade", "Cutlass", "Scimitar", "Bastard Sword"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average craft", " well-worn, well-proven %s"}, 
			new String[] {" expertly crafted %s", " finely-honed, powerful %s", " exceptional %s"}
			),
	SHIELD("shield", false, true, 15, 
			(item, statSum) -> {
				item.setArmorBonus(statSum*2);
				return item;
			},
			new String[] {"Dented ", "Rusty ", "Worn "}, 
			new String[] {"Steel ", "Sturdy ", "Burnished "},
			new String[] {"Pristine ", "Reinforced ", "Tempered "}, 
			new String[] {"Masterwork ", "Glowing ", "Adamantine "}, 
			new String[] {"Shield", "Buckler", "Kite Shield", "Tower Shield", "Targe"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average craft", " well-worn, well-proven %s"}, 
			new String[] {" expertly crafted %s", " heavily-reinforced, sturdy %s", " exceptional %s"}
			),
	BACK("back", false, true, 33, 
			(item, statSum) -> {
				item.setArmorBonus(statSum);
				return item;
			},
			new String[] {"Motheaten ", "Frayed ", "Tattered "}, 
			new String[] {"Linen ", "Layered ", "Well-Woven "},
			new String[] {"Silken ", "Embroidered ", "Fine "}, 
			new String[] {"Archmage's ", "Gold-Threaded ", "Ley-Attuned "}, 
			new String[] {"Cloak", "Cape", "Mantle", "Shawl", "Tippet"}, 
			new String[] {" %s of little note", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " well-worn, proven %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, radiant %s", " exceptional %s"}
			),
	NECK("neck", true, false, 18, 
			(item, statSum) -> {
				item.setPowerBonus(statSum/2);
				return item;
			},
			new String[] {"Tin ", "Patinated ", "Dull "}, 
			new String[] {"Bronze ", "Shiny ", "Opalescent "},
			new String[] {"Silver ", "Shimmering ", "Rubied "}, 
			new String[] {"Gold ", "Radiant ", "Gem-Studded "}, 
			new String[] {"Necklace", "Amulet", "Choker", "Necklet", "Chain", "Locket", "Torque"}, 
			new String[] {" %s of little value", " %s that has seen better days", " pitiful %s"}, 
			new String[] {" %s of average quality", " unremarkable %s", " %s of the usual sort"},
			new String[] {" perfectly fine %s", " %s of above-average merit", " gleaming %s"}, 
			new String[] {" expertly crafted %s", " magic-infused, glowing %s", " exceptional %s"}
			);

	private final String type;
	private final boolean offense;
	private final boolean defense;
	private final int imageCount;
	private final BiFunction<Item, Integer, Item> statAdjust;
	String[] prefixesCommon;
	String[] prefixesUncommon;
	String[] prefixesRare;
	String[] prefixesEpic;
	String[] nouns;
	String[] descriptionsCommon;
	String[] descriptionsUncommon;
	String[] descriptionsRare;
	String[] descriptionsEpic;
	
	EquipmentType(String type, boolean offense, boolean defense, int imageCount, BiFunction<Item, Integer, Item> statAdjust, 
			String[] prefixesCommon, String[] prefixesUncommon, String[] prefixesRare, String[] prefixesEpic, String[] nouns,
			String[] descriptionsCommon, String[] descriptionsUncommon, String[] descriptionsRare, String[] descriptionsEpic) {
		this.type = type;
		this.offense = offense;
		this.defense = defense;
		this.imageCount = imageCount;
		this.statAdjust = statAdjust;
		this.prefixesCommon = prefixesCommon;
		this.prefixesUncommon = prefixesUncommon;
		this.prefixesRare = prefixesRare;
		this.prefixesEpic = prefixesEpic;
		this.nouns = nouns;
		this.descriptionsCommon = descriptionsCommon;
		this.descriptionsUncommon = descriptionsUncommon;
		this.descriptionsRare = descriptionsRare;
		this.descriptionsEpic = descriptionsEpic;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isOffense(){
		return this.offense;
	}
	
	public boolean isDefense() {
		return this.defense;
	}
	
	public int getImageCount() {
		return this.imageCount;
	}
	
	public String getRandomPrefixCommon() {
		return this.prefixesCommon[ThreadLocalRandom.current().nextInt(this.prefixesCommon.length)];
	}
	
	public String getRandomPrefixUncommon() {
		return this.prefixesUncommon[ThreadLocalRandom.current().nextInt(this.prefixesUncommon.length)];
	}
	
	public String getRandomPrefixRare() {
		return this.prefixesRare[ThreadLocalRandom.current().nextInt(this.prefixesRare.length)];
	}
	
	public String getRandomPrefixEpic() {
		return this.prefixesEpic[ThreadLocalRandom.current().nextInt(this.prefixesEpic.length)];
	}
	
	public String getRandomNoun() {
		return this.nouns[ThreadLocalRandom.current().nextInt(this.nouns.length)];
	}
	
	public String getRandomDescriptionCommon() {
		return this.descriptionsCommon[ThreadLocalRandom.current().nextInt(this.descriptionsCommon.length)];
	}
	
	public String getRandomDescriptionUncommon() {
		return this.descriptionsUncommon[ThreadLocalRandom.current().nextInt(this.descriptionsUncommon.length)];
	}
	
	public String getRandomDescriptionRare() {
		return this.descriptionsRare[ThreadLocalRandom.current().nextInt(this.descriptionsRare.length)];
	}
	
	public String getRandomDescriptionEpic() {
		return this.descriptionsEpic[ThreadLocalRandom.current().nextInt(this.descriptionsEpic.length)];
	}
	
	public Item modifyStats(Item item, Integer statSum) {
		return this.statAdjust.apply(item, statSum);
	}
	
	public String getRandomImage() {
		return Integer.toString(ThreadLocalRandom.current().nextInt(1, this.imageCount + 1));
	}
	
	public EquipmentModifier getRandomModifier() {
		return this.offense
			? EquipmentModifier.getRandomOffense()
			: this.defense
				? EquipmentModifier.getRandomDefense()
				: null;
	}
	
	public static List<EquipmentType> asList() {
		return new ArrayList<>(Arrays.asList(EquipmentType.values()));
	}
	
	public static List<EquipmentType> fromStrings(List<String> strings){
		List<EquipmentType> enums = new ArrayList<>();
		for(String string: strings) {
			enums.add(
				Stream.of(EquipmentType.values())
				.filter((el) -> el.getType().equals(string))
				.findFirst().orElse(null)
			);
		}
		return enums;
	}
	
}
