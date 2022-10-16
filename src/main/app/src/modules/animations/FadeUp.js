import React from "react";
import {CSSTransition} from "react-transition-group";

export default class FadeUp extends React.Component {
  render(){
    document.documentElement.style.setProperty('--animate-duration', '2s')
      return(
        <CSSTransition
          appear 
          classNames={{
            appear: 'animate__animated',
            appearActive: 'animate__fadeInUp',
            enter: 'animate__animated',
            enterActive: 'animate__fadeInUp',
           }}
          unmountOnExit 
          timeout={{appear: 2000, enter: 2000}}
          {...this.props}
        />
    );
  }
}