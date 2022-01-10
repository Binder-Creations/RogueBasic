import React from "react";
import {min, max, round} from "./AbilityServices";
import {camelCase} from "./GeneralUtilities";

class AbilityTooltip extends React.Component {
  
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
    let targetImage;
    let targetText;

    if(ability.type === "Attack") {
      targetImage = this.props.props.images["target"+ability.target[0].toUpperCase() + ability.target.substring(1)];
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
      targetImage = this.props.props.images.targetChain;
      targetText = "Prioritizes the back center enemy before bouncing to random targets"
    } else if (ability.type === "Buff") {
      targetImage = this.props.props.images.targetSelf;
      targetText = "Self-targeted"
    }

    if(ability.icon === "Power"){
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
      <div className={"ability-tooltip-box"} style={this.style}>
        <img className="absolute-fill" src={this.props.props.images.tooltipSkill} alt="background"/>
        <img className="item-tooltip-image" src={this.props.props.abilities[camelCase(this.props.props.pc.abilities[this.props.number].name)]} alt="Skill"/>
        <img className="ability-tooltip-target" src={targetImage} style={{pointerEvents: "auto"}} title={targetText} alt="Target"/>
        <img className={"item-tooltip-badge-"+this.props.props.pc.characterClass.toLowerCase()} src={this.props.props.images["badge"+this.props.props.pc.characterClass+"Small"]} alt="class"/>
        <img className={iconClass} src={icon} alt="icon"/>
        <p className="item-tooltip-icon-value" style={valueStyle}>{value+hits}</p>
        <p className="item-tooltip-name">{ability.name}</p>
        <p className="ability-tooltip-cost">{ability.cost}</p>      
        <div className="ability-tooltip-requirement">
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

export default AbilityTooltip;