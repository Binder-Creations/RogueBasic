import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import Ui from "./modules/Ui"
import PcServices from "./modules/PcServices";
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
    this.images = images;
    this.items = items;
    this.pcServices = "";
    this.appState = this.appState.bind(this);
    this.updateWidth = this.updateWidth.bind(this);
  };

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
    
    this.props.props = {
        images: this.images, 
        items: this.items, 
        appState: this.appState, 
        pc: this.state.pc, 
        combat: this.state.combat, 
        widthChange: this.state.widthChange
      }
      
    if(!this.state.pc.name){
      return(<></>)
    }
    if(this.pcServices == ""){
      this.pcServices = new PcServices(this.state.pc)
      
    }
    this.pcServices.updateStats()
    if(!this.listenersAdded){
      window.addEventListener("resize", this.updateWidth)
      this.listenersAdded = true;
    }
    let backgroundSrc;
    let elements;
    let InnerElements = () => {
      return(elements);
    }

    let TownButton = () => {
      return(
      <button className="btn-home" onClick={ () => { this.setState({scene:"Default"})} }>
        <img src={this.images.townIcon} alt="Town"/>
      </button>
      );
    }
    if (this.state.pc.location == "Town"){
      if(this.state.scene=="Default"){
        backgroundSrc = this.images.town
        elements = <>
          <input className="tavern" type="image" src={this.images.tavernExterior} alt="Tavern" onClick={ () => { this.setState({scene:"Tavern"})} }/>
          <input className="inn" type="image" src={this.images.innExterior} alt="Inn" onClick={ () => { this.setState({scene:"Inn"})} }/>
          <input className="shop" type="image" src={this.images.shopExterior} alt="Shop" onClick={ () => { this.setState({scene:"Shop"})} }/>
        </>
      } else if(this.state.scene=="Tavern"){
        backgroundSrc = this.images.tavern
        elements = <>
          <TownButton/>
        </>
      } else if(this.state.scene=="Inn"){
        backgroundSrc = this.images.inn
        elements = <>
          <TownButton/>
        </>
      } else if(this.state.scene=="Shop"){
        backgroundSrc = this.images.shop
        elements = <>
          <TownButton/>
        </>
      }
    }else{
      backgroundSrc = this.images.town
      elements = <></>
      }
    return(
    <div className="app-container">
      <img className="background" src={backgroundSrc} alt="Background"/>
      <InnerElements/>
      <Ui props={this.props.props}/>
    </div>
    );
    }

    componentDidMount(){
      Object.keys(this.images).forEach((image) => {
        new Image().src = this.images[image];
      });
      Object.keys(this.items).forEach((type) => {
        Object.keys(this.items[type]).forEach((item) => {
          new Image().src = this.items[type][item];
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
    updateWidth(){
      this.setState({widthChange: ++this.state.widthChange});
    }
    appState(method, key, value){
      let pc;
      method: switch(method){
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
              pc.currentHealth += 4;
              break key;
            case "str":
              pc.strength += 1;
              break key;
            case "dex":
              pc.dexterity += 1;
              break key;
            case "int":
              pc.intelligence += 1;
              pc.currentEnergy += 6;
              break key;
            default:
              break key;
          }
          this.savePc(pc)
            .then(()=>{this.setState({pc: pc})});
          break method;
      }
    }

}

export default App
