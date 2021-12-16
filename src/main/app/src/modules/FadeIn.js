import React from "react";
import {CSSTransition} from "react-transition-group";

class FadeIn extends React.Component {
  constructor(props){
    super(props);
  }

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
export default FadeIn