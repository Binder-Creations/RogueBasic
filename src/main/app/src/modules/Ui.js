import React from "react";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";
import SkillTooltip from "./SkillTooltip";
import Minimap from "./Minimap";

class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      characterMenu: false,
      inventoryMenu: false
    };
    if(this.props.props.pc.characterClass === "Rogue"){
      this.buttonAttack = this.props.props.images.buttonAttackRogue;
    } else if(this.props.props.pc.characterClass === "Wizard"){
      this.buttonAttack = this.props.props.images.buttonAttackWizard;
    } else {
      this.buttonAttack = this.props.props.images.buttonAttackWarrior;
    }
    this.toggleCharacterMenu = this.toggleCharacterMenu.bind(this);
    this.toggleInventoryMenu = this.toggleInventoryMenu.bind(this);

    this.Skill = (props) => {
      let className = "gray-100";
      let onClick = "";

      if(this.props.props.pc.level >= this.props.props.pc.abilities[props.number].level){
        if(this.props.props.combat){
          if(this.props.props.pc.currentEnergy >= this.props.props.pc.abilities[props.number].cost){
            className="hover-saturate"
            onClick= () => this.props.props.appState("ability", this.props.number)
          } else {
            className="saturate-66"
          }
        } else {
          className="saturate-66"
        }
      }

      return(
        <button className={className+" skill-tooltip skill s-"+props.number} onClick={onClick}>
          <img className="absolute-fill" src={this.props.props.images["skill" + this.props.props.pc.characterClass + props.number]} alt="Skill"/>
          <SkillTooltip props={this.props.props} number={props.number}/>
        </button>
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

    this.attackButton = <></>;
    this.AttackButton = () => {
      return(this.attackButton);
    }
  }

  render(){
    this.ReturnButton = this[this.props.button];
    this.healthStyle = {
      width: 20*(this.props.props.pc.currentHealth/this.props.props.pc.healthTotal) + "%"
    }
    this.energyStyle = {
       width: 20*(this.props.props.pc.currentEnergy/this.props.props.pc.energyTotal) + "%"
    }
    this.healthHover = this.props.props.pc.currentHealth + "/" + this.props.props.pc.healthTotal;
    this.energyHover = this.props.props.pc.currentEnergy + "/" + this.props.props.pc.energyTotal;

    if(this.props.disableUiMenus && (this.state.characterMenu || this.state.inventoryMenu)){
      this.setState({characterMenu: false, inventoryMenu: false})
    }
    
    if(this.props.props.combat){
      this.attackButton = <input class="btn-attack hover-saturate" type="image" src={this.buttonAttack} alt="Attack" onClick={() => this.props.props.appState("ability", 0)}/>
    } else {
      this.attackButton = <input class="btn-attack" type="image" src={this.buttonAttack} alt="Attack"/>
    }

    return(
      <>
        <img className="bar-health-background" src={this.props.props.images.barBackground} alt="Health" title={this.healthHover}/>
        <img className="bar-health" src={this.props.props.images.barHealth} alt="Health" title={this.healthHover} style={this.healthStyle}/>
        <img className="bar-health-frame" src={this.props.props.images.barFrame} alt="Health" title={this.healthHover}/>
        <img className="heart" src={this.props.props.images.heart} alt="Health" title={this.healthHover}/>
        <img className="bar-energy-background" src={this.props.props.images.barBackground} alt="Energy" title={this.energyHover}/>
        <img className="bar-energy" src={this.props.props.images.barEnergy} alt="Energy" title={this.energyHover} style={this.energyStyle}/>
        <img className="bar-energy-frame" src={this.props.props.images.barFrame} alt="Energy" title={this.energyHover}/>
        <img className="swirl" src={this.props.props.images.swirl} alt="Energy" title={this.energyHover}/>
        <this.Skill number="1"/>
        <this.Skill number="2"/>
        <this.Skill number="3"/>
        <this.Skill number="4"/>  
        <Minimap props={this.props.props} dungeon={this.props.dungeon}/>
        <this.AttackButton/>
        <input class="btn-character hover-saturate-250" type="image" src={this.props.props.images.buttonCharacter} alt="Character" onClick={this.toggleCharacterMenu}/>
        <input class="btn-inventory hover-saturate-250" type="image" src={this.props.props.images.buttonInventory} alt="Inventory" onClick={this.toggleInventoryMenu}/>
        <CharacterMenu props={this.props.props} toggle={this.toggleCharacterMenu} on={this.state.characterMenu}/>
        <InventoryMenu props={this.props.props} toggle={this.toggleInventoryMenu} on={this.state.inventoryMenu}/>
        <this.ReturnButton/>
      </>
    )
  }

  toggleCharacterMenu(){
    if(!this.props.disableUiMenus){
      this.state.characterMenu
        ? this.setState({characterMenu: false})
        : this.setState({characterMenu: true});
    }
  }

  toggleInventoryMenu(){
    if(!this.props.disableUiMenus){
      this.state.inventoryMenu
        ? this.setState({inventoryMenu: false})
        : this.setState({inventoryMenu: true});
    }
  }

}

export default Ui

/* <input class="btn-inventory" type="image" src={this.props.props.images.buttonInventory} alt="Inventory" onClick={this.toggleInventoryMenu} 
onMouseEnter={e => (e.currentTarget.src=this.props.props.images.buttonInventoryHover)}
onMouseLeave={e => (e.currentTarget.src=this.props.props.images.buttonInventory)}
/> */