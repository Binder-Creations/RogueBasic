import React from "react";
import tooltipItem from "../images/tooltip-item.png";
import iconArmor from "../images/icon-armor.png";
import iconHeal from "../images/icon-heal.png";
import iconPower from "../images/icon-power.png";
import badgeWizardSmall from "../images/badge-wizard-small.png";
import badgeRogueSmall from "../images/badge-rogue-small.png";
import badgeWarriorSmall from "../images/badge-warrior-small.png";
import badgeGenericSmall from "../images/badge-generic-small.png";
import items from "../images/items";

class ItemTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.state = {
      boundingRect: ""
    }
    this.items = items;
    this.tooltipBox = "";
    this.statBox = [];
    this.badge = "";
    this.badgeClass = "";
    this.nameColor = this.commonColor;
    this.commonColor = {color: "#39363a"};
    this.uncommonColor = {color:"#4d610f"};
    this.rareColor = {color:"#1e4d7a"};
    this.epicColor = {color: "#4e0f62"};
    this.icon = iconHeal;
    this.iconClass = "";
    this.iconValue = "";
    this.iconValueColor = this.armorColor;
    this.armorColor = {color: "#136f9b"};
    this.powerColor = {color: "#a83a0e"};
    this.healColor = {color: "#ce6eb9"};
    this.type = "";
    this.tooltipQuadrant = "bottom-left";
    this.style = "";
    this.previousWidthChange = 0;
    this.tooltipBox = React.createRef();
    
    this.fillStatBox = this.fillStatBox.bind(this);
    this.setBadgeIconType = this.setBadgeIconType.bind(this);
    this.setTooltipQuadrant = this.setTooltipQuadrant.bind(this);
    this.setStyle = this.setStyle.bind(this);
  }

  render(){
    this.fillStatBox();
    this.setBadgeIconType();
    this.setTooltipQuadrant();
    this.setStyle();
    this.setNameColor();

    return (
      <div className={"item-tooltip-box " + this.tooltipQuadrant} style={this.style} ref={this.tooltipBox}>
        <img className="absolute-fill" src={tooltipItem}/>
        <img className="item-tooltip-image" src={this.items[this.props.item.type]["i" + this.props.item.image]}/>
        <img className={this.badgeClass} src={this.badge}/>
        <img className={this.iconClass} src={this.icon}/>
        <p className="item-tooltip-icon-value" style={this.iconValueColor}>{this.iconValue}</p>
        <p className="item-tooltip-name" style={this.nameColor}>{this.props.item.name}</p>
        <p className="item-tooltip-cost">{this.props.item.cost}</p>
        <p className="item-tooltip-description">{this.props.item.description}</p>        
        <div className="item-tooltip-general">{this.type}</div>
        <div className="item-tooltip-statbox">{this.statBox}</div>
      </div>
    )
  }

  componentDidMount(){
    this.setState({boundingRect: this.tooltipBox.current.getBoundingClientRect()});
  }

  componentDidUpdate(){
    if(this.props.widthChange > this.previousWidthChange){
      this.previousWidthChange = this.props.widthChange;
      this.setState({boundingRect: this.tooltipBox.current.getBoundingClientRect()});
    }
  }

  fillStatBox(){
    this.statBox = [];
    if(this.props.item.constitutionBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.constitutionBonus + " Constitution"}</p>
      )
    }
    if(this.props.item.strengthBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.strengthBonus + " Strength"}</p>
      )
    }
    if(this.props.item.dexterityBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.dexterityBonus + " Dexterity"}</p>
      )
    }
    if(this.props.item.intelligenceBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.intelligenceBonus + " Intelligence"}</p>
      )
    }
    if(this.props.item.healthBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.healthBonus + " Health"}</p>
      )
    }
    if(this.props.item.healthRegenBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.healthRegenBonus + " Health Regen"}</p>
      )
    }
    if(this.props.item.armorPenBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.armorPenBonus + " Armor Pen"}</p>
      )
    }
    if(this.props.item.dodgeRatingBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.dodgeRatingBonus + " Dodge Rating"}</p>
      )
    }
    if(this.props.item.critRatingBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.critRatingBonus + " Crit Rating"}</p>
      )
    }
    if(this.props.item.energyBonus){
      this.statBox.push(
        <p>{"+" + this.props.item.energyBonus + " Energy"}</p>
      )
    }
  }

  setBadgeIconType(){
    switch(this.props.item.type){
      case "potion":
        this.badge = badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.type = "Potion";
        this.iconValue = this.props.item.actionValue;
        actionType: switch(this.props.item.actionType){
          case "heal":
            this.icon = iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.healColor
            break actionType;
          default:
            this.icon = iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.healColor;
            break actionType;
        }
        break;
      case "consumable":
        this.badge = badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.type = "Consumable";
        this.iconValue = this.props.item.actionValue;
        actionType: switch(this.props.item.actionType){
          case "heal":
            this.icon = iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.healColor
            break actionType;
          default:
            this.icon = iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.healColor
            break actionType;
        }
        break;
      case "headLight":
        this.badge = badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Hat";
        break;
      case "headMedium":
        this.badge = badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Light Helmet";
        break;
      case "headHeavy":
        this.badge = badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Heavy Helmet";
        break;
      case "bodyLight":
        this.badge = badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Clothing";
        break;
      case "bodyMedium":
        this.badge = badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Light Armor";
        break;
      case "bodyHeavy":
        this.badge = badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Heavy Armor";
        break;
      case "back":
        this.badge = badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Cloak";
        break;  
      case "neck":
        this.badge = badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Amulet";
        break;
      case "staff":
        this.badge = badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Staff";
        break;
      case "spellbook":
        this.badge = badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Spellbook";
        break;
      case "bow":
        this.badge = badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Bow";
        break;
      case "dagger":
        this.badge = badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Dagger";
        break;
      case "sword":
        this.badge = badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.powerColor;
        this.type = "Sword";
        break;
      case "shield":
        this.badge = badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.armorColor;
        this.type = "Shield";
        break;
    }
    
  }

  setTooltipQuadrant(){
    if(this.state.boundingRect != ""){
      let centerPointX = 0;
      let centerPointY = 0;
      let blackSpace = (window.innerHeight - window.innerWidth*0.4286) > 0 
        ? (window.innerHeight - window.innerWidth*0.4286)/2
        : 0;
      switch(this.tooltipQuadrant){
        case "top-left":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "center-left":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.4952;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "bottom-left":
          centerPointY = this.state.boundingRect.bottom - blackSpace - this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "top-right":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
        case "center-right":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.4952;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
        case "bottom-right":
          centerPointY = this.state.boundingRect.bottom - blackSpace - this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
      }

      let top = true;
      let left = true;
      let center = false;

      top = centerPointY < (window.innerWidth*0.4286)*0.45
        ? true
        : false
      center = (centerPointY < (window.innerWidth*0.4286)*0.60 && centerPointY > (window.innerWidth*0.4286)*0.45)
        ? true
        : false
      left = centerPointX < window.innerWidth*this.props.leftMod
        ? true
        : false
      this.tooltipQuadrant = left
        ? center
          ? "center-left"
          : top
            ? "top-left"
            : "bottom-left"
        : center
          ? "center-right"
          : top
            ? "top-right"
            : "bottom-right"
    }
  }

  setStyle(){
    this.style = {height: + window.innerWidth*0.25 + "px", width: + window.innerWidth*0.1667 + "px"}
  }

  setNameColor(){
    switch(this.props.item.rarity){
      case "Common":
        this.nameColor = this.commonColor;
        break;
      case "Uncommon":
        this.nameColor = this.uncommonColor;
        break;
      case "Rare":
        this.nameColor = this.rareColor;
        break;
      case "Epic":
        this.nameColor = this.epicColor;
        break;
    }
  }

}

export default ItemTooltip;