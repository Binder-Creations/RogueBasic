import Currency from "../beans/Currency";
import Consumable from "../beans/Consumable";
import Equipment from "../beans/Equipment";

class ItemFactory {
  static build(item){
    if(item.type === "currency"){
      return new Currency(item);
    } else if (item.type === "consumable" || item.type === "potion"){
      return new Consumable(item);
    } else {
      return new Equipment(item);
    }
  }

  static parseArray(array){
    let newArray = [];
    for(let item of array){
      newArray.push(ItemFactory.build(item));
    }
    return newArray;
  }

  static parseDungeon(dungeon){
    dungeon.rewardSet = ItemFactory.parseArray(dungeon.rewardSet);
    if(dungeon.floors && dungeon.floors.length > 0){
      for(let floor of dungeon.floors){
        for(let room of floor.rooms){
          if(room.loot && room.loot.length > 0){
            room.loot = ItemFactory.parseArray(room.loot);
          }
        }
      }
    }
  }

  static parsePC(pc){
    let slots = ["Head", "Body", "Back", "Neck", "Primary", "Secondary"];
    for(let slot of slots){
      if(pc["equipped"+slot]){
        pc["equipped"+slot] = ItemFactory.build(pc["equipped"+slot]);
      }
    }
    if(pc.inventory && pc.inventory.length > 0){
      pc.inventory = ItemFactory.parseArray(pc.inventory);
    }
  }
}

export default ItemFactory;