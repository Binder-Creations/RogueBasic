import React from "react";
import ItemTooltip from "./ItemTooltip";

class Inventory extends React.Component {
  
  constructor(props){
    super(props);
    this.itemList = [];
    this.empty = () => {
      return(
        <></>
      );
    }
    if(this.props.type == "inventory" || this.props.type == "shop-player"){
      this.inventory = this.props.props.pc.inventory;
      this.inventoryCache = this.props.props.pc.inventoryCache;
    } else if (this.props.type == "shop-store") {
      this.inventory = null;
      this.inventoryCache = this.props.shop.inventoryCache;
    }
    this.push = this.push.bind(this);
    this.pushUsable = this.pushUsable.bind(this);
    this.pushUnusable = this.pushUnusable.bind(this);
    this.pushTableUsable = this.pushTableUsable.bind(this);
    this.pushTableUnusable = this.pushTableUnusable.bind(this);

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
      let tempArray = []
      this.inventoryCache.forEach(item => {
        if(item.type == type){
          tempArray.push(item)
        }
      });
      while(tempArray.length > 0){
        let highestCost = 0;
        let toRemove = []
        tempArray.forEach(item => {
          if(item.cost > highestCost){
            highestCost = item.cost;
          }
        });
        tempArray.forEach(item => {
          if(item.cost == highestCost){
            this.push(item);
            toRemove.push(item);
          }
        });
        toRemove.forEach(item => {
          tempArray.splice(tempArray.indexOf(item), 1)
        });
      }
    });

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
    let count = () => {
      return(
        <p className="item-count">
          {this.inventory[item.id]}
        </p>
      );
    }

    if(this.props.type == "shop-store"){
      if(this.props.props.pc.currency >= item.cost*5){
        this.pushTableUsable(item);
      } else {
        this.pushTableUnusable(item);
      }
    }
    
    if(this.props.type == "shop-player"){
      if(this.inventory[item.id] > 1){
        this.pushUsable(item, count);
      } else {
        this.pushUsable(item, this.empty);
      }
    }

    if (this.props.type == "inventory"){
      if(item.type == "potion"){
        if(this.inventory[item.id] > 1){
          this.pushUsable(item, count);
        } else {
          this.pushUsable(item, this.empty);
        }
      }
      if(item.type == "consumable"){
        if(this.props.props.combat){
          if(this.inventory[item.id] > 1){
            this.pushUnusable(item, count);
          } else {
            this.pushUnusable(item, this.empty);
          }
        } else {
          if(this.inventory[item.id] > 1){
            this.pushUsable(item, count);
          } else {
            this.pushUsable(item, this.empty);
          }
        }
      }
      if(item.type == "back" || item.type == "neck"){
        if(this.props.props.combat){
          this.pushUnusable(item, this.empty);
        } else {
          this.pushUsable(item, this.empty);
        }
      }
      if(item.type == "staff" || item.type == "spellbook" || item.type == "headLight" || item.type == "bodyLight"){
        if(this.props.props.pc.characterClass == "Wizard" && !this.props.props.combat){
          this.pushUsable(item, this.empty);
        } else {
          this.pushUnusable(item, this.empty);
        }
      }
      if(item.type == "bow" || item.type == "dagger" || item.type == "headMedium" || item.type == "bodyMedium"){
        if(this.props.props.pc.characterClass == "Rogue" && !this.props.props.combat){
          this.pushUsable(item, this.empty);
        } else {
          this.pushUnusable(item, this.empty);
        }
      }
      if(item.type == "sword" || item.type == "shield" || item.type == "headHeavy" || item.type == "bodyHeavy"){
        if(this.props.props.pc.characterClass == "Warrior" && !this.props.props.combat){
          this.pushUsable(item, this.empty);
        } else {
          this.pushUnusable(item, this.empty);
        }
      }
    }
  }

  pushUsable(item, Count){
    let itemProps = this.props.props.itemServices.getProps(item);
    this.itemList.push(
      <div className="inventory-box-item item-tooltip" onClick={()=>{this.props.props.appState(this.props.type, item)}}>
        <div className="absolute-fill hover-saturate" style={{pointerEvents: "auto"}}>
          <img src={itemProps.background} className="absolute-fill"/>
          <img src={itemProps.image} className="item-image"/>
          <img src={itemProps.frame} className="absolute-fill"/>
        </div>
        <Count/>
        <ItemTooltip props={this.props.props} itemProps={itemProps} item={item} update={this.props.update} leftMod={0.7} costMult={1}/>
      </div>
    )
  }

  pushUnusable(item, Count){
    let itemProps = this.props.props.itemServices.getProps(item);
    this.itemList.push(
      <div className="inventory-box-item item-tooltip">
        <img src={itemProps.background} className="absolute-fill gray-75"/>
        <img src={itemProps.image} className="item-image gray-75"/>
        <img src={itemProps.frame} className="absolute-fill gray-75"/>
        <Count/>
        <ItemTooltip props={this.props.props} itemProps={itemProps} item={item} update={this.props.update} leftMod={0.7} costMult={1}/>
      </div>
    )
  }
  pushTableUsable(item){
    let itemProps = this.props.props.itemServices.getProps(item);
    this.itemList.push(
        <tr className="hover-saturate shop-box-item" onClick={()=>{this.props.props.appState(this.props.type, item)}} style={{backgroundImage: `url(${this.props.props.images.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image"/>
            <ItemTooltip props={this.props.props} itemProps={itemProps} item={item} update={this.props.update} leftMod={0.7} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass}/>
            <p style={itemProps.iconValueColor} className="inline-block">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={this.props.props.items.currency.i1} className="shop-coin"/>
            <p className="inline-block">{item.cost*5} </p>
          </td>
        </tr>
    )
  }
  pushTableUnusable(item){
    let itemProps = this.props.props.itemServices.getProps(item);
    this.itemList.push(
        <tr className="shop-box-item" style={{backgroundImage: `url(${this.props.props.images.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image gray-75"/>
            <ItemTooltip props={this.props.props} itemProps={itemProps} item={item} update={this.props.update} leftMod={0.7} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass}/>
            <p style={itemProps.iconValueColor} className="inline-block gray-75">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={this.props.props.items.currency.i1} className="shop-coin gray-75"/>
            <p className="inline-block gray-75">{item.cost*5} </p>
          </td>
        </tr>
    )
  }
}

export default Inventory