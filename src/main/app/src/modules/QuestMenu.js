import React from "react";
import Fade from "./Fade";
import ItemTooltip from "./ItemTooltip";

class LootMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
      let QuestItem = (props) => {
        let item = this.props.rewardItems[props.i];
        let itemProps = this.props.props.itemServices.getProps(item);
        return(
          <div className={"quest-item-"+props.i+" item-tooltip"} onClick={()=>{this.props.props.appState("quest", props.i)}}>
            <div className="absolute-fill hover-saturate">
              <img src={itemProps.background} className="absolute-fill" alt=""/>
              <img src={itemProps.image} className="item-image" alt="item"/>
              <img src={itemProps.frame} className="absolute-fill" alt="frame"/>
            </div>
            <ItemTooltip props={this.props.props} itemProps={itemProps} item={item} qMods={[0.45, 0.6, 0.5]} costMult={1}/>
          </div>
        );
      }
      return(
        <Fade in key='q' className="quest-menu">
          <img className="background" src={this.props.props.images.questMenu} alt="Quest Screen"/>
          <p className="quest-gold">{this.props.reward}</p>
          <p className="quest-exp">{Math.floor(this.props.reward*2.5)}</p>
          <QuestItem i={0}/>
          <QuestItem i={1}/>
          <QuestItem i={2}/>
        </Fade>
      )  
    }
}

export default LootMenu;