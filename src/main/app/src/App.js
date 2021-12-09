import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import Ui from "./modules/Ui"
import ShopMenu from "./modules/ShopMenu";
import InnMenu from "./modules/InnMenu";
import TavernMenu from "./modules/TavernMenu";
import PcServices from "./modules/PcServices";
import ItemServices from "./modules/ItemServices";
import Dungeon from "./modules/Dungeon";
import * as images from "./images"
import * as items from "./images/items";
import * as monsters from "./images/monsters";
import CombatEngine from "./modules/CombatEngine";


class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: {name: null},
      scene: "Default",
      combat: false,
      combatUpdates: [], 
      character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*=\s*([^;]*).*$)|^.*$/, "$1"),
      widthChange: 0
    };
    if(this.state.character_id){
      fetch('/pc/'+ this.state.character_id)
      .then(response => response.json())
      .then(data => {
        this.setState({pc: data})
      });
      
    }
    this.combat = false;
    this.pcServices = new PcServices(this.state.pc.characterClass);
    this.appState = this.appState.bind(this);
    this.updateWidth = this.updateWidth.bind(this);
    this.sortDungeon = this.sortDungeon.bind(this);

    this.props.props = {
      pc: this.state.pc,
      combat: this.state.combat,
      images: images, 
      items: items,
      monsters: monsters, 
      appState: this.appState,
      colorArmor: {color: "#136f9b"},
      colorPower: {color: "#a83a0e"},
      colorHeal: {color: "#ce6eb9"}
    }

    this.props.props.itemServices = new ItemServices({
      images: images, 
      items: items, 
      monsters: monsters,
      colorArmor: this.props.props.colorArmor,
      colorPower: this.props.props.colorPower,
      colorHeal: this.props.props.colorHeal,
      colorCommon: "#39363a",
      colorUncommon: "#4d610f",
      colorRare: "#1e4d7a",
      colorEpic: "#4e0f62",
      colorShopCommon: "#d2d2d2",
      colorShopUncommon: "#b4de31",
      colorShopRare: "#3296f6",
      colorShopEpic: "#c111f9"
    });

    this.disableUiMenus = false;
    this.backgroundSrc = "";
    this.innerElements = <></>
    this.outerElements = <></>
    this.InnerElements = () => {
      return(this.innerElements);
    }
    this.OuterElements = () => {
      return(this.outerElements);
    }
  }

  render(){
    this.checkCombat();
    console.log(this.state.combat)

    if (!this.state.character_id)
      return(
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/login'; 
            return null;
          }}/>
        </Router>
      );
    if(this.state.scene==="Home"){
        return(
          <Router>
            <Route path='/' component={() => { 
              window.location.href = '/home'; 
              return null;
            }}/>
          </Router>
        );
      }
    if(!this.state.pc.name){
      return(<></>)
    }
    if(!this.initialize){
      this.pcServices.updateStats(this.state.pc);
      window.addEventListener("resize", this.updateWidth)
      this.initialize = true;
    }

    this.props.props.pc = this.state.pc;
    this.props.props.combat = this.state.combat;
    this.props.props.combatUpdates = this.state.combatUpdates;
    this.props.props.widthChange = this.state.widthChange;
      
    this.innerElements = <></>
    this.outerElements = <></>
    this.disableUiMenus = false;
    let button = "TownButton";

    if (this.state.pc.location === "Town"){
      if(this.state.scene === "Default"){
        this.backgroundSrc = this.props.props.images.town
        button = "HomeButton"
        this.innerElements = <>
          <input className="tavern" type="image" src={this.props.props.images.tavernExterior} alt="Tavern" onClick={ () => { this.setState({scene:"Tavern"})} }/>
          <input className="inn" type="image" src={this.props.props.images.innExterior} alt="Inn" onClick={ () => { this.setState({scene:"Inn"})} }/>
          <input className="shop" type="image" src={this.props.props.images.shopExterior} alt="Shop" onClick={ () => { this.setState({scene:"Shop"})} }/>
        </>
      } else if(this.state.scene === "Tavern"){
        if(this.state.pc.currentDungeon && (!this.state.dungeon || this.state.dungeon.id !== this.state.pc.currentDungeon)){
          fetch('/dungeon/'+ this.state.pc.currentDungeon)
          .then(response => response.json())
          .then(data => {
            this.setState({dungeon: this.sortDungeon(data)});
          });
        }
        if(!this.state.pc.dungeonBoard){
          fetch('/dungeon/new/'+ this.state.pc.id)
          .then(response => response.json())
          .then(data => {
            let pc = {...this.state.pc};
            pc.dungeonBoard = [];
            data.forEach(dungeon => pc.dungeonBoard.push(dungeon.id))
            this.save("pc", pc)
              .then(this.setState({pc: pc, dungeonBoard: data}));
          });
        } 
        if(this.state.pc.dungeonBoard && (!this.state.dungeonBoard)){
          fetch('/dungeon/getBoard/'+ this.state.pc.id)
            .then(response => response.json())
            .then(data => {
              this.setState({dungeonBoard: data});
            });
        }

        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.tavern
        this.outerElements = <>
          <TavernMenu props={this.props.props} dungeonBoard={this.state.dungeonBoard} dungeon={this.state.dungeon}/>
        </>
      } else if(this.state.scene==="Inn"){
        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.inn
        this.outerElements = <>
          <InnMenu props={this.props.props}/>
        </>
      } else if(this.state.scene==="Shop"){
        if(this.state.pc.currentShop){
          if(!this.state.shop){
            fetch('/shop/'+ this.state.pc.currentShop)
              .then(response => response.json())
              .then(data => {
                this.setState({shop: data});
              });
          } else if(this.state.shop.id !== this.state.pc.currentShop){
            fetch('/shop/'+ this.state.pc.currentShop)
              .then(response => response.json())
              .then(data => {
                this.setState({shop: data});
              });
          }
        } else {
          fetch('/shop/new/'+ this.state.pc.id)
            .then(response => response.json())
            .then(data => {
              let pc = {...this.state.pc}
              pc.currentShop = data.id;
              this.save("pc", this.state.pc)
                .then(this.setState({shop: data, pc: pc}));
            });
        }

        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.shop
        this.outerElements = <>
          <ShopMenu props={this.props.props} shop={this.state.shop}/>
        </>
      }
    } else if(this.state.pc.location === "Dungeon") {
      if(!this.state.dungeon){
        fetch('/dungeon/' + this.state.pc.currentDungeon)
          .then(response => response.json())
          .then(data => {
            this.setState({dungeon: this.sortDungeon(data)});
        });
        return(<></>)
      }
      if(!this.state.dungeon.floors && !this.state.dungeon.floorIds){
        fetch('/dungeon/' + this.state.dungeon.id)
          .then(response => response.json())
          .then(data => {
            this.setState({dungeon: this.sortDungeon(data)});
        });
      } else if(!this.state.dungeon.floors){
        fetch('/dungeon/convert/' + this.state.dungeon.id)
          .then(response => response.json())
          .then(data => {
            this.setState({dungeon: this.sortDungeon(data)});
        });
      }

      if(this.state.dungeon && this.state.dungeon.floors){
        this.backgroundSrc = this.props.props.images[this.state.dungeon.theme.toLowerCase() + this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].variant];
        this.innerElements = <Dungeon props={this.props.props} dungeon={this.state.dungeon} pcServices={this.pcServices}/>
      } else {
        this.backgroundSrc = this.props.props.images.tavern
      }
    } else {
      this.backgroundSrc = this.props.props.images.town
    }

    return(
    <div className="app-container">
      <img className="background" src={this.backgroundSrc} alt="Background"/>
      <this.InnerElements/>
      <Ui props={this.props.props} dungeon={this.state.dungeon} button={button} disableUiMenus={this.disableUiMenus}/>
      <this.OuterElements/>
    </div>
    );
    }

    componentDidMount(){
      Object.keys(this.props.props.images).forEach((image) => {
        new Image().src = this.props.props.images[image];
      });
      Object.keys(this.props.props.items).forEach((type) => {
        Object.keys(this.props.props.items[type]).forEach((item) => {
          new Image().src = this.props.props.items[type][item];
        });
      });
      Object.keys(this.props.props.monsters).forEach((type) => {
        Object.keys(this.props.props.monsters[type]).forEach((item) => {
          new Image().src = this.props.props.monsters[type][item];
        });
      });
    }

    checkCombat(){
      console.log("here1")
      if(!this.state.combat && this.state.pc.location === "Dungeon" && this.state.dungeon && this.state.dungeon.floors && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.length){
        console.log("here2")
        this.appState("combat");
      }
      if(this.state.combat && (this.state.pc.location !== "Dungeon" || !this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.length)){
        console.log("here3")
        this.appState("combat");
      }
    }

    sortDungeon(dungeon){
      dungeon.floors.forEach(floor=>floor.rooms.sort((a,b)=>(a.stairsPrevious) ? -1 : (b.stairsNext ? -1 : 0)));
      dungeon.floors.sort((a,b) => (a.level < b.level) ? -1 : (b.level < a.level ? 1 : 0));
      return dungeon;
    }

    async save(type, object){
      await fetch('/'+type+'/', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(object)
      });
      return;
    }

    updateWidth(){
      this.setState({widthChange: this.state.widthChange + 1});
    }

    appState(method, key, value){
      let pc;
      let dungeon;
      let shop;
      let addItem = true;
      switch(method){
        case "ability":
          dungeon = {...this.state.dungeon}
          this.combatEngine.runRound(this.state.pc.abilities[key]);
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
          this.setState({
            pc: this.combatEngine.pc,
            dungeon: dungeon
          });
          break;
        case "combat":
          if(!this.state.combat){
              console.log("combatOn")
              console.log(this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters)
              dungeon = {...this.state.dungeon}
              this.combatEngine = new CombatEngine(this.state.pc, this.pcServices, this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters, this.state.dungeon.postFix);
              dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
              this.setState({
                combat: true,
                dungeon: dungeon
              });
              break;
          } else {
            console.log("combatOff")
            pc = {...this.state.pc}
            this.pcServices.resetTempStats(pc);
            this.pcServices.updateStats(pc);
            this.combatEngine = null;
            this.save("pc", this.state.pc)
              .then(this.save("dungeon", this.state.dungeon))
              .then(() => this.setState({pc: pc, combat: false}));
            break;
          }
          
        case "stairs":
          dungeon = {...this.state.dungeon};
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
          if(key){
            if(dungeon.currentFloor === 0){
              pc = {...this.state.pc};
              pc.location = "Town";
              this.save("dungeon", dungeon)
                .then(this.save("pc", pc))
                .then(()=>{this.setState({dungeon: dungeon, pc:pc, scene:"Default"})})
            } else {
              dungeon.currentFloor -= 1;
              dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.length-1;
              this.save("dungeon", dungeon)
                .then(()=>{this.setState({dungeon: dungeon})})
            }
          } else {
            dungeon.currentFloor += 1;
            dungeon.currentRoom = 0;
            this.save("dungeon", dungeon)
              .then(()=>{this.setState({dungeon: dungeon})})
          }
          break;
        case "move-room":
          dungeon = {...this.state.dungeon};
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
          dungeon.floors[dungeon.currentFloor].rooms.some(room => {
            if(room.id === key){
              dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.indexOf(room);
              return true;
            }
            return false;
          });
          if(!dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared){
            pc = {...this.state.pc};
            this.pcServices.regenHealth(pc);
            this.pcServices.regenEnergy(pc);
            this.save("dungeon", dungeon)
              .then(this.save("pc", pc))
              .then(()=>{this.setState({dungeon: dungeon, pc:pc})})
          }else {
            this.save("dungeon", dungeon)
              .then(()=>{this.setState({dungeon: dungeon})})
          }
          break;
        case "scene":
          if(this.state.pc.location === value) {
            this.setState({scene: key})
          } else {
            pc = {...this.state.pc}
            pc.location = value
            this.save("pc", pc)
              .then(()=>{this.setState({pc: pc, scene: key})})
          }
          break;
        case "to-dungeon":
          pc = {...this.state.pc};
          pc.location = "Dungeon";
          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc})});
          break;
        case "set-dungeon":
          pc = {...this.state.pc};
          pc.currentDungeon = key;
          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc})});
          break;
        case "rest":
          pc = {...this.state.pc};
          pc.currentHealth = pc.healthTotal;
          pc.currentEnergy = pc.energyTotal;
          pc.ate = false;
          pc.currentShop = null;
          pc.dungeonBoard = null;
          pc.currentDungeon = null;
          pc.currentFloor = -1;
          pc.currentRoom = -1;
          pc.day += 1;
          pc.currency -= 250;
          pc.currency = pc.currency > 0 ? pc.currency : 0;

          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc, dungeon: null})});
          break;
        case "eat":
            pc = {...this.state.pc};
            pc.currentHealth = pc.healthTotal;
            pc.currentEnergy = pc.energyTotal;
            pc.ate = true;
            pc.currency -= 100;
            pc.currency = pc.currency > 0 ? pc.currency : 0;

            this.save("pc", pc)
              .then(()=>{this.setState({pc: pc})});
            break;
        case "shop-store":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(key.type !== "potion" && key.type !== "consumable"){
            var index = shop.inventoryCache.indexOf(key);
            if (index !== -1)
              shop.inventoryCache.splice(index, 1);
          }
          pc.currency -= key.cost*5;
          if(pc.currency < 0){
            pc.currency = 0;
          }

          if(pc.inventory[key.id]){
            pc.inventory[key.id] += 1;
          } else {
            pc.inventory[key.id] = 1;
          }
          for(const item of pc.inventoryCache){
            if(item.id === key.id){
              addItem = false;
            }
          }
          if(addItem){
            pc.inventoryCache.push(key);
          }
          this.save("pc", pc)
            .then(this.save("shop", shop))
            .then(()=>{this.setState({pc: pc, shop: shop})});
          break;
        case "shop-player":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            let index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }

          pc.currency += key.cost;
          
          if(key.type !== "potion" && key.type !== "consumable"){
            shop.inventoryCache.push(key);
          }

          this.save("pc", pc)
            .then(this.save("shop", shop))
            .then(()=>{this.setState({pc: pc, shop: shop})});
          break;
        case "inventory":  
          pc = {...this.state.pc};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            let index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }
          switch (key.actionType){
            case "heal":
              if(this.state.combat){
                this.combatEngine.itemHeal(key.actionValue);
                pc = this.combatEngine.pc;
              } else {
                pc.currentHealth = (pc.currentHealth + pc.healthTotal*(1+key.actionValue/100) > pc.healthTotal)
                ? pc.healthTotal
                : pc.currentHealth + pc.healthTotal*(1+key.actionValue/100);
              }
              break;
            default:
              var slot;
              if(key.type === "headLight" || key.type === "headMedium" || key.type === "headHeavy"){
                slot = "Head";
              } else if (key.type === "bodyLight" || key.type === "bodyMedium" || key.type === "bodyHeavy"){
                slot = "Body"
              } else if (key.type === "neck"){
                slot = "Neck"
              } else if (key.type === "back"){
                slot = "Back"
              } else if (key.type === "bow" || key.type === "staff" || key.type === "sword"){
                slot = "Primary"
              } else {
                slot = "Secondary"
              }
              if(pc["equipped" + slot]){
                pc.inventory[pc["equipped" + slot].id] = 1;
                pc.inventoryCache.push(pc["equipped" + slot])
              }
              pc["equipped" + slot] = key;
              break;
          }
          this.pcServices.updateStats(pc);
          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc})});
          break;
        case "unequip":  
          pc = {...this.state.pc};
          pc.inventory[key.id] = 1;
          pc.inventoryCache.push(key);
          if(key.type === "headLight" || key.type === "headMedium" || key.type === "headHeavy"){
            pc.equippedHead = null;
          } else if (key.type === "bodyLight" || key.type === "bodyMedium" || key.type === "bodyHeavy"){
            pc.equippedBody = null;
          } else if (key.type === "neck"){
            pc.equippedNeck = null;
          } else if (key.type === "back"){
            pc.equippedBack = null;
          } else if (key.type === "bow" || key.type === "staff" || key.type === "sword"){
            pc.equippedPrimary = null;
          } else {
            pc.equippedSecondary = null;
          }
          this.pcServices.updateStats(pc);
          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc})});
          break;
        case "pointbuy":
          pc = {...this.state.pc};
          pc.attributePoints > 0
            ? pc.attributePoints -= 1
            : pc.attributePoints = 0;
          switch(key){ 
            case "con":
              pc.constitution += 1;
              break;
            case "str":
              pc.strength += 1;
              break;
            case "dex":
              pc.dexterity += 1;
              break;
            case "int":
              pc.intelligence += 1;
              break;
            default:
              break;
          }
          this.pcServices.updateStats(pc);
          this.save("pc", pc)
            .then(()=>{this.setState({pc: pc})});
          break;
        default:
          break;
      }
    }

}

export default App
