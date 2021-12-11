import React from "react";
import {min, max, round} from "./AbilityServices";
import {camelCase} from "./GeneralUtilities";

class SkillTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.setStyle = this.setStyle.bind(this);
  }

  render(){
    this.setStyle();
    let ability = this.props.props.pc.abilities[this.props.number]

    let hits = ability.hits > 1
      ? "x"+ability.hits
      : ""

    let Level = () => {
      return(
        this.props.props.pc.level >= ability.level
          ? <></>
          : <>
              <p>Requires:</p>
              <p>{"level " + ability.level}</p>
            </>
      );
    }  

    let icon;
    let iconClass;
    let valueStyle;
    let value;

    if(ability.type === "Power"){
      icon = this.props.props.images.iconPower
      iconClass = "item-tooltip-icon-power"
      valueStyle = this.props.props.colorPower
    } else {
      icon = this.props.props.images.iconArmor
      iconClass = "item-tooltip-icon-armor"
      valueStyle = this.props.props.colorArmor
    }

    if(ability.factor){
      value = min(this.props.props.pc, ability.modifier, ability.factor) + "-" + max(this.props.props.pc, ability.modifier, ability.factor)
    } else {
      value = round(this.props.props.pc, ability.modifier)
    }
    
    return (
      <div className={"skill-tooltip-box"} style={this.style}>
        <img className="absolute-fill" src={this.props.props.images.tooltipSkill} alt="background"/>
        <img className="item-tooltip-image" src={this.props.props.abilities[camelCase(this.props.props.pc.abilities[this.props.number].name)]} alt="Skill"/>
        <img className={"item-tooltip-badge-"+this.props.props.pc.characterClass.toLowerCase()} src={this.props.props.images["badge"+this.props.props.pc.characterClass+"Small"]} alt="class"/>
        <img className={iconClass} src={icon} alt="icon"/>
        <p className="item-tooltip-icon-value" style={valueStyle}>{value+hits}</p>
        <p className="item-tooltip-name">{ability.name}</p>
        <p className="skill-tooltip-cost">{ability.cost}</p>      
        <div className="skill-tooltip-requirement">
          <Level/>
        </div>
        <div className="item-tooltip-statbox">{ability.description}</div>
      </div>
    )
  }

  setStyle(){
    this.style = {height: + window.innerWidth*0.25 + "px", width: + window.innerWidth*0.1667 + "px"}
  }

}

export default SkillTooltip;