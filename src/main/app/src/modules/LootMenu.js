import React from "react";
import Inventory from "./Inventory";
import Fade from "./Fade";

class LootMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
      return(
        <Fade in key='l' className="loot-menu">
          <img className="background" src={this.props.props.images.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={this.props.props.images.buttonClose} alt="Close" onClick={() => this.props.props.appState("menu", "loot")}/>
          <div className="loot-box">
            <Inventory props={this.props.props} type="loot" room={this.props.room}/>
          </div>
        </Fade>
      )  
    }
}

export default LootMenu;