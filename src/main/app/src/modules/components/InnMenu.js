import React from "react";
import c from "../../data/CommonProperties";
import Fade from "../animations/Fade";
import AppServices from "../services/AppServices";

export default class InnMenu extends React.Component {

  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();

    this.state = {
      moon: false,
      moonIn:true,
      sun: false,
      sunIn:true,
      food: false,
      foodIn: true,
      rested: this.props.s.rested,
      eaten: this.props.s.eaten
    }
  }

  render(){
      if(this.props.s.rested > this.state.rested){
        this.setState({moon: true, rested: this.props.s.rested});
      }
      if(this.props.s.eaten > this.state.eaten){
        this.setState({food: true,  eaten: this.props.s.eaten})
      }
      let rest = this.props.s.pc.currency < 250
        ? <input className="rest gray-75" type="image" src={c.images.common.rest} alt="Rest"/>
        : <input className="rest hover-saturate" type="image" src={c.images.common.rest} alt="Rest" onClick={this.appServices.startRest}/>
      let eat = (this.props.s.pc.ate || this.props.s.pc.currency < 100)
        ? <input className="eat gray-75" type="image" src={c.images.common.eat} alt="Eat"/>
        : <input className="eat hover-saturate" type="image" src={c.images.common.eat} alt="Eat" onClick={this.appServices.eat}/>
      
      return(
        <>
          <div className="inn-menu">
            <img className="background" src={c.images.common.innMenu} alt="Inn Menu"/>
            {rest}
            {eat}
            <p>{this.props.s.pc.currency}</p>
          </div>
          {this.state.moon && (
            <Fade 
              in={this.state.moonIn} 
              onEntered={() => setTimeout(() =>this.setState({moonIn:false}), 700)}
              onExited={() =>this.setState({sun:true, moon: false})}
            >
              <img className="inn-float" src={c.images.common.moon} alt="moon"/>
            </Fade>
          )}
          {this.state.sun && (
            <Fade
              in={this.state.sunIn} 
              onEntered={() => setTimeout(() =>this.setState({sunIn: false}), 700)}            
              onExited={() =>this.setState({sun:false})}
            >
              <img className="inn-float" src={c.images.common.sun} alt="sun"/>
            </Fade>
          )}
          {this.state.food && (
            <Fade
              in={this.state.foodIn} 
              onEntered={() => setTimeout(() =>this.setState({foodIn: false}), 700)}
              onExited={() =>this.setState({food: false})}
            >
              <img className="inn-float" src={c.images.common.food} alt="food"/>
            </Fade>
          )}
        </>
      );  
  }
}