import React from "react";
import Inventory from "./Inventory";
import Equipped from "./Equipped";


class InventoryMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {update: 0};
    if(this.props.s.pc.characterClass === "Rogue"){
      this.silhouetteClass = "silhouette-rogue";
    } else if(this.props.s.pc.characterClass === "Wizard"){
      this.silhouetteClass = "silhouette-wizard";
    } else {
      this.silhouetteClass = "silhouette-warrior";
    }
    this.scrollable = React.createRef();
    this.update = this.update.bind(this);
  }

  render(){
    return(
      <div key="menu" className="menu">
        <img className="background" src={this.props.c.images.common.inventoryMenu} alt="Inventory Screen"/>
        <img className={this.silhouetteClass} src={this.props.c.images.class.silhouette} alt={this.props.s.pc.characterClass}/>
        <input className="btn-close hover-saturate" type="image" src={this.props.c.images.common.buttonClose} alt="Close" onClick={() => this.props.c.menu("inventory")}/>
        <p className="gold-count">{this.props.s.pc.currency}</p>
        <p className="soul-count">{this.props.s.pc.metacurrency}</p>
        <Equipped c={this.props.c} s={this.props.s} slot="Head"/>
        <Equipped c={this.props.c} s={this.props.s} slot="Body"/>
        <Equipped c={this.props.c} s={this.props.s} slot="Back"/>
        <Equipped c={this.props.c} s={this.props.s} slot="Neck"/>
        <Equipped c={this.props.c} s={this.props.s} slot="Primary"/>
        <Equipped c={this.props.c} s={this.props.s} slot="Secondary"/>
        <div className="inventory-box-wrapper" ref={this.scrollable}>
          <div className="inventory-box">
            <Inventory c={this.props.c} s={this.props.s} update={this.state.update} type="inventory"/>
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

export default InventoryMenu