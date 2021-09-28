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
    if(this.props.pc.characterClass == "Rogue"){
      this.silhouette = silhouetteRogue;
      this.silhouetteClass = "silhouette-rogue";
    } else if(this.props.pc.characterClass == "Wizard"){
      this.silhouette = silhouetteWizard;
      this.silhouetteClass = "silhouette-wizard";
    } else {
      this.silhouette = silhouetteWarrior;
      this.silhouetteClass = "silhouette-warrior";
    }
  }

  render(){
    if(this.props.on){
      return(
        <div className="menu">
          <img className="background" src={inventoryMenu} alt="Inventory Screen"/>
          <img className={this.silhouetteClass} src={this.silhouette} alt={this.props.pc.characterClass}/>
          <input className="btn-close" type="image" src={buttonClose} alt="Close" onClick={this.props.toggle}
            onMouseEnter={e => (e.currentTarget.src = buttonCloseHover)}
            onMouseLeave={e => (e.currentTarget.src = buttonClose)}
          />
          <p className="gold-count">{this.props.pc.currency}</p>
          <Equipped appState={this.props.appState} pc={this.props.pc} combat={this.props.combat} width={this.props.width}/>
          <div className="inventory-box-wrapper">
            <div className="inventory-box">
              <Inventory appState={this.props.appState} pc={this.props.pc} combat={this.props.combat} width={this.props.width}/>
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
}

export default InventoryMenu