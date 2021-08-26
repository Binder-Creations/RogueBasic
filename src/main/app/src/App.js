import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";

function App() {
  let character_id = document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*\=\s*([^;]*).*$)|^.*$/, "$1");
  if (!character_id){
    return (
      <Router>
        <Route path='/' component={() => { 
          window.location.href = '/login'; 
          return null;
        }}/>
      </Router>
    );
  } else {
    return (
      <div>
        <p>
          "{character_id}"
        </p>
      </div>

    )
  }

}

export default App;
