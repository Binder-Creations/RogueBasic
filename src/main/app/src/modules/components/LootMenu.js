import React from "react";
import c from "../../data/CommonProperties";
import Inventory from "./Inventory";
import AppServices from "../services/AppServices";

export default class LootMenu extends React.Component {
  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();
  }
  render(){
      return(
        <div className="loot-menu">
          <img className="background" src={c.images.common.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={c.images.common.buttonClose} alt="Close" onClick={() => this.appServices.menu("loot")}/>
          <div className="loot-box">
            <Inventory s={this.props.s} type="loot" room={this.props.room}/>
          </div>
          <div className="btn-loot-all hover-saturate">
            <input className="background" type="image" src={c.images.common.buttonGeneric} alt="Loot All" onClick={() => this.appServices.lootAll()}/>
            <p className="v-h-centered nopointer">
              Take All
            </p>
          </div>
        </div>
      )  
    }
}