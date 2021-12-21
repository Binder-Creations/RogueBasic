import React from "react";
import Fade from "./Fade.js";
import Pulse from "./Pulse.js";
import {camelCase} from "./GeneralUtilities";

class AbilityAnimation extends React.Component {
 constructor(props){
   super(props);
   this.state = {
     in: true,
   }
   this.ability = this.props.props.abilityAnimation;
 }

  render(){
    return(
      <div className="combat-ability-popup v-h-centered">
        {
          this.props.props.renderAbilityAnimation && (
            <Pulse 
              in={this.state.in}
              key="ability-animation"
              onEntered={() => setTimeout(() => this.setState({in: false}), 250)}
              onExited={() => setTimeout(() => {this.props.props.appState("pcCombat", this.ability)}, 150)}
            >
              <img src={this.props.props.abilities[camelCase(this.props.props.pc.abilities[this.ability].name)]} alt="ability"/>
            </Pulse>
          )
        }
      </div>
    );
  }

}

export default AbilityAnimation;