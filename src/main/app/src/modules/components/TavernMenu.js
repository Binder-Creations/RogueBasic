import React from "react";
import c from "../../data/CommonProperties";
import DungeonPanel from "./DungeonPanel";


class TavernMenu extends React.Component {

  render(){
    let dungeon = c.images.common.dungeonEmpty;
    let className = "dungeon-portal-inactive";
    let onClick = "";
    if(this.props.s.dungeonBoard) {
      if(this.props.s.dungeon){
        className = "dungeon-portal-active hover-saturate";
        onClick = () => c.toDungeon()
        switch(this.props.s.dungeon.theme){
          case "Arcane":
            dungeon = c.images.common.dungeonArcane;
            break;
          case "Castle":
            dungeon = c.images.common.dungeonCastle;
            break;
          case "Cave":
            dungeon = c.images.common.dungeonCave;
            break;
          default:
            break;
        }
      }
          
      return(
        <div className="menu dungeon-menu">
          <img className="background" src={c.images.common.tavernMenu} alt="Tavern Menu"/>
          <img className={className} src={dungeon} alt="Dungeon Portal"  onClick={onClick}/>
          <img className="dungeon-portal-frame hover-saturate-secondary" src={c.images.common.dungeonPortal} alt="Dungeon Portal"/>
          <DungeonPanel s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="1"/>
          <DungeonPanel s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="2"/>
          <DungeonPanel s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="3"/>
        </div>
      );
    } else {
      return (<></>);
    }
  }
}

export default TavernMenu