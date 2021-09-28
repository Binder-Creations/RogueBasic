import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import town from "./images/town.png";
import townIcon from "./images/town-icon.png";
import tavernExterior from "./images/tavern-exterior.png";
import tavern from "./images/tavern.png";
import inn from "./images/inn.png";
import innExterior from "./images/inn-exterior.png";
import shop from "./images/shop.png";
import shopExterior from "./images/shop-exterior.png";
import Ui from "./modules/Ui"
import PcServices from "./modules/PcServices";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: {name: null},
      scene: "Default",
      combat: false, 
      character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1"),
      width: window.innerWidth
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
    this.appState = this.appState.bind(this);
    this.updateWidth = this.updateWidth.bind(this);
  };

  render(){
    if (!this.state.character_id)
      return this.routeToLogin();
    if(!this.state.pc.name){
      return(<></>)
    }
    if(!this.listeningWidth){
      window.addEventListener("resize", this.updateWidth)
      this.listeningWidth = true;
    }
    const pcServices = new PcServices(this.state.pc)
    pcServices.addStats()
    if (this.state.pc.location == "Town"){
      if(this.state.scene=="Default"){
        return this.renderTown();
      } else if(this.state.scene=="Tavern"){
        return this.renderTavern();
      } else if(this.state.scene=="Inn"){
        return this.renderInn();
      } else if(this.state.scene=="Shop"){
        return this.renderShop();
      }
    }else{
      return this.renderRoom()
      }
    }
    routeToLogin(){
      return (
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/login'; 
            return null;
          }}/>
        </Router>
      );
    }
    renderTown(){
      return (
        <div className="app-container">
          <img className="background" src={town} alt="Town"/>
          <input className="tavern" type="image" src={tavernExterior} alt="Tavern" onClick={ () => { this.setState({scene:"Tavern"})} }/>
          <input className="inn" type="image" src={innExterior} alt="Inn" onClick={ () => { this.setState({scene:"Inn"})} }/>
          <input className="shop" type="image" src={shopExterior} alt="Shop" onClick={ () => { this.setState({scene:"Shop"})} }/>
          <Ui appState={this.appState} pc={this.state.pc} combat={this.state.combat} width={this.state.width}/>
        </div>
      );
    }
    renderTavern(){
      return (
        <div className="app-container">
          <img className="background" alt="Tavern" src={tavern}/>
          <button className="btn-home" onClick={ () => { this.setState({scene:"Default"})} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui appState={this.appState} pc={this.state.pc} combat={this.state.combat} width={this.state.width}/>
        </div>
      );
    }
    renderInn(){
      return (
        <div className="app-container">
          <img className="background" alt="Inn" src={inn}/>
          <button className="btn-home" onClick={ () => { this.setState({scene:"Default"})} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui appState={this.appState} pc={this.state.pc} combat={this.state.combat} width={this.state.width}/>
        </div>
      );
    }
    renderShop(){
      return (
        <div className="app-container">
          <img className="background" alt="Shop" src={shop}/>
          <button className="btn-home" onClick={ () => { this.setState({scene:"Default"})} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui appState={this.appState} pc={this.state.pc} combat={this.state.combat} width={this.state.width}/>
        </div>
      );
    }
    renderRoom(){
      return (
        <div className="app-container">
          <img className="background" src={town} alt="Town"/>
          <Ui appState={this.appState} pc={this.state.pc} combat={this.state.combat} width={this.state.width}/>
        </div>
      );
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
      this.setState({width: window.innerWidth});
    }
    appState(method, key, value){
      method: switch(method){
        case "pointbuy":
          let pc = {...this.state.pc};
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
