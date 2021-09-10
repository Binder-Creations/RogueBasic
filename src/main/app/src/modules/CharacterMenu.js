import React from "react";
import characterMenu from "../images/character-menu.png";
import buttonClose from "../images/button-close.png";
import buttonCloseHover from "../images/button-close-hover.png";
import badgeWarrior from "../images/badge-warrior.png";
import badgeRogue from "../images/badge-rogue.png";
import badgeMage from "../images/badge-mage.png";
import barExperience from "../images/bar-experience.png";
import barFrame from "../images/bar-frame.png";
import characterLevelBackground from "../images/character-level-background.png";
import AttributeBuyButtons from "./AttributeBuyButtons";

class CharacterMenu extends React.Component {

  constructor(props){
    super(props);
    if(this.props.pc.characterClass == "Rogue"){
      this.badge = badgeRogue;
    } else if(this.props.pc.characterClass == "Wizard"){
      this.badge = badgeMage;
    } else {
      this.badge = badgeWarrior;
    }
    this.experienceStyle = {
      width: 43.8984*(this.props.pc.experience/(Math.round(this.props.pc.level**1.5)*100)) + "%"
    }
    this.experienceHover = this.props.pc.experience + "/" + Math.round(this.props.pc.level**1.5)*100 + " experience to next level";
  }

