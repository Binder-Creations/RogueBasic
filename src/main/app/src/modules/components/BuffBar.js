import React from "react";
import c from "../../data/CommonProperties";
import {TransitionGroup} from 'react-transition-group';
import Fade from "../animations/Fade";

export default class BuffBar extends React.Component {
  
  render(){
    let components = [];
    let position = this.props.entity.position ? " " + this.props.entity.position : " pc";
    this.buffs(components);

    return(
      <TransitionGroup key={this.props.entity.position+"-"+this.props.type} className={"buff-box " + this.props.type + position}>
        {components}
      </TransitionGroup>
    );
  }

  buffs(components){
    let key = 0;
    for(let buff of this.props.entity[this.props.type]){
      let statsLine = "";
      let duration = buff.duration > 0
        ? buff.duration === 1
          ? " Lasts 1 more round."
          : " Lasts " + buff.duration + " more rounds."
        : " Lasts until end of combat."
      for(let stat in buff){
        if (buff[stat] > 0){
          if(stat === "regenerate") {
            statsLine += `heals ${buff[stat]} points of health each turn, `
          } else if (stat === "bleed" || stat === "burn" || stat === "poison") {
              statsLine += `inflicts ${buff[stat]} points of ${stat[0].toUpperCase() + stat.substring(1)}, `
          } else if (stat !== "duration" && this.props.type === "buffs") {
            statsLine += `increases ${stat[0].toUpperCase() + stat.substring(1).split(/(?=[A-Z])/).join(" ")} by ${buff[stat]}, `
          } else if (stat !== "duration" && this.props.type === "debuffs") {
            statsLine += `decreases ${stat[0].toUpperCase() + stat.substring(1).split(/(?=[A-Z])/).join(" ")} by ${buff[stat]}, `
          }
        }
      }
      statsLine = statsLine[0].toUpperCase() + statsLine.substring(1);
      statsLine = statsLine.substring(0, statsLine.length - 2);
      statsLine += "."

      components.push(
        this.props.s.combatTransitions && (
        <Fade
          key={key++} 
          in
        >
          <img className="buff-icon" src={c.images.abilities[buff.name]} title={statsLine + duration} alt="buff"/>
        </Fade>
      ));
    }
  }
}