export default class CombatUpdate{
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
        if(updateType === "gameOver"){
          this.source = source;
        } else if(updateType !== "death" && updateType !== "stun"){
          if(source !== "pc" && updateType !== "experience"){
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

        if(updateType === "gameOver"){
          this.value = value;
        } else {
          if(value === 0){
            value = " Dodged!";
          } else if (value) { 
            value = " "+value;
          }
        }
        
        if(updateType === "gameOver"){
          this.abilityNameSize = {fontSize: 100*(1-(this.abilityName.length - 10)/35) + "%"};
          this.sourceSize = {fontSize: 100*(1-(this.source.length - 10)/35) + "%"};
        } else {
          this.text = updateType === "Attack" || updateType === "Chain"
            ? attackEnergy
              ? value + source + crit + " Energy Damage!"
              : value + source + crit
              : updateType === "death"
                ? source + " Dies!"
                : updateType === "experience"
                  ? "+ " + value + " EXP!"
                  : updateType === "Buff"
                    ? "++" + source
                    : updateType === "Debuff"
                      ? "--" + source
                      : updateType === "Heal"
                        ? value
                        : updateType === "stun"
                          ? source + " is Stunned!"
                          : "";     
          this.size = {fontSize: 130*(1-(this.text.length - 14)/40) + "%"};
        }
      }
    }
  }
}