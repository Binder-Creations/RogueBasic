import * as abilities from "./abilities";
import AbilityServices from "./AbilityServices";
import CombatUpdate from "./CombatUpdate";
import MonsterServices from "./MonsterServices";
import PcServices from "./PcServices";

class CombatEngine {
  constructor(pc, monsters, dungeonMod){
    this.pc = JSON.parse(JSON.stringify(pc));
    this.monsters = JSON.parse(JSON.stringify(monsters));
    this.monsterServices = new MonsterServices(dungeonMod);
    this.pcServices = new PcServices(pc.characterClass);
    this.updateMonsterStats();
    this.combatUpdates = [];
    this.abilityServices = null;
  }

  runRound(ability){
    this.combatUpdates = [];
    AbilityServices["pc"+ability.type](this.ability, this.pc, this.monsters, this.combatUpdates);
    // this.pcAbility(abilityName);
    this.monsters.forEach(monster => {
      if(!monster.flags.stun){
        this.monsterAbility(monster, this.selectAbility(monster));
      } else {
        this.combatUpdates.push(new CombatUpdate(monster.position, {stun: false}));
      }
    });
    this.decrementFlags();
    this.decrementBuffs();
    this.decrementDebuffs();
    this.pcServices.updateStats(this.pc);   
  }

  updateMonsterStats(){
    this.monsters.forEach(monster =>{
      monster = this.monsterServices.updateStats(monster);
    })
  }

  pcAbility(abilityName){
    abilities[abilityName].use(this.pc, this.monsters, this.combatUpdates);
  }

  monsterAbility(monster, abilityName){
    abilities[abilityName].use(this.pc, this.monsters, monster, this.combatUpdates)
  }

  selectAbility(monster){
    let abilityChance = (30 + monster.level + (monster.type == "Rogue" ? 5 : monster.type == "Wizard" ? 10 : 0))*(monster.boss ? 1.5 : monster.miniboss ? 1.25 : 1);
    if(Math.random()*100 < abilityChance){
      return monster.abilities[Math.floor(Math.random()*monster.abilities.length)].name.replace(" ", "");
    } else {
      return "MonsterAttack";
    }
  }

  decrementFlags(){
    for(let flag in this.pc.flags){
      if(flag && !isNaN(flag)){
        flag -= 1;
      }
    }
    this.monsters.forEach(monster => {
      for(let flag in monster.flags){
        if(flag && !isNaN(flag)){
          flag -= 1;
        }  
      }
    });
  }

  decrementBuffs(){
    for(let buff in this.pc.buffs){
      if(buff.duration){
        if(!isNaN(buff.duration)){
          buff.duration -= 1;
        }
      } else {
        delete this.pc.buffs[buff];
      }
    }
    this.monsters.forEach(monster => {
      for(let buff in monster.buffs){
        if(buff.duration){
          if(!isNaN(buff.duration)){
            buff.duration -= 1;
          }
        } else {
          delete this.monster.buffs[buff];
        } 
      }
    });
  }

  decrementDebuffs(){
    for(let debuff in this.pc.debuffs){
      if(debuff.duration){
        if(!isNaN(debuff.duration)){
          debuff.duration -= 1;
        }
      } else {
        delete this.pc.debuffs[debuff];
      }
    }
    this.monsters.forEach(monster => {
      for(let debuff in monster.debuffs){
        if(debuff.duration){
          if(!isNaN(debuff.duration)){
            debuff.duration -= 1;
          }
        } else {
          delete this.monster.debuffs[debuff];
        } 
      }
    });
  }

}

export default CombatEngine;