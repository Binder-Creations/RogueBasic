import React from "react";
import Inventory from "./Inventory";

class LootMenu extends React.Component {
  render(){
      return(
        <div className="loot-menu">
          <img className="background" src={this.props.c.images.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={this.props.c.images.buttonClose} alt="Close" onClick={() => this.props.c.menu("loot")}/>
          <div className="loot-box">
            <Inventory c={this.props.c} s={this.props.s} type="loot" room={this.props.room}/>
          </div>
          <div className="btn-loot-all hover-saturate">
            <input className="background" type="image" src={this.props.c.images.buttonGeneric} alt="Loot All" onClick={() => this.props.c.lootAll()}/>
            <p className="v-h-centered nopointer">
              Take All
            </p>
          </div>
        </div>
      )  
    }
}

export default LootMenu;