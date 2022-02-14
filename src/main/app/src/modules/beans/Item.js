import Binder from "../services/Binder";

class Item {
  constructor(item){
    for(let property in item){
      this[property] = item[property];
    }
    Binder.bind(this);
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

  doAction(c, s, pc){}

  unequip(c, pc){}
}

export default Item;