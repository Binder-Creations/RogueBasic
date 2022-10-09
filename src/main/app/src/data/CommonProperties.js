import * as abilities from "../images/abilities"; 
import * as abilitiesSmall from "../images-small/abilities";
import * as common from "../images/common";
import * as commonSmall from "../images-small/common";
import * as environments from "../images/environments";
import * as environmentsSmall from "../images-small/environments";
import * as classes from "../images/classes";
import * as classesSmall from "../images-small/classes";
import * as items from "../images/items";
import * as itemsSmall from "../images-small/items";
import * as monsters from "../images/monsters";
import * as monstersSmall from "../images-small/monsters";

let largeWindow = window.innerWidth > 960 ? true : false;

export default {
  positions: ["pc", "frontLeft", "frontCenter", "frontRight", "backLeft", "backCenter", "backRight"],
  colorArmor: {color: "#136f9b"},
  colorPower: {color: "#a83a0e"},
  colorHeal: {color: "#ce6eb9"},
  colorEnergize: {color: "#2a52be"},
  colorCommon: "#39363a",
  colorUncommon: "#4d610f",
  colorRare: "#1e4d7a",
  colorEpic: "#4e0f62",
  colorShopCommon: "#d2d2d2",
  colorShopUncommon: "#b4de31",
  colorShopRare: "#3296f6",
  colorShopEpic: "#c111f9",
  zeroId: {id: "00000000-0000-0000-0000-000000000000"},
  disableUiMenus: false,
  imageMax: 250,
  largeWindow: largeWindow,
  images: {
    common: largeWindow ? common : commonSmall,
    environments: largeWindow ? environments : environmentsSmall,
    classes: largeWindow ? classes : classesSmall,
    abilities: largeWindow > 960 ? abilities : abilitiesSmall,
    items: largeWindow > 960 ? items : itemsSmall,
    monsters: largeWindow > 960 ? monsters : monstersSmall
  }
};