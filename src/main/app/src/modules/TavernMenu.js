import React from "react";
import DungeonPanel from "./DungeonPanel";


class TavernMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
      let dungeon;
      if(!this.props.dungeon){
        dungeon = this.props.props.images.dungeonEmpty;
      } else {
        switch(this.props.dungeon.theme){
          case "Arcane":
            dungeon = this.props.props.images.dungeonArcane;
            break;
          case "Castle":
            dungeon = this.props.props.images.dungeonCastle;
            break;
          case "Cave":
            dungeon = this.props.props.images.dungeonCave;
            break;
        }
      }
         
      return(
        <div className="tavern-menu">
          <img className="background" src={this.props.props.images.tavernMenu} alt="Tavern Menu"/>
          <img className="dungeon" src={dungeon} alt="Dungeon Portal"/>
          <img className="dungeon-portal" src={this.props.props.images.dungeonPortal} alt="Dungeon Portal"/>
          <DungeonPanel props={this.props.props} number="1"/>
          <DungeonPanel props={this.props.props} number="2"/>
          <DungeonPanel props={this.props.props} number="3"/>
        </div>
      );  
  }
}

export default TavernMenu