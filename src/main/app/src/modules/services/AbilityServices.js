import {unmountComponentAtNode} from "react-dom";
import AppServices from "./AppServices";
import CombatUpdate from "../beans/CombatUpdate";

export const pcAttack = (ability, pc, monsters, combatUpdates) => {
  for(let i = 0; i < ability.hits; i++){
    if(monsters.length){
      let targets = targeting[ability.target](monsters);
      if(ability.selfFlags || ability.targetFlags){
        parseFlags(ability, pc, targets);
      }
      targets.forEach(monster => {
        let crit = isCrit(pc, monster);
        let damage = calcDamage(calcBase(ability, pc.baseDamage), pc, monster, crit);
        crit = damage ? crit : false;

        if(ability.targetFlags && ability.targetFlags.stun && !damage){
          monster.flags.stun = 0;
        }

        monster.currentHealth = (monster.currentHealth - damage) >= 0 ? (monster.currentHealth - damage) : 0;
        combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", damage, crit));
      });
      corpseCollector(pc, monsters, combatUpdates);
    } else {
      break;
    }
  }
}

const targeting = {
  random: (monsters) => {
    return [monsters[Math.floor(Math.random()*monsters.length)]];
  },
  frontLeft: (monsters) => {
    return [targetByPosition(monsters, ['f', 'b'], ['L', 'C', 'R'])];
  },
  frontCenter: (monsters) => {
    return [targetByPosition(monsters, ['f', 'b'], ['C', 'L', 'R'])];
  },
  frontRight: (monsters) => {
    return [targetByPosition(monsters, ['f', 'b'], ['R', 'C', 'L'])];
  },
  backLeft: (monsters) => {
    return [targetByPosition(monsters, ['b', 'f'], ['L', 'C', 'R'])];
  },
  backCenter: (monsters) => {
    return [targetByPosition(monsters, ['b', 'f'], ['C', 'L', 'R'])];
  },
  backRight: (monsters) => {
    return [targetByPosition(monsters, ['b', 'f'], ['R', 'C', 'L'])];
  },
  frontRow: (monsters) => {
    return targetByRow(monsters, 'f', 'b');
  },
  cone: (monsters) => {
    let targets = targetByRow(monsters, 'b'); 
    let frontCenter = monsters.find(monster => monster.position === "frontCenter");
    
    if(frontCenter){
      targets.push(frontCenter);
    }

    return targets;
  },
  lowHealth: (monsters) => {
    return [targetByLowStat(monsters, "currentHealth")];
  },
  highHealth: (monsters) => {
    return [targetByHighStat(monsters, "currentHealth")]
  }
}

export const monsterAttack = (ability, pc, monster, monsters, combatUpdates, c) => {
  for(let i = 0; i < ability.hits; i++){
    if(ability.selfFlags || ability.targetFlags){
      parseFlags(ability, monster, pc);
    }

    let crit = pc.flags.fortress ? false : isCrit(monster, pc);
    let damage = calcDamage(calcBase(ability, monster.baseDamage), monster, pc, crit);
    crit = damage ? crit : false;

    if(monster.flags.attackEnergy){
      pc.currentEnergy = (pc.currentEnergy - damage) >= 0 ? (pc.currentEnergy - damage) : 0;
    } else {
      pc.currentHealth = (pc.currentHealth - damage) >= 0 ? (pc.currentHealth - damage) : 0;
    }

    combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", monster.name, damage, crit, monster.flags.attackEnergy));
    if(pcCorpseCollector(pc, monster, ability, damage, crit, c)){
      break;
    }
  }
}

export const monsterHeal = (ability, pc, monster, monsters, combatUpdates) => {
  if(ability.selfFlags || ability.targetFlags){
    parseFlags(ability, monster, pc);
  }
  let healValue = calcValue(calcBase(ability, monster.baseDamage), monster);
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

export const pcChain = (ability, pc, monsters, combatUpdates) => {
  let tempAbility = {...ability};
  let bounces = tempAbility.hits - 1;
  
  tempAbility.hits = 1;
  pcAttack(tempAbility, pc, monsters, combatUpdates);
  
  tempAbility.hits = bounces;
  tempAbility.target = "random";
  pcAttack(tempAbility, pc, monsters, combatUpdates);
}

export const pcBuff = (ability, pc, monsters, combatUpdates) => {
  if(ability.selfFlags || ability.targetFlags){
    parseFlags(ability, pc);
  }

  let value = calcValue(calcBase(ability, pc.baseDamage), pc)
  for(let buff of ability.buffs){
    parseBuff(buff, value);
    combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", "pc", value));
  }
  mergeBuffs(pc.buffs, ability.buffs);
}

export const monsterBuff = (ability, pc, monster, monsters, combatUpdates) => {
  if(ability.selfFlags || ability.targetFlags){
    parseFlags(ability, monster, monsters);
  }
  
  let value = calcValue(calcBase(ability, monster.baseDamage), monster)
  let targets = ability.target === "self" 
    ? [monster]
    : monsters;
  for(let target of targets){
    for(let buff of ability.buffs){
      parseBuff(buff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, target.position, target.name, value));
    }
    mergeBuffs(target.buffs, ability.buffs);
  }
}

