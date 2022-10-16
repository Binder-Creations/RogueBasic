import c from "../../data/CommonProperties";
import AppServices from "./AppServices";
import Ui from "../components/Ui";
import LoadScreen from "../components/LoadScreen";
// import MainMenu from "../components/MainMenu";
// import Login from "../components/Login";
import Register from "../components/Register";
import CharacterCreation from "../components/CharacterCreation";
import SpendSouls from "../components/SpendSouls";
import {Route, BrowserRouter as Router} from "react-router-dom";


export default class ComponentFactory {
  static #instance;
  static #isInternalConstructing = false;

  constructor(){
    if(!ComponentFactory.#isInternalConstructing) throw TypeError("ComponentFactory is not constructable");
    
    this.appServices = AppServices.getInstance();
  }

  static getInstance(){
    if(this.#instance) return this.#instance;
    
    this.#isInternalConstructing = true;
    this.#instance = new ComponentFactory();
    this.#isInternalConstructing = false;
    return this.#instance;
  }

  createComponent (componentName, state) {    
    switch(componentName){
      case "firstUser":
        this.appServices.assignTempAccount();
      case "home":
        return(
          <Router>
            <Route path='/' component={() => { 
              window.location.href = '/home'; 
              return null;
            }}/>
          </Router>
        );
        //for future use when current html pages are react-integrated
        // return <MainMenu s={state}/>;
      case "login":
        return(
          <Router>
            <Route path='/' component={() => { 
              window.location.href = '/login'; 
              return null;
            }}/>
          </Router>
        );
        // return <Login s={state}/>;
      case "register":
        return <Register s={state}/>;
      case "characterCreation":
        return <CharacterCreation s={state}/>;
      case "spendSouls":
        return <SpendSouls s={state}/>;
      case "helpButton":
        return(
          <button className="btn-help" onClick={this.appServices.help}>
            <img src={c.images.common.helpIcon} alt="Help"/>
          </button>
        );
      case "exteriorBuildings":
        return(
          <>
            <input className="tavern" type="image" src={c.images.common.tavernExterior} alt="Tavern" onClick={ () => { this.appServices.setState({scene:"Tavern"})} }/>
            <input className="inn" type="image" src={c.images.common.innExterior} alt="Inn" onClick={ () => { this.appServices.setState({scene:"Inn"})} }/>
            <input className="shop" type="image" src={c.images.common.shopExterior} alt="Shop" onClick={ () => { this.appServices.setState({scene:"Shop"})} }/>
          </>
        );
      case "tempScreen":
        return(
          <div className="app-container v-h-centered" 
            style={{backgroundImage: "url("+c.images.common.loadingBackground+")", 
            height: c.appHeight + "px", width: c.appWidth + "px"}}>
          </div>
        );
      case "game":
        this.appServices.gameInitialization();

        if(this.appServices.isLoading())
          return <LoadScreen s={state}/>;

        this.appServices.gamePreRender();
        
        return(
          <div className="app-container v-h-centered" style={{backgroundImage: "url("+this.getBackgroundSrc(state)+")", height: c.appHeight + "px", width: c.appWidth + "px"}}>
            {this.appServices.innerElements}
            <Ui s={state}/>
            {this.appServices.outerElements}
          </div>
        )
      default:
        throw new TypeError("Component name not found");
    }
  }

  getBackgroundSrc(state){
    if(!state.pc) return c.images.common.loadingBackground;
    if(state.pc.location === "Town"){
      switch(state.scene){
        case "Tavern":
          return c.images.common.tavern;
        case "Inn":
          return c.images.common.inn;
        case "Shop":
          return c.images.common.tavern;
        default:
          return c.images.common.town;
      }
    } else if (state.pc.location === "Dungeon"){
      if(state.dungeon && state.dungeon.floors){
        return c.images.environment["background" + state.dungeon.floors[state.dungeon.currentFloor].rooms[state.dungeon.currentRoom].variant];
      } else {
        return c.images.common.tavern;
      }
    }
  }
}