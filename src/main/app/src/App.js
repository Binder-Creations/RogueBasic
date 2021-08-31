import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {pc: {}, room: {}, character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1")};
    if(this.state.character_id){
      fetch('/pc/'+ this.state.character_id)
        .then(response => response.json())
        .then(data => this.setState({pc: data}))
      if(this.state.pc.location != "Town"){
        fetch('/room/'+this.state.pc.location)
          .then(response => response.json())
          .then(data => this.setState({room: data}))
      };
    };
  }

  render(){
    return this.state.character_id 
      ? this.state.pc.location == "Town"
        ? this.renderTown()
        : this.renderRoom()
      : this.routeToLogin()
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
        <div>
          <p>
            {this.state.pc.name}
          </p>
        </div>
      );
    }

    renderRoom(){
      return (
        <div>
          <p>
            {this.state.pc.name}
          </p>
        </div>
      );
    }

}

export default App
