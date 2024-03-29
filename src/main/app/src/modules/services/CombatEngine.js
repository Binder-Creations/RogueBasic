import c from "../../data/CommonProperties";
import * as AbilityServices from "./AbilityServices";
import MonsterServices from "./MonsterServices";
import CombatUpdate from "../beans/CombatUpdate";
import PcServices from "./PcServices";

export default class CombatEngine {
  static #instance;
  static #isInternalConstructing = false;

  constructor(s){
    if(!CombatEngine.#isInternalConstructing) throw new TypeError("CombatEngine is not constructable");

    this.pc = {...s.pc}
    this.pcServices = new PcServices(this.pc);
    this.monsters = JSON.parse(JSON.stringify(s.dungeon.floors[s.dungeon.currentFloor].rooms[s.dungeon.currentRoom].monsters));
    this.monsterServices = new MonsterServices(s.dungeon.postFix);
    this.combatUpdates = [];
    this.updateMonsterStats();
  }

  static getInstance(s){
    if(this.#instance) return this.#instance;

    this.#isInternalConstructing = true;
    this.#instance = new CombatEngine(s);
    this.#isInternalConstructing = false;
    return this.#instance;
  }

  static clearInstance(){
    this.#instance = null;
  }

  runRound(ability, position){
    this.combatUpdates = [];
    if(!position){
      AbilityServices["pc"+ability.type](ability, this.pc, this.monsters, this.combatUpdates);
      this.pc.currentEnergy = (this.pc.currentEnergy - ability.cost) < 0 ? 0 : this.pc.currentEnergy - ability.cost;
      this.damageOverTime(this.pc);
    } else {
      let monster = this.monsters.find(monster => monster.position === c.positions[position]);
      if(monster.boss || monster.miniboss || !monster.flags.stun){
        let monsterAbility = this.selectAbility(monster);
        AbilityServices["monster"+monsterAbility.type](monsterAbility, this.pc, monster, this.monsters, this.combatUpdates);
      } else {
        this.combatUpdates.push(new CombatUpdate("stun", "stun", monster.position, monster.name));
      }
      this.damageOverTime(monster);  
    }
    this.pcServices.updateStats();
    this.updateMonsterStats();
  }

  endRound(){
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
    AbilityServices.corpseCollector(this.pc, this.monsters, this.combatUpdates);
  }

  damageOverTime(entity){
    if(entity.tempStats.regenerate){
      entity.currentHealth = Math.min((entity.currentHealth + entity.tempStats.regenerate), entity.healthTotal);
      this.combatUpdates.push(new CombatUpdate("Heal", "regenerate", entity.position ? entity.position : "pc", "Regenerate", entity.tempStats.regenerate));
    }
    if(entity.tempStats.bleed){
      entity.currentHealth = Math.max((entity.currentHealth + entity.tempStats.bleed), 0);
      this.combatUpdates.push(new CombatUpdate("Attack", "bleed", entity.position ? entity.position : "pc", "Bleed", entity.tempStats.bleed));
      if(!entity.position){
        AbilityServices.pcCorpseCollector(entity, "bleed", "bleed", entity.tempStats.bleed, false);
      }
    }
    if(entity.tempStats.burn){
      entity.currentHealth = Math.max((entity.currentHealth + entity.tempStats.burn), 0);
      this.combatUpdates.push(new CombatUpdate("Attack", "burn", entity.position ? entity.position : "pc", "Burn", entity.tempStats.burn));
    }
    if(entity.tempStats.poison){
      entity.currentHealth = Math.max((entity.currentHealth + entity.tempStats.poison), 0);
      this.combatUpdates.push(new CombatUpdate("Attack", "poison", entity.position ? entity.position : "pc", "Poison", entity.tempStats.poison));
    }
  }

  updateMonsterStats(){
    for(let monster of this.monsters){
      monster = this.monsterServices.updateStats(monster);
    }
  }

  selectAbility(monster){
    let abilityChance = (30 + monster.level + (monster.type === "Rogue" ? 5 : monster.type === "Wizard" ? 10 : 0))*(monster.boss ? 1.5 : monster.miniboss ? 1.25 : 1);
    if(Math.random()*100 < abilityChance){
      return monster.abilities[Math.floor(Math.random()*(monster.abilities.length))];
    } else {
      return {
        name: "Basic Attack",
        type: "Attack",
        hits: 1,
        modifier: 1,
        factor: 20
      }
    }
  }

  updateFlags(flags){
    for(let flag in flags){
      if(flags[flag] > 0){
        flags[flag] -= 1;
      }
    }
  }

  decrementBuffs(buffs){
    let i = buffs.length;

    while(i--){
      if(buffs[i].duration > 0){
        buffs[i].duration -= 1;
      }
      if (!buffs[i].duration) {
        buffs.splice(i, 1);
        continue;
      } 
    }
  }

  itemHeal(value){
    this.pc.currentHealth = (this.pc.currentHealth + this.pc.healthTotal*(value/100)) > this.pc.healthTotal
      ? this.pc.healthTotal
      : this.pc.currentHealth + this.pc.healthTotal*(value/100);
  }

  itemEnergize(value){
    this.pc.currentEnergy = (this.pc.currentEnergy + this.pc.energyTotal*(value/100)) > this.pc.energyTotal
      ? this.pc.energyTotal
      : this.pc.currentEnergy + this.pc.energyTotal*(value/100);
  }

}