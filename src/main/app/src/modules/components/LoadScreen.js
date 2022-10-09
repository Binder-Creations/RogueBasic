import React from "react";
import c from "../../data/CommonProperties";

class LoadScreen extends React.Component {
  
  constructor(props){
    super(props);
    this.barPercent = this.barPercent.bind(this);
  }

  render(){
    return(
      <div className="app-container v-h-centered" style={{backgroundImage: "url("+c.images.common.loadingBackground+")", height: c.appHeight + "px", width: c.appWidth + "px"}}>
        <div className="loading-bar v-h-centered">
          <img className="background" src={c.images.common.barBackgroundLoading} alt="background"/>
          <img className="bar" src={c.images.common.barLoading} style={{width: this.barPercent() + "%"}} alt="load bar"/>
          <img className="background v-h-centered" src={c.images.common.barFrame} alt="load bar"/>
          <p className="v-h-centered" style={{fontSize: "100%"}}>
            {
              this.props.s.loadingShop
                ? this.props.s.loadingDungeons 
                  ? this.props.s.loadedImages >= c.imageMax
                    ? "Loading..."
                    : "Loading Images (" + this.props.s.loadedImages + "/" + c.imageMax + ")..."
                  : "Populating Dungeon Options..."    
                : "Generating Shop Inventory..."
            }
          </p>
        </div>
      </div>
    )
  }

  barPercent(){
    let percent = 0;
    percent += (this.props.s.loadedImages/c.imageMax)*30;
    if(this.props.s.loadingDungeons){
      percent += 40;
    }
    if(this.props.s.loadingShop){
      percent += 30;
    }
    return percent;
  }
}

export default LoadScreen;