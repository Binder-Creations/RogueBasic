import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {pc: {}, room: {}, character_id: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1")};
    if(this.state.character_id){
      fetch('/pc/'+ this.state.character_id)
        .then(response => response.json())
        // .then(data => {this.setState({pc: data}); let dataObj = JSON.parse(data); return dataObj;})
        // .then(dataObj => dataObj.location == "Town"
        //   ? this.setState({room:null}) 
        //   : fetch('/room/'+ dataObj.location)
        //   .then(response => response.json())
        //   .then(data => this.setState({room: data})))
        .then(data => this.setState({pc: data}))
        .then(this.setState(state => state.pc.location == "Town"
          ? {room: null}
          : {room: fetch('/room/'+ state.pc.location)
            .then(response => response.json())}))
    };
  };

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
