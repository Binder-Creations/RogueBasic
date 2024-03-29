import React from "react";
import c from "../../data/CommonProperties";
import AppServices from "../services/AppServices";

export default class GameOver extends React.Component {
  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();
  }

  render(){
    let update = this.props.s.gameOver;
      return(
        <div className="death-menu">
          <img className="background" src={c.images.common.deathMenu} alt="Quest Screen"/>
          <div className="death-cause">
            <p style={update.abilityNameSize}>{update.abilityName}</p>
            <p>{update.value + (update.crit ? " CRIT " : "")  + " damage" }</p>
            <p style={update.sourceSize}>{update.source}</p>
          </div>
          <p className="death-souls">{this.props.s.pc.metacurrency}</p>
          <div className="btn-death hover-saturate">
            <input className="background" type="image" src={c.images.common.buttonDeath} alt="Accept Fate" onClick={() => this.appServices.endGame()}/>
            <p className="v-h-centered nopointer">
              Accept Fate
            </p>
          </div>
        </div>
      )  
    }
}