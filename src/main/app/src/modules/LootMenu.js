import React from "react";
import Inventory from "./Inventory";

class LootMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {update: 0};
    this.scrollable = React.createRef();
    this.update = this.update.bind(this);
  }

  render(){
      return(
        <div className="loot-menu">
          <img className="background" src={this.props.props.images.lootMenu} alt="Loot Screen"/>
          <input className="btn-close-small hover-saturate" type="image" src={this.props.props.images.buttonClose} alt="Close" onClick={this.props.toggle}/>
          <div className="loot-box-wrapper" ref={this.scrollable}>
            <div className="loot-box">
              <Inventory props={this.props.props} update={this.state.update} type="loot" room={this.props.room}/>
            </div>
          </div>
        </div>
      )  
    }

  componentDidUpdate() {
    if(!this.listenersAdded){
      if(this.scrollable.current){
        this.scrollable.current.addEventListener("scroll", this.update)
        this.listenersAdded = true;
      }
    }
  }

  update() {
    this.setState({update: this.state.update + 1});
  }
}

export default LootMenu;