import React from "react";
import {CSSTransition} from "react-transition-group";

export default class Fade extends React.Component {
  render(){
      return(
        <CSSTransition
          appear 
          classNames="fade"
          unmountOnExit 
          timeout={{appear: 200, enter: 200, exit: 500}}
          {...this.props}
        />
    );
  }
}