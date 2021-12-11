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
    console.log(this.props.props.combatUpdates)
    let gameOver = this.props.props.combatUpdates.find(combatUpdate => combatUpdate.type === "gameOver");
    if(gameOver){

    } else {
      for(let position of this.positions){
        let updates = [];
        let Updates = () => {
          return updates.length > 0 ? updates : null;
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
        if(updates.length > 0){
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
}

export default Combat;