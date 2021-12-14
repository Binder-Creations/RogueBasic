import CombatUpdate from "./CombatUpdate";

class AbilityServices {

  static pcAttack(ability, pc, monsters, combatUpdates){
    for(let i = 0; i < ability.hits; i++){
      if(monsters.length){
        let targets = this[ability.target+"Target"](monsters);
        if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
          this.parseFlags(ability, pc, targets, monsters);
        }
        targets.forEach(monster => {
          let crit = this.crit(pc, monster);
          let damage = this.calcDamagePc(AbilityServices.randomize(pc.baseDamage*ability.modifier, ability.factor), pc, monster, crit);
          if(damage === 0){
            crit = false;
          }
          monster.currentHealth = (monster.currentHealth - damage) >= 0 ? (monster.currentHealth - damage) : 0;
          combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", damage, crit));
        });
        this.corpseCollector(monsters, combatUpdates);
      } else {
        break;
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
    if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
      this.parseFlags(ability, pc);
    }

    let value = this.calcValue(this.randomize(pc.baseDamage*ability.modifier, ability.factor), pc)
    for(let buff of ability.buffs){
      this.parseBuff(buff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", "pc", value));
    }
    this.mergeBuffs(pc.buffs, ability.buffs);
  }

  static monsterBuff(ability, monster, monsters, combatUpdates){
    if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
      this.parseFlags(ability, null, monster);
    }
    
    let value = this.calcValue(this.randomize(monster.baseDamage*ability.modifier, ability.factor), monster)
    for(let buff of ability.buffs){
      this.parseBuff(buff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, monster.name, value));
    }
    this.mergeBuffs(monster.buffs, ability.buffs);
  }

  static monsterBuffAll(ability, source, monsters, combatUpdates){
    if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
      this.parseFlags(ability, null, null, monsters);
    }

    let value = this.calcValue(this.randomize(source.baseDamage*ability.modifier, ability.factor), source)
    for(let monster of monsters){
      for(let buff of ability.buffs){
        this.parseBuff(buff, value);
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, source.name, value));
      }
      this.mergeBuffs(monster.buffs, ability.buffs);
    };
  }

  static pcDebuff(ability, pc, monsters, combatUpdates){
    let targets = this[ability.target+"Target"](monsters);
    if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
      this.parseFlags(ability, pc, targets, monsters);
    }
    
    ability.debuffs = JSON.parse(ability.debuffs);

    targets.forEach(monster => { 
      for(let debuff in ability.debuffs){
        ability.debuffs[debuff].value = this.lv(this.calcValue(this.randomize(pc.baseDamage*ability.modifier, ability.factor), pc.level - monster.level));
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", ability.debuffs[debuff].value));
      }
      monster.debuffs = Object.assign(monster.debuffs, ability.debuffs);
    }); 
  }

  static monsterDebuff(ability, pc, monster, monsters, combatUpdates){
    if(ability.pcFlags || ability.monsterFlags || ability.allMonstersFlags){
      this.parseFlags(ability, pc);
    }
    
    ability.debuffs = JSON.parse(ability.debuffs);
    for(let debuff in ability.debuffs){
      ability.debuffs[debuff].value = this.lv(this.calcValue(this.randomize(monster.baseDamage*ability.modifier, ability.factor), monster.level - pc.level));
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", monster.name, ability.debuffs[debuff].value));
    }
    pc.debuffs = Object.assign(pc.debuffs, ability.debuffs);
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
      monster.priority = rowPriority.indexOf(monster.position[0])*10 + columnPriority.indexOf(monster.position.match(/[A-Z][a-z]*/)[0]);
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

  static parseFlags(ability, pc, monster, monsters){
    if(pc && ability.pcFlags){
      pc.flags = Object.assign(pc.flags, ability.pcFlags);
    }
    if(monster && ability.monsterFlags){
      monster.flags = Object.assign(monster.flags, ability.monsterFlags);
    }
    if(monsters && ability.allMonstersFlags){
      monsters.forEach(monster => {
        monster.flags = Object.assign(monster.flags, ability.allMonstersFlags);
      })
    }
  }

  static parseBuff(buff, value){
    for(let stat in buff){
      if(buff[stat] === -1){
        buff[stat] = value;
      }
    }
  }

  static mergeBuffs(actorBuffs, abilityBuffs){
    for(let abilityBuff of abilityBuffs){
      let actorMatch = actorBuffs.find(actorBuff => abilityBuff.name === actorBuff.name);
      if(!actorMatch){
        actorBuffs.push(abilityBuff);
      } else {
        actorBuffs[actorBuffs.indexOf(actorMatch)] = abilityBuff;
      }
    }
  }

  static crit(source, target){
    return source.flags.crit ? true : (target.flags.fortress ? false : (Math.random()*100 <= this.lv(source.critChance, source.level - target.level) ? true : false));
  }

  static calcDamagePc(base, pc, monster, crit){
    let pDiff = pc.level - monster.level;
    let mDiff = monster.level - pc.level;
    let hit = pc.flags.hit ? true : (monster.flags.dodge ? false : (Math.random()*100 > this.lv(monster.dodgeChance, mDiff) ? true : false));
    if(!hit){
      return 0;
    }

    let resist = pc.flags.fullPen ? 0 : (pc.flags.magic ? (this.lv(monster.magResist, mDiff) - this.lv(pc.magResistReduction, pDiff)*(pc.flags.highPen ? 1.5 : 1)) : (this.lv(monster.physResist, mDiff) - this.lv(pc.physResistReduction, pDiff)*(pc.flags.highPen ? 1.5 : 1)));
    resist = resist < 0 ? 0 : resist;
    return Math.round(base*(1+this.lv(pc.powerTotal, pDiff)/100)*(1-resist/100)*(crit ? pc.flags.critDoubleDamage ? 4 : 2 : 1) + Number.EPSILON);
  }

  static calcDamageMonster(base, pc, monster, critProp){
    let pDiff = pc.level - monster.level;
    let mDiff = monster.level - pc.level;

    let hit = monster.flags.hit ? true : (pc.flags.dodge ? false : (Math.random()*100 > this.lv(pc.dodgeChance, pDiff) ? true : false));
    if(!hit){
      return -1;
    }
    
    let crit = monster.flags.crit ? true : (pc.flags.fortress ? false : (Math.random()*100 <= this.lv(monster.critChance, mDiff) ? true : false));
    
    if(crit){
      critProp = true;
    }
    
    let resist = monster.flags.fullPen ? 0 : (monster.flags.magic ? this.lv(pc.magResist, pDiff) : this.lv(pc.physResist, pDiff));
    resist = resist < 0 ? 0 : resist;
    return Math.round(base*(1+this.lv(monster.powerTotal, mDiff)/100)*(1-resist/100)*(crit ? 2 : 1) + Number.EPSILON);
  }

  static calcValue(base, entity){
    return Math.round(base*(1+entity.powerTotal/100) + Number.EPSILON);
  }

  static corpseCollector(monsters, combatUpdates){
    let i = monsters.length;
    while (i--){
      if(monsters[i].currentHealth <= 0){
        combatUpdates.push(new CombatUpdate("death", "death", monsters[i].position, monsters[i].name));
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