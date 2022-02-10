import Item from "./Item";

class Consumable extends Item {
  addTo(target){
    let item = target.inventory.find(i => i.id === this.id);
    if(item){
      item.count += this.count;
    } else {
      target.inventory.push(this);
    }
  }
  
  removeFrom(source){
    if(this.count > 1){
      --this.count
    } else {
      super.removeFrom(source);
    }
  }

  removeFromShop(source){}

  addToShop(target){}

  doAction(c, s, pc){
    if(this.actionType === "heal"){
      if(s.combat){
        c.combatEngine.itemHeal(this.actionValue);
        c.pcServices.updateStats(c.combatEngine.pc);
        c.setState({pc: c.combatEngine.pc});
      } else {
        pc.currentHealth = (pc.currentHealth + pc.healthTotal*(this.actionValue/100)) > pc.healthTotal
          ? pc.healthTotal
          : pc.currentHealth + pc.healthTotal*(this.actionValue/100);
        c.pcServices.updateStats(pc);
        c.save({pc: pc}, {pc: pc});
      }
    } else if(this.actionType === "energize"){
      if(s.combat){
        c.combatEngine.itemEnergize(this.actionValue);
        c.pcServices.updateStats(c.combatEngine.pc);
        c.setState({pc: c.combatEngine.pc});
      } else {
        pc.currentEnergy = (pc.currentEnergy + pc.energyTotal*(this.actionValue/100)) > pc.energyTotal
          ? pc.energyTotal
          : pc.currentEnergy + pc.energyTotal*(this.actionValue/100);
        c.pcServices.updateStats(pc);
        c.save({pc: pc}, {pc: pc});
      }
    }
  }
  
}

export default Consumable;