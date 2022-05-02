import Item from "./Item";

class Equipment extends Item {
  getUsability(pc){
    if(this.type === "back" || this.type === "neck"){
      return 1;
    }
    if(pc.characterClass === "Wizard"){
      if(this.type === "staff" || this.type === "spellbook" || this.type === "headLight" || this.type === "bodyLight"){
        return 1;
      }
    }
    if(pc.characterClass === "Rogue"){
      if(this.type === "bow" || this.type === "dagger" || this.type === "headMedium" || this.type === "bodyMedium"){
        return 1;
      }
    }
    if(pc.characterClass === "Warrior"){
      if(this.type === "sword" || this.type === "shield" || this.type === "headHeavy" || this.type === "bodyHeavy"){
        return 1;
      }
    }
    return 0;
  }

  addTo(target){
    target.inventory.push(this);
  }

  doAction(c, s, pc){
    var slot;
    if(this.type === "headLight" || this.type === "headMedium" || this.type === "headHeavy"){
      slot = "Head";
    } else if (this.type === "bodyLight" || this.type === "bodyMedium" || this.type === "bodyHeavy"){
      slot = "Body"
    } else if (this.type === "neck"){
      slot = "Neck"
    } else if (this.type === "back"){
      slot = "Back"
    } else if (this.type === "bow" || this.type === "staff" || this.type === "sword"){
      slot = "Primary"
    } else {
      slot = "Secondary"
    }
    if(pc["equipped" + slot] && pc["equipped" + slot].id !== c.zeroId.id){
      pc["equipped"+slot].addTo(pc);
    }
    pc["equipped" + slot] = this;
    c.pcServices.updateStats(pc);
    c.save({pc: pc}, {pc: pc});
  }

  unequip(c, pc){
    if(this.type === "headLight" || this.type === "headMedium" || this.type === "headHeavy"){
      pc.equippedHead = c.zeroId;
    } else if (this.type === "bodyLight" || this.type === "bodyMedium" || this.type === "bodyHeavy"){
      pc.equippedBody = c.zeroId;
    } else if (this.type === "neck"){
      pc.equippedNeck = c.zeroId;
    } else if (this.type === "back"){
      pc.equippedBack = c.zeroId;
    } else if (this.type === "bow" || this.type === "staff" || this.type === "sword"){
      pc.equippedPrimary = c.zeroId;
    } else {
      pc.equippedSecondary = c.zeroId;
    }
    c.pcServices.updateStats(pc);
    c.save({pc: pc}, {pc: pc});
  }

}

export default Equipment;