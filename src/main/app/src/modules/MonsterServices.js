class MonsterServices {
  constructor(dungeonMod){
    this.monster = null;
    this.healthMod = dungeonMod ? (dungeonMod[0]==='h' ? 1 + dungeonMod.match(/(\d+)/)[1]/100 : 1) : 1;
    this.damageMod = dungeonMod ? (dungeonMod[0]==='d' ? 1 + dungeonMod.match(/(\d+)/)[1]/100 : 1) : 1;
  }

  updateStats(monster){
    this.monster = monster;
    if(!this.monster.hasOwnProperty('tempStats')){
      this.resetTempStats(this.monster);
    }

    if(Object.keys(this.monster.buffs).length){
      this.parseBuffs();
    }

    if(Object.keys(this.monster.debuffs).length){
      this.parseDebuffs();
    }

    this.monster.healthTotal = this.calcHealthTotal();
    if(this.monster.currentHealth === -1){
      this.monster.currentHealth = this.monster.healthTotal;
    }
    this.monster.experience = this.calcExperience();
    this.monster.powerTotal = this.powerTotal();
    this.monster.physResist = this.calcPhysResist();
    this.monster.magResist = this.calcMagResist();
    this.monster.critChance = this.calcCritChance();
    this.monster.dodgeChance = this.calcDodgeChance();
    this.monster.baseDamage = this.calcBaseDamage();

    return this.monster;
  }

  resetTempStats(monster){
    monster.flags = {};
    monster.buffs = [];
    monster.debuffs = [];
    monster.tempStats = {
      poison: 0,
      bleed: 0,
      burn: 0,
      power: 0,
      armor: 0,
      dodgeRating: 0,
      critRating: 0
    }
  }

  parseBuffs(){
    for(let buff in this.monster.buffs){
      for(let stat in this.monster.tempStats){
          stat += buff[stat];
      }
    }
  }

  parseDebuffs(){
    for(let debuff in this.monster.debuffs){
      for(let stat in this.monster.tempStats){
        stat += debuff[stat];
      }
    }
  }

  powerTotal(){
    return this.monster.power*1 + this.monster.tempStats.power;
  }
  calcExperience(){
    return Math.round((10+this.monster.level*5)*(this.monster.boss ? 8 : this.monster.miniboss ? 4 : 1) + Number.EPSILON);
  }
  calcHealthTotal(){
    return Math.round(this.monster.health*this.healthMod + Number.EPSILON);
  }
  calcPhysResist(){
    return Math.round((95-95**(1-(((((this.monster.armor + this.monster.tempStats.armor) >= 0 ? this.monster.armor : 0)*(1/((13+this.monster.level)/14)))**0.55)/100)) + Number.EPSILON)*10)/10
  }
  calcMagResist(){
    return Math.round((75-75**(1-(((((this.monster.armor + this.monster.tempStats.armor) >= 0 ? this.monster.armor : 0)*(1/((13+this.monster.level)/14)))**0.4)/100)) + Number.EPSILON)*10)/10
  }
  calcCritChance(){
    return Math.round((((this.monster.critRating + this.monster.tempStats.critRating)*(1/((13+this.monster.level)/14)))**0.78 + Number.EPSILON)*10)/10
  }
  calcDodgeChance(){
    return Math.round((95-(95*(1-((((this.monster.dodgeRating  + this.monster.tempStats.dodgeRating)*(1/((13+this.monster.level)/14)))**0.7)/100))) + Number.EPSILON)*10)/10
  }
  calcBaseDamage(){
    return Math.round((4+this.monster.level)*(this.monster.boss ? 2 : this.monster.miniboss ? 1.5 : 1)*this.damageMod + Number.EPSILON)
  }

}

export default MonsterServices;