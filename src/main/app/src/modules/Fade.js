import React from "react";
import {CSSTransition} from "react-transition-group";

class Fade extends React.Component {
  constructor(props){
    super(props);
  }

  render(){
      return(
        <CSSTransition
          appear={true} 
          classNames="fade"
          unmountOnExit 
          timeout={{appear: 200, enter: 200, exit: 1000}}
          {...this.props}
        />
    );
  }
}
export default Fade