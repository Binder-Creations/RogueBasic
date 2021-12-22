import CombatUpdate from "./CombatUpdate";
import {unmountComponentAtNode} from "react-dom";

class AbilityServices {

  static pcAttack(ability, pc, monsters, combatUpdates){
    for(let i = 0; i < ability.hits; i++){
      if(monsters.length){
        let targets = this[ability.target+"Target"](monsters);
        if(ability.selfFlags || ability.targetFlags){
          this.parseFlags(ability, pc, targets);
        }
        targets.forEach(monster => {
          let crit = this.crit(pc, monster);
          let damage = this.calcDamage(this.calcBase(ability, pc.baseDamage), pc, monster, crit);
          crit = damage ? crit : false;

          monster.currentHealth = (monster.currentHealth - damage) >= 0 ? (monster.currentHealth - damage) : 0;
          combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", damage, crit));
        });
        this.corpseCollector(pc, monsters, combatUpdates);
      } else {
        break;
     }
    }
  }

  static monsterAttack(ability, pc, monster, monsters, combatUpdates){
    for(let i = 0; i < ability.hits; i++){
      if(ability.selfFlags || ability.targetFlags){
        this.parseFlags(ability, monster, pc);
      }

      let crit = pc.flags.fortress ? false : this.crit(monster, pc);
      let damage = this.calcDamage(this.calcBase(ability, monster.baseDamage), monster, pc, crit);
      crit = damage ? crit : false;

      if(monster.flags.attackEnergy){
        pc.currentEnergy = (pc.currentEnergy - damage) >= 0 ? (pc.currentEnergy - damage) : 0;
      } else {
        pc.currentHealth = (pc.currentHealth - damage) >= 0 ? (pc.currentHealth - damage) : 0;
      }

      combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", monster.name, damage, crit, monster.flags.attackEnergy));
      this.pcCorpseCollector(pc, combatUpdates);
    }
  }

