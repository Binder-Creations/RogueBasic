import React from "react";
import c from "../../data/CommonProperties";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";
import Ability from "./Ability";
import AbilityTooltip from "./AbilityTooltip";
import Minimap from "./Minimap";
import GameOver from "./GameOver";
import FadeUp from "../animations/FadeUp";
import Fade from "../animations/Fade";
import AppServices from "../services/AppServices";


export default class Ui extends React.Component {

  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();

    this.state = {
      characterMenu: false,
      inventoryMenu: false,
      level: this.props.s.pc.level
    };

    this.townButton =
      <button className="btn-home" onClick={() => {this.appServices.scene("Default", "Town")} }>
        <img src={c.images.common.townIcon} alt="Town"/>
      </button>


    this.homeButton = 
      <button className="btn-home" onClick={() => {this.appServices.setState({page: "home", menu: null})} }>
        <img src={c.images.common.scrollIcon} alt="Home"/>
      </button>

    this.noButton = <></>;
  }

  render(){
    let components = [];
    let attackButton =
    <div className={"btn-attack hover-saturate ability-tooltip"} 
      onClick={() => {
        if(this.props.s.combat && !this.props.s.position){
          this.appServices.ability(0)
        }
      }
    }>
      <img className="absolute-fill nopointer" src={c.images.class.buttonAttack} alt="Attack"/>
      <AbilityTooltip s={this.props.s} number={0}/>
    </div>
    this.returnButton = (this.props.s.combat || this.props.s.gameOver) 
      ? this.NoButton 
      : c.homeButton 
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
          <img className="bar-health-background" src={c.images.common.barBackground} alt="Health" title={this.healthHover}/>
          <img className="bar-health" src={c.images.common.barHealth} alt="Health" title={this.healthHover} style={this.healthStyle}/>
          <img className="bar-health-frame" src={c.images.common.barFrame} alt="Health" title={this.healthHover}/>
          <img className="heart" src={c.images.common.heart} alt="Health" title={this.healthHover}/>
          <img className="bar-energy-background" src={c.images.common.barBackground} alt="Energy" title={this.energyHover}/>
          <img className="bar-energy" src={c.images.common.barEnergy} alt="Energy" title={this.energyHover} style={this.energyStyle}/>
          <img className="bar-energy-frame" src={c.images.common.barFrame} alt="Energy" title={this.energyHover}/>
          <img className="swirl" src={c.images.common.swirl} alt="Energy" title={this.energyHover}/>
          <Ability s={this.props.s} number={1}/>
          <Ability s={this.props.s} number={2}/>
          <Ability s={this.props.s} number={3}/>
          <Ability s={this.props.s} number={4}/>  
          <Minimap s={this.props.s} dungeon={this.props.s.dungeon}/>
          {attackButton}
          <input class="btn-character hover-saturate-250" type="image" src={c.images.common.buttonCharacter} alt="Character" onClick={() => this.appServices.menu("character")}/>
          <input class="btn-inventory hover-saturate-250" type="image" src={c.images.common.buttonInventory} alt="Inventory" onClick={() => this.appServices.menu("inventory")}/>
          {this.returnButton}
      </>
    );
    
    if(this.props.s.gameOver){
      components.push(
        <Fade
          in
          key="gg"
        >
          <GameOver s={this.props.s}/>
        </Fade>
      )
    } else {
      if(this.props.s.menu === "character"){
        components.push(<CharacterMenu s={this.props.s}/>)
      }

      if(this.props.s.menu === "inventory"){
        components.push(<InventoryMenu s={this.props.s}/>)
      }
      
      if(this.props.s.pc.attributePoints && this.props.s.menu !== "character" && this.props.s.menu !== "inventory"){
        components.push(<img className="unspent-points noclick" src={c.images.common.unspentPoints} alt="unspent points"/>);
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
            <img src={c.images.common.levelUp} alt="level up"/>
          </FadeUp>
        </div>
      ));
    }

    return components;
  }
}