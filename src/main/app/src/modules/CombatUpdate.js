class CombatUpdate{
  constructor(updateType, abilityName, target, source, value, crit){
    this.type = updateType;

    if(abilityName){
      this.abilityName = abilityName;
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
          ? value + source + crit
          : updateType === "Heal"
            ? value + source + crit
            : updateType === "Buff"
              ? "++" + source
              : updateType === "Buff"
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