import React from "react";
import c from "../../data/CommonProperties";
import {camelCase} from "../services/GeneralUtilities";
import {TransitionGroup} from 'react-transition-group';
import Fade from "../animations/Fade";
import AppServices from "../services/AppServices";

export default class Combat extends React.Component {
  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();

    this.state = {
      showUpdate: true
    }
    this.previousUpdates = null;
  }

  render(){
    if(this.previousUpdates && this.previousUpdates !== this.props.s.combatUpdates){
      this.setState({showUpdate: true})
    }

    let components = [];
    this.previousUpdates = this.props.s.combatUpdates;
    if(!this.props.s.gameOver){
      let key = 0;
      for(let position of c.positions){
        let updates = [];

        for(let combatUpdate of this.props.s.combatUpdates){
          if(combatUpdate.target === position){
            updates.push(
              <tr className="nowrap">
                <td>
                  <div className="right-align combat-div">
                    <img className="combat-ability-image" src={c.images.abilities[camelCase(combatUpdate.abilityName)]} alt="ability"/>
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
          let onExited = null;
          if(!key){
            onExited = () => setTimeout(() => this.appServices.nextCombat(), 100);
          }
          
          components.push(  
            this.state.showUpdate && 
            this.props.s.combatTransitions && (
              <Fade 
                key={key++} 
                in 
                onEntered={() => setTimeout(() =>this.setState({showUpdate:false}), 500)}
                onExited={onExited}
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