import React from "react";
import DungeonPanel from "./DungeonPanel";


class TavernMenu extends React.Component {

  render(){
    let dungeon = this.props.c.images.dungeonEmpty;
    let className = "dungeon-portal-inactive";
    let onClick = "";
    if(this.props.s.dungeonBoard) {
      if(this.props.s.dungeon){
        className = "dungeon-portal-active hover-saturate";
        onClick = () => this.props.c.toDungeon()
        switch(this.props.s.dungeon.theme){
          case "Arcane":
            dungeon = this.props.c.images.dungeonArcane;
            break;
          case "Castle":
            dungeon = this.props.c.images.dungeonCastle;
            break;
          case "Cave":
            dungeon = this.props.c.images.dungeonCave;
            break;
          default:
            break;
        }
      }
          
      return(
        <div className="menu dungeon-menu">
          <img className="background" src={this.props.c.images.tavernMenu} alt="Tavern Menu"/>
          <img className={className} src={dungeon} alt="Dungeon Portal"  onClick={onClick}/>
          <img className="dungeon-portal-frame hover-saturate-secondary" src={this.props.c.images.dungeonPortal} alt="Dungeon Portal"/>
          <DungeonPanel c={this.props.c} s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="1"/>
          <DungeonPanel c={this.props.c} s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="2"/>
          <DungeonPanel c={this.props.c} s={this.props.s} dungeonBoard={this.props.s.dungeonBoard} number="3"/>
        </div>
      );
    } else {
      return (<></>);
    }
  }
}

export default TavernMenu