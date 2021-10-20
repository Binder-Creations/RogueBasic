import React from "react";


class InnMenu extends React.Component {

  constructor(props){
    super(props);

    this.rest = "";
    this.eat = "";
    this.restAvailable = <input className="rest hover-saturate" type="image" src={this.props.props.images.rest} alt="Rest" onClick={()=>{this.props.props.appState("rest")}}/>
    this.restUnavailable = <input className="rest gray-75" type="image" src={this.props.props.images.rest} alt="Rest"/>
    this.eatAvailable = <input className="eat hover-saturate" type="image" src={this.props.props.images.eat} alt="Eat" onClick={()=>{this.props.props.appState("eat")}}/>
    this.eatUnavailable = <input className="eat gray-75" type="image" src={this.props.props.images.eat} alt="Eat"/>

    this.Rest = () => {
      return(this.rest);
    }

    this.Eat = () => {
      return(this.eat);
    }
  }

  render(){
      this.rest = this.props.props.pc.currency < 250
        ? this.restUnavailable
        : this.restAvailable
      this.eat = (this.props.props.pc.ate || this.props.props.pc.currency < 100)
        ? this.eatUnavailable
        : this.eatAvailable
      
      return(
        <div className="inn-menu">
          <img className="background" src={this.props.props.images.innMenu} alt="Inn Menu"/>
          <this.Rest/>
          <this.Eat/>
          <p>{this.props.props.pc.currency}</p>
        </div>
      );  
  }
}

export default InnMenu