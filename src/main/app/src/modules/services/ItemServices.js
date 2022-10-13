import c from "../../data/CommonProperties";

export default {
  getProps: (item, isShop) => {
    let itemProps = {};
    let fontSize = item.name.length > 32 
      ? "50%"
      : "75%"
    itemProps.background = c.images.common["background"+item.rarity] 
    itemProps.frame = c.images.common["frame"+item.rarity]
    itemProps.image = c.images.items[item.type]["i"+item.image]
    itemProps.nameStyle = {color: c["color"+item.rarity], fontSize: fontSize}
    itemProps.shopNameStyle = {color: c["colorShop"+item.rarity], fontSize: fontSize}

    switch(item.type){
      case "potion":
        itemProps.badge = c.images.common.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.typeName = "Potion";
        itemProps.iconValue = item.actionValue + "%";
        switch(item.actionType){
          case "heal":
            itemProps.icon = c.images.common.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorHeal
            break;
          case "energize":
            itemProps.icon = c.images.common.iconEnergize;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorEnergize
            break;
          default:
            itemProps.icon = c.images.common.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorHeal;
            break;
        }
        break;
      case "consumable":
        itemProps.badge = c.images.common.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.typeName = "Consumable";
        itemProps.iconValue = item.actionValue + "%";
        switch(item.actionType){
          case "heal":
            itemProps.icon = c.images.common.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorHeal
            break;
          case "energize":
            itemProps.icon = c.images.common.iconEnergize;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorEnergize
            break;
          default:
            itemProps.icon = c.images.common.iconHeal;
            itemProps.iconClass = "icon-heal";
            itemProps.iconValueColor = c.colorHeal
            break;
        }
        break;
      case "headLight":
        itemProps.badge = c.images.common.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Hat";
        break;
      case "headMedium":
        itemProps.badge = c.images.common.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Light Helmet";
        break;
      case "headHeavy":
        itemProps.badge = c.images.common.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Heavy Helmet";
        break;
      case "bodyLight":
        itemProps.badge = c.images.common.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Clothing";
        break;
      case "bodyMedium":
        itemProps.badge = c.images.common.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Light Armor";
        break;
      case "bodyHeavy":
        itemProps.badge = c.images.common.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Heavy Armor";
        break;
      case "back":
        itemProps.badge = c.images.common.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
        itemProps.typeName = "Cloak";
        break;  
      case "neck":
        itemProps.badge = c.images.common.badgeGenericSmall;
        itemProps.badgeClass = "badge-generic";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Amulet";
        break;
      case "staff":
        itemProps.badge = c.images.common.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Staff";
        break;
      case "spellbook":
        itemProps.badge = c.images.common.badgeWizardSmall;
        itemProps.badgeClass = "badge-wizard";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Spellbook";
        break;
      case "bow":
        itemProps.badge = c.images.common.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Bow";
        break;
      case "dagger":
        itemProps.badge = c.images.common.badgeRogueSmall;
        itemProps.badgeClass = "badge-rogue";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Dagger";
        break;
      case "sword":
        itemProps.badge = c.images.common.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = c.images.common.iconPower;
        itemProps.iconClass = "icon-power";
        itemProps.iconValue = item.powerBonus;
        itemProps.iconValueColor = c.colorPower;
        itemProps.typeName = "Sword";
        break;
      case "shield":
        itemProps.badge = c.images.common.badgeWarriorSmall;
        itemProps.badgeClass = "badge-warrior";
        itemProps.icon = c.images.common.iconArmor;
        itemProps.iconClass = "icon-armor";
        itemProps.iconValue = item.armorBonus;
        itemProps.iconValueColor = c.colorArmor;
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