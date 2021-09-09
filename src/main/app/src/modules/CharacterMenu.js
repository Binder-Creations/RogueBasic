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
            <p></p>
            <p>{"+"+(this.props.pc.constitution + this.props.pc.constitutionBonus)*4+" Health" + " – " + "+" + Math.round((this.props.pc.constitution + this.props.pc.constitutionBonus)/3)+" Health Regen"}</p>
            <p></p>
            <p>{"+"+Math.round((this.props.pc.strength + this.props.pc.strengthBonus)/2)+" Power" + " – " + "+"+(this.props.pc.strength + this.props.pc.strengthBonus)*2+" Armor" + " – " + "+"+(this.props.pc.strength + this.props.pc.strengthBonus)*2+" Armor Penetration"}</p>
            <p></p>
            <p>{"+"+Math.round((this.props.pc.dexterity + this.props.pc.dexterityBonus)/2)+" Power" + " – " + "+"+(this.props.pc.dexterity + this.props.pc.dexterityBonus)*2+" Dodge Rating" + " – " + "+"+(this.props.pc.dexterity + this.props.pc.dexterityBonus)*2+" Critical Rating"}</p>
            <p></p>
            <p>{"+"+Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2)+" Power" + " – " + "+"+(this.props.pc.intelligence + this.props.pc.intelligenceBonus)*6+" Energy" + " – " + "+"+Math.round((this.props.pc.intelligence + this.props.pc.intelligenceBonus)/2)+" Energy Regen"}</p>
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