  static monsterHeal(ability, pc, monster, monsters, combatUpdates){
    if(ability.selfFlags || ability.targetFlags){
      this.parseFlags(ability, monster, pc);
    }
    let healValue = this.calcValue(this.calcBase(ability, monster.baseDamage), monster);
    if(ability.target === "self"){
      monster.currentHealth = (monster.currentHealth + healValue) > monster.healthTotal ? monster.healthTotal : (monster.currentHealth + healValue);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, monster.name, healValue));
    }
    if(ability.target === "allMonsters"){
      for(let m of monsters){
        m.currentHealth = (m.currentHealth + healValue) > m.healthTotal ? m.healthTotal : (m.currentHealth + healValue);
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, m.position, monster.name, healValue));
      }
    }

  }

  static pcChain(ability, pc, monsters, combatUpdates){
    let tempAbility = {...ability};
    let bounces = tempAbility.hits - 1;
    
    tempAbility.hits = 1;
    this.pcAttack(tempAbility, pc, monsters, combatUpdates);
    
    tempAbility.hits = bounces;
    tempAbility.target = "random";
    this.pcAttack(tempAbility, pc, monsters, combatUpdates);
  }

  static pcBuff(ability, pc, monsters, combatUpdates){
    if(ability.selfFlags || ability.targetFlags){
      this.parseFlags(ability, pc);
    }

    let value = this.calcValue(this.calcBase(ability, pc.baseDamage), pc)
    for(let buff of ability.buffs){
      this.parseBuff(buff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", "pc", value));
    }
    this.mergeBuffs(pc.buffs, ability.buffs);
  }

  static monsterBuff(ability, pc, monster, monsters, combatUpdates){
    if(ability.selfFlags || ability.targetFlags){
      this.parseFlags(ability, monster, monsters);
    }
    
    let value = this.calcValue(this.calcBase(ability, monster.baseDamage), monster)
    let targets = ability.target === "self" 
      ? [monster]
      : monsters;
    for(let target of targets){
      for(let buff of ability.buffs){
        this.parseBuff(buff, value);
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, target.position, target.name, value));
      }
      this.mergeBuffs(target.buffs, ability.buffs);
    }
  }

  static pcDebuff(ability, pc, monsters, combatUpdates){
    let targets = this[ability.target+"Target"](monsters);
    if(ability.selfFlags || ability.targetFlags){
      this.parseFlags(ability, pc, targets);
    }

    let value = this.calcValue(this.calcBase(ability, pc.baseDamage), pc);

    for(let monster of targets) { 
      for(let debuff of ability.debuffs){
        this.parseBuff(debuff, value);
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", value));
      }
      this.mergeBuffs(monster.debuffs, ability.debuffs)
    } 
  }

  static monsterDebuff(ability, pc, monster, monsters, combatUpdates){
    if(ability.selfFlags || ability.targetFlags){
      this.parseFlags(ability, monster, [pc]);
    }
    
    let value = this.calcValue(this.calcBase(ability, monster.baseDamage), monster);

    for(let debuff of ability.debuffs){
      this.parseBuff(debuff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", monster.name, value));
    }
    this.mergeBuffs(pc.debuffs, ability.debuffs) 
  }

  static randomTarget(monsters){
    return [monsters[Math.floor(Math.random()*monsters.length)]];
  }

  static frontLeftTarget(monsters){
    return [this.targetByPosition(monsters, ['f', 'b'], ['L', 'C', 'R'])];
  }

  static frontCenterTarget(monsters){
    return [this.targetByPosition(monsters, ['f', 'b'], ['C', 'L', 'R'])];
  }

  static frontRightTarget(monsters){
    return [this.targetByPosition(monsters, ['f', 'b'], ['R', 'C', 'L'])];
  }

  static backLeftTarget(monsters){
    return [this.targetByPosition(monsters, ['b', 'f'], ['L', 'C', 'R'])];
  }

  static backCenterTarget(monsters){
    return [this.targetByPosition(monsters, ['b', 'f'], ['C', 'L', 'R'])];
  }

  static backRightTarget(monsters){
    return [this.targetByPosition(monsters, ['b', 'f'], ['R', 'C', 'L'])];
  }

  static frontRowTarget(monsters){
    return this.targetByRow(monsters, 'f', 'b');
  }

  static coneTarget(monsters){
    let targets = this.targetByRow(monsters, 'b'); 
    let frontCenter = monsters.find(monster => monster.position === "frontCenter");
    
    if(frontCenter){
      targets.push(frontCenter);
    }

    return targets;
  }

  static lowHealthTarget(monsters){
    return [this.targetByLowStat(monsters, "currentHealth")];
  }

  static highHealthTarget(monsters){
    return [this.targetByHighStat(monsters, "currentHealth")]
  }

  static targetByRow(monsters, row, alternate){
    let targets = [];
    monsters.forEach(monster => {
      if(monster.position[0] === row){
        targets.push(monster);
      }
    });
    if(!targets.length && alternate){
      monsters.forEach(monster => {
        if(monster.position[0] === alternate){
          targets.push(monster);
        }
      });  
    }
    return targets;
  }

  static targetByPosition(monsters, rowPriority, columnPriority){
    for(let monster of monsters){
      monster.priority = rowPriority.indexOf(monster.position[0])*10 + columnPriority.indexOf(monster.position.match(/[A-Z][a-z]*/)[0][0]);
    }
    
    let i = 0;
    let priority = 99;
    let iPriority = 0;

    for(let monster of monsters){
      if(monster.priority < priority){
        priority = monster.priority;
        iPriority = i;
      }
      i++;
    }

    return monsters[iPriority]; 
  }

  static targetByHighStat(monsters, stat){
    let i = 0;
    let highestValue = 0;
    let iPriority = 0;

    for(let monster of monsters){
      if(monster[stat] > highestValue){
        highestValue = monster[stat];
        iPriority = i;
      }
      i++;
    }

    return monsters[iPriority]; 
  }

  static targetByLowStat(monsters, stat){
    let i = 0;
    let lowestValue = Number.MAX_VALUE;
    let iPriority = 0;

    for(let monster of monsters){
      if(monster[stat] < lowestValue){
        lowestValue = monster[stat];
        iPriority = i;
      }
      i++;
    }

    return monsters[iPriority]; 
  }

  static parseFlags(ability, self, targets){
    if(self && ability.selfFlags){
      for(let flag in ability.selfFlags){
        if(ability.selfFlags[flag]){
          self.flags[flag] = ability.selfFlags[flag];
        }
      }
    }
    if(targets && ability.targetFlags){
      for(let target of targets){
        for(let flag in ability.targetFlags){
          if(ability.targetFlags[flag]){
            target.flags[flag] = ability.targetFlags[flag];
          }
        }
      }
    }
  }

  static parseBuff(buff, value){
    for(let stat in buff){
      if(stat !== "duration"){
        if(buff[stat] === -1){
          buff[stat] = value;
        }
      }
    }
  }

  static mergeBuffs(actorBuffs, abilityBuffs){
    for(let abilityBuff of abilityBuffs){
      let actorMatch = actorBuffs.find(actorBuff => abilityBuff.name === actorBuff.name);
      if(!actorMatch){
        actorBuffs.push({...abilityBuff});
      } else {
        actorBuffs[actorBuffs.indexOf(actorMatch)] = {...abilityBuff};
      }
    }
  }

  static crit(source, target){
    return source.flags.crit ? true : (target.flags.fortress ? false : (Math.random()*100 <= this.lv(source.critChance, source.level - target.level) ? true : false));
  }

  static calcDamage(base, source, target, crit){
    let sDiff = source.level - target.level;
    let tDiff = target.level - source.level;
    let hit = source.flags.hit ? true : (target.flags.dodge ? false : (Math.random()*100 > this.lv(target.dodgeChance, tDiff) ? true : false));
    if(!hit){
      return 0;
    }

    let resist = source.flags.fullPen ? 0 : (source.flags.magic ? (this.lv(target.magResist, tDiff) - (source.magResistReduction ? (this.lv(source.magResistReduction, sDiff)*(source.flags.highPen ? 1.5 : 1)):0)) : (this.lv(target.physResist, tDiff) - (source.physResistReduction ? (this.lv(source.physResistReduction, sDiff)*(source.flags.highPen ? 1.5 : 1)) : 0)));
    resist = resist < 0 ? 0 : resist;
    return Math.round(base*(1+this.lv(source.powerTotal, sDiff)/100)*(1-resist/100)*(crit ? source.flags.critDoubleDamage ? 4 : 2 : 1) + Number.EPSILON);
  }

  static calcValue(base, entity){
    return Math.round(base*(1+entity.powerTotal/100) + Number.EPSILON);
  }

  static calcBase(ability, baseDamage){
    return ability.factor ? this.randomize(baseDamage*ability.modifier, ability.factor) : baseDamage*ability.modifier;
  }

  static corpseCollector(pc, monsters, combatUpdates){
    let i = monsters.length;
    while (i--){
      if(monsters[i].currentHealth <= 0){
        pc.experience += monsters[i].experience;
        combatUpdates.push(new CombatUpdate("death", "death", monsters[i].position, monsters[i].name));
        combatUpdates.push(new CombatUpdate("experience", "experience", monsters[i].position, monsters[i].name, monsters[i].experience));
        let buffs = document.getElementById(monsters[i].position + "Buffs");
        let debuffs = document.getElementById(monsters[i].position + "Debuffs"); 
        if(buffs){
          unmountComponentAtNode(buffs);
        }
        if(debuffs){
          unmountComponentAtNode(debuffs);
        }
        monsters.splice(i, 1);
      }
    }
    if(!monsters.length){
      combatUpdates.push(new CombatUpdate("endCombat"));
    }
  }

  static pcCorpseCollector(pc, combatUpdates){
    if(pc.currentHealth <= 0){
      combatUpdates.push(new CombatUpdate("gameOver"));
    }
  }

  static lv(base, lvDifference){
    return base*(0.95**lvDifference);
  }

  static randomize(value, factor){
    return Math.round(value*(1-(factor/100)+((Math.random()*factor)/50)) + Number.EPSILON);
  }

  static min(pc, modifier, factor){
    return Math.floor(pc.baseDamage*(1-(factor/100))*modifier*(1+pc.powerTotal/100));
  }

  static max(pc, modifier, factor){
    return Math.ceil(pc.baseDamage*(1+(factor/100))*modifier*(1+pc.powerTotal/100));
  }

  static round(pc, modifier){
    return Math.round(pc.baseDamage*modifier*(1+pc.powerTotal/100) + Number.EPSILON);
  }
}

export default AbilityServices;
export const min = AbilityServices.min;
export const max = AbilityServices.max;
export const round = AbilityServices.round;