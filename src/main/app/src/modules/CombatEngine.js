import AbilityServices from "./AbilityServices";
import CombatUpdate from "./CombatUpdate";
import MonsterServices from "./MonsterServices";

class CombatEngine {
  constructor(pc, pcServices, monsters, dungeonMod){
    this.pc = JSON.parse(JSON.stringify(pc));
    this.monsters = JSON.parse(JSON.stringify(monsters));
    this.monsterServices = new MonsterServices(dungeonMod);
    this.pcServices = pcServices;
    this.updateMonsterStats();
    this.combatUpdates = [];
  }

  runRound(ability){
    this.combatUpdates = [];
    AbilityServices["pc"+ability.type](ability, this.pc, this.monsters, this.combatUpdates);
    this.pc.currentEnergy = (this.pc.currentEnergy - ability.cost) < 0 ? 0 : this.pc.currentEnergy - ability.cost;
    
    this.monsters.forEach(monster => {
      if(monster.boss || monster.miniboss || !monster.flags.stun){
        // let monsterAbility = this.selectAbility(monster);
        // AbilityServices["monster"+monsterAbility.type](monsterAbility, this.pc, monster, this.monsters, this.combatUpdates);
      } else {
        this.combatUpdates.push(new CombatUpdate("stun", "stun", monster.position));
      }
    });
    this.updateFlags(this.pc.flags);
    this.decrementBuffs(this.pc.buffs);
    this.decrementBuffs(this.pc.debuffs);
    for(let monster of this.monsters){
      this.updateFlags(monster.flags);
      this.decrementBuffs(monster.buffs);
      this.decrementBuffs(monster.debuffs);
    }
    
    this.pcServices.updateStats(this.pc);
    this.updateMonsterStats();
    AbilityServices.corpseCollector(this.monsters, this.combatUpdates);
    AbilityServices.pcCorpseCollector(this.pc, this.combatUpdates);
  }

  updateMonsterStats(){
    this.monsters.forEach(monster =>{
      monster = this.monsterServices.updateStats(monster);
    })
  }

  selectAbility(monster){
    let abilityChance = (30 + monster.level + (monster.type === "Rogue" ? 5 : monster.type === "Wizard" ? 10 : 0))*(monster.boss ? 1.5 : monster.miniboss ? 1.25 : 1);
    if(Math.random()*100 < abilityChance){
      return monster.abilities[Math.floor(Math.random()*monster.abilities.length)+1];
    } else {
      return monster.abilities[0];
    }
  }

  updateFlags(flags){
    for(let flag in flags){
      if(flag === 1){
        flag = -1;
        continue;
      }
      if (flag === -1){
        flag = 0;
        continue;
      }
    }
  }

  decrementBuffs(buffs){
    for(let buff of buffs){
      if(buff.duration > 0){
        buff.duration -= 1;
      } else if (!buff.duration) {
        delete buffs[buff];
      }
    }
  }

  itemHeal(value){
    this.pc.currentHealth = (this.pc.currentHealth + this.pc.healthTotal*(1+value/100) > this.pc.healthTotal)
      ? this.pc.healthTotal
      : this.pc.currentHealth + this.pc.healthTotal*(1+value/100);
  }

  itemEnergize(value){
    this.pc.currentEnergy = (this.pc.currentEnergy + this.pc.energyTotal*(1+value/100) > this.pc.energyTotal)
      ? this.pc.energyTotal
      : this.pc.currentEnergy + this.pc.energyTotal*(1+value/100);
  }

}

export default CombatEngine;