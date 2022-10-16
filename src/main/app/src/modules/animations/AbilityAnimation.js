import React from "react";
import Pulse from "./Pulse";
import {camelCase} from "../services/GeneralUtilities";
import c from "../../data/CommonProperties";
import AppServices from "../services/AppServices";

export default class AbilityAnimation extends React.Component {
 constructor(props){
   super(props);
   this.appServices = AppServices.getInstance();

   this.state = {
     in: true,
   }
   this.ability = this.props.s.abilityAnimation;
 }

  render(){
    return(
      <Pulse 
        in={this.state.in}
        key="n"
        className="popup nopointer"
        onEntered={() => setTimeout(() => this.setState({in: false}), 250)}
        onExited={() => setTimeout(() => this.appServices.pcCombat(this.ability), 150)}
      >
        <img src={c.images.abilities[camelCase(this.props.s.pc.abilities[this.ability].name)]} alt="ability"/>
      </Pulse>
    );
  }

}