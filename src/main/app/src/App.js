import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import Ui from "./modules/Ui"
import ShopMenu from "./modules/ShopMenu";
import InnMenu from "./modules/InnMenu";
import PcServices from "./modules/PcServices";
import ItemServices from "./modules/ItemServices";
import * as images from "./images"
import * as items from "./images/items";


class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: {name: null},
      scene: "Default",
      combat: false, 
      character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1"),
      widthChange: 0
    };
    if(this.state.character_id){
      fetch('/pc/'+ this.state.character_id)
      .then(response => response.json())
      .then(data => this.setState({pc: data}))
        // .then(this.setState(state => state.pc.location == "Town"
        //   ? {room: null}
        //   : {room: fetch('/room/'+ state.pc.location)
        //     .then(response => response.json())}))
        // .then(data => this.state.pc = JSON.parse(data))
        // .then(this.state.room = this.state.pc.location == "Town"
        //   ? null
        //   : JSON.parse(fetch('/room/'+ this.state.pc.location)
        //     .then(response => response.json())))
      
    };
    this.pcServices = new PcServices(this.state.pc.characterClass);
    this.appState = this.appState.bind(this);
    this.updateWidth = this.updateWidth.bind(this);

    this.props.props = {
      images: images, 
      items: items, 
      appState: this.appState,
      itemServices: new ItemServices({
        images: images, 
        items: items, 
        colorArmor: {color: "#136f9b"},
        colorPower: {color: "#a83a0e"},
        colorHeal: {color: "#ce6eb9"},
        colorCommon: "#39363a",
        colorUncommon: "#4d610f",
        colorRare: "#1e4d7a",
        colorEpic: "#4e0f62",
        colorShopCommon: "#d2d2d2",
        colorShopUncommon: "#b4de31",
        colorShopRare: "#3296f6",
        colorShopEpic: "#c111f9"
      })
    }

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
    this.TownButton = () => {
      return(
      <button className="btn-home" onClick={ () => { this.setState({scene:"Default"})} }>
        <img src={this.props.props.images.townIcon} alt="Town"/>
      </button>
      );
    }
    this.HomeButton = () => {
      return(
      <button className="btn-home" onClick={ () => { this.setState({scene:"Home"})} }>
        <img src={this.props.props.images.scrollIcon} alt="Home"/>
      </button>
      );
    }
  }

  render(){
    if (!this.state.character_id)
      return(
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/login'; 
            return null;
          }}/>
        </Router>
      );
    if(this.state.scene=="Home"){
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
      this.pcServices.setPc(this.state.pc);
      this.pcServices.updateStats()
      window.addEventListener("resize", this.updateWidth)
      this.initialize = true;
     }

    this.props.props.pc = this.state.pc;
    this.props.props.combat = this.state.combat;
    this.props.props.widthChange = this.state.widthChange;
      
    this.innerElements = <></>
    this.outerElements = <></>
    this.disableUiMenus = false;

    if (this.state.pc.location == "Town"){
      if(this.state.scene=="Default"){
        this.backgroundSrc = this.props.props.images.town
        this.innerElements = <>
          <input className="tavern" type="image" src={this.props.props.images.tavernExterior} alt="Tavern" onClick={ () => { this.setState({scene:"Tavern"})} }/>
          <input className="inn" type="image" src={this.props.props.images.innExterior} alt="Inn" onClick={ () => { this.setState({scene:"Inn"})} }/>
          <input className="shop" type="image" src={this.props.props.images.shopExterior} alt="Shop" onClick={ () => { this.setState({scene:"Shop"})} }/>
          <this.HomeButton/>
        </>
      } else if(this.state.scene=="Tavern"){
        this.backgroundSrc = this.props.props.images.tavern
        this.outerElements = <>
          <this.TownButton/>
        </>
      } else if(this.state.scene=="Inn"){
        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.inn
        this.outerElements = <>
          <InnMenu props={this.props.props}/>
          <this.TownButton/>
        </>
      } else if(this.state.scene=="Shop"){
        if(this.state.pc.currentShop){
          if(!this.state.shop){
            fetch('/shop/'+ this.state.pc.currentShop)
              .then(response => response.json())
              .then(data => {
                this.state.pc.currentShop = data.id;
                this.savePc(this.state.pc);
                this.setState({shop: data});
              });
          } else if(this.state.shop.id != this.state.pc.currentShop){
            fetch('/shop/'+ this.state.pc.currentShop)
              .then(response => response.json())
              .then(data => {
                this.state.pc.currentShop = data.id;
                this.savePc(this.state.pc);
                this.setState({shop: data});
              });
          }
        } else {
          fetch('/shop/new/'+ this.state.pc.id)
            .then(response => response.json())
            .then(data => {
              this.state.pc.currentShop = data.id;
              this.savePc(this.state.pc);
              this.setState({shop: data});
            });
        }

        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.shop
        this.outerElements = <>
          <ShopMenu props={this.props.props} shop={this.state.shop}/>
          <this.TownButton/>
        </>
      }
    }else{
      this.backgroundSrc = this.props.props.images.town
      }

    return(
    <div className="app-container">
      <img className="background" src={this.backgroundSrc} alt="Background"/>
      <this.InnerElements/>
      <Ui props={this.props.props} disableUiMenus={this.disableUiMenus}/>
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
    }

    async savePc(pc){
      await fetch('/pc/', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(pc)
      });
      return;
    }
    async saveShop(shop){
      await fetch('/shop/', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(shop)
      });
      return;
    }
    updateWidth(){
      this.setState({widthChange: ++this.state.widthChange});
    }
    appState(method, key, value){
      let pc;
      let shop;
      let addItem = true;
      method: switch(method){
        case "rest":
          pc = {...this.state.pc};
          pc.currentHealth = pc.healthTotal;
          pc.currentEnergy = pc.energyTotal;
          pc.ate = false;
          pc.currentShop = null;
          pc.currentDungeon = null;
          pc.day += 1;
          pc.currency -= 250;
          pc.currency = pc.currency > 0 ? pc.currency : 0;

          this.savePc(pc)
            .then(()=>{this.setState({pc: pc})});
          break method;
        case "eat":
            pc = {...this.state.pc};
            pc.currentHealth = pc.healthTotal;
            pc.currentEnergy = pc.energyTotal;
            pc.ate = true;
            pc.currency -= 100;
            pc.currency = pc.currency > 0 ? pc.currency : 0;

            this.savePc(pc)
              .then(()=>{this.setState({pc: pc})});
            break method;
        case "shop-store":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(key.type != "potion" && key.type != "consumable"){
            delete shop.inventory[key.id]
            var index = shop.inventoryCache.indexOf(key);
            if (index !== -1)
              shop.inventoryCache.splice(index, 1);
          }
          pc.currency -= key.cost*5;
          pc.currency < 0 
            ? pc.currency = 0
            : pc.currency = pc.currency

          if(pc.inventory[key.id]){
            pc.inventory[key.id] += 1;
          } else {
            pc.inventory[key.id] = 1;
          }
          for(const item of pc.inventoryCache){
            if(item.id == key.id){
              addItem = false;
            }
          }
          if(addItem){
            pc.inventoryCache.push(key);
          }
          this.savePc(pc)
            .then(this.saveShop(shop))
            .then(()=>{this.setState({pc: pc, shop: shop})});
          break method;
        case "shop-player":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            var index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }

          pc.currency += key.cost;
          
          if(key.type != "potion" && key.type != "consumable"){
            shop.inventory[key.id] = 1;
            shop.inventoryCache.push(key);
          }

          this.savePc(pc)
            .then(this.saveShop(shop))
            .then(()=>{this.setState({pc: pc, shop: shop})});
        break method;
        case "inventory":  
          pc = {...this.state.pc};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            var index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }
          key: switch (key.actionType){
            case "heal":
              pc.currentHealth = (pc.currentHealth + pc.healthTotal*(1+key.actionValue/100) > pc.healthTotal)
                ? pc.healthTotal
                : pc.currentHealth + pc.healthTotal*(1+key.actionValue/100);
              break key;
            default:
              var slot;
              if(key.type == "headLight" || key.type == "headMedium" || key.type == "headHeavy"){
                slot = "Head";
              } else if (key.type == "bodyLight" || key.type == "bodyMedium" || key.type == "bodyHeavy"){
                slot = "Body"
              } else if (key.type == "neck"){
                slot = "Neck"
              } else if (key.type == "back"){
                slot = "Back"
              } else if (key.type == "bow" || key.type == "staff" || key.type == "sword"){
                slot = "Primary"
              } else {
                slot = "Secondary"
              }
              if(pc["equipped" + slot]){
                pc.inventory[pc["equipped" + slot].id] = 1;
                pc.inventoryCache.push(pc["equipped" + slot])
              }
              pc["equipped" + slot] = key;
              break key;
          }
          this.pcServices.setPc(pc);
          this.pcServices.updateStats();
          this.savePc(pc)
            .then(()=>{this.setState({pc: pc})});
          break method;
        case "unequip":  
          pc = {...this.state.pc};
          pc.inventory[key.id] = 1;
          pc.inventoryCache.push(key);
          if(key.type == "headLight" || key.type == "headMedium" || key.type == "headHeavy"){
            pc.equippedHead = null;
          } else if (key.type == "bodyLight" || key.type == "bodyMedium" || key.type == "bodyHeavy"){
            pc.equippedBody = null;
          } else if (key.type == "neck"){
            pc.equippedNeck = null;
          } else if (key.type == "back"){
            pc.equippedBack = null;
          } else if (key.type == "bow" || key.type == "staff" || key.type == "sword"){
            pc.equippedPrimary = null;
          } else {
            pc.equippedSecondary = null;
          }
          this.pcServices.setPc(pc);
          this.pcServices.updateStats();
          this.savePc(pc)
            .then(()=>{this.setState({pc: pc})});
          break method;
        case "pointbuy":
          pc = {...this.state.pc};
          pc.attributePoints > 0
            ? pc.attributePoints -= 1
            : pc.attributePoints = 0;
          key: switch(key){ 
            case "con":
              pc.constitution += 1;
              break key;
            case "str":
              pc.strength += 1;
              break key;
            case "dex":
              pc.dexterity += 1;
              break key;
            case "int":
              pc.intelligence += 1;
              break key;
            default:
              break key;
          }
          this.pcServices.setPc(pc);
          this.pcServices.updateStats();
          this.savePc(pc)
            .then(()=>{this.setState({pc: pc})});
          break method;
      }
    }

}

export default App
