import React from "react";
import ItemTooltip from "./ItemTooltip";

class Inventory extends React.Component {
  
  constructor(props){
    super(props);
    this.itemList = [];
    this.frame = "";
    this.background = "";
    this.push = this.push.bind(this);
    this.pushUsableCount = this.pushUsableCount.bind(this);
    this.pushUnusableCount = this.pushUnusableCount.bind(this);
    this.pushUsable = this.pushUsable.bind(this);
    this.pushUnusable = this.pushUnusable.bind(this);
  }

  render(){
    this.itemList = [];
    let sort = [];

    if(this.props.props.pc.characterClass == "Rogue"){
      sort = ["potion", "consumable", "bow", "dagger", "headMedium", "bodyMedium", "neck", "back", "staff", "spellbook", "sword", "shield", "headLight", "bodyLight", "headHeavy", "bodyHeavy"];
    } else if (this.props.props.pc.characterClass == "Wizard"){
      sort = ["potion", "consumable", "staff", "spellbook", "headLight", "bodyLight", "neck", "back", "bow", "dagger", "sword", "shield", "headMedium", "bodyMedium", "headHeavy", "bodyHeavy"];
    } else {
      sort = ["potion", "consumable", "sword", "shield", "headHeavy", "bodyHeavy", "neck", "back", "staff", "spellbook", "bow", "dagger", "headLight", "bodyLight", "headMedium", "bodyMedium"];
    }

    sort.forEach(type => {
      this.props.props.pc.inventoryCache.forEach(item => {
        if(item.type == type){
          this.background = this.props.props.images["background"+item.rarity]
          this.frame = this.props.props.images["frame"+item.rarity]
          this.push(item);
        }
      })
    })

    let fillSize = Math.ceil(this.itemList.length/5);
    fillSize = fillSize < 5 
      ? 5
      : fillSize;
    while (this.itemList.length < fillSize*5){
      this.itemList.push(<div className="inventory-box-item"><img src={this.props.props.images.frameEmpty} className="absolute-fill"/></div>)
    }

    return this.itemList;
  }

  push(item){
    if(item.type == "potion"){
      if(this.props.props.pc.inventory[item.id] > 1){
        this.pushUsableCount(item);
      } else {
        this.pushUsable(item);
      }
    }
    if(item.type == "consumable"){
      if(this.props.props.combat){
        if(this.props.props.pc.inventory[item.id] > 1){
          this.pushUnusableCount(item);
        } else {
          this.pushUnusable(item);
        }
      } else {
        if(this.props.props.pc.inventory[item.id] > 1){
          this.pushUsableCount(item);
        } else {
          this.pushUsable(item);
        }
      }
    }
    if(item.type == "back" || item.type == "neck"){
      if(this.props.props.combat){
        this.pushUnusable(item);
      } else {
        this.pushUsable(item);
      }
    }
    if(item.type == "staff" || item.type == "spellbook" || item.type == "headLight" || item.type == "bodyLight"){
      if(this.props.props.pc.characterClass == "Wizard" && !this.props.props.combat){
        this.pushUsable(item);
      } else {
        this.pushUnusable(item);
      }
    }
    if(item.type == "bow" || item.type == "dagger" || item.type == "headMedium" || item.type == "bodyMedium"){
      if(this.props.props.pc.characterClass == "Rogue" && !this.props.props.combat){
        this.pushUsable(item);
      } else {
        this.pushUnusable(item);
      }
    }
    if(item.type == "sword" || item.type == "shield" || item.type == "headHeavy" || item.type == "bodyHeavy"){
      if(this.props.props.pc.characterClass == "Warrior" && !this.props.props.combat){
        this.pushUsable(item);
      } else {
        this.pushUnusable(item);
      }
    }
  }

  pushUsableCount(item){
    this.itemList.push(
      <div className="inventory-box-item item-tooltip" onClick={()=>{this.props.props.appState("inventory", item, 0)}}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image"/>
        <img src={this.frame} className="absolute-fill"/>
        <p className="item-count">
          {this.props.props.pc.inventory[item.id]}
        </p>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7}/>
      </div>
      )
  }

  pushUnusableCount(item){
    this.itemList.push(
      <div className="inventory-box-item item-tooltip">
        <img src={this.background} className="absolute-fill gray-75"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image gray-75"/>
        <img src={this.frame} className="absolute-fill gray-75"/>
        <p className="item-count">
          {this.props.props.pc.inventory[item.id]}
        </p>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7}/>
      </div>
      )
  }

  pushUsable(item){
    this.itemList.push(
      <div className="inventory-box-item item-tooltip" onClick={()=>{this.props.props.appState("inventory", item, 0)}}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image"/>
        <img src={this.frame} className="absolute-fill"/>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7}/>
      </div>
    )
  }

  pushUnusable(item){
    this.itemList.push(
      <div className="inventory-box-item item-tooltip">
        <img src={this.background} className="absolute-fill gray-75"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image gray-75"/>
        <img src={this.frame} className="absolute-fill gray-75"/>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7}/>
      </div>
    )
  }
}

export default Inventory