import c from "../../data/CommonProperties";
import AppServices from "./AppServices";
import Ui from "../components/Ui";
import {Route, BrowserRouter as Router} from "react-router-dom";

export const buildComponent = (componentName, state, innerElements, outerElements) => {
  let appServices = AppServices.getInstance();
  
  switch(componentName){
    case "helpButton":
      return(
        <button className="btn-help" onClick={appServices.help}>
          <img src={c.images.common.helpIcon} alt="Help"/>
        </button>
      );
    case "routeHome":
      return(
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/home'; 
            return null;
          }}/>
        </Router>
      );
    case "routeLogin":
      return(
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/login'; 
            return null;
          }}/>
        </Router>
      );
    case "exteriorBuildings":
      return(
        <>
          <input className="tavern" type="image" src={c.images.common.tavernExterior} alt="Tavern" onClick={ () => { appServices.setState({scene:"Tavern"})} }/>
          <input className="inn" type="image" src={c.images.common.innExterior} alt="Inn" onClick={ () => { appServices.setState({scene:"Inn"})} }/>
          <input className="shop" type="image" src={c.images.common.shopExterior} alt="Shop" onClick={ () => { appServices.setState({scene:"Shop"})} }/>
        </>
      );
    case "tempScreen":
      return(
        <div className="app-container v-h-centered" 
          style={{backgroundImage: "url("+c.images.common.loadingBackground+")", 
          height: c.appHeight + "px", width: c.appWidth + "px"}}>
        </div>
      );
    case "primaryView":
      return(
        <div className="app-container v-h-centered" style={{backgroundImage: "url("+getBackgroundSrc(state)+")", height: c.appHeight + "px", width: c.appWidth + "px"}}>
          {innerElements}
          <Ui s={state}/>
          {outerElements}
        </div>
      )
    default:
      throw new TypeError("Component name not found");
  }
}

function getBackgroundSrc(state){
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