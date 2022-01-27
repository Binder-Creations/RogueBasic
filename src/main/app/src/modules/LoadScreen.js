import React from "react";

class LoadScreen extends React.Component {
  
  constructor(props){
    super(props);
    this.barPercent = this.barPercent.bind(this);
  }

  render(){
    return(
      <div className="app-container v-h-centered" style={{backgroundImage: "url("+this.props.props.images.loadingBackground+")", height: this.props.props.appHeight + "px", width: this.props.props.appWidth + "px"}}>
        <div className="loading-bar v-h-centered">
          <img className="background" src={this.props.props.images.barBackgroundLoading}/>
          <img className="bar" src={this.props.props.images.barLoading} style={{width: this.barPercent() + "%"}}/>
          <img className="background v-h-centered" src={this.props.props.images.barFrame}/>
          <p className="v-h-centered">
            {
              this.props.imageCount > 0 && this.props.imageCount < this.props.imageMax
                ? "Loading Images (" + this.props.imageCount + "/" + this.props.imageMax + ")..."
                : this.props.loadingDungeons
                  ? this.props.loadingShop
                    ? "Loading..."
                    : "Generating Shop Inventory..."
                  : "Generating Dungeon Shells..."
            }
          </p>
        </div>
      </div>
    )
  }

  barPercent(){
    let percent = 0;
    percent += (this.props.imageCount/this.props.imageMax)*10;
    if(this.props.loadingDungeons){
      percent += 50;
    }
    if(this.props.loadingShop){
      percent += 40;
    }
    return percent;
  }
}

export default LoadScreen;