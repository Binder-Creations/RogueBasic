import React from "react";
import inventoryMenu from "../images/inventory-menu.png";
import buttonClose from "../images/button-close.png";
import buttonCloseHover from "../images/button-close-hover.png";
import silhouetteWarrior from "../images/silhouette-warrior.png";
import silhouetteRogue from "../images/silhouette-rogue.png";
import silhouetteWizard from "../images/silhouette-wizard.png";
import Inventory from "./Inventory";
import Equipped from "./Equipped";


class InventoryMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {update: 0};
    if(this.props.props.pc.characterClass == "Rogue"){
      this.silhouette = silhouetteRogue;
      this.silhouetteClass = "silhouette-rogue";
    } else if(this.props.props.pc.characterClass == "Wizard"){
      this.silhouette = silhouetteWizard;
      this.silhouetteClass = "silhouette-wizard";
    } else {
      this.silhouette = silhouetteWarrior;
      this.silhouetteClass = "silhouette-warrior";
    }
    this.scrollable = React.createRef();
    this.update = this.update.bind(this);
  }

  render(){
    if(this.props.on){
      return(
        <div className="menu">
          <img className="background" src={inventoryMenu} alt="Inventory Screen"/>
          <img className={this.silhouetteClass} src={this.silhouette} alt={this.props.props.pc.characterClass}/>
          <input className="btn-close" type="image" src={buttonClose} alt="Close" onClick={this.props.toggle}
            onMouseEnter={e => (e.currentTarget.src = buttonCloseHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonClose)}
          />
          <p className="gold-count">{this.props.props.pc.currency}</p>
          <Equipped slot="head" item={this.props.props.pc.equippedHead} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <Equipped slot="body" item={this.props.props.pc.equippedBody} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <Equipped slot="back" item={this.props.props.pc.equippedBack} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <Equipped slot="neck" item={this.props.props.pc.equippedNeck} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <Equipped slot="primary" item={this.props.props.pc.equippedPrimary} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <Equipped slot="secondary" item={this.props.props.pc.equippedSecondary} appState={this.props.props.appState} combat={this.props.props.combat} widthChange={this.props.props.widthChange}/>
          <div className="inventory-box-wrapper" ref={this.scrollable}>
            <div className="inventory-box">
              <Inventory props={this.props.props} update={this.state.update}/>
            </div>
          </div>
        </div>
      )  
    } else {
      return(
        <></>
      )
    }
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
    this.setState({update: ++this.state.update});
  }
}

export default InventoryMenu