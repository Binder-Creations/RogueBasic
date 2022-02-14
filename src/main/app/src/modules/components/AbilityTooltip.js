import React from "react";
import {min, max, round} from "../services/AbilityServices";
import {camelCase} from "../services/GeneralUtilities";

class AbilityTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.setStyle = this.setStyle.bind(this);
  }

  render(){
    this.setStyle();
    let ability = this.props.s.pc.abilities[this.props.number]

    let hits = ability.hits > 1
      ? "x"+ability.hits
      : ""

    let Level = () => {
      return(
        this.props.s.pc.level >= ability.level
          ? <></>
          : <>
              <p>Requires:</p>
              <p>{"level " + ability.level}</p>
            </>
      );
    }  

    let icon;
    let iconClass;
    let valueColor;
    let value;
    let targetImage;
    let targetText;
    let is0 = "";

    if(this.props.number === 0){
      is0 = "-0"
    }

    if(ability.type === "Attack") {
      targetImage = this.props.c.images["target"+ability.target[0].toUpperCase() + ability.target.substring(1)];
      switch(ability.target) {
        case "random":
          targetText = "Targets enemies at random";
          break;
        case "cone":
          targetText = "Targets the front-center and all back-row enemies";
          break;
        case "backCenter":
          targetText = "Prioritizes the back-center enemy";
          break;
        case "frontLeft":
          targetText = "Prioritizes the front-left enemy";
          break;
        case "frontCenter":
          targetText = "Prioritizes the front-center enemy";
          break;
        case "frontRight":
          targetText = "Prioritizes the front-right enemy";
          break;
        case "frontRow":
          targetText = "Targets all enemies in a row, prioritizing the front row";
          break;
        case "lowHealth":
          targetText = "Prioritizes the lowest-health enemy";
          break;
        case "highHealth":
          targetText = "Prioritizes the highest-health enemy";
          break;
      }
    } else if (ability.type === "Chain") {
      targetImage = this.props.c.images.targetChain;
      targetText = "Prioritizes the back center enemy before bouncing to random targets"
    } else if (ability.type === "Buff") {
      targetImage = this.props.c.images.targetSelf;
      targetText = "Self-targeted"
    }

    if(ability.icon === "Power"){
      icon = this.props.c.images.iconPower
      iconClass = "item-tooltip-icon-power"
      valueColor = this.props.c.colorPower.color
    } else {
      icon = this.props.c.images.iconArmor
      iconClass = "item-tooltip-icon-armor"
      valueColor = this.props.c.colorArmor.color
    }

    if(ability.factor){
      value = min(this.props.s.pc, ability.modifier, ability.factor) + "-" + max(this.props.s.pc, ability.modifier, ability.factor)
    } else {
      value = round(this.props.s.pc, ability.modifier)
    }

    let valueText = value + hits;

    let valueStyle = {
      fontSize: (valueText.length < 5 ? 128 : (134-valueText.length*7)) + "%",
      color: valueColor
    }
    
    return (
      <div className={"ability-tooltip-box"+is0} style={this.style}>
        <img className="absolute-fill" src={this.props.c.images.tooltipSkill} alt="background"/>
        <img className="item-tooltip-image" src={this.props.c.abilities[camelCase(this.props.s.pc.abilities[this.props.number].name)]} alt="Skill"/>
        <img className="ability-tooltip-target" src={targetImage} style={{pointerEvents: "auto"}} title={targetText} alt="Target"/>
        <img className={"item-tooltip-badge-"+this.props.s.pc.characterClass.toLowerCase()} src={this.props.c.images["badge"+this.props.s.pc.characterClass+"Small"]} alt="class"/>
        <img className={iconClass} src={icon} alt="icon"/>
        <p className="item-tooltip-icon-value" style={valueStyle}>{valueText}</p>
        <p className="item-tooltip-name" style={ability.name.length > 13 ? {fontSize: (139 - ability.name.length*3) + "%" } : {}}>{ability.name}</p>
        <p className="ability-tooltip-cost">{ability.cost}</p>      
        <div className="ability-tooltip-requirement">
          <Level/>
        </div>
        <div className="item-tooltip-statbox" style={ability.description.length > 80 ? {fontSize: (70 - (ability.description.length - 80)/3.5) + "%" } : {}}>{ability.description}</div>
      </div>
    )
  }

  setStyle(){
    this.style = {height: + this.props.c.appWidth*0.25 + "px", width: + this.props.c.appWidth*0.1667 + "px"}
  }

}

export default AbilityTooltip;