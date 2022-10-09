import React from "react";
import c from "../../data/CommonProperties";
import Inventory from "./Inventory";

class LootMenu extends React.Component {
  render(){
      return(
        <div className="loot-menu">
          <img className="background" src={c.images.common.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={c.images.common.buttonClose} alt="Close" onClick={() => c.menu("loot")}/>
          <div className="loot-box">
            <Inventory s={this.props.s} type="loot" room={this.props.room}/>
          </div>
          <div className="btn-loot-all hover-saturate">
            <input className="background" type="image" src={c.images.common.buttonGeneric} alt="Loot All" onClick={() => c.lootAll()}/>
            <p className="v-h-centered nopointer">
              Take All
            </p>
          </div>
        </div>
      )  
    }
}

export default LootMenu;