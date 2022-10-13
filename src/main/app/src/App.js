import React from "react";

import defaultState from "./data/DefaultState";
import c from "./data/CommonProperties";
import AppServices from "./modules/services/AppServices";
import Ui from "./modules/components/Ui";
import LoadScreen from "./modules/components/LoadScreen";
import ImageServices from "./modules/services/ImageServices";
import { buildComponent } from "./modules/services/ComponentFactory";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = defaultState;
    this.appServices = AppServices.getInstance(this);
    this.returningUser = document.cookie.replace(/(?:(?:^|.*;\s*)returning_user\s*=\s*([^;]*).*$)|^.*$/, "$1");
  }

  render(){
    if(this.state.routeHome){
      return buildComponent("routeHome");
    }

    if(this.showTempScreen){
      return buildComponent("tempScreen");
    }

    if (!this.state.characterId || !this.state.playerId){
      if(!this.returningUser){
        this.appServices.assignTempAccount();   
      } else {
        if(this.state.playerId){
          return buildComponent("routeHome");
        }
        return buildComponent("routeLogin");
      }
    }

    if(this.state.scene==="Home"){
        return buildComponent("routeHome");
    }

    this.appServices.sizeWindow();

    if(!this.initialized){
      this.appServices.initialize(); 
    }

    if(!this.imagesInitialized && this.state.startImageInitialization){
      this.appServices.initializeImages();
    }

    if(this.toRest){
      this.appServices.rest()
    }

    if(this.state.loading){
      if(this.state.loadedImages >= c.imageMax && this.state.loadingDungeons && this.state.loadingShop) {
        this.appServices.setState({loading: false, loadingDungeons: false, loadingShop: false})
      } else {
        return <LoadScreen s={this.state}/>;
      }
    }

    this.appServices.checkCombat();
    this.appServices.setElements();
    
    return(buildComponent("primaryView", this.state, this.innerElements, this.outerElements));
  }

  componentDidUpdate(){
    if(this.state.loading){
      setTimeout(() => {
        this.appServices.setState({loadedImages: ImageServices.getInstance().loadedImages})
      }, 33);
    }
    if(this.state.generatingDungeon){
      setTimeout(() => {
        if(this.state.generatingDungeon){
          this.appServices.setState({barPercent: (this.state.barPercent && this.state.barPercent < 100 ) ? this.state.barPercent + 20 : 20})
        }
      }, 2000);
    }
  }
}

export default App;


