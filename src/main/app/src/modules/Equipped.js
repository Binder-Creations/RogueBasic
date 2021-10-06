import React from "react";
import ItemTooltip from "./ItemTooltip";

class Equipped extends React.Component {
  
  constructor(props){
    super(props);
    this.item = "";
    this.background = "";

    this.unequip = this.unequip.bind(this);
  }

  render(){
    if (this.props.props.pc["equipped"+this.props.slot]){
      this.item = this.props.props.pc["equipped"+this.props.slot]
      this.background = this.props.props.images["background"+this.item.rarity]
    } else {
      return <></>
    }

    return(
      <div className={"item-tooltip item-equipped equipped-" + this.props.slot.toLowerCase()} onClick={() => {this.unequip()}}>
        <img src={this.background} className="absolute-fill"/>
        <img src={this.props.props.items[this.item.type]["i"+this.item.image]} className="item-image"/>
        <img src={this.props.props.images.equippedFrame} className="absolute-fill"/>
        <ItemTooltip props={this.props.props} item={this.item} leftMod={0.2}/>
      </div>
    );
  }

  unequip(){
    if (!this.props.props.combat)
      this.props.props.appState("unequip", this.item, 0)   
  }
}

export default Equipped