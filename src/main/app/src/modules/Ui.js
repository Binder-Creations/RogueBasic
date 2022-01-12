import React from "react";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";
import AbilityTooltip from "./AbilityTooltip";
import Minimap from "./Minimap";
import {camelCase} from "./GeneralUtilities";
import FadeUp from "./FadeUp";
import Fade from "./Fade";
import GameOver from "./GameOver";

class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      characterMenu: false,
      inventoryMenu: false,
      level: this.props.props.pc.level
    };
    if(this.props.props.pc.characterClass === "Rogue"){
      this.buttonAttack = this.props.props.images.buttonAttackRogue;
    } else if(this.props.props.pc.characterClass === "Wizard"){
      this.buttonAttack = this.props.props.images.buttonAttackWizard;
    } else {
      this.buttonAttack = this.props.props.images.buttonAttackWarrior;
    }
    
    this.Ability = (props) => {
      let className = "gray-100";
      let onClick = null;
      if(this.props.props.pc.level >= this.props.props.pc.abilities[props.number].level){
        if(this.props.props.combat && !this.props.props.position){
          if(this.props.props.pc.currentEnergy >= this.props.props.pc.abilities[props.number].cost){
            className="hover-saturate"
            onClick= () => this.props.props.appState("ability", props.number)
          } else {
            className="saturate-66"
          }
        } else {
          className="saturate-66"
        }
      }
      return(
        <div className={className+" ability-tooltip ability a-"+props.number} onClick={onClick}>
          <img className="absolute-fill" src={this.props.props.abilities[camelCase(this.props.props.pc.abilities[props.number].name)]} alt="Ability"/>
          <AbilityTooltip props={this.props.props} number={props.number}/>
        </div>
      )
    }

    this.TownButton = () => {
      return(
      <button className="btn-home" onClick={ () => {this.props.props.appState("scene", "Default", "Town")} }>
        <img src={this.props.props.images.townIcon} alt="Town"/>
      </button>
      );
    }

    this.HomeButton = () => {
      return(
      <button className="btn-home" onClick={ () => {this.props.props.appState("scene", "Home", "Town")} }>
        <img src={this.props.props.images.scrollIcon} alt="Home"/>
      </button>
      );
    }

    this.NoButton = () => {
      return(<></>);
    }

    this.attackButton = <></>;
    this.AttackButton = () => {
      return(this.attackButton);
    }
  }

  render(){
    let components = [];

    this.ReturnButton = (this.props.props.combat || this.props.props.gameOver) ? this.NoButton : this[this.props.button];
    this.healthStyle = {
      width: 20*(this.props.props.pc.currentHealth/this.props.props.pc.healthTotal) + "%"
    }
    this.energyStyle = {
       width: 20*(this.props.props.pc.currentEnergy/this.props.props.pc.energyTotal) + "%"
    }
    this.healthHover = this.props.props.pc.currentHealth + "/" + this.props.props.pc.healthTotal;
    this.energyHover = this.props.props.pc.currentEnergy + "/" + this.props.props.pc.energyTotal;
    
    if(this.props.props.combat && !this.props.props.position){
      this.attackButton =
        <div className={"btn-attack hover-saturate ability-tooltip"} onClick={() => this.props.props.appState("ability", 0)}>
          <img className="absolute-fill" src={this.buttonAttack} alt="Attack"/>
          <AbilityTooltip props={this.props.props} number={0}/>
        </div>
    } else {
      this.attackButton =
        <div className={"btn-attack hover-saturate ability-tooltip"}>
          <img className="absolute-fill" src={this.buttonAttack} alt="Attack"/>
          <AbilityTooltip props={this.props.props} number={0}/>
        </div>
    }

    components.push(
      <>
          <img className="bar-health-background" src={this.props.props.images.barBackground} alt="Health" title={this.healthHover}/>
          <img className="bar-health" src={this.props.props.images.barHealth} alt="Health" title={this.healthHover} style={this.healthStyle}/>
          <img className="bar-health-frame" src={this.props.props.images.barFrame} alt="Health" title={this.healthHover}/>
          <img className="heart" src={this.props.props.images.heart} alt="Health" title={this.healthHover}/>
          <img className="bar-energy-background" src={this.props.props.images.barBackground} alt="Energy" title={this.energyHover}/>
          <img className="bar-energy" src={this.props.props.images.barEnergy} alt="Energy" title={this.energyHover} style={this.energyStyle}/>
          <img className="bar-energy-frame" src={this.props.props.images.barFrame} alt="Energy" title={this.energyHover}/>
          <img className="swirl" src={this.props.props.images.swirl} alt="Energy" title={this.energyHover}/>
          <this.Ability number={1}/>
          <this.Ability number={2}/>
          <this.Ability number={3}/>
          <this.Ability number={4}/>  
          <Minimap props={this.props.props} dungeon={this.props.dungeon}/>
          <this.AttackButton/>
          <input class="btn-character hover-saturate-250" type="image" src={this.props.props.images.buttonCharacter} alt="Character" onClick={() => this.props.props.appState("menu", "character")}/>
          <input class="btn-inventory hover-saturate-250" type="image" src={this.props.props.images.buttonInventory} alt="Inventory" onClick={() => this.props.props.appState("menu", "inventory")}/>
          <this.ReturnButton/>
      </>
    );
    
    if(this.props.props.gameOver){
      components.push(
        <Fade
          in
          key="gg"
        >
          <GameOver props={this.props.props}/>
        </Fade>
      )
    } else {
      if(this.props.props.menu === "character"){
        components.push(<CharacterMenu props={this.props.props}/>)
      }

      if(this.props.props.menu === "inventory"){
        components.push(<InventoryMenu props={this.props.props}/>)
      }
      
      if(this.props.props.pc.attributePoints && this.props.props.menu !== "character" && this.props.props.menu !== "inventory"){
        components.push(<img className="unspent-points noclick" src={this.props.props.images.unspentPoints} alt="unspent points"/>);
      }
      
      components.push((this.props.props.pc.level > this.state.level) && (
        <div className="popup level-up">
          <FadeUp
          in = {(this.props.props.pc.level > this.state.level)}
          onEntered={() => {
            document.documentElement.style.setProperty('--animate-duration', '1s')
            this.setState({level: this.props.props.pc.level})
          }}
          >
            <img src={this.props.props.images.levelUp} alt="level up"/>
          </FadeUp>
        </div>
      ));
    }

    return components;
  }
}

export default Ui