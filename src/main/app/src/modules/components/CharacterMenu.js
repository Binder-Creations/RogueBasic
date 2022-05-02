import React from "react";
import AttributeBuyButtons from "./AttributeBuyButtons";

class CharacterMenu extends React.Component {

  render(){
    this.experienceStyle = {
      width: 43.8984*(this.props.s.pc.experience/this.props.s.pc.experienceNeeded) + "%"
    }
    this.experienceHover = this.props.s.pc.experience + "/" + this.props.s.pc.experienceNeeded;
    return(
      <div key="menu" className="menu">
        <img className="background" src={this.props.c.images.common.characterMenu} alt="Character Information Screen"/>
        <img className="badge" src={this.props.c.images.class.badge} alt={this.props.s.pc.characterClass}/>
        <input className="btn-close hover-saturate" type="image" src={this.props.c.images.common.buttonClose} alt="Close" onClick={() => this.props.c.menu("character")}/>
        <AttributeBuyButtons c={this.props.c} s={this.props.s}/>
        <div className="character-name">
          <p className="v-h-centered">{this.props.s.pc.name}</p>
        </div>
        <div className="character-points">
          <p className="v-h-centered">{"Points: "+this.props.s.pc.attributePoints}</p>
        </div>
        <img className="bar-experience" src={this.props.c.images.common.barExperience} alt="Experience" title={this.experienceHover} style={this.experienceStyle}/>
        <img className="bar-experience-frame" src={this.props.c.images.common.barFrame} alt="Experience" title={this.experienceHover}/>
        <img className="character-level-background" src={this.props.c.images.common.characterLevelBackground} alt=""></img>
        <div className="character-level">
          <p className="v-h-centered">{this.props.s.pc.level}</p>
        </div>
        <div className="atr-values">
          <p title={this.atrValueTitle("con", this.props.s.pc.constitutionBonus)}>Constitution: <span style={this.atrValueStyle(this.props.s.pc.constitutionBonus)}>{this.props.s.pc.conTotal}</span></p>
          <p title={this.atrValueTitle("str", this.props.s.pc.strengthBonus)}>Strength: <span style={this.atrValueStyle(this.props.s.pc.strengthBonus)}>{this.props.s.pc.strTotal}</span></p>
          <p title={this.atrValueTitle("dex", this.props.s.pc.dexterityBonus)}>Dexterity: <span style={this.atrValueStyle(this.props.s.pc.dexterityBonus)}>{this.props.s.pc.dexTotal}</span></p>
          <p title={this.atrValueTitle("int", this.props.s.pc.intelligenceBonus)}>Intelligence: <span style={this.atrValueStyle(this.props.s.pc.intelligenceBonus)}>{this.props.s.pc.intTotal}</span></p>
        </div>
        <div className="atr-bonuses">
          <p>{"+"+this.props.s.pc.conTotal*4+" Health"}</p>
          <p>{"+" + Math.round(this.props.s.pc.conTotal/3)+" Health Regen"}</p>
          <p className="spacer-xs"></p>
          <p>{"+"+Math.round(this.props.s.pc.strTotal/2)+" Power"}</p>
          <p>{"+"+this.props.s.pc.strTotal*2+" Armor +"+(this.props.s.pc.strTotal)*2+" Armor Pen"}</p>
          <p className="spacer-xs"></p>
          <p>{"+"+Math.round(this.props.s.pc.dexTotal/2)+" Power"}</p>
          <p>{"+"+this.props.s.pc.dexTotal*2+" Dodge Rating +"+this.props.s.pc.dexTotal*2+" Crit Rating"}</p>
          <p className="spacer-xs"></p>
          <p>{"+"+Math.round(this.props.s.pc.intTotal/2)+" Power"}</p>
          <p>{"+"+this.props.s.pc.intTotal*6+" Energy +"+Math.round(this.props.s.pc.intTotal/2)+" Energy Regen"}</p>
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
          <p>{this.props.s.pc.powerTotal}</p>
          <p>{this.props.s.pc.healthTotal}</p>
          <p>{this.props.s.pc.healthRegenTotal}</p>
          <p>{this.props.s.pc.armorTotal}</p>
          <p title="Based on Enemies of Equal Level">{this.props.s.pc.physResist + "%"}</p>
          <p title="Based on Enemies of Equal Level">{this.props.s.pc.magResist + "%"}</p>
          <p>{this.props.s.pc.armorPenTotal}</p>
        </div>
        <div className="der-values-2">
          <p>{this.props.s.pc.critRatingTotal}</p>
          <p title="Based on Enemies of Equal Level">{this.props.s.pc.critChance + "%"}</p>
          <p>{this.props.s.pc.dodgeRatingTotal}</p>
          <p title="Based on Enemies of Equal Level">{this.props.s.pc.dodgeChance + "%"}</p>
          <p>{this.props.s.pc.energyTotal}</p>
          <p>{this.props.s.pc.energyRegenTotal}</p>
        </div>
      </div>
    )  
  }

  atrValueTitle(atr, bonus){
    if(bonus > 0){
      switch(atr){
        case "con":
          return this.props.s.pc.constitution + "(base) + " + this.props.s.pc.constitutionBonus + "(bonus)";
        case "str":
          return this.props.s.pc.strength + "(base) + " + this.props.s.pc.strengthBonus + "(bonus)";
        case "dex":
          return this.props.s.pc.dexterity + "(base) + " + this.props.s.pc.dexterityBonus + "(bonus)";
        case "int":
          return this.props.s.pc.intelligence + "(base) + " + this.props.s.pc.intelligenceBonus + "(bonus)";
        default:
          return;
      }
    }else{
      switch(atr){
        case "con":
          return this.props.s.pc.constitution + "(base)";
        case "str":
          return this.props.s.pc.strength + "(base)";
        case "dex":
          return this.props.s.pc.dexterity + "(base)";
        case "int":
          return this.props.s.pc.intelligence + "(base)";
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