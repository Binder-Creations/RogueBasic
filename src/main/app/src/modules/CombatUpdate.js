class CombatUpdate{
  constructor(updateType, abilityName, target, source, value, crit, attackEnergy){
    this.type = updateType;
    this.abilityName = abilityName ? abilityName : null;
    if (this.type === "Attack"){
      this.type = attackEnergy 
        ? this.type + "Energy"
        : this.type + "Health"
    }

    if(target){
      this.target = target;
      if(source){
        if(updateType !== "death" && updateType !== "stun"){
          if(source !== "pc"){
            source = " (" + source + ")";
          } else {
            source = "";
          }
        }
        
        if(crit){
          this.crit = crit;
          crit = " Crit!"
        } else {
          crit = ""
        }

        if(value === 0){
          value = " Dodged!";
        } else if (value) { 
          value = " "+value;
        }
        
        this.text = updateType === "Attack" || updateType === "Chain"
          ? attackEnergy
            ? value + source + crit + " Energy Damage!"
            : value + source + crit
            : updateType === "Heal"
              ? value
              : updateType === "Buff"
                ? "++" + source
                : updateType === "Debuff"
                  ? "--" + source
                    : updateType === "death"
                      ? source + " Dies!"
                      : updateType === "stun"
                        ? source + " is Stunned!"
                    : "";     
        this.size = {fontSize: 130*(1-(this.text.length - 14)/40) + "%"};
      }
    }
  }
}

export default CombatUpdate;