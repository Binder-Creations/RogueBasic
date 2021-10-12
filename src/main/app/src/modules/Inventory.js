import React from "react";
import ItemTooltip from "./ItemTooltip";

class Inventory extends React.Component {
  
  constructor(props){
    super(props);
    this.itemList = [];
    this.frame = "";
    this.background = "";
    this.empty = () => {
      return(
        <></>
      );
    }
    this.countOne = () => {
      return(
        <p>
          1
        </p>
      );
    }
    if(this.props.type == "inventory" || this.props.type == "shop-player"){
      this.inventory = this.props.props.pc.inventory;
      this.inventoryCache = this.props.props.pc.inventoryCache;
    } else if (this.props.type == "shop-store") {
      this.inventory = this.props.shop.inventory;
      this.inventoryCache = this.props.shop.inventoryCache;
    }
    this.icon = null;
    this.iconValue = null;
    this.iconClass = null;
    this.iconValueColor = null;
    this.type = null;
    this.push = this.push.bind(this);
    this.pushUsable = this.pushUsable.bind(this);
    this.pushUnusable = this.pushUnusable.bind(this);
    this.pushTableUsable = this.pushTableUsable.bind(this);
    this.pushTableUnusable = this.pushTableUnusable.bind(this);
    this.setIconType = this.setIconType.bind(this);

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
      this.inventoryCache.forEach(item => {
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
    let count = () => {
      return(
        <p className="item-count">
          {this.inventory[item.id]}
        </p>
      );
    }
    let countShop = () => {
      return(
        <p>
          {this.inventory[item.id]}
        </p>
      );
    }

    if(this.props.type == "shop-store"){
      this.setIconType(item);
      if(this.props.props.pc.currency >= item.cost*5){
        if(this.inventory[item.id] > 1){
          this.pushTableUsable(item, countShop);
        } else {
          this.pushTableUsable(item, this.countOne);
        }
      } else {
        if(this.inventory[item.id] > 1){
          this.pushTableUnusable(item, countShop);
        } else {
          this.pushTableUnusable(item, this.countOne);
        }
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
    this.itemList.push(
      <div className="inventory-box-item item-tooltip" onClick={()=>{this.props.props.appState(this.props.type, item, 0)}}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image"/>
        <img src={this.frame} className="absolute-fill"/>
        <Count/>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7} costMult={1}/>
      </div>
    )
  }

  pushUnusable(item, Count){
    this.itemList.push(
      <div className="inventory-box-item item-tooltip">
        <img src={this.background} className="absolute-fill gray-75"/>
        <img src={this.props.props.items[item.type]["i"+item.image]} className="item-image gray-75"/>
        <img src={this.frame} className="absolute-fill gray-75"/>
        <Count/>
        <ItemTooltip props={this.props.props} item={item} update={this.props.update} leftMod={0.7} costMult={1}/>
      </div>
    )
  }
  
  pushTableUsable(item, Count){
    this.itemList.push(
        <tr className="shop-box-item item-tooltip" onClick={()=>{this.props.props.appState(this.props.type, item, 0)}} style={{backgroundImage: `url(${this.props.props.images.tableBackground})`}}>
          <td>
            <div className="shop-item-container">
              <img src={this.background} className="shop-fill-absolute"/>
              <img src={this.props.props.items[item.type]["i"+item.image]} className="shop-image"/>
              <img src={this.frame} className="shop-fill-absolute"/>
            </div>
          </td>
          <td>
            <img src={this.icon} className={this.iconClass}/>
            <p style={this.iconValueColor} className="inline-block">{this.iconValue}</p>
          </td>
          <td>
            <img src={this.props.props.items.currency.i1} className="shop-coin"/>
            <p className="inline-block">{item.cost*5} </p>
          </td>
          <td>
            <Count/>
          </td>
        </tr>
    )
  }
  pushTableUnusable(item, Count){
    this.itemList.push(
        <tr className="shop-box-item item-tooltip" style={{backgroundImage: `url(${this.props.props.images.tableBackground})`}}>
          <td>
            <div className="shop-item-container">
              <img src={this.background} className="shop-fill-absolute gray-75"/>
              <img src={this.props.props.items[item.type]["i"+item.image]} className="shop-image gray-75"/>
              <img src={this.frame} className="shop-fill-absolute gray-75"/>
            </div>
          </td>
          <td>
            <img src={this.icon} className={this.iconClass}/>
            <p style={this.iconValueColor} className="inline-block gray-75">{this.iconValue}</p>
          </td>
          <td>
            <img src={this.props.props.items.currency.i1} className="shop-coin gray-75"/>
            <p className="inline-block gray-75">{item.cost*5} </p>
          </td>
          <td>
            <Count/>
          </td>
        </tr>
    )
  }
  
  setIconType(item){
    switch(item.type){
      case "potion":
        this.type = "Potion";
        this.iconValue = item.actionValue;
        actionType: switch(item.actionType){
          case "heal":
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "shop-icon-heal";
            this.iconValueColor = this.props.props.colorHeal
            break actionType;
          default:
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "shop-icon-heal";
            this.iconValueColor = this.props.props.colorHeal;
            break actionType;
        }
        break;
      case "consumable":
        this.type = "Consumable";
        this.iconValue = item.actionValue;
        actionType: switch(item.actionType){
          case "heal":
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "shop-icon-heal";
            this.iconValueColor = this.props.props.colorHeal
            break actionType;
          default:
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "shop-icon-heal";
            this.iconValueColor = this.props.props.colorHeal
            break actionType;
        }
        break;
      case "headLight":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Hat";
        break;
      case "headMedium":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Light Helmet";
        break;
      case "headHeavy":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Heavy Helmet";
        break;
      case "bodyLight":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Clothing";
        break;
      case "bodyMedium":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Light Armor";
        break;
      case "bodyHeavy":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Heavy Armor";
        break;
      case "back":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Cloak";
        break;  
      case "neck":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Amulet";
        break;
      case "staff":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Staff";
        break;
      case "spellbook":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Spellbook";
        break;
      case "bow":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Bow";
        break;
      case "dagger":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Dagger";
        break;
      case "sword":
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "shop-icon-power";
        this.iconValue = item.powerBonus;
        this.iconValueColor = this.props.props.colorPower;
        this.type = "Sword";
        break;
      case "shield":
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "shop-icon-armor";
        this.iconValue = item.armorBonus;
        this.iconValueColor = this.props.props.colorArmor;
        this.type = "Shield";
        break;
      }
    }
}

export default Inventory