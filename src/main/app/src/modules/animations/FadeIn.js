import React from "react";
import {CSSTransition} from "react-transition-group";

export default class FadeIn extends React.Component {
  render(){
      return(
        <CSSTransition
          appear={true} 
          classNames="fade-in"
          unmountOnExit 
          timeout={{appear: 200, enter: 200}}
          {...this.props}
        />
    );
  }
}