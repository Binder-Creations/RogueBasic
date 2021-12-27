import React from "react";
import Inventory from "./Inventory";

class LootMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
      return(
        <div className="loot-menu">
          <img className="background" src={this.props.props.images.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={this.props.props.images.buttonClose} alt="Close" onClick={() => this.props.props.appState("menu", "loot")}/>
          <div className="loot-box">
            <Inventory props={this.props.props} type="loot" room={this.props.room}/>
          </div>
          <div className="btn-loot-all hover-saturate">
            <input className="background" type="image" src={this.props.props.images.buttonGeneric} alt="Loot All" onClick={() => this.props.props.appState("loot-all")}/>
            <p className="v-h-centered nopointer">
              Take All
            </p>
          </div>
        </div>
      )  
    }
}

export default LootMenu;