import React from "react";

class SkillTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.setStyle = this.setStyle.bind(this);
  }

  render(){
    this.setStyle();

    let hits = this.props.props.pc.abilities[this.props.number-1].hits > 1
      ? "x"+this.props.props.pc.abilities[this.props.number-1].hits
      : ""

    let Level = () => {
      return(
        this.props.props.pc.level >= this.props.props.pc.abilities[this.props.number-1].level
          ? <></>
          : <>
              <p>Requires:</p>
              <p>{"level " + this.props.props.pc.abilities[this.props.number-1].level}</p>
            </>
      );
    }  

    let icon;
    let iconClass;
    let valueStyle;

    if(this.props.props.pc.abilities[this.props.number-1].type == "Power"){
      icon = this.props.props.images.iconPower
      iconClass = "item-tooltip-icon-power"
      valueStyle = this.props.props.colorPower
    } else {
      icon = this.props.props.images.iconArmor
      iconClass = "item-tooltip-icon-armor"
      valueStyle = this.props.props.colorArmor
    }

    return (
      <div className={"skill-tooltip-box"} style={this.style}>
        <img className="absolute-fill" src={this.props.props.images.tooltipSkill}/>
        <img className="item-tooltip-image" src={this.props.props.images["skill" + this.props.props.pc.characterClass + this.props.number]} alt="Skill"/>
        <img className={"item-tooltip-badge-"+this.props.props.pc.characterClass.toLowerCase()} src={this.props.props.images["badge"+this.props.props.pc.characterClass+"Small"]}/>
        <img className={iconClass} src={icon}/>
        <p className="item-tooltip-icon-value" style={valueStyle}>{Math.round((this.props.props.pc.abilities[this.props.number-1].modifier*this.props.props.pc.baseDamage/100)*(1+(this.props.props.pc.powerBonus/100)))+hits}</p>
        <p className="item-tooltip-name">{this.props.props.pc.abilities[this.props.number-1].name}</p>
        <p className="skill-tooltip-cost">{this.props.props.pc.abilities[this.props.number-1].cost}</p>      
        <div className="skill-tooltip-requirement">
          <Level/>
        </div>
        <div className="item-tooltip-statbox">{this.props.props.pc.abilities[this.props.number-1].description}</div>
      </div>
    )
  }

  setStyle(){
    this.style = {height: + window.innerWidth*0.25 + "px", width: + window.innerWidth*0.1667 + "px"}
  }
}

export default SkillTooltip;