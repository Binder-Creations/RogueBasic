import React from "react";

class Dungeon extends React.Component {
  constructor(props){
    super(props);
    this.currentRoom = null;

    this.pushMonstersByPosition = this.pushMonstersByPosition.bind(this);
  }

  render(){
    console.log(this.props.dungeon)
    let components = [];
    this.currentRoom = this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom];
      if(((this.currentRoom.stairsPrevious || this.currentRoom.stairsNext) && !this.currentRoom.monsters)){
        components.push(<img src={this.props.props.images["dungeon"+this.props.dungeon.theme+"Stairs"]} className="dungeon-stairs hover-saturate" alt="Stairs" onClick={() => this.props.props.appState("stairs", this.currentRoom.stairsPrevious)}/>);
      }
      if(this.currentRoom.monsters){
        components.push(<img src={this.props.props.images["arena"+this.props.dungeon.theme]} className="dungeon-arena" alt="Arena"/>);
        this.pushMonstersByPosition(components, 'b');
        this.pushMonstersByPosition(components, 'f');
      }

    return components;
  }

  pushMonstersByPosition(components, char){
    this.currentRoom.monsters.forEach(monster =>{
      if(monster.position[0] == char){
        let healthStyle = {
          width: 20*(monster.currentHealth/monster.healthTotal) + "%"
        }
        let nameStyle = {
          fontSize: monster.name.length > 25 ? 80 + "%" : 50 + "%"
        }
        components.push(<img src={this.props.props.monsters[monster.species][(monster.boss ? "boss" : monster.miniboss ? "miniboss" : monster.type) + monster.variant]} className={"monster "+monster.position} alt={monster.name}/>);
        components.push(<img src={this.props.props.images.barBackground} className={"monster-bar "+monster.position} title={monster.curentHealth + "/" + monster.healthTotal} alt="Health"/>)
        components.push(<img src={this.props.props.images.barHealth} className={"monster-bar "+monster.position} title={monster.curentHealth + "/" + monster.healthTotal} alt="Health" style={healthStyle}/>)
        components.push(<img src={this.props.props.images.barFrame} className={"monster-bar "+monster.position} title={monster.curentHealth + "/" + monster.healthTotal} alt="Health"/>)
        components.push(<p className={"monster-bar "+monster.position} style={nameStyle}>{"lv." + monster.level + " " + monster.name}</p>)
      }
    });
  }

}

export default Dungeon;