import React from "react";
import Combat from "./Combat.js"

class Dungeon extends React.Component {
  constructor(props){
    super(props);
    this.currentRoom = null;
    this.pushMonstersByPosition = this.pushMonstersByPosition.bind(this);
  }

  render(){
    let components = [];
    this.currentRoom = this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom];

    if(((this.currentRoom.stairsPrevious || this.currentRoom.stairsNext) && !(this.currentRoom.monsters && this.currentRoom.monsters.length > 0))){
      components.push(<img src={this.props.props.images["dungeon"+this.props.dungeon.theme+"Stairs"]} className="dungeon-stairs hover-saturate" alt="Stairs" onClick={() => this.props.props.appState("stairs", this.currentRoom.stairsPrevious)}/>);
    }
    if(this.currentRoom.monsters && this.currentRoom.monsters.length > 0){
      components.push(<img src={this.props.props.images["arena"+this.props.dungeon.theme]} className="dungeon-arena" alt="Arena"/>);
      this.pushMonstersByPosition(components, 'b');
      this.pushMonstersByPosition(components, 'f');
      components.push(<Combat props={this.props.props}/>)
    }

    return components;
  }

  pushMonstersByPosition(components, char){
    this.currentRoom.monsters.forEach(monster =>{
      if(monster.position[0] == char){
        let healthStyle = {
          width: 19.512*(monster.currentHealth/monster.healthTotal) + "%"
        }
        let fontSize;
        if(monster.name.length <= 15){
          fontSize = "100%"
        } else if(monster.name.length <= 18){
          fontSize = "90%"
        } else if(monster.name.length <= 21){
          fontSize = "80%"
        } else if(monster.name.length <=24){
          fontSize = "70%"
        } else {
          fontSize = "60%"
        }
        let nameStyle = {
          fontSize: fontSize
        }
        components.push(<img src={this.props.props.monsters[monster.species][(monster.boss ? "boss" : monster.miniboss ? "miniboss" : monster.type) + monster.variant]} className={"monster "+monster.position} alt={monster.name}/>);
        components.push(<img src={this.props.props.images.barBackground} className={"monster-bar "+monster.position} alt="Health"/>)
        components.push(<img src={this.props.props.images.barHealth} className={"monster-bar "+monster.position} alt="Health" style={healthStyle}/>)
        components.push(<img src={this.props.props.images.barFrame} className={"monster-bar "+monster.position} alt="Health"/>)
        components.push(<div className={"monster-bar "+monster.position} title={monster.currentHealth + "/" + monster.healthTotal}><p className="nowrap v-h-centered" style={nameStyle}>{"lv." + monster.level + " " + monster.name}</p></div>)
      }
    });
  }

}

export default Dungeon;