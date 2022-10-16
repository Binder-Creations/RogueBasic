export default class PcServices {
  static #rogueBase = {
    armorBonus: 0,
    armorPenBonus: 0,
    constitutionBonus: 0,
    critRatingBonus: 10,
    dexterityBonus: 0,
    dodgeRatingBonus: 10,
    energyBonus: 20,
    energyRegenBonus: 2,
    healthBonus: 20,
    healthRegenBonus: 2,
    powerBonus: 20,
    intelligenceBonus: 0,
    strengthBonus: 0
  };
  static #wizardBase = {
    armorBonus: 0,
    armorPenBonus: 0,
    constitutionBonus: 0,
    critRatingBonus: 0,
    dexterityBonus: 0,
    dodgeRatingBonus: 5,
    energyBonus: 50,
    energyRegenBonus: 8,
    healthBonus: 10,
    healthRegenBonus: 1,
    intelligenceBonus: 0,
    powerBonus: 15,
    strengthBonus: 0
  };
  static #warriorBase = {
    armorBonus: 10,
    armorPenBonus: 10,
    constitutionBonus: 0,
    critRatingBonus: 5,
    dexterityBonus: 0,
    dodgeRatingBonus: 0,
    energyBonus: 0,
    energyRegenBonus: 0,
    healthBonus: 30,
    healthRegenBonus: 5,
    intelligenceBonus: 0,
    powerBonus: 10,
    strengthBonus: 0
  };
  static #emptySlot = {
    armorBonus: 0,
    armorPenBonus: 0,
    constitutionBonus: 0,
    critRatingBonus: 0,
    dexterityBonus: 0,
    dodgeRatingBonus: 0,
    energyBonus: 0,
    energyRegenBonus: 0,
    healthBonus: 0,
    healthRegenBonus: 0,
    intelligenceBonus: 0,
    powerBonus: 0,
    strengthBonus: 0
  };
  static #bonuses = ["constitution", "strength", "dexterity", "intelligence", "power", "health", "healthRegen", "armor", "armorPen", "critRating", "dodgeRating", "energy", "energyRegen"];
  
  constructor(pc){
    this.pc = pc;
    
    this.healthTotalPrevious = null;
    this.energyTotalPrevious = null;

    if(this.pc.flags === undefined) this.pc.flags = {};
    if(this.pc.buffs === undefined) this.pc.buffs = [];
    if(this.pc.debuffs === undefined) this.pc.debuffs = [];

    this.baseStats = pc.characterClass === "Rogue" 
    ? PcServices.#rogueBase
    : pc.characterClass === "Wizard"
      ? PcServices.#wizardBase
      : PcServices.#warriorBase
  }

  updateStats(){
    this.resetTempStats();
    if(this.pc.buffs.length){
      this.parseBuffs();
    }

    if(this.pc.debuffs.length){
      this.parseDebuffs();
    }

    if(this.pc.experience >= this.pc.experienceNeeded){
      this.pc.experience = this.pc.experience - this.pc.experienceNeeded;
      this.pc.level = this.pc.level*1 + 1;
      this.pc.attributePoints = this.pc.attributePoints*1 + 4;
    }
    
    let head = this.pc.equippedHead && this.pc.equippedHead.type ? this.pc.equippedHead : PcServices.#emptySlot
    let body = this.pc.equippedBody && this.pc.equippedBody.type ? this.pc.equippedBody : PcServices.#emptySlot
    let back = this.pc.equippedBack && this.pc.equippedBack.type ? this.pc.equippedBack : PcServices.#emptySlot
    let neck = this.pc.equippedNeck && this.pc.equippedNeck.type ? this.pc.equippedNeck : PcServices.#emptySlot
    let primary = this.pc.equippedPrimary && this.pc.equippedPrimary.type ? this.pc.equippedPrimary : PcServices.#emptySlot
    let secondary = this.pc.equippedSecondary && this.pc.equippedSecondary.type ? this.pc.equippedSecondary : PcServices.#emptySlot
    let equipped = [this.baseStats, head, body, back, neck, primary, secondary];

    for(const bonus of PcServices.#bonuses){
      let tempValue = 0;
      for(const slot of equipped){
        tempValue += slot[bonus+"Bonus"];
      }
      this.pc[bonus+"Bonus"] = tempValue;
    }

    this.pc.experienceNeeded = this.experienceNeededCalc();
    this.pc.conTotal = this.conTotal();
    this.pc.strTotal = this.strTotal();
    this.pc.dexTotal = this.dexTotal();
    this.pc.intTotal = this.intTotal();
    this.pc.powerTotal = this.powerTotal();
    this.pc.healthTotal = this.healthTotal();
    this.pc.healthRegenTotal = this.healthRegenTotal();
    this.pc.armorTotal = this.armorTotal();
    this.pc.armorPenTotal = this.armorPenTotal();
    this.pc.critRatingTotal = this.critRatingTotal();
    this.pc.dodgeRatingTotal = this.dodgeRatingTotal();
    this.pc.energyTotal = this.energyTotal();
    this.pc.energyRegenTotal = this.energyRegenTotal();
    this.pc.physResist = this.physResistCalc();
    this.pc.magResist = this.magResistCalc();
    this.pc.physResistReduction = this.physResistReductionCalc();
    this.pc.magResistReduction = this.magResistReductionCalc();
    this.pc.critChance = this.critChanceCalc();
    this.pc.dodgeChance = this.dodgeChanceCalc();
    this.pc.baseDamage = this.baseDamageCalc();

    if(this.pc.currentHealth === -1){
      this.pc.currentHealth = this.healthTotal();
    }

    if(this.pc.currentEnergy === -1){
      this.pc.currentEnergy = this.energyTotal();
    }

    if(this.healthTotalPrevious){
      if(this.healthTotalPrevious > this.healthTotal()){
        this.pc.currentHealth -= (this.healthTotalPrevious - this.healthTotal());
        this.pc.currentHealth = this.pc.currentHealth > 0
          ? this.pc.currentHealth
          : 1
      }
      if(this.healthTotalPrevious < this.healthTotal()){
        this.pc.currentHealth += (this.healthTotal() - this.healthTotalPrevious);
        this.pc.currentHealth = this.pc.currentHealth > this.healthTotal()
          ? this.healthTotal()
          : this.pc.currentHealth
      }
    }

    if(this.energyTotalPrevious){
      if(this.energyTotalPrevious > this.energyTotal()){
        this.pc.currentEnergy -= (this.energyTotalPrevious - this.energyTotal());
        this.pc.currentEnergy = this.pc.currentEnergy > 0
          ? this.pc.currentEnergy
          : 1
      }
      if(this.energyTotalPrevious < this.energyTotal()){
        this.pc.currentEnergy += (this.energyTotal() - this.energyTotalPrevious);
        this.pc.currentEnergy = this.pc.currentEnergy > this.energyTotal()
          ? this.energyTotal()
          : this.pc.currentEnergy
      }
    }
    this.healthTotalPrevious = this.healthTotal();
    this.energyTotalPrevious = this.energyTotal();
  }

  clearBuffs(){
    this.pc.flags = {};
    this.pc.buffs = [];
    this.pc.debuffs = [];
  }

  resetTempStats(){
    this.pc.tempStats = {
      regenerate: 0,
      poison: 0,
      bleed: 0,
      burn: 0,
      constitution: 0,
      strength: 0,
      dexterity: 0,
      intelligence: 0,
      power: 0,
      health: 0,
      healthRegen: 0,
      armorPen: 0,
      armor: 0,
      dodgeRating: 0,
      critRating: 0,
      energy: 0,
      energyRegen: 0
    }
  }

  parseBuffs(){
    for(let buff in this.pc.buffs){
      for(let stat in this.pc.tempStats){
          this.pc.tempStats[stat] += this.pc.buffs[buff][stat];
      }
    }
  }

  parseDebuffs(){
    for(let debuff in this.pc.debuffs){
      for(let stat in this.pc.tempStats){
        this.pc.tempStats[stat] -= this.pc.debuffs[debuff][stat];
      }
    }
  }

  conTotal(){
    return Math.max((this.pc.constitution*1 + this.pc.constitutionBonus*1 + this.pc.tempStats.constitution), 0);
  }
  strTotal(){
    return Math.max((this.pc.strength*1 + this.pc.strengthBonus*1  + this.pc.tempStats.strength), 0);
  }
  dexTotal(){
    return Math.max((this.pc.dexterity*1 + this.pc.dexterityBonus*1  + this.pc.tempStats.dexterity), 0);
  }
  intTotal(){
    return Math.max((this.pc.intelligence*1 + this.pc.intelligenceBonus*1  + this.pc.tempStats.intelligence), 0);
  }
  powerTotal() {
		return Math.max(((Math.round(this.strTotal()/2) + Math.round(this.dexTotal()/2) + Math.round(this.intTotal()/2) + this.pc.powerBonus*1  + this.pc.tempStats.power)), 0);
	}	
	healthTotal() {
		return Math.round(this.conTotal()*(4+(this.pc.level-1)/2) + this.pc.healthBonus*1 +  + this.pc.tempStats.health + Number.EPSILON);
	}
	healthRegenTotal() {
		return Math.round(this.conTotal()/3) + this.pc.healthRegenBonus*1 +  + this.pc.tempStats.healthRegen;
	}
	armorPenTotal() {
		return this.strTotal()*2 + this.pc.armorPenBonus*1 +  + this.pc.tempStats.armorPen;
	}	
	armorTotal() {
		return Math.max((this.strTotal()*2 + this.pc.armorBonus*1  + this.pc.tempStats.armor), 0);
	}	
	dodgeRatingTotal() {
		return Math.max((this.dexTotal()*2 + this.pc.dodgeRatingBonus*1  + this.pc.tempStats.dodgeRating), 0);
	}	
  critRatingTotal() {
		return Math.max((this.dexTotal()*2 + this.pc.critRatingBonus*1 + this.pc.tempStats.critRating), 0);
	}
	energyTotal() {
		return Math.round(this.intTotal()*(6+(this.pc.level - 1)*0.75) + this.pc.energyBonus*1  + this.pc.tempStats.energy + Number.EPSILON);
	}
	energyRegenTotal() {
		return Math.round(this.intTotal()/2) + this.pc.energyRegenBonus*1 + this.pc.tempStats.energyRegen;
	}
  experienceNeededCalc(){
    return Math.round(this.pc.level**1.5)*100;
  }
  physResistCalc(){
    return Math.round((95-95**(1-(((this.armorTotal()*(1/((13+this.pc.level)/14)))**0.55)/100)) + Number.EPSILON)*10)/10
  }
  magResistCalc(){
    return Math.round((75-75**(1-(((this.armorTotal()*(1/((13+this.pc.level)/14)))**0.4)/100)) + Number.EPSILON)*10)/10
  }
  physResistReductionCalc(){
    return Math.round((95-95**(1-(((this.armorPenTotal()*(1/((13+this.pc.level)/14)))**0.55)/100)) + Number.EPSILON)*10)/10
  }
  magResistReductionCalc(){
    return Math.round((75-75**(1-(((this.armorPenTotal()*(1/((13+this.pc.level)/14)))**0.4)/100)) + Number.EPSILON)*10)/10
  }
  critChanceCalc(){
    return Math.round(((this.critRatingTotal()*(1/((13+this.pc.level)/14)))**0.78 + Number.EPSILON)*10)/10
  }
  dodgeChanceCalc(){
    return Math.round((95-(95*(1-(((this.dodgeRatingTotal()*(1/((13+this.pc.level)/14)))**0.7)/100))) + Number.EPSILON)*10)/10
  }
  baseDamageCalc(){
    return 8+this.pc.level*2
  }
  regenHealth(){
    this.pc.currentHealth = Math.round(((this.pc.currentHealth + this.pc.healthRegenTotal) > this.pc.healthTotal ? this.pc.healthTotal : this.pc.currentHealth + this.pc.healthRegenTotal) + Number.EPSILON);
  }
  regenEnergy(){
    this.pc.currentEnergy = Math.round(((this.pc.currentEnergy + this.pc.energyRegenTotal) > this.pc.energyTotal ? this.pc.energyTotal : this.pc.currentEnergy + this.pc.energyRegenTotal) + Number.EPSILON);
  }
}