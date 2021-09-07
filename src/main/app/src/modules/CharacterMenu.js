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
  }

  render(){
    if(this.props.on && !this.state.close){
      return(
        <>
          <img className="menu-character" src={characterMenu} alt="Character Information Screen"/>
          <img className="badge" src={this.badge} alt={this.props.pc.characterClass}/>
          <input className="btn-close" type="image" src={buttonClose} alt="Close" onClick={ () => { this.closeToggle = true; this.setState({close:true}) } }
            onMouseEnter={e => (e.currentTarget.src = buttonCloseHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonClose)}
          />
          <input className="btn-atr con" type="image" src={buttonConstitution} alt="Constitution"
            onMouseEnter={e => (e.currentTarget.src = buttonConstitutionHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonConstitution)}
          />
          <input className="btn-atr str" type="image" src={buttonStrength} alt="Strength"
            onMouseEnter={e => (e.currentTarget.src = buttonStrengthHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonStrength)}
          />
          <input className="btn-atr dex" type="image" src={buttonDexterity} alt="Dexterity"
            onMouseEnter={e => (e.currentTarget.src = buttonDexterityHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonDexterity)}
          />
          <input className="btn-atr int" type="image" src={buttonIntelligence} alt="Intelligence"
            onMouseEnter={e => (e.currentTarget.src = buttonIntelligenceHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonIntelligence)}
          />
          <div className="character-name">
            <p className="v-h-centered">{this.props.pc.name}</p>
          </div>
        </>
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
    
  }

}

export default CharacterMenu