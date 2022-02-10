import React from "react";
import Fade from "../animations/Fade";

class InnMenu extends React.Component {

  constructor(props){
    super(props);

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
        ? <input className="rest gray-75" type="image" src={this.props.c.images.rest} alt="Rest"/>
        : <input className="rest hover-saturate" type="image" src={this.props.c.images.rest} alt="Rest" onClick={this.props.c.startRest}/>
      let eat = (this.props.s.pc.ate || this.props.s.pc.currency < 100)
        ? <input className="eat gray-75" type="image" src={this.props.c.images.eat} alt="Eat"/>
        : <input className="eat hover-saturate" type="image" src={this.props.c.images.eat} alt="Eat" onClick={this.props.c.eat}/>
      
      return(
        <>
          <div className="inn-menu">
            <img className="background" src={this.props.c.images.innMenu} alt="Inn Menu"/>
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
              <img className="inn-float" src={this.props.c.images.moon} alt="moon"/>
            </Fade>
          )}
          {this.state.sun && (
            <Fade
              in={this.state.sunIn} 
              onEntered={() => setTimeout(() =>this.setState({sunIn: false}), 700)}            
              onExited={() =>this.setState({sun:false})}
            >
              <img className="inn-float" src={this.props.c.images.sun} alt="sun"/>
            </Fade>
          )}
          {this.state.food && (
            <Fade
              in={this.state.foodIn} 
              onEntered={() => setTimeout(() =>this.setState({foodIn: false}), 700)}
              onExited={() =>this.setState({food: false})}
            >
              <img className="inn-float" src={this.props.c.images.food} alt="food"/>
            </Fade>
          )}
        </>
      );  
  }
}

export default InnMenu