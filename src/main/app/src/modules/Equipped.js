import React from "react";
import ItemTooltip from "./ItemTooltip";

class Equipped extends React.Component {
  
  constructor(props){
    super(props);
    this.item = "";
    this.itemProps = "";
    this.background = "";

    this.unequip = this.unequip.bind(this);
  }

  render(){
    if (this.props.props.pc["equipped"+this.props.slot]){
      this.item = this.props.props.pc["equipped"+this.props.slot];
      this.itemProps = this.props.props.itemServices.getProps(this.item);
    } else {
      return <></>
    }

    return(
      <div className={"hover-saturate item-tooltip item-equipped equipped-" + this.props.slot.toLowerCase()} onClick={() => {this.unequip()}}>
        <img src={this.itemProps.background} className="absolute-fill" alt=""/>
        <img src={this.itemProps.image} className="item-image" alt="item"/>
        <img src={this.props.props.images.equippedFrame} className="absolute-fill" alt="frame"/>
        <ItemTooltip props={this.props.props} itemProps={this.itemProps} item={this.item} leftMod={0.2} costMult={1}/>
      </div>
    );
  }

  unequip(){
    if (!this.props.props.combat)
      this.props.props.appState("unequip", this.item)   
  }
}

export default Equipped