import React from "react";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";
import Ability from "./Ability";
import AbilityTooltip from "./AbilityTooltip";
import Minimap from "./Minimap";
import GameOver from "./GameOver";
import FadeUp from "../animations/FadeUp";
import Fade from "../animations/Fade";


class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      characterMenu: false,
      inventoryMenu: false,
      level: this.props.s.pc.level
    };

    this.townButton =
      <button className="btn-home" onClick={() => {this.props.c.scene("Default", "Town")} }>
        <img src={this.props.c.images.townIcon} alt="Town"/>
      </button>


    this.homeButton = 
      <button className="btn-home" onClick={() => {this.props.c.scene("Home", "Town")} }>
        <img src={this.props.c.images.scrollIcon} alt="Home"/>
      </button>

    this.noButton = <></>;
  }

  render(){
    let components = [];
    let attackButton =
    <div className={"btn-attack hover-saturate ability-tooltip"} 
      onClick={() => {
        if(this.props.s.combat && !this.props.s.position){
          this.props.c.ability(0)
        }
      }
    }>
      <img className="absolute-fill nopointer" src={this.props.c.images["buttonAttack"+this.props.s.pc.characterClass]} alt="Attack"/>
      <AbilityTooltip c={this.props.c} s={this.props.s} number={0}/>
    </div>
    this.returnButton = (this.props.s.combat || this.props.s.gameOver) 
      ? this.NoButton 
      : this.props.c.homeButton 
        ? this.homeButton 
        : this.townButton;
    this.healthStyle = {
      width: 20*(this.props.s.pc.currentHealth/this.props.s.pc.healthTotal) + "%"
    }
    this.energyStyle = {
       width: 20*(this.props.s.pc.currentEnergy/this.props.s.pc.energyTotal) + "%"
    }
    this.healthHover = this.props.s.pc.currentHealth + "/" + this.props.s.pc.healthTotal;
    this.energyHover = this.props.s.pc.currentEnergy + "/" + this.props.s.pc.energyTotal;
    

    components.push(
      <>
          <img className="bar-health-background" src={this.props.c.images.barBackground} alt="Health" title={this.healthHover}/>
          <img className="bar-health" src={this.props.c.images.barHealth} alt="Health" title={this.healthHover} style={this.healthStyle}/>
          <img className="bar-health-frame" src={this.props.c.images.barFrame} alt="Health" title={this.healthHover}/>
          <img className="heart" src={this.props.c.images.heart} alt="Health" title={this.healthHover}/>
          <img className="bar-energy-background" src={this.props.c.images.barBackground} alt="Energy" title={this.energyHover}/>
          <img className="bar-energy" src={this.props.c.images.barEnergy} alt="Energy" title={this.energyHover} style={this.energyStyle}/>
          <img className="bar-energy-frame" src={this.props.c.images.barFrame} alt="Energy" title={this.energyHover}/>
          <img className="swirl" src={this.props.c.images.swirl} alt="Energy" title={this.energyHover}/>
          <Ability c={this.props.c} s={this.props.s} number={1}/>
          <Ability c={this.props.c} s={this.props.s} number={2}/>
          <Ability c={this.props.c} s={this.props.s} number={3}/>
          <Ability c={this.props.c} s={this.props.s} number={4}/>  
          <Minimap c={this.props.c} s={this.props.s} dungeon={this.props.s.dungeon}/>
          {attackButton}
          <input class="btn-character hover-saturate-250" type="image" src={this.props.c.images.buttonCharacter} alt="Character" onClick={() => this.props.c.menu("character")}/>
          <input class="btn-inventory hover-saturate-250" type="image" src={this.props.c.images.buttonInventory} alt="Inventory" onClick={() => this.props.c.menu("inventory")}/>
          {this.returnButton}
      </>
    );
    
    if(this.props.s.gameOver){
      components.push(
        <Fade
          in
          key="gg"
        >
          <GameOver c={this.props.c} s={this.props.s}/>
        </Fade>
      )
    } else {
      if(this.props.s.menu === "character"){
        components.push(<CharacterMenu c={this.props.c} s={this.props.s}/>)
      }

      if(this.props.s.menu === "inventory"){
        components.push(<InventoryMenu c={this.props.c} s={this.props.s}/>)
      }
      
      if(this.props.s.pc.attributePoints && this.props.s.menu !== "character" && this.props.s.menu !== "inventory"){
        components.push(<img className="unspent-points noclick" src={this.props.c.images.unspentPoints} alt="unspent points"/>);
      }
      
      components.push((this.props.s.pc.level > this.state.level) && (
        <div className="level-up">
          <FadeUp
          in = {(this.props.s.pc.level > this.state.level)}
          onEntered={() => {
            document.documentElement.style.setProperty('--animate-duration', '1s')
            this.setState({level: this.props.s.pc.level})
          }}
          >
            <img src={this.props.c.images.levelUp} alt="level up"/>
          </FadeUp>
        </div>
      ));
    }

    return components;
  }
}

export default Ui