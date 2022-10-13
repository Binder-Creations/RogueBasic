import Currency from "../beans/Currency";
import Consumable from "../beans/Consumable";
import Equipment from "../beans/Equipment";

export const build = (item) => {
  if(item.type === "currency"){
    return new Currency(item);
  } else if (item.type === "consumable" || item.type === "potion"){
    return new Consumable(item);
  } else {
    return new Equipment(item);
  }
}

export const parseArray = (array) => {
  let newArray = [];
  for(let item of array){
    newArray.push(build(item));
  }
  return newArray;
}

export const parseDungeon = (dungeon) => {
  dungeon.rewardSet = parseArray(dungeon.rewardSet);
  if(dungeon.floors && dungeon.floors.length > 0){
    for(let floor of dungeon.floors){
      for(let room of floor.rooms){
        if(room.loot && room.loot.length > 0){
          room.loot = parseArray(room.loot);
        }
      }
    }
  }
}

export const parsePC = (pc) => {
  let slots = ["Head", "Body", "Back", "Neck", "Primary", "Secondary"];
  for(let slot of slots){
    if(pc["equipped"+slot]){
      pc["equipped"+slot] = build(pc["equipped"+slot]);
    }
  }
  if(pc.inventory && pc.inventory.length > 0){
    pc.inventory = parseArray(pc.inventory);
  }
}