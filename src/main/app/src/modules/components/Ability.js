import c from "../../data/CommonProperties";
import React from "react";
import AbilityTooltip from "./AbilityTooltip";
import {camelCase} from "../services/GeneralUtilities";

class Ability extends React.Component {
 render(){
  let className = "gray-100";
  if(this.props.s.pc.level >= this.props.s.pc.abilities[this.props.number].level){
    if(this.props.s.combat && !this.props.s.position){
      if(this.props.s.pc.currentEnergy >= this.props.s.pc.abilities[this.props.number].cost){
        className = "hover-saturate"
      } else {
        className = "saturate-66";
      }
    } else {
      className = "saturate-66";
    }
  }
  return(
    <div className={className+" ability-tooltip ability a-" + this.props.number} 
      onClick={() => {
        if(this.props.s.combat && !this.props.s.position && this.props.s.pc.currentEnergy >= this.props.s.pc.abilities[this.props.number].cost){
          c.ability(this.props.number)
        }
      }
    }>
      <img className="absolute-fill nopointer" src={c.images.abilities[camelCase(this.props.s.pc.abilities[this.props.number].name)]} alt="Ability"/>
      <AbilityTooltip s={this.props.s} number={this.props.number}/>
    </div>
  )
 }
}

export default Ability;