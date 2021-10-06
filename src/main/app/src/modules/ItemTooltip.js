import React from "react";

class ItemTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.state = {
      boundingRect: ""
    }
    this.tooltipBox = "";
    this.statBox = [];
    this.badge = "";
    this.badgeClass = "";
    this.nameColor = this.colorCommon;
    this.colorCommon = {color: "#39363a"};
    this.colorUncommon = {color:"#4d610f"};
    this.colorRare = {color:"#1e4d7a"};
    this.colorEpic = {color: "#4e0f62"};
    this.icon = this.props.props.images.iconHeal;
    this.iconClass = "";
    this.iconValue = "";
    this.iconValueColor = this.colorArmor;
    this.colorArmor = {color: "#136f9b"};
    this.colorPower = {color: "#a83a0e"};
    this.colorHeal = {color: "#ce6eb9"};
    this.type = "";
    this.tooltipQuadrant = "bottom-left";
    this.style = "";
    this.previousWidthChange = 0;
    this.previousUpdate = 0;
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
    this.nameColor = this["color"+this.props.item.rarity]

    return (
      <div className={"item-tooltip-box " + this.tooltipQuadrant} style={this.style} ref={this.tooltipBox}>
        <img className="absolute-fill" src={this.props.props.images.tooltipItem}/>
        <img className="item-tooltip-image" src={this.props.props.items[this.props.item.type]["i" + this.props.item.image]}/>
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
    if(this.props.props.widthChange > this.previousWidthChange || this.props.update > this.previousUpdate){
      this.previousWidthChange = this.props.props.widthChange;
      this.previousUpdate = this.props.update;
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
        this.badge = this.props.props.images.badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.type = "Potion";
        this.iconValue = this.props.item.actionValue;
        actionType: switch(this.props.item.actionType){
          case "heal":
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.colorHeal
            break actionType;
          default:
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.colorHeal;
            break actionType;
        }
        break;
      case "consumable":
        this.badge = this.props.props.images.badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.type = "Consumable";
        this.iconValue = this.props.item.actionValue;
        actionType: switch(this.props.item.actionType){
          case "heal":
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.colorHeal
            break actionType;
          default:
            this.icon = this.props.props.images.iconHeal;
            this.iconClass = "item-tooltip-icon-heal";
            this.iconValueColor = this.colorHeal
            break actionType;
        }
        break;
      case "headLight":
        this.badge = this.props.props.images.badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Hat";
        break;
      case "headMedium":
        this.badge = this.props.props.images.badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Light Helmet";
        break;
      case "headHeavy":
        this.badge = this.props.props.images.badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Heavy Helmet";
        break;
      case "bodyLight":
        this.badge = this.props.props.images.badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Clothing";
        break;
      case "bodyMedium":
        this.badge = this.props.props.images.badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Light Armor";
        break;
      case "bodyHeavy":
        this.badge = this.props.props.images.badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Heavy Armor";
        break;
      case "back":
        this.badge = this.props.props.images.badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
        this.type = "Cloak";
        break;  
      case "neck":
        this.badge = this.props.props.images.badgeGenericSmall;
        this.badgeClass = "item-tooltip-badge-generic";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Amulet";
        break;
      case "staff":
        this.badge = this.props.props.images.badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Staff";
        break;
      case "spellbook":
        this.badge = this.props.props.images.badgeWizardSmall;
        this.badgeClass = "item-tooltip-badge-wizard";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Spellbook";
        break;
      case "bow":
        this.badge = this.props.props.images.badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Bow";
        break;
      case "dagger":
        this.badge = this.props.props.images.badgeRogueSmall;
        this.badgeClass = "item-tooltip-badge-rogue";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Dagger";
        break;
      case "sword":
        this.badge = this.props.props.images.badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = this.props.props.images.iconPower;
        this.iconClass = "item-tooltip-icon-power";
        this.iconValue = this.props.item.powerBonus;
        this.iconValueColor = this.colorPower;
        this.type = "Sword";
        break;
      case "shield":
        this.badge = this.props.props.images.badgeWarriorSmall;
        this.badgeClass = "item-tooltip-badge-warrior";
        this.icon = this.props.props.images.iconArmor;
        this.iconClass = "item-tooltip-icon-armor";
        this.iconValue = this.props.item.armorBonus;
        this.iconValueColor = this.colorArmor;
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
}

export default ItemTooltip;