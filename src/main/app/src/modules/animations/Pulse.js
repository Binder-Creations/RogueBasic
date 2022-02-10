import React from "react";
import {CSSTransition} from "react-transition-group";

class Pulse extends React.Component {
  render(){
    document.documentElement.style.setProperty('--animate-duration', '.4s');
      return(
        <CSSTransition
          appear 
          classNames={{
            appear: 'animate__animated',
            appearActive: 'animate__pulse',
            enter: 'animate__animated',
            enterActive: 'animate__pulse',
            exit: 'animate__animated',
            exitActive: 'animate__fadeOut',
           }}
          unmountOnExit 
          timeout={{appear: 350, enter: 350, exit: 250}}
          {...this.props}
        />
    );
  }
}
export default Pulse