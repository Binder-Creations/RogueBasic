import React from "react";
import frameEmpty from "../images/frame-empty.png";
import frameGrey from "../images/frame-grey.png";
import frameGreen from "../images/frame-green.png";
import frameBlue from "../images/frame-blue.png";
import framePurple from "../images/frame-purple.png";
import backgroundGrey from "../images/background-grey.png";
import backgroundGreen from "../images/background-green.png";
import backgroundBlue from "../images/background-blue.png";
import backgroundPurple from "../images/background-purple.png";

class Inventory extends React.Component {
  render(){
    let itemList = [];

    this.props.pc.inventoryCache.forEach(item => {
      switch(item.rarity){
        case "uncommon":
          background = backgroundGreen;
          frame = frameGreen;
          break;
        case "rare":
          background = backgroundBlue;
          frame = frameBlue;
          break;
        case "epic":
          background = backgroundPurple;
          frame = framePurple;
          break;
        default:
          background = backgroundGrey;
          frame = frameGrey;
          break;
      }
      switch(item.type){
        case "potion":
          itemList.push(

          )
          break;
        case "consumable":
          break;
        case "headLight":
          break;
        case "headMedium":
          break;
        case "headHeavy":
          break;
        case "bodyLight":
          break;
        case "bodyMedium":
          break;
        case "bodyHeavy":
          break;
        case "back":
          break;
        case "neck":
          break;
        case "staff":
          break;
        case "spellbook":
          break;
        case "bow":
          break;
        case "dagger":
          break;
        case "sword":
          break;
        case "shield":
          break;
      }
    });

    //should also populate until a multiple of 5 when greater than 25
    while (itemList.length < 25){
      itemList.push(<div><img src={frameEmpty}/></div>)
    }
    return itemList;
  }
}

export default Inventory