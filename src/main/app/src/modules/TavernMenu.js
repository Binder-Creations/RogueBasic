import React from "react";
import DungeonPanel from "./DungeonPanel";


class TavernMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
    console.log(this.props.props.pc)
    let dungeon;
    let className;
    if(this.props.props.pc.dungeonBoard){
      if(!this.props.dungeon){
        dungeon = this.props.props.images.dungeonEmpty;
        className = "dungeon-portal-inactive"
      } else {
        switch(this.props.dungeon.theme){
          case "Arcane":
            dungeon = this.props.props.images.dungeonArcane;
            className = "dungeon-portal-active"
            break;
          case "Castle":
            dungeon = this.props.props.images.dungeonCastle;
            className = "dungeon-portal-active"
            break;
          case "Cave":
            dungeon = this.props.props.images.dungeonCave;
            className = "dungeon-portal-active"
            break;
        }
      }
          
      return(
        <div className="menu dungeon-menu">
          <img className="background" src={this.props.props.images.tavernMenu} alt="Tavern Menu"/>
          <img className={className} src={dungeon} alt="Dungeon Portal"/>
          <img className="dungeon-portal-frame" src={this.props.props.images.dungeonPortal} alt="Dungeon Portal"/>
          <DungeonPanel props={this.props.props} number="1"/>
          <DungeonPanel props={this.props.props} number="2"/>
          <DungeonPanel props={this.props.props} number="3"/>
        </div>
      );
    } else {
      return (<></>);
    }
  }
}

export default TavernMenu