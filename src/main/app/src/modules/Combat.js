import React from "react";
import {camelCase} from "./GeneralUtilities";
// import GameOver from "./GameOver.js";

class Combat extends React.Component {
  constructor(props){
    super(props);
    this.positions = ["pc", "frontLeft", "frontCenter", "frontRight", "backLeft", "backCenter", "backRight"];
  }

  render(){
    let components = [];
    let gameOver = this.props.props.combatUpdates.find(combatUpdate => combatUpdate.type === "gameOver");
    if(gameOver){

    } else {
      components = components.concat(this.parseBuffs(this.props.props.pc))
      
      for(let monster of this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom].monsters){
        components = components.concat(this.parseBuffs(monster));
      }

      for(let position of this.positions){
        let updates = [];
        let Updates = () => {
          return updates.length ? updates : null;
        }
        
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
                    {combatUpdate.text}
                  </div>
                </td>
              </tr>);
          }
        }
        if(updates.length){
          components.push(
          <div className={"monster " + position}>
            <table className="monster-update-box">
              <Updates/>
            </table>
          </div>);
        }
      }
    }

    return components;
  }

  parseBuffs(entity){
    let components = [];
    let position = entity.position ? entity.position : "pc";
    let buffs = [];
    let Buffs = () => {
      return buffs.length ? buffs : null;
    }
    let debuffs = [];
    let Debuffs = () => {
      return debuffs.length ? debuffs : null;
    }

    for(let buff of entity.buffs){
      let statsLine = "Increases";
      for(let stat in buff){
        if (buff[stat] > 0){
          statsLine += ` ${stat} by ${buff[stat]},`
        }
      }
      statsLine = statsLine.substring(0, statsLine.length - 2);
      statsLine += "."

      buffs.push(<img className="buff-icon" src={this.props.props.abilities[buff.name]} title={statsLine} alt="buff"/>)
    }
    if(buffs.length){
      components.push(
        <div className={"buff-box buff " + position}>
          <Buffs/>
        </div>
      );
    }

    for(let debuff of entity.debuffs){
      let statsLine = "";
      for(let stat in debuff){
        if (debuff[stat] > 0){
          if(debuff === "bleed" || debuff === "burn" || debuff === "poison"){
            statsLine += `inflicts ${debuff[stat]} points of ${debuff}, `
          } else {
            statsLine += `decreases ${stat} by ${debuff[stat]}, `
          }   
        }
      }
      statsLine = statsLine[0].toUpperCase + statsLine.substring(1);
      statsLine = statsLine.substring(0, statsLine.length - 3);
      statsLine += "."

      debuffs.push(<img className="debuff-icon" src={this.props.props.abilities[debuff.name]} title={statsLine} alt="debuff"/>)
    }
    if(debuffs.length){
      components.push(
        <div className={"buff-box debuff " + position}>
          <Debuffs/>
        </div>
      );
    }
    return components;
  }
}

export default Combat;