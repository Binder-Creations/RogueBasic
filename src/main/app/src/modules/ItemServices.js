class ItemServices {
  constructor(props){
    this.props = props;
    this.getProps = this.getProps.bind(this);
  }

  getProps(item, isShop){
    let itemProps = {};
    let fontSize = item.name.length > 32 
      ? "50%"
      : "75%"
    itemProps.background = this.props.images["background"+item.rarity] 
    itemProps.frame = this.props.images["frame"+item.rarity]
    itemProps.image = this.props.items[item.type]["i"+item.image]
    itemProps.nameStyle = {color: this.props["color"+item.rarity], fontSize: fontSize}
    itemProps.shopNameStyle = {color: this.props["colorShop"+item.rarity], fontSize: fontSize}

    switch(item.type){
      case "potion":
        itemProps.badge = this.props.images.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.typeName = "Potion";
        itemProps.iconValue = item.actionValue;
        switch(item.actionType){
          case "heal":
            itemProps.icon = this.props.images.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = this.props.colorHeal
            break;
          default:
            itemProps.icon = this.props.images.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = this.props.colorHeal;
            break;
        }
        break;
      case "consumable":
        itemProps.badge = this.props.images.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.typeName = "Consumable";
        itemProps.iconValue = item.actionValue;
        switch(item.actionType){
          case "heal":
            itemProps.icon = this.props.images.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = this.props.colorHeal
            break;
          default:
            itemProps.icon = this.props.images.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = this.props.colorHeal
            break;
        }
        break;
      case "headLight":
        itemProps.badge = this.props.images.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Hat";
        break;
      case "headMedium":
        itemProps.badge = this.props.images.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Light Helmet";
        break;
      case "headHeavy":
        itemProps.badge = this.props.images.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Heavy Helmet";
        break;
      case "bodyLight":
        itemProps.badge = this.props.images.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Clothing";
        break;
      case "bodyMedium":
        itemProps.badge = this.props.images.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Light Armor";
        break;
      case "bodyHeavy":
        itemProps.badge = this.props.images.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Heavy Armor";
        break;
      case "back":
        itemProps.badge = this.props.images.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Cloak";
        break;  
      case "neck":
        itemProps.badge = this.props.images.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Amulet";
        break;
      case "staff":
        itemProps.badge = this.props.images.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Staff";
        break;
      case "spellbook":
        itemProps.badge = this.props.images.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Spellbook";
        break;
      case "bow":
        itemProps.badge = this.props.images.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Bow";
        break;
      case "dagger":
        itemProps.badge = this.props.images.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Dagger";
        break;
      case "sword":
        itemProps.badge = this.props.images.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = this.props.images.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = this.props.colorPower;
        itemProps.typeName = "Sword";
        break;
      case "shield":
        itemProps.badge = this.props.images.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = this.props.images.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = this.props.colorArmor;
        itemProps.typeName = "Shield";
        break;
      default:
        break;
      }

    itemProps.stats = [];
    if(item.constitutionBonus){
      itemProps.stats.push(
        <p>{"+" + item.constitutionBonus + " Constitution"}</p>
      )
    }
    if(item.strengthBonus){
      itemProps.stats.push(
        <p>{"+" + item.strengthBonus + " Strength"}</p>
      )
    }
    if(item.dexterityBonus){
      itemProps.stats.push(
        <p>{"+" + item.dexterityBonus + " Dexterity"}</p>
      )
    }
    if(item.intelligenceBonus){
      itemProps.stats.push(
        <p>{"+" + item.intelligenceBonus + " Intelligence"}</p>
      )
    }
    if(item.healthBonus){
      itemProps.stats.push(
        <p>{"+" + item.healthBonus + " Health"}</p>
      )
    }
    if(item.healthRegenBonus){
      itemProps.stats.push(
        <p>{"+" + item.healthRegenBonus + " Health Regen"}</p>
      )
    }
    if(item.armorPenBonus){
      itemProps.stats.push(
        <p>{"+" + item.armorPenBonus + " Armor Pen"}</p>
      )
    }
    if(item.dodgeRatingBonus){
      itemProps.stats.push(
        <p>{"+" + item.dodgeRatingBonus + " Dodge Rating"}</p>
      )
    }
    if(item.critRatingBonus){
      itemProps.stats.push(
        <p>{"+" + item.critRatingBonus + " Crit Rating"}</p>
      )
    }
    if(item.energyBonus){
      itemProps.stats.push(
        <p>{"+" + item.energyBonus + " Energy"}</p>
      )
    }
    if(item.energyRegenBonus){
      itemProps.stats.push(
        <p>{"+" + item.energyRegenBonus + " Energy Regen"}</p>
      )
    }
    return itemProps
  }
}
export default ItemServices;