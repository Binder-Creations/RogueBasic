import React from "react";
import c from "../../data/CommonProperties";
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
    if (this.props.s.pc["equipped"+this.props.slot] && this.props.s.pc["equipped"+this.props.slot].id !== "00000000-0000-0000-0000-000000000000"){
      this.item = this.props.s.pc["equipped"+this.props.slot];
      this.itemProps = c.itemServices.getProps(this.item);
    } else {
      return <></>
    }

    return(
      <div className={"hover-saturate item-tooltip item-equipped equipped-" + this.props.slot.toLowerCase()} onClick={() => {this.unequip()}}>
        <img src={this.itemProps.background} className="absolute-fill" alt=""/>
        <img src={this.itemProps.image} className="item-image" alt="item"/>
        <img src={c.images.common.equippedFrame} className="absolute-fill" alt="frame"/>
        <ItemTooltip s={this.props.s} itemProps={this.itemProps} item={this.item} qMods={[0.45, 0.6, 0.2]} costMult={1}/>
      </div>
    );
  }

  unequip(){
    if (!this.props.s.combat)
      c.unequip(this.item)   
  }
}

export default Equipped