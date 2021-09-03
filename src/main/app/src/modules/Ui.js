import React from "react";
import barBackground from "../images/bar-background.png";
import barFrame from "../images/bar-frame.png";
import healthBar from "../images/health-bar.png";
import energyBar from "../images/energy-bar.png";
import heart from "../images/heart.png";
import swirl from "../images/swirl.png";
import skillBackground from "../images/skill-background.png";
import skillFrame from "../images/skill-frame.png";
import mapOverlay from "../images/map-overlay.png";
import mapUnderlay from "../images/map-underlay.png";
import buttonAttack from "../images/button-attack.png";
import buttonCharacter from "../images/button-character.png";
import buttonInventory from "../images/button-inventory.png";


class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {};
    this.state.healthHover = this.props.pc.currentHealth + "/" + ((this.props.pc.constitution*1 + this.props.pc.constitutionBonus*1)*4 + this.props.pc.healthBonus*1);
    this.state.energyHover = this.props.pc.currentEnergy + "/" + ((this.props.pc.intelligence*1 + this.props.pc.intelligenceBonus*1)*6 + this.props.pc.energyBonus*1);
    this.state.healthStyle = {
      width: 20*(this.props.pc.currentHealth/((this.props.pc.constitution*1 + this.props.pc.constitutionBonus*1)*4 + this.props.pc.healthBonus*1)) + "%"
    }
    this.state.energyStyle = {
      width: 20*(this.props.pc.currentEnergy/((this.props.pc.intelligence*1 + this.props.pc.intelligenceBonus*1)*6 + this.props.pc.energyBonus*1)) + "%"
    }
  }

  render(){
    return(
      <>
        <img className="bar-health-background" src={barBackground} alt="Health" title={this.state.healthHover}/>
        <img className="bar-health" src={healthBar} alt="Health" title={this.state.healthHover} style={this.state.healthStyle}/>
        <img className="bar-health-frame" src={barFrame} alt="Health" title={this.state.healthHover}/>
        <img className="heart" src={heart} alt="Health" title={this.state.healthHover}/>
        <img className="bar-energy-background" src={barBackground} alt="Energy" title={this.state.energyHover}/>
        <img className="bar-energy" src={energyBar} alt="Energy" title={this.state.energyHover} style={this.state.energyStyle}/>
        <img className="bar-energy-frame" src={barFrame} alt="Energy" title={this.state.energyHover}/>
        <img className="swirl" src={swirl} alt="Energy" title={this.state.energyHover}/>
        <button className="skill s-1">
          <img src={skillBackground} alt="Skill-1"/>
          <img src={skillFrame} alt="Skill-1"/>
        </button>
        <button className="skill s-2">
          <img src={skillBackground} alt="Skill-2"/>
          <img src={skillFrame} alt="Skill-2"/>
        </button>
        <button className="skill s-3">
          <img src={skillBackground} alt="Skill-3"/>
          <img src={skillFrame} alt="Skill-3"/>
        </button>
        <button className="skill s-4">
          <img src={skillBackground} alt="Skill-4"/>
          <img src={skillFrame} alt="Skill-4"/>
        </button>
        <img className="map-underlay" src={mapUnderlay} alt="Map"/>
        <img className="map-overlay" src={mapOverlay} alt="Map"/>
        <input class="btn-attack" type="image" src={buttonAttack} alt="Attack"/>
        <input class="btn-character" type="image" src={buttonCharacter} alt="Character"/>
        <input class="btn-inventory" type="image" src={buttonInventory} alt="Inventory"/>
      </>
    )
  }

}

export default Ui