  render(){
    if(this.props.on){
      return(
        <div className="menu-character">
          <img className="background" src={characterMenu} alt="Character Information Screen"/>
          <img className="badge" src={this.badge} alt={this.props.pc.characterClass}/>
          <input className="btn-close" type="image" src={buttonClose} alt="Close" onClick={this.props.toggle}
            onMouseEnter={e => (e.currentTarget.src = buttonCloseHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonClose)}
          />
          <AttributeBuyButtons appState={this.props.appState} attributePoints={this.props.pc.attributePoints}/>
          <div className="character-name">
            <p className="v-h-centered">{this.props.pc.name}</p>
          </div>
          <div className="character-points">
            <p className="v-h-centered">{"Points: "+this.props.pc.attributePoints}</p>
          </div>
          <img className="bar-experience" src={barExperience} alt="Experience" title={this.experienceHover} style={this.experienceStyle}/>
          <img className="bar-experience-frame" src={barFrame} alt="Experience" title={this.experienceHover}/>
          <img className="character-level-background" src={characterLevelBackground}></img>
          <div className="character-level">
            <p className="v-h-centered">{this.props.pc.level}</p>
          </div>
          <div className="atr-values">
            <p title={this.atrValueTitle("con", this.props.pc.constitutionBonus)}>Constitution: <span style={this.atrValueStyle(this.props.pc.constitutionBonus)}>{(this.props.pc.constitution*1 + this.props.pc.constitutionBonus*1)}</span></p>
            <p title={this.atrValueTitle("str", this.props.pc.strengthBonus)}>Strength: <span style={this.atrValueStyle(this.props.pc.strengthBonus)}>{(this.props.pc.strength*1 + this.props.pc.strengthBonus*1)}</span></p>
            <p title={this.atrValueTitle("dex", this.props.pc.dexterityBonus)}>Dexterity: <span style={this.atrValueStyle(this.props.pc.dexterityBonus)}>{(this.props.pc.dexterity*1 + this.props.pc.dexterityBonus*1)}</span></p>
            <p title={this.atrValueTitle("int", this.props.pc.intelligenceBonus)}>Intelligence: <span style={this.atrValueStyle(this.props.pc.intelligenceBonus)}>{(this.props.pc.intelligence*1 + this.props.pc.intelligenceBonus*1)}</span></p>
          </div>
          <div className="atr-bonuses">
            <p>{"+"+(this.props.pc.constitution + this.props.pc.constitutionBonus)*4+" Health"}</p>
            <p>{"+" + Math.round((this.props.pc.constitution + this.props.pc.constitutionBonus)/3)+" Health Regen"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round((this.props.pc.strength + this.props.pc.strengthBonus)/2)+" Power"}</p>
            <p>{"+"+(this.props.pc.strength + this.props.pc.strengthBonus)*2+" Armor" + " +"+(this.props.pc.strength + this.props.pc.strengthBonus)*2+" Armor Pen"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round((this.props.pc.dexterity + this.props.pc.dexterityBonus)/2)+" Power"}</p>
            <p>{"+"+(this.props.pc.dexterity + this.props.pc.dexterityBonus)*2+" Dodge Rating" + " +"+(this.props.pc.dexterity + this.props.pc.dexterityBonus)*2+" Crit Rating"}</p>
            <p className="spacer-xs"></p>
            <p>{"+"+Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2)+" Power"}</p>
            <p>{"+"+(this.props.pc.intelligence + this.props.pc.intelligenceBonus)*6+" Energy" + " +"+Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2)+" Energy Regen"}</p>
          </div>
          <div className="der-stats-1">
            <p title="All Damage Increases by 1% per Power">Power:</p>
            <p title="Total Damage You Can Take Before Dying">Max Health:</p>
            <p title="Health Regained Per Dungeon Room Traveled">Health Regen:</p>
            <p title="Reduces Physical Damage Taken; Half Effect Against Magic">Armor:</p>
            <p title="Percent Incoming Physical Damage is Reduced by">Physical Resistance:</p>
            <p title="Percent Incoming Magical Damage is Reduced by">Magical Resistance:</p>
            <p title="Reduces Enemy Armor">Armor Penetration:</p>
          </div>
          <div className="der-stats-2">
            <p title="Increases Critical Chance">Critical Rating:</p>
            <p title="Percent Chance to Deal Double Damage with Attack or Spell">Critical Chance:</p>
            <p title="Increases Dodge Chance">Dodge Rating:</p>
            <p title="Percent Chance to Avoid Enemy Attacks and Spells">Dodge Chance:</p>
            <p title="Total Resource Available to Spend on Skills">Max Energy:</p>
            <p title="Energy Regained Per Dungeon Room Traveled">Energy Regen:</p>
          </div>
          <div className="der-values-1">
            <p title={Math.round((this.props.pc.strength + this.props.pc.strengthBonus)/2)+ "(str) + " + Math.round((this.props.pc.dexterity + this.props.pc.dexterityBonus)/2) + "(dex) + " + Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2) + "(int) + " + this.props.pc.powerBonus + "(bonus)"}>{Math.round((this.props.pc.strength + this.props.pc.strengthBonus)/2) + Math.round((this.props.pc.dexterity + this.props.pc.dexterityBonus)/2) + Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2) + this.props.pc.powerBonus}</p>
            <p title={(this.props.pc.constitution + this.props.pc.constitutionBonus)*4 + "(con) + " + this.props.pc.healthBonus + "(bonus)"}>{(this.props.pc.constitution + this.props.pc.constitutionBonus)*4 + this.props.pc.healthBonus}</p>
            <p title={(this.props.pc.constitution + this.props.pc.constitutionBonus)*4 + "(con) + " + this.props.pc.healthBonus + "(bonus)"}>{(this.props.pc.constitution + this.props.pc.constitutionBonus)*4 + this.props.pc.healthBonus}</p>
            <p title="">{Math.round(70-(Math.pow(70, 1-(Math.sqrt((this.props.pc.armorBonus + (this.props.pc.strength + this.props.pc.strengthBonus)*2) > 1000 ? 1000 : (this.props.pc.armorBonus + (this.props.pc.strength + this.props.pc.strengthBonus)*2))/100)))) + "%"}</p>
            <p title="">{Math.round((70-(Math.pow(70, 1-(Math.sqrt((this.props.pc.armorBonus + (this.props.pc.strength + this.props.pc.strengthBonus)*2) > 1000 ? 1000 : (this.props.pc.armorBonus + (this.props.pc.strength + this.props.pc.strengthBonus)*2))/100))))/2) + "%"}</p>
            <p title="">{}</p>
            <p title="">{}</p>
          </div>
          <div className="der-values-2">
            <p title="">{}</p>
            <p title="">{}</p>
            <p title="">{}</p>
            <p title="">{}</p>
            <p title="">{}</p>
            <p title="">{}</p>
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
          return this.props.pc.constitution + "(base) + " + this.props.pc.constitutionBonus + "(bonus)";
        case "str":
          return this.props.pc.strength + "(base) + " + this.props.pc.strengthBonus + "(bonus)";
        case "dex":
          return this.props.pc.dexterity + "(base) + " + this.props.pc.dexterityBonus + "(bonus)";
        case "int":
          return this.props.pc.intelligence + "(base) + " + this.props.pc.intelligenceBonus + "(bonus)";
        default:
          return;
      }
    }else{
      switch(atr){
        case "con":
          return this.props.pc.constitution + "(base)";
        case "str":
          return this.props.pc.strength + "(base)";
        case "dex":
          return this.props.pc.dexterity + "(base)";
        case "int":
          return this.props.pc.intelligence + "(base)";
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

  componentDidUpdate(){
    this.experienceStyle = {
      width: 43.8984*(this.props.pc.experience/(Math.round(this.props.pc.level**1.5)*100)) + "%"
    }
    this.experienceHover = this.props.pc.experience + "/" + Math.round(this.props.pc.level**1.5)*100;
    
  }

}

export default CharacterMenu