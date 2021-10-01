import React from "react";
import backgroundGrey from "../images/background-grey.png";
import backgroundGreen from "../images/background-green.png";
import backgroundBlue from "../images/background-blue.png";
import backgroundPurple from "../images/background-purple.png";
import frame from "../images/equipped-frame.png";
import items from "../images/items";
import ItemTooltip from "./ItemTooltip";

class Equipped extends React.Component {
  
  constructor(props){
    super(props);
    this.items = items;
    this.equippedList = [];
    this.background = "";

    this.setBackground = this.setBackground.bind(this);
    this.push = this.push.bind(this);
    this.pushUsable = this.pushUsable.bind(this);
    this.pushUnusable = this.pushUnusable.bind(this);
  }

  render(){
    this.equippedList = [];
    if(this.props.pc.equippedHead){
      this.setBackground(this.props.pc.equippedHead.rarity)
      this.push(this.props.pc.equippedHead, "head");
    }
    if(this.props.pc.equippedBody){
      this.setBackground(this.props.pc.equippedBody.rarity)
      this.push(this.props.pc.equippedBody, "body");
    }
    if(this.props.pc.equippedBack){
      this.setBackground(this.props.pc.equippedBack.rarity)
      this.push(this.props.pc.equippedBack, "back");
    }
    if(this.props.pc.equippedNeck){
      this.setBackground(this.props.pc.equippedNeck.rarity)
      this.push(this.props.pc.equippedNeck, "neck");
    }
    if(this.props.pc.equippedPrimary){
      this.setBackground(this.props.pc.equippedPrimary.rarity)
      this.push(this.props.pc.equippedPrimary, "primary");
    }
    if(this.props.pc.equippedSecondary){
      this.setBackground(this.props.pc.equippedSecondary.rarity)
      this.push(this.props.pc.equippedSecondary, "secondary");
    }
    return this.equippedList;
  }

  setBackground(rarity){
    switch(rarity){
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

  push(item, slot){
    this.props.combat
      ? this.pushUnusable(item, slot)
      : this.pushUsable(item, slot);
  }

  pushUsable(item, slot){
    this.equippedList.push(
      <div className={"item-tooltip item-equipped equipped-" + slot}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.items[item.type]["i"+item.image]} className="item-image"/>
        <img src={frame} className="absolute-fill"/>
        <ItemTooltip item={item} widthChange={this.props.widthChange} leftMod={0.2}/>
      </div>
    )
  }

  pushUnusable(item, slot){
    this.equippedList.push(
      <div className={"item-tooltip item-equipped equipped-" + slot}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.items[item.type]["i"+item.image]} className="item-image"/>
        <img src={frame} className="absolute-fill"/>
        <ItemTooltip item={item} widthChange={this.props.widthChange} leftMod={0.2}/>
      </div>
    )
  }
}

export default Equipped