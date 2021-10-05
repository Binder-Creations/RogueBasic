import React from "react";
import backgroundGrey from "../images/background-grey.png";
import backgroundGreen from "../images/background-green.png";
import backgroundBlue from "../images/background-blue.png";
import backgroundPurple from "../images/background-purple.png";
import frame from "../images/equipped-frame.png";
import * as items from "../images/items";
import ItemTooltip from "./ItemTooltip";

class Equipped extends React.Component {
  
  constructor(props){
    super(props);
    this.items = items;
    this.background = "";
    this.slot = "";

    this.setBackground = this.setBackground.bind(this);
    this.unequip = this.unequip.bind(this);
  }

  render(){
    if (!this.props.item){
      return <></>
    }
    this.setBackground();
    return(
      <div className={"item-tooltip item-equipped equipped-" + this.props.slot} onClick={() => {this.unequip()}}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.items[this.props.item.type]["i"+this.props.item.image]} className="item-image"/>
        <img src={frame} className="absolute-fill"/>
        <ItemTooltip item={this.props.item} widthChange={this.props.widthChange} leftMod={0.2}/>
      </div>
    );
  }

  setBackground(){
    switch(this.props.item.rarity){
      case "Uncommon":
        this.background = backgroundGreen;
        break;
      case "Rare":
        this.background = backgroundBlue;
        break;
      case "Epic":
        this.background = backgroundPurple;
        break;
      default:
        this.background = backgroundGrey;
        break;
    }
  }

  unequip(){
    if (!this.props.combat)
      this.props.appState("unequip", this.props.item, 0)   
  }
}

export default Equipped