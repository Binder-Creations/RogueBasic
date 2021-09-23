import React from "react";
import barBackground from "../images/bar-background.png";
import barFrame from "../images/bar-frame.png";
import barHealth from "../images/bar-health.png";
import barEnergy from "../images/bar-energy.png";
import heart from "../images/heart.png";
import swirl from "../images/swirl.png";
import skillGlow from "../images/skill-glow.png";
import skillBackground from "../images/skill-background.png";
import skillFrame from "../images/skill-frame.png";
import mapOverlay from "../images/map-overlay.png";
import mapUnderlay from "../images/map-underlay.png";
import buttonAttackWarrior from "../images/button-attack-warrior.png";
import buttonAttackRogue from "../images/button-attack-rogue.png";
import buttonAttackWizard from "../images/button-attack-wizard.png";
import buttonAttackWarriorHover from "../images/button-attack-warrior-hover.png";
import buttonAttackRogueHover from "../images/button-attack-rogue-hover.png";
import buttonAttackWizardHover from "../images/button-attack-wizard-hover.png";
import buttonCharacter from "../images/button-character.png";
import buttonInventory from "../images/button-inventory.png";
import buttonCharacterHover from "../images/button-character-hover.png";
import buttonInventoryHover from "../images/button-inventory-hover.png";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";

class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      characterMenu: false,
      inventoryMenu: false
    };
    if(this.props.pc.characterClass == "Rogue"){
      this.buttonAttack = buttonAttackRogue;
      this.buttonAttackHover = buttonAttackRogueHover;
    } else if(this.props.pc.characterClass == "Wizard"){
      this.buttonAttack = buttonAttackWizard;
      this.buttonAttackHover = buttonAttackWizardHover;
    } else {
      this.buttonAttack = buttonAttackWarrior;
      this.buttonAttackHover = buttonAttackWarriorHover;   
    }
    this.toggleCharacterMenu = this.toggleCharacterMenu.bind(this);
    this.toggleInventoryMenu = this.toggleInventoryMenu.bind(this);
  }

  render(){
    this.healthStyle = {
      width: 20*(this.props.pc.currentHealth/this.props.pc.healthTotal) + "%"
    }
    this.energyStyle = {
       width: 20*(this.props.pc.currentEnergy/this.props.pc.energyTotal) + "%"
    }
    this.healthHover = this.props.pc.currentHealth + "/" + this.props.pc.healthTotal;
    this.energyHover = this.props.pc.currentEnergy + "/" + this.props.pc.energyTotal;
    return(
      <>
        <img className="bar-health-background" src={barBackground} alt="Health" title={this.healthHover}/>
        <img className="bar-health" src={barHealth} alt="Health" title={this.healthHover} style={this.healthStyle}/>
        <img className="bar-health-frame" src={barFrame} alt="Health" title={this.healthHover}/>
        <img className="heart" src={heart} alt="Health" title={this.healthHover}/>
        <img className="bar-energy-background" src={barBackground} alt="Energy" title={this.energyHover}/>
        <img className="bar-energy" src={barEnergy} alt="Energy" title={this.energyHover} style={this.energyStyle}/>
        <img className="bar-energy-frame" src={barFrame} alt="Energy" title={this.energyHover}/>
        <img className="swirl" src={swirl} alt="Energy" title={this.energyHover}/>
        <button className="skill s-1">
          <img className="glow" src={skillGlow} alt="Skill-1"/>
          <img className="frame" src={skillBackground} alt="Skill-1"/>
          <img className="frame" src={skillFrame} alt="Skill-1"/>
        </button>
        <button className="skill s-2">
          <img className="glow" src={skillGlow} alt="Skill-2"/>
          <img className="frame" src={skillBackground} alt="Skill-2"/>
          <img className="frame" src={skillFrame} alt="Skill-2"/>
        </button>
        <button className="skill s-3">
          <img className="glow" src={skillGlow} alt="Skill-3"/>
          <img className="frame" src={skillBackground} alt="Skill-3"/>
          <img className="frame" src={skillFrame} alt="Skill-3"/>
        </button>
        <button className="skill s-4">
          <img className="glow" src={skillGlow} alt="Skill-4"/>
          <img className="frame" src={skillBackground} alt="Skill-4"/>
          <img className="frame" src={skillFrame} alt="Skill-4"/>
        </button>
        <img className="map-underlay" src={mapUnderlay} alt="Map"/>
        <img className="map-overlay" src={mapOverlay} alt="Map"/>
        <input class="btn-attack" type="image" src={this.buttonAttack} alt="Attack"
          onMouseEnter={e => (e.currentTarget.src = this.buttonAttackHover)}
          onMouseLeave={e => (e.currentTarget.src = this.buttonAttack)}
        />
        <input class="btn-character" type="image" src={buttonCharacter} alt="Character" onClick={this.toggleCharacterMenu} 
          onMouseEnter={e => (e.currentTarget.src = buttonCharacterHover)}
          onMouseLeave={e => (e.currentTarget.src = buttonCharacter)}
        />
        <input class="btn-inventory" type="image" src={buttonInventory} alt="Inventory" onClick={this.toggleInventoryMenu} 
          onMouseEnter={e => (e.currentTarget.src = buttonInventoryHover)}
          onMouseLeave={e => (e.currentTarget.src = buttonInventory)}
        />
        <CharacterMenu appState={this.props.appState} toggle={this.toggleCharacterMenu} on={this.state.characterMenu} pc={this.props.pc} combat={this.props.combat}/>
        <InventoryMenu appState={this.props.appState} toggle={this.toggleInventoryMenu} on={this.state.inventoryMenu} pc={this.props.pc} combat={this.props.combat}/>
      </>
    )
  }

  toggleCharacterMenu(){
    this.state.characterMenu
      ? this.setState({characterMenu: false})
      : this.setState({characterMenu: true});
  }

  toggleInventoryMenu(){
    this.state.inventoryMenu
      ? this.setState({inventoryMenu: false})
      : this.setState({inventoryMenu: true});
  }

}

export default Ui