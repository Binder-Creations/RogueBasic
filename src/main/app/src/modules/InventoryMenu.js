import React from "react";
import Inventory from "./Inventory";
import Equipped from "./Equipped";


class InventoryMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {update: 0};
    if(this.props.props.pc.characterClass === "Rogue"){
      this.silhouette = this.props.props.images.silhouetteRogue;
      this.silhouetteClass = "silhouette-rogue";
    } else if(this.props.props.pc.characterClass === "Wizard"){
      this.silhouette = this.props.props.images.silhouetteWizard;
      this.silhouetteClass = "silhouette-wizard";
    } else {
      this.silhouette = this.props.props.images.silhouetteWarrior;
      this.silhouetteClass = "silhouette-warrior";
    }
    this.scrollable = React.createRef();
    this.update = this.update.bind(this);
  }

  render(){
    return(
      <div key="menu" className="menu">
        <img className="background" src={this.props.props.images.inventoryMenu} alt="Inventory Screen"/>
        <img className={this.silhouetteClass} src={this.silhouette} alt={this.props.props.pc.characterClass}/>
        <input className="btn-close hover-saturate" type="image" src={this.props.props.images.buttonClose} alt="Close" onClick={() => this.props.props.appState("menu", "inventory")}/>
        <p className="gold-count">{this.props.props.pc.currency}</p>
        <Equipped props={this.props.props} slot="Head"/>
        <Equipped props={this.props.props} slot="Body"/>
        <Equipped props={this.props.props} slot="Back"/>
        <Equipped props={this.props.props} slot="Neck"/>
        <Equipped props={this.props.props} slot="Primary"/>
        <Equipped props={this.props.props} slot="Secondary"/>
        <div className="inventory-box-wrapper" ref={this.scrollable}>
          <div className="inventory-box">
            <Inventory props={this.props.props} update={this.state.update} type="inventory"/>
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