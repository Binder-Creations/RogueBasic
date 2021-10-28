import React from "react";
import DungeonPanel from "./DungeonPanel";


class TavernMenu extends React.Component {

  constructor(props){
    super(props);
  }

  render(){
    let dungeon = this.props.props.images.dungeonEmpty;
    let className = "dungeon-portal-inactive";
    let onClick = "";
    if(this.props.dungeonBoard) {
      if(this.props.dungeon){
        className = "dungeon-portal-active hover-saturate";
        onClick = () => this.props.props.appState("to-dungeon")
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
        <div className="menu dungeon-menu">
          <img className="background" src={this.props.props.images.tavernMenu} alt="Tavern Menu"/>
          <img className={className} src={dungeon} alt="Dungeon Portal"  onClick={onClick}/>
          <img className="dungeon-portal-frame hover-saturate-secondary" src={this.props.props.images.dungeonPortal} alt="Dungeon Portal"/>
          <DungeonPanel props={this.props.props} dungeonBoard={this.props.dungeonBoard} number="1"/>
          <DungeonPanel props={this.props.props} dungeonBoard={this.props.dungeonBoard} number="2"/>
          <DungeonPanel props={this.props.props} dungeonBoard={this.props.dungeonBoard} number="3"/>
        </div>
      );
    } else {
      return (<></>);
    }
  }
}

export default TavernMenu