import AppServices from "../services/AppServices";
import CombatEngine from "../services/CombatEngine";
import PcServices from "../services/PcServices";
import Item from "./Item";

class Consumable extends Item {
  getUsability(){
    if(this.type === "potion"){
      return 2;
    }
    return 1;
  }

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

  doAction(s, pc){
    let appServices = AppServices.getInstance();

    if(this.actionType === "heal"){
      if(s.combat){
        let combatEngine = CombatEngine.getInstance();
        combatEngine.itemHeal(this.actionValue);
        new PcServices(combatEngine.pc).updateStats();
        appServices.setState({pc: combatEngine.pc});
      } else {
        pc.currentHealth = (pc.currentHealth + pc.healthTotal*(this.actionValue/100)) > pc.healthTotal
          ? pc.healthTotal
          : pc.currentHealth + pc.healthTotal*(this.actionValue/100);
        new PcServices(pc).updateStats();
        appServices.save({pc: pc}, {pc: pc});
      }
    } else if(this.actionType === "energize"){
      if(s.combat){
        let combatEngine = CombatEngine.getInstance();
        combatEngine.itemEnergize(this.actionValue);
        new PcServices(combatEngine.pc).updateStats();
        appServices.setState({pc: combatEngine.pc});
      } else {
        pc.currentEnergy = (pc.currentEnergy + pc.energyTotal*(this.actionValue/100)) > pc.energyTotal
          ? pc.energyTotal
          : pc.currentEnergy + pc.energyTotal*(this.actionValue/100);
        new PcServices(pc).updateStats();
        appServices.save({pc: pc}, {pc: pc});
      }
    }
  }
  
}

export default Consumable;