class PcServices {
  pc = {};

  constructor(pc){
    console.log(pc);
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
		return (Math.round(this.strTotal/2) + Math.round(this.dexTotal/2) + Math.round(this.intTotal/2) + this.pc.powerBonus*1);
	}
	
	healthTotal() {
		return this.conTotal*4 + this.pc.healthBonus*1;
	}
	
	healthRegenTotal() {
		return Math.round(this.conTotal/3) + this.pc.healthRegenBonus*1;
	}
	
	armorPenTotal() {
		return this.strTotal*2 + this.pc.armorPenBonus*1;
	}
	
	armorTotal() {
		return this.strTotal*2 + this.pc.armorBonus*1;
	}
	
	dodgeRatingTotal() {
		return this.dexTotal*2 + this.pc.dodgeRatingBonus*1;
	}
	
  critRatingTotal() {
		return this.dexTotal*2 + this.pc.critRatingBonus*1;
	}
	
	energyTotal() {
		return this.intTotal*6 + this.pc.energyBonus*1;
	}
	
	energyRegenTotal() {
		return Math.round(this.intTotal/2) + this.pc.energyRegenBonus*1;
	}
  physResistCalc(){
    return Math.round(70-(Math.pow(70, 1-(Math.sqrt(this.armorTotal > 1000 ? 1000 : this.armorTotal)/100))))
  }
  magResistCalc(){
    return Math.round((70-(Math.pow(70, 1-(Math.sqrt(this.armorTotal > 1000 ? 1000 : this.armorTotal)/100))))/2)
  }
  critChanceCalc(){
    return Math.round(Math.pow(this.critRatingTotal, 0.78))
  }
  dodgeChanceCalc(){
    return Math.round(95-(Math.pow(95, 1-(Math.pow(this.dodgeRatingTotal, 0.7))/100)))
  }
  addStats(){
    this.pc.conTotal = this.conTotal();
    this.pc.strTotal = this.strTotal();
    this.pc.dexTotal = this.dexTotal();
    this.pc.intTotal = this.intTotal();
    this.pc.healthTotal = this.healthTotal();
    this.pc.healthRegenTotal = this.healthRegenTotal();
    this.pc.armorTotal = this.armorTotal();
    this.pc.armorPenTotal = this.armorTotal();
    this.pc.critRatingTotal = this.critRatingTotal();
    this.pc.dodgeRatingTotal = this.dodgeRatingTotal();
    this.pc.energyTotal = this.energyTotal();
    this.pc.energyRegenTotal = this.energyRegenTotal();
    this.pc.physResist = this.physResistCalc();
    this.pc.magResistCalc = this.magResistCalc();
    this.pc.critChance = this.critChanceCalc();
    this.pc.dodgeChance = this.dodgeChanceCalc();
    return this.pc;
  }

}

export default PcServices;