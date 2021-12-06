import React from "react";
import AttributeBuyButtons from "./AttributeBuyButtons";

class CharacterMenu extends React.Component {

  constructor(props){
    super(props);
    if(this.props.props.pc.characterClass === "Rogue"){
      this.badge = this.props.props.images.badgeRogue;
    } else if(this.props.props.pc.characterClass === "Wizard"){
      this.badge = this.props.props.images.badgeWizard;
    } else {
      this.badge = this.props.props.images.badgeWarrior;
    }
  }

  render(){
    this.experienceStyle = {
      width: 43.8984*(this.props.props.pc.experience/this.props.props.pc.experienceNeeded) + "%"
    }
    this.experienceHover = this.props.props.pc.experience + "/" + this.props.props.pc.experienceNeeded;
    if(this.props.on){
      return(
        <div className="menu">
          <img className="background" src={this.props.props.images.characterMenu} alt="Character Information Screen"/>
          <img className="badge" src={this.badge} alt={this.props.props.pc.characterClass}/>
          <input className="btn-close hover-saturate" type="image" src={this.props.props.images.buttonClose} alt="Close" onClick={this.props.toggle}/>
          <AttributeBuyButtons props={this.props.props}/>
          <div className="character-name">
            <p className="v-h-centered">{this.props.props.pc.name}</p>
          </div>
          <div className="character-points">
            <p className="v-h-centered">{"Points: "+this.props.props.pc.attributePoints}</p>
          </div>
          <img className="bar-experience" src={this.props.props.images.barExperience} alt="Experience" title={this.experienceHover} style={this.experienceStyle}/>
          <img className="bar-experience-frame" src={this.props.props.images.barFrame} alt="Experience" title={this.experienceHover}/>
          <img className="character-level-background" src={this.props.props.images.characterLevelBackground} alt=""></img>
          <div className="character-level">
            <p className="v-h-centered">{this.props.props.pc.level}</p>
          </div>
          <div className="atr-values">
            <p title={this.atrValueTitle("con", this.props.props.pc.constitutionBonus)}>Constitution: <span style={this.atrValueStyle(this.props.props.pc.constitutionBonus)}>{this.props.props.pc.conTotal}</span></p>
            <p title={this.atrValueTitle("str", this.props.props.pc.strengthBonus)}>Strength: <span style={this.atrValueStyle(this.props.props.pc.strengthBonus)}>{this.props.props.pc.strTotal}</span></p>
            <p title={this.atrValueTitle("dex", this.props.props.pc.dexterityBonus)}>Dexterity: <span style={this.atrValueStyle(this.props.props.pc.dexterityBonus)}>{this.props.props.pc.dexTotal}</span></p>
            <p title={this.atrValueTitle("int", this.props.props.pc.intelligenceBonus)}>Intelligence: <span style={this.atrValueStyle(this.props.props.pc.intelligenceBonus)}>{this.props.props.pc.intTotal}</span></p>
          </div>
          <div className="atr-bonuses">
            <p>{"+"+this.props.props.pc.conTotal*4+" Health"}</p>
            <p>{"+" + Math.round(this.props.props.pc.conTotal/3)+" Health Regen"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round(this.props.props.pc.strTotal/2)+" Power"}</p>
            <p>{"+"+this.props.props.pc.strTotal*2+" Armor +"+(this.props.props.pc.strTotal)*2+" Armor Pen"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round(this.props.props.pc.dexTotal/2)+" Power"}</p>
            <p>{"+"+this.props.props.pc.dexTotal*2+" Dodge Rating +"+this.props.props.pc.dexTotal*2+" Crit Rating"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round(this.props.props.pc.intTotal/2)+" Power"}</p>
            <p>{"+"+this.props.props.pc.intTotal*6+" Energy +"+Math.round(this.props.props.pc.intTotal/2)+" Energy Regen"}</p>
          </div>
          <div className="der-stats-1">
            <p title="All Damage Increases by 1% per Power">Power:</p>
            <p title="Total Damage You Can Take Before Dying">Max Health:</p>
            <p title="Health Regained Per Dungeon Room Traveled">Health Regen:</p>
            <p title="Reduces Physical Damage Taken; Half Effect Against Magic">Armor:</p>
            <p title="Percent Incoming Physical Damage is Reduced by">Phys Resist:</p>
            <p title="Percent Incoming Magical Damage is Reduced by">Mag Resist:</p>
            <p title="Reduces Enemy Armor">Armor Pen:</p>
          </div>
          <div className="der-stats-2">
            <p title="Increases Critical Chance">Crit Rating:</p>
            <p title="Percent Chance to Deal Double Damage with Attack or Spell">Crit Chance:</p>
            <p title="Increases Dodge Chance">Dodge Rating:</p>
            <p title="Percent Chance to Avoid Enemy Attacks and Spells">Dodge Chance:</p>
            <p title="Total Resource Available to Spend on Skills">Max Energy:</p>
            <p title="Energy Regained Per Dungeon Room Traveled">Energy Regen:</p>
          </div>
          <div className="der-values-1">
            <p>{this.props.props.pc.powerTotal}</p>
            <p>{this.props.props.pc.healthTotal}</p>
            <p>{this.props.props.pc.healthRegenTotal}</p>
            <p>{this.props.props.pc.armorTotal}</p>
            <p title="Based on Enemies of Equal Level">{this.props.props.pc.physResist + "%"}</p>
            <p title="Based on Enemies of Equal Level">{this.props.props.pc.magResist + "%"}</p>
            <p>{this.props.props.pc.armorPenTotal}</p>
          </div>
          <div className="der-values-2">
            <p>{this.props.props.pc.critRatingTotal}</p>
            <p title="Based on Enemies of Equal Level">{this.props.props.pc.critChance + "%"}</p>
            <p>{this.props.props.pc.dodgeRatingTotal}</p>
            <p title="Based on Enemies of Equal Level">{this.props.props.pc.dodgeChance + "%"}</p>
            <p>{this.props.props.pc.energyTotal}</p>
            <p>{this.props.props.pc.energyRegenTotal}</p>
          </div>
        </div>
      )  
    } else {
      return(
        <></>
      )
    }
  }

  atrValueTitle(atr, bonus){
    if(bonus > 0){
      switch(atr){
        case "con":
          return this.props.props.pc.constitution + "(base) + " + this.props.props.pc.constitutionBonus + "(bonus)";
        case "str":
          return this.props.props.pc.strength + "(base) + " + this.props.props.pc.strengthBonus + "(bonus)";
        case "dex":
          return this.props.props.pc.dexterity + "(base) + " + this.props.props.pc.dexterityBonus + "(bonus)";
        case "int":
          return this.props.props.pc.intelligence + "(base) + " + this.props.props.pc.intelligenceBonus + "(bonus)";
        default:
          return;
      }
    }else{
      switch(atr){
        case "con":
          return this.props.props.pc.constitution + "(base)";
        case "str":
          return this.props.props.pc.strength + "(base)";
        case "dex":
          return this.props.props.pc.dexterity + "(base)";
        case "int":
          return this.props.props.pc.intelligence + "(base)";
        default:
          return;
      }
    }
  }

  atrValueStyle(bonus){
    if(bonus > 0){
      return{
        color: "#007503"
      }
    }else{
      return{
        color: "#000000"
      }
    }
  }

}

export default CharacterMenu