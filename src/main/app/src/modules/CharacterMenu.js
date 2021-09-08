import React from "react";
import characterMenu from "../images/character-menu.png";
import buttonClose from "../images/button-close.png";
import buttonCloseHover from "../images/button-close-hover.png";
import buttonConstitution from "../images/button-constitution.png";
import buttonStrength from "../images/button-strength.png";
import buttonDexterity from "../images/button-dexterity.png";
import buttonIntelligence from "../images/button-intelligence.png";
import buttonConstitutionHover from "../images/button-constitution-hover.png";
import buttonStrengthHover from "../images/button-strength-hover.png";
import buttonDexterityHover from "../images/button-dexterity-hover.png";
import buttonIntelligenceHover from "../images/button-intelligence-hover.png";
import badgeWarrior from "../images/badge-warrior.png";
import badgeRogue from "../images/badge-rogue.png";
import badgeMage from "../images/badge-mage.png";
import barExperience from "../images/bar-experience.png";
import barFrame from "../images/bar-frame.png";

class CharacterMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {};
    this.state.close = false;
    this.closeToggle = false;
    if(this.props.pc.characterClass == "Rogue"){
      this.badge = badgeRogue;
    } else if(this.props.pc.characterClass == "Wizard"){
      this.badge = badgeMage;
    } else {
      this.badge = badgeWarrior;
    }
    this.experienceStyle = {
      width: 47.9754*(this.props.pc.experience/(Math.round(this.props.pc.level**1.5)*100)) + "%"
    }
    this.experienceHover = this.props.pc.experience + "/" + Math.round(this.props.pc.level**1.5)*100 + " experience to next level";
  }

  render(){
    if(this.props.on && !this.state.close){
      return(
        <div className="menu-character">
          <img className="background" src={characterMenu} alt="Character Information Screen"/>
          <img className="badge" src={this.badge} alt={this.props.pc.characterClass}/>
          <input className="btn-close" type="image" src={buttonClose} alt="Close" onClick={ () => { this.closeToggle = true; this.setState({close:true}) } }
            onMouseEnter={e => (e.currentTarget.src = buttonCloseHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonClose)}
          />
          <div className="btn-atr con">
            <input className="background" type="image" src={buttonConstitution} alt="Constitution"
              onMouseEnter={e => (e.currentTarget.src = buttonConstitutionHover)}
              onMouseLeave={e => (e.currentTarget.src = buttonConstitution)}
            />
            <p className="v-h-centered noselect">
              +Con
            </p>
          </div>
          <div className="btn-atr str">
            <input className="background" type="image" src={buttonStrength} alt="Strength"
              onMouseEnter={e => (e.currentTarget.src = buttonStrengthHover)}
              onMouseLeave={e => (e.currentTarget.src = buttonStrength)}
            />
            <p className="v-h-centered noselect">
              +Str
            </p>
          </div>
          <div className="btn-atr dex">
            <input className="background" type="image" src={buttonDexterity} alt="Dexterity"
              onMouseEnter={e => (e.currentTarget.src = buttonDexterityHover)}
              onMouseLeave={e => (e.currentTarget.src = buttonDexterity)}
            />
            <p className="v-h-centered noselect">
              +Dex
            </p>
          </div>
          <div className="btn-atr int">
            <input className="background" type="image" src={buttonIntelligence} alt="Intelligence"
              onMouseEnter={e => (e.currentTarget.src = buttonIntelligenceHover)}
              onMouseLeave={e => (e.currentTarget.src = buttonIntelligence)}
            />
            <p className="v-h-centered noselect">
              +Int
            </p>
          </div>
          <div className="character-name">
            <p className="v-h-centered">{this.props.pc.name}</p>
          </div>
          <div className="character-level">
            <p className="v-h-centered">{this.props.pc.level}</p>
          </div>
          <div className="character-points">
            <p className="v-h-centered">{"Points: "+this.props.pc.attributePoints}</p>
          </div>
          <img className="bar-experience" src={barExperience} alt="Experience" title={this.experienceHover} style={this.experienceStyle}/>
          <img className="bar-experience-frame" src={barFrame} alt="Experience" title={this.experienceHover}/>
        </div>
      )  
    } else {
      return(
        <></>
      )
    }
  }

  componentDidUpdate(){
    if(this.closeToggle){
      this.state.close = false;
      this.closeToggle = false;
    }
    this.experienceStyle = {
      width: 47.9754*(this.props.pc.experience/(Math.round(this.props.pc.level**1.5)*100)) + "%"
    }
    this.experienceHover = this.props.pc.experience + "/" + Math.round(this.props.pc.level**1.5)*100;
    
  }

}

export default CharacterMenu