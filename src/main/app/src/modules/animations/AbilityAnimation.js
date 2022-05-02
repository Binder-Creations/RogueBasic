import React from "react";
import Pulse from "./Pulse";
import {camelCase} from "../services/GeneralUtilities";

class AbilityAnimation extends React.Component {
 constructor(props){
   super(props);
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
        onExited={() => setTimeout(() => this.props.c.pcCombat(this.ability), 150)}
      >
        <img src={this.props.c.images.abilities[camelCase(this.props.s.pc.abilities[this.ability].name)]} alt="ability"/>
      </Pulse>
    );
  }

}

export default AbilityAnimation;