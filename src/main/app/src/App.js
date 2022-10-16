import React from "react";
import defaultState from "./data/DefaultState";
import AppServices from "./modules/services/AppServices";
import ComponentFactory from "./modules/services/ComponentFactory";

export default class App extends React.Component {
  constructor(props) {
    super(props);
    defaultState.page = AppServices.startingPage(defaultState);
    this.state = {...defaultState};
    this.appServices = AppServices.getInstance(this);
    this.componentFactory = ComponentFactory.getInstance();
  }

  render(){
    this.appServices.sizeWindow();
    return(this.componentFactory.createComponent(this.state.page, this.state));
  }

  componentDidUpdate(){
    this.appServices.updateTimeouts();
  }
}

