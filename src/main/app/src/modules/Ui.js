import React from "react";
import CharacterMenu from "./CharacterMenu";
import InventoryMenu from "./InventoryMenu";

class Ui extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      characterMenu: false,
      inventoryMenu: false
    };
    if(this.props.props.pc.characterClass == "Rogue"){
      this.buttonAttack = this.props.props.images.buttonAttackRogue;
      this.buttonAttackHover = this.props.props.images.buttonAttackRogueHover;
    } else if(this.props.props.pc.characterClass == "Wizard"){
      this.buttonAttack = this.props.props.images.buttonAttackWizard;
      this.buttonAttackHover = this.props.props.images.buttonAttackWizardHover;
    } else {
      this.buttonAttack = this.props.props.images.buttonAttackWarrior;
      this.buttonAttackHover = this.props.props.images.buttonAttackWarriorHover;   
    }
    this.toggleCharacterMenu = this.toggleCharacterMenu.bind(this);
    this.toggleInventoryMenu = this.toggleInventoryMenu.bind(this);
  }

  render(){
    this.healthStyle = {
      width: 20*(this.props.props.pc.currentHealth/this.props.props.pc.healthTotal) + "%"
    }
    this.energyStyle = {
       width: 20*(this.props.props.pc.currentEnergy/this.props.props.pc.energyTotal) + "%"
    }
    this.healthHover = this.props.props.pc.currentHealth + "/" + this.props.props.pc.healthTotal;
    this.energyHover = this.props.props.pc.currentEnergy + "/" + this.props.props.pc.energyTotal;
    let Skill = (props) => {
      return(
        <button className={"skill s-"+props.number}>
          <img className="glow" src={this.props.props.images.skillGlow} alt="Skill"/>
          <img className="frame" src={this.props.props.images.skillBackground} alt="Skill"/>
          <img className="frame" src={this.props.props.images.skillFrame} alt="Skill"/>
        </button>
      )
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
        <Skill number="1"/>
        <Skill number="2"/>
        <Skill number="3"/>
        <Skill number="4"/>
        <img className="map-underlay" src={this.props.props.images.mapUnderlay} alt="Map"/>
        <img className="map-overlay" src={this.props.props.images.mapOverlay} alt="Map"/>
        <input class="btn-attack" type="image" src={this.buttonAttack} alt="Attack"
          onMouseEnter={e => (e.currentTarget.src=this.buttonAttackHover)}
          onMouseLeave={e => (e.currentTarget.src=this.buttonAttack)}
        />
        <input class="btn-character" type="image" src={this.props.props.images.buttonCharacter} alt="Character" onClick={this.toggleCharacterMenu} 
          onMouseEnter={e => (e.currentTarget.src=this.props.props.images.buttonCharacterHover)}
          onMouseLeave={e => (e.currentTarget.src=this.props.props.images.buttonCharacter)}
        />
        <input class="btn-inventory" type="image" src={this.props.props.images.buttonInventory} alt="Inventory" onClick={this.toggleInventoryMenu} 
          onMouseEnter={e => (e.currentTarget.src=this.props.props.images.buttonInventoryHover)}
          onMouseLeave={e => (e.currentTarget.src=this.props.props.images.buttonInventory)}
        />
        <CharacterMenu props={this.props.props} toggle={this.toggleCharacterMenu} on={this.state.characterMenu}/>
        <InventoryMenu props={this.props.props} toggle={this.toggleInventoryMenu} on={this.state.inventoryMenu}/>
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