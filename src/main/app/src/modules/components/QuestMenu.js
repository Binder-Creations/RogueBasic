import React from "react";
import c from "../../data/CommonProperties";
import ItemTooltip from "./ItemTooltip";

class QuestMenu extends React.Component {
  constructor(props){
    super(props);
    this.QuestItem = (i) =>{
      let item = this.props.s.dungeon.rewardSet[i];
      let itemProps = c.itemServices.getProps(item);
      return(
        <div className={"quest-item-"+i+" item-tooltip"} onClick={()=>{c.quest(i)}}>
          <div className="absolute-fill hover-saturate nopointer">
            <img src={itemProps.background} className="absolute-fill" alt=""/>
            <img src={itemProps.image} className="item-image" alt="item"/>
            <img src={itemProps.frame} className="absolute-fill" alt="frame"/>
          </div>
          <ItemTooltip s={this.props.s} itemProps={itemProps} item={item} qMods={[0.45, 0.6, 0.5]} costMult={1}/>
        </div>
      );
    }
  }

  render(){
      return(
        <div className="quest-menu">
          <img className="background" src={c.images.common.questMenu} alt="Quest Screen"/>
          <p className="quest-gold">{this.props.s.dungeon.reward}</p>
          <p className="quest-exp">{Math.floor(this.props.s.dungeon.reward*2.5)}</p>
          {this.QuestItem(0)}
          {this.QuestItem(1)}
          {this.QuestItem(2)}
        </div>
      )  
    }
}

export default QuestMenu;