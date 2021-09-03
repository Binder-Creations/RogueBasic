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

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: {},
      scene: "Default", 
      character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1")
    };
    if(this.state.character_id){
      fetch('/pc/'+ this.state.character_id)
      .then(response => response.json())
        .then(data => this.setState({pc: data}))
        .then(this.setState(state => state.pc.location == "Town"
          ? {room: null}
          : {room: fetch('/room/'+ state.pc.location)
            .then(response => response.json())}))
        // .then(data => this.state.pc = JSON.parse(data))
        // .then(this.state.room = this.state.pc.location == "Town"
        //   ? null
        //   : JSON.parse(fetch('/room/'+ this.state.pc.location)
        //     .then(response => response.json())))
    };
    this.changeScene = this.changeScene.bind(this);
    this.render = this.render.bind(this);
  };

  render(){
    if (!this.state.character_id)
      return this.routeToLogin();

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
          <input className="tavern" type="image" src={tavernExterior} alt="Tavern" onClick={ () => { this.changeScene("Tavern")} }/>
          <input className="inn" type="image" src={innExterior} alt="Inn" onClick={ () => { this.changeScene("Inn")} }/>
          <input className="shop" type="image" src={shopExterior} alt="Shop" onClick={ () => { this.changeScene("Shop")} }/>
          <Ui pc={this.state.pc}/>
        </div>
      );
    }
    renderTavern(){
      return (
        <div className="app-container">
          <img className="background" alt="Tavern" src={tavern}/>
          <button className="btn-home" onClick={ () => { this.changeScene("Default")} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui pc={this.state.pc}/>
        </div>
      );
    }
    renderInn(){
      return (
        <div className="app-container">
          <img className="background" alt="Inn" src={inn}/>
          <button className="btn-home" onClick={ () => { this.changeScene("Default")} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui pc={this.state.pc}/>
        </div>
      );
    }
    renderShop(){
      return (
        <div className="app-container">
          <img className="background" alt="Shop" src={shop}/>
          <button className="btn-home" onClick={ () => { this.changeScene("Default")} }>
            <img src={townIcon} alt="Town"/>
          </button>
          <Ui pc={this.state.pc}/>
        </div>
      );
    }
    renderRoom(){
      return (
        <div className="app-container">
          <img className="background" src={town} alt="Town"/>
          <Ui pc={this.state.pc}/>
        </div>
      );
    }
    changeScene(newScene){
      this.setState({scene:newScene}, ()=>{console.log(this.state.scene)});
    }

}

export default App
