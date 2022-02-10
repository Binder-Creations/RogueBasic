import React from "react";
import Binder from "../services/Binder";
import ItemTooltip from "./ItemTooltip";

class Inventory extends React.Component {
  
  constructor(props){
    super(props);
    if(this.props.type && this[this.props.type]){
      this[this.props.type + "Props"]();
    }
    Binder.bind(this);
  }

  render(){
    if(!this.itemsMax){
      return <></>
    }

    this.components = [];
    for(let item of items){
      this.push(item);
    }
    this.fillToMax();
    return this.components;
  }

  inventoryProps(){
    this.items = this.props.s.pc.inventory;
    this.itemBoxClass = "inventory-box-item";
    this.qMods = [0.45, 0.6, 0.7];
    this.itemsMax = 25;
  }

  shopPlayerProps(){
    this.items = this.props.s.pc.inventory;
    this.itemBoxClass = "inventory-box-item";
    this.qMods = [0.45, 0.6, 0.7];
    this.itemsMax = 25;
  }

  shopStoreProps(){
    this.items = this.props.s.shop.inventory;
    this.itemBoxClass = "inventory-box-item";
    this.qMods = [0.45, 0.6, 0.7];
    this.itemsMax = 25;
  }

  lootProps(){
    this.items = this.props.room.loot;
    this.itemBoxClass = "loot-box-item";
    this.qMods = [0.3, 0.4, 0.55];
    this.itemsMax = 20;
  }

  getSortValue(item){
    return this.props.c.itemSortOrder.indexOf(item.type) + (0.9999 - item.cost/10000);
  } 

  sort(items){
    items.sort((a,b) => this.getSortValue(a) - this.getSortValue(b));
  }

  push(item){
    let count = 
      <p className="item-count">
        {this.items[item.id]}
      </p>

    if(this.props.type === "shopStore"){
      if(this.props.s.pc.currency >= item.cost*5){
        this.pushTableUsable(item);
      } else {
        this.pushTableUnusable(item);
      }
    }
    
    if(this.props.type === "shopPlayer" || this.props.type === "loot"){
      if(this.items[item.id] > 1){
        this.pushUsable(item, count);
      } else {
        this.pushUsable(item);
      }
    }

    if (this.props.type === "inventory"){
      if(item.type === "potion"){
        if(this.props.s.position){
          if(this.items[item.id] > 1){
            this.pushUnusable(item, count);
          } else {
            this.pushUnusable(item);
          }
        } else {
          if(this.items[item.id] > 1){
            this.pushUsable(item, count);
          } else {
            this.pushUsable(item);
          }
        }
      }
      if(item.type === "consumable"){
        if(this.props.s.combat){
          if(this.items[item.id] > 1){
            this.pushUnusable(item, count);
          } else {
            this.pushUnusable(item);
          }
        } else {
          if(this.items[item.id] > 1){
            this.pushUsable(item, count);
          } else {
            this.pushUsable(item);
          }
        }
      }
      if(item.type === "back" || item.type === "neck"){
        if(this.props.s.combat){
          this.pushUnusable(item);
        } else {
          this.pushUsable(item);
        }
      }
      if(item.type === "staff" || item.type === "spellbook" || item.type === "headLight" || item.type === "bodyLight"){
        if(this.props.s.pc.characterClass === "Wizard" && !this.props.s.combat){
          this.pushUsable(item);
        } else {
          this.pushUnusable(item);
        }
      }
      if(item.type === "bow" || item.type === "dagger" || item.type === "headMedium" || item.type === "bodyMedium"){
        if(this.props.s.pc.characterClass === "Rogue" && !this.props.s.combat){
          this.pushUsable(item);
        } else {
          this.pushUnusable(item);
        }
      }
      if(item.type === "sword" || item.type === "shield" || item.type === "headHeavy" || item.type === "bodyHeavy"){
        if(this.props.s.pc.characterClass === "Warrior" && !this.props.s.combat){
          this.pushUsable(item);
        } else {
          this.pushUnusable(item);
        }
      }
    }
  }

  pushUsable(item, count){
    let itemProps = this.props.c.itemServices.getProps(item);
    this.components.push(
      <div className={this.itemBoxClass+" item-tooltip"} onClick={()=>{this.props.c[this.props.type](item)}}>
        <div className="absolute-fill hover-saturate" style={{pointerEvents: "auto"}}>
          <img src={itemProps.background} className="absolute-fill" alt=""/>
          <img src={itemProps.image} className="item-image" alt="item"/>
          <img src={itemProps.frame} className="absolute-fill" alt="frame"/>
        </div>
        {count ? count : <></>}
        <ItemTooltip c={this.props.c} s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={1}/>
      </div>
    )
  }

  pushUnusable(item, count){
    let itemProps = this.props.c.itemServices.getProps(item);
    this.components.push(
      <div className={this.itemBoxClass+" item-tooltip"}>
        <img src={itemProps.background} className="absolute-fill gray-100" alt=""/>
        <img src={itemProps.image} className="item-image gray-100" alt="item"/>
        <img src={itemProps.frame} className="absolute-fill gray-100" alt="frame"/>
        {count ? count : <></>}
        <ItemTooltip c={this.props.c} s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={1}/>
      </div>
    )
  }
  pushTableUsable(item){
    let itemProps = this.props.c.itemServices.getProps(item);
    this.components.push(
        <tr className="hover-saturate shop-box-item" onClick={()=>{this.props.c[this.props.type](item)}} style={{backgroundImage: `url(${this.props.c.images.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image" alt="item"/>
            <ItemTooltip c={this.props.c} s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass} alt="icon"/>
            <p style={itemProps.iconValueColor} className="inline-block">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={this.props.c.items.currency.i1} className="shop-coin" alt="coin"/>
            <p className="inline-block">{item.cost*5} </p>
          </td>
        </tr>
    )
  }
  pushTableUnusable(item){
    let itemProps = this.props.c.itemServices.getProps(item);
    this.components.push(
        <tr className="shop-box-item" style={{backgroundImage: `url(${this.props.c.images.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image gray-75" alt="item"/>
            <ItemTooltip c={this.props.c} s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass} alt="icon"/>
            <p style={itemProps.iconValueColor} className="inline-block gray-75">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={this.props.c.items.currency.i1} className="shop-coin gray-75" alt="coin"/>
            <p className="inline-block gray-75">{item.cost*5} </p>
          </td>
        </tr>
    )
  }

  fillToMax(){
    while (this.components.length < this.itemsMax){
      this.components.push(<div className={this.itemBoxClass}><img src={this.props.c.images.frameEmpty} className="absolute-fill" alt="frame"/></div>)
    }
  }
}

export default Inventory