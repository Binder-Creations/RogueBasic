class PcServices {
  constructor(characterClass){
    this.pc = null;
    this.healthTotalPrevious = null;
    this.energyTotalPrevious = null;
    this.emptySlot = {
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
      strengthBonus: 0}
    this.bonuses = ["constitution", "strength", "dexterity", "intelligence", "power", "health", "healthRegen", "armor", "armorPen", "critRating", "dodgeRating", "energy", "energyRegen"];
    this.baseStats = characterClass == "Rogue" 
    ? {
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
    }
    : characterClass == "Wizard"
      ? {
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
      }
      : {
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

  }
  
  setPc(pc){
    this.pc = pc;
  }
  conTotal(){
    return this.pc.constitution*1 + this.pc.constitutionBonus*1;
  }
  strTotal(){
    return this.pc.strength*1 + this.pc.strengthBonus*1;
  }
  dexTotal(){
    return this.pc.dexterity*1 + this.pc.dexterityBonus*1;
  }
  intTotal(){
    return this.pc.intelligence*1 + this.pc.intelligenceBonus*1;
  }
  powerTotal() {
		return (Math.round(this.strTotal()/2) + Math.round(this.dexTotal()/2) + Math.round(this.intTotal()/2) + this.pc.powerBonus*1);
	}
	
	healthTotal() {
		return this.conTotal()*4 + this.pc.healthBonus*1;
	}
	
	healthRegenTotal() {
		return Math.round(this.conTotal()/3) + this.pc.healthRegenBonus*1;
	}
	
	armorPenTotal() {
		return this.strTotal()*2 + this.pc.armorPenBonus*1;
	}
	
	armorTotal() {
		return this.strTotal()*2 + this.pc.armorBonus*1;
	}
	
	dodgeRatingTotal() {
		return this.dexTotal()*2 + this.pc.dodgeRatingBonus*1;
	}
	
  critRatingTotal() {
		return this.dexTotal()*2 + this.pc.critRatingBonus*1;
	}
	
	energyTotal() {
		return this.intTotal()*6 + this.pc.energyBonus*1;
	}
	
	energyRegenTotal() {
		return Math.round(this.intTotal()/2) + this.pc.energyRegenBonus*1;
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
  critChanceCalc(){
    return Math.round(((this.critRatingTotal()*(1/((13+this.pc.level)/14)))**0.78 + Number.EPSILON)*10)/10
  }
  dodgeChanceCalc(){
    return Math.round((95-(95*(1-(((this.dodgeRatingTotal()*(1/((13+this.pc.level)/14)))**0.7)/100))) + Number.EPSILON)*10)/10
  }
  updateStats(){
    let head = this.pc.equippedHead ? this.pc.equippedHead : this.emptySlot
    let body = this.pc.equippedBody ? this.pc.equippedBody : this.emptySlot
    let back = this.pc.equippedBack ? this.pc.equippedBack : this.emptySlot
    let neck = this.pc.equippedNeck ? this.pc.equippedNeck : this.emptySlot
    let primary = this.pc.equippedPrimary ? this.pc.equippedPrimary : this.emptySlot
    let secondary = this.pc.equippedSecondary ? this.pc.equippedSecondary : this.emptySlot
    let equipped = [this.baseStats, head, body, back, neck, primary, secondary];

    for(const bonus of this.bonuses){
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
    this.pc.armorPenTotal = this.armorTotal();
    this.pc.critRatingTotal = this.critRatingTotal();
    this.pc.dodgeRatingTotal = this.dodgeRatingTotal();
    this.pc.energyTotal = this.energyTotal();
    this.pc.energyRegenTotal = this.energyRegenTotal();
    this.pc.physResist = this.physResistCalc();
    this.pc.magResist = this.magResistCalc();
    this.pc.critChance = this.critChanceCalc();
    this.pc.dodgeChance = this.dodgeChanceCalc();

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
}

export default PcServices;