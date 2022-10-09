import React from "react";
import c from "../../data/CommonProperties";
import Binder from "../services/Binder";
import ItemTooltip from "./ItemTooltip";

class Inventory extends React.Component {
  
  constructor(props){
    super(props);
    Binder.bind(this);
    if(this.props.type && this[this.props.type + "Props"]){
      this[this.props.type + "Props"]();
      this.sort(this.items);
    }
    
  }

  render(){
    if(!this.itemsMax){
      return <></>
    }
    let components = [];
    if(this.props.type && this[this.props.type + "Parse"]){
      for(let item of this.items){
        components.push(this[this.props.type + "Parse"](item));
      }
    }
    this.fillToMax(components);
    return components;
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
    return c.itemSortOrder.indexOf(item.type) + (0.9999 - item.cost/10000);
  } 

  sort(items){
    items.sort((a,b) => this.getSortValue(a) - this.getSortValue(b));
  }

  getCount(item){
    return(
      <div className="item-count">
        <div className="v-h-centered">
          {item.count}
        </div>
      </div>
    );
  }

  inventoryParse(item){
    if(item.getUsability(this.props.s.pc) === 0){
      return this.parseUnusable(item);
    }
    if(item.getUsability(this.props.s.pc) === 1){
      if(this.props.s.combat){
        return this.parseUnusable(item); 
      } else {
        return this.parseUsable(item);
      }
    }
    if(item.getUsability(this.props.s.pc) === 2){
      if(this.props.s.position){
        return this.parseUnusable(item);
      } else {
        return this.parseUsable(item);
      }
    }
  }

  shopPlayerParse(item){
    return this.parseUsable(item);
  }

  shopStoreParse(item){
    if(this.props.s.pc.currency >= item.cost*5){
      return this.parseTableUsable(item);
    } else {
      return this.parseTableUnusable(item);
    }
  }

  lootParse(item){
    return this.parseUsable(item);
  }

  parseUsable(item){
    let itemProps = c.itemServices.getProps(item);
    return(
      <div className={this.itemBoxClass+" item-tooltip"} onClick={()=>{c[this.props.type](item)}}>
        <div className="absolute-fill hover-saturate" style={{pointerEvents: "auto"}}>
          <img src={itemProps.background} className="absolute-fill" alt=""/>
          <img src={itemProps.image} className="item-image" alt="item"/>
          <img src={itemProps.frame} className="absolute-fill" alt="frame"/>
        </div>
        {item.count > 1 ? this.getCount(item) : <></>}
        <ItemTooltip s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={1}/>
      </div>
    )
  }

  parseUnusable(item){
    let itemProps = c.itemServices.getProps(item);
    return(
      <div className={this.itemBoxClass+" item-tooltip"}>
        <img src={itemProps.background} className="absolute-fill gray-100" alt=""/>
        <img src={itemProps.image} className="item-image gray-100" alt="item"/>
        <img src={itemProps.frame} className="absolute-fill gray-100" alt="frame"/>
        {item.count > 1 ? this.getCount(item) : <></>}
        <ItemTooltip s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={1}/>
      </div>
    )
  }
  parseTableUsable(item){
    let itemProps = c.itemServices.getProps(item);
    return(
        <tr className="hover-saturate shop-box-item" onClick={()=>{c[this.props.type](item)}} style={{backgroundImage: `url(${c.images.common.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image" alt="item"/>
            <ItemTooltip s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass} alt="icon"/>
            <p style={itemProps.iconValueColor} className="inline-block">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={c.images.items.currency.i1} className="shop-coin" alt="coin"/>
            <p className="inline-block">{item.cost*5} </p>
          </td>
        </tr>
    )
  }
  parseTableUnusable(item){
    let itemProps = c.itemServices.getProps(item);
    return(
        <tr className="shop-box-item" style={{backgroundImage: `url(${c.images.common.tableBackground})`, height: window.innerWidth*0.0631 + "px"}}>
          <td className="item-tooltip relative">
            <img src={itemProps.image} className="shop-image gray-75" alt="item"/>
            <ItemTooltip s={this.props.s} itemProps={itemProps} item={item} update={this.props.update} qMods={this.qMods} costMult={5}/>
          </td>
          <td colSpan="2">
            <p style={itemProps.shopNameStyle}>{item.name}</p>
          </td>
          <td>
            <img src={itemProps.icon} className={"shop-"+itemProps.iconClass} alt="icon"/>
            <p style={itemProps.iconValueColor} className="inline-block gray-75">{itemProps.iconValue}</p>
          </td>
          <td>
            <img src={c.images.items.currency.i1} className="shop-coin gray-75" alt="coin"/>
            <p className="inline-block gray-75">{item.cost*5} </p>
          </td>
        </tr>
    )
  }

  fillToMax(components){
    while (components.length < this.itemsMax){
      components.push(<div className={this.itemBoxClass}><img src={c.images.common.frameEmpty} className="absolute-fill" alt="frame"/></div>)
    }
  }
}

export default Inventory