export const pcDebuff = (ability, pc, monsters, combatUpdates) => {
  let targets = this[ability.target+"Target"](monsters);
  if(ability.selfFlags || ability.targetFlags){
    parseFlags(ability, pc, targets);
  }

  let value = calcValue(calcBase(ability, pc.baseDamage), pc);

  for(let monster of targets) { 
    for(let debuff of ability.debuffs){
      parseBuff(debuff, value);
      combatUpdates.push(new CombatUpdate(ability.type, ability.name, monster.position, "pc", value));
    }
    mergeBuffs(monster.debuffs, ability.debuffs)
  } 
}

export const monsterDebuff = (ability, pc, monster, monsters, combatUpdates) => {
  if(ability.selfFlags || ability.targetFlags){
    parseFlags(ability, monster, [pc]);
  }

  let value = calcValue(calcBase(ability, monster.baseDamage), monster);

  for(let debuff of ability.debuffs){
    parseBuff(debuff, value);
    combatUpdates.push(new CombatUpdate(ability.type, ability.name, "pc", monster.name, value));
  }
  mergeBuffs(pc.debuffs, ability.debuffs) 
}

export const targetByRow = (monsters, row, alternate) => {
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

export const targetByPosition = (monsters, rowPriority, columnPriority) => {
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

export const targetByHighStat = (monsters, stat) => {
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

export const targetByLowStat = (monsters, stat) => {
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

export const parseFlags = (ability, self, targets) => {
  if(self && ability.selfFlags){
    for(const [flag, value] in ability.selfFlags){
      if(value){
        self.flags[flag] = value;
      }
    }
  }
  if(targets && ability.targetFlags){
    for(let target of targets){
      for(const [flag, value] in ability.targetFlags){
        if(value){
          target.flags[flag] = value;
        }
      }
    }
  }
}

export const parseBuff = (buff, value) => {
  for(let stat in buff){
    if(stat !== "duration"){
      if(buff[stat] === -1){
        buff[stat] = value;
      }
    }
  }
}

export const mergeBuffs = (actorBuffs, abilityBuffs) => {
  for(let abilityBuff of abilityBuffs){
    let actorMatch = actorBuffs.find(actorBuff => abilityBuff.name === actorBuff.name);
    if(!actorMatch){
      actorBuffs.push({...abilityBuff});
    } else {
      actorBuffs[actorBuffs.indexOf(actorMatch)] = {...abilityBuff};
    }
  }
}

export const isCrit = (source, target) => {
  return source.flags.crit ? true : (target.flags.fortress ? false : (Math.random()*100 <= lv(source.critChance, source.level - target.level) ? true : false));
}

export const calcDamage = (base, source, target, crit) => {
  let sDiff = source.level - target.level;
  let tDiff = target.level - source.level;
  let hit = source.flags.hit ? true : (target.flags.dodge ? false : (Math.random()*100 > lv(target.dodgeChance, tDiff) ? true : false));
  if(!hit){
    return 0;
  }

  let resist = source.flags.fullPen ? 0 : (source.flags.magic ? (lv(target.magResist, tDiff) - (source.magResistReduction ? (lv(source.magResistReduction, sDiff)*(source.flags.highPen ? 1.5 : 1)):0)) : (lv(target.physResist, tDiff) - (source.physResistReduction ? (lv(source.physResistReduction, sDiff)*(source.flags.highPen ? 1.5 : 1)) : 0)));
  resist = resist < 0 ? 0 : resist;
  return Math.round(base*(1+lv(source.powerTotal, sDiff)/100)*(1-resist/100)*(crit ? source.flags.critDoubleDamage ? 4 : 2 : 1) + Number.EPSILON);
}

export const calcValue = (base, entity) => {
  return Math.round(base*(1+entity.powerTotal/100) + Number.EPSILON);
}

export const calcBase = (ability, baseDamage) => {
  return ability.factor ? randomize(baseDamage*ability.modifier, ability.factor) : baseDamage*ability.modifier;
}

export const corpseCollector = (pc, monsters, combatUpdates) => {
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

export const pcCorpseCollector = (pc, monster, ability, damage, crit, c) => {
  if(pc.currentHealth <= 0){
    AppServices.getInstance().gameOver(new CombatUpdate("gameOver", ability.name, "pc", monster.name, damage, crit));
  }
}

export const lv = (base, lvDifference) => {
  return base*(0.95**lvDifference);
}

export const randomize = (value, factor) => {
  return Math.round(value*(1-(factor/100)+((Math.random()*factor)/50)) + Number.EPSILON);
}

export const min = (pc, modifier, factor) => {
  return Math.floor(pc.baseDamage*(1-(factor/100))*modifier*(1+pc.powerTotal/100));
}

export const max = (pc, modifier, factor) => {
  return Math.ceil(pc.baseDamage*(1+(factor/100))*modifier*(1+pc.powerTotal/100));
}

export const round = (pc, modifier) => {
  return Math.round(pc.baseDamage*modifier*(1+pc.powerTotal/100) + Number.EPSILON);
}