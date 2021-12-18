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
                  <div className={"combat-div combat-text " + combatUpdate.type} style={combatUpdate.size}>
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
}

export default Combat;