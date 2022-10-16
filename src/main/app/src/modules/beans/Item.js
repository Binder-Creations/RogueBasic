import {bindAll} from "../services/Binder";

export default class Item {
  constructor(item){
    for(let property in item){
      this[property] = item[property];
    }
    bindAll(this);
  }
  getUsability(){
    return 1;
  }

  addTo(target){}

  addToShop(target){
    this.addTo(target);
  }
  
  removeFrom(source){
    let index = source.indexOf(this);
    if(index >= 0){
      source.splice(index, 1);
    }
  }

  removeFromShop(source){
    this.removeFrom(source);
  }

  doAction(s, pc){}

  unequip(pc){}
}