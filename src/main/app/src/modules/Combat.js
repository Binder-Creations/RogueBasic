import React from "react";
import {camelCase} from "./GeneralUtilities";
import {TransitionGroup} from 'react-transition-group';
import Fade from "./Fade";
// import GameOver from "./GameOver.js";

class Combat extends React.Component {
  constructor(props){
    super(props);
    this.positions = ["pc", "frontLeft", "frontCenter", "frontRight", "backLeft", "backCenter", "backRight"];
    this.state = {
      showUpdate: true
    }
    this.previousUpdates = null;
  }

  render(){
    if(this.previousUpdates && this.previousUpdates !== this.props.props.combatUpdates){
      this.setState({showUpdate: true})
    }

    let components = [];
    this.previousUpdates = this.props.props.combatUpdates;
    let gameOver = this.props.props.combatUpdates.find(combatUpdate => combatUpdate.type === "gameOver");
    if(gameOver){

    } else {
      components = components.concat(this.parseBuffs(this.props.props.pc))
      
      for(let monster of this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom].monsters){
        components = components.concat(this.parseBuffs(monster));
      }

      let key = 0;
      for(let position of this.positions){
        let updates = [];
        key++;

        for(let combatUpdate of this.props.props.combatUpdates){
          if(combatUpdate.target === position){
            updates.push(
              <tr className="nowrap">
                <td>
                  <div className="right-align combat-div">
                    <img className="combat-ability-image" src={this.props.props.abilities[camelCase(combatUpdate.abilityName)]} alt="ability"/>
                  </div>
                </td>
                <td>
                  <div className={"combat-div combat-text "+combatUpdate.type} style={combatUpdate.size}>
                    <span>
                      {combatUpdate.text}
                    </span>
                  </div>
                </td>
              </tr>);
          }
        }
        if(updates.length){
          components.push(  
            this.state.showUpdate && (
              <Fade 
                key={key} 
                in={true} 
                onEntered={() => setTimeout(() =>this.setState({showUpdate:false}), 700)}
              >
                <div className={"nopointer monster " + position}>
                  <table className="monster-update-box">
                    {updates}
                  </table>
                </div>
              </Fade>)
          );
        }
      }
    }

    return(
      <TransitionGroup>
        {components}
      </TransitionGroup>  
    );
  }

  parseBuffs(entity){
    let components = [];
    let position = entity.position ? entity.position : "pc";
    let buffs = [];
    let debuffs = [];

    let key = 0;
    for(let buff of entity.buffs){
      let statsLine = "";
      for(let stat in buff){
        if (buff[stat] > 0){
          if(stat === "regenerate"){
            statsLine += `heals ${buff[stat]} points of health each turn, `
          } else if (stat !== "duration") {
            statsLine += `increases ${stat[0].toUpperCase() + stat.substring(1).split(/(?=[A-Z])/).join(" ")} by ${buff[stat]}, `
          }
        }
      }
      statsLine = statsLine[0].toUpperCase() + statsLine.substring(1);
      statsLine = statsLine.substring(0, statsLine.length - 2);
      statsLine += "."

      buffs.push(<img className="buff-icon" src={this.props.props.abilities[buff.name]} title={statsLine} alt="buff"/>);
    }
    if(buffs.length){
      components.push(
        <Fade 
          key={key++} 
          in={true} 
        >
          <div className={"buff-box buff " + position}>
            {buffs}
          </div>
        </Fade>
      );
    }

    key = 0;
    for(let debuff of entity.debuffs){
      let statsLine = "";
      for(let stat in debuff){
        if (debuff[stat] > 0){
          if(stat === "bleed" || stat === "burn" || stat === "poison"){
            statsLine += `inflicts ${debuff[stat]} points of ${stat[0].toUpperCase() + stat.substring(1)}, `
          } else if (stat !== "duration") {
            statsLine += `decreases ${stat[0].toUpperCase() + stat.substring(1).split(/(?=[A-Z])/).join(" ")} by ${debuff[stat]}, `
          }   
        }
      }
      statsLine = statsLine[0].toUpperCase() + statsLine.substring(1);
      statsLine = statsLine.substring(0, statsLine.length - 2);
      statsLine += "."

      debuffs.push(<img className="debuff-icon" src={this.props.props.abilities[debuff.name]} title={statsLine} alt="debuff"/>);
    }
    if(debuffs.length){
      components.push(
        <Fade 
          key={key++}
          in={true}
        >
          <div className={"buff-box debuff " + position}>
            {debuffs}
          </div>
        </Fade>
      );
    }
    return components;
  }
}

export default Combat;