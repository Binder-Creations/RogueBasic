class PcServices {
  pc = {};

  constructor(pc){
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
    return Math.round((70-70**(1-(((this.armorTotal()*(1/((13+this.pc.level)/14)))**0.5)/100)) + Number.EPSILON)*10)/10
  }
  magResistCalc(){
    return Math.round((70-70**(1-(((this.armorTotal()*(1/((13+this.pc.level)/14)))**0.5)/100)) + Number.EPSILON)*5)/10
  }
  critChanceCalc(){
    return Math.round(((this.critRatingTotal()*(1/((13+this.pc.level)/14)))**0.78 + Number.EPSILON)*10)/10
  }
  dodgeChanceCalc(){
    return Math.round((95-(95*(1-(((this.dodgeRatingTotal()*(1/((13+this.pc.level)/14)))**0.7)/100))) + Number.EPSILON)*10)/10
  }
  addStats(){
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
  }

}

export default PcServices;