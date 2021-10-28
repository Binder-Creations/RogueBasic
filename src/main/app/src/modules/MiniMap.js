import React from "react";

class Minimap extends React.Component {

  constructor(props){
    super(props);

    this.rooms = [];
    this.connectors = [];
    this.alreadyConnected = [];
    this.components = [];
    this.underlay = <img className="minimap-underlay" src={this.props.props.images.mapUnderlay} alt="Minimap"/>;
    this.overlay = <img className="minimap-overlay" src={this.props.props.images.mapOverlay} alt="Minimap"/>

    this.addComponents = this.addComponents.bind(this);
    this.addConnector = this.addConnector.bind(this);
    this.addArrows = this.addArrows.bind(this);
  }

  render(){
    if(!this.props.dungeon){
      return(<></>)
    }
    if(!this.props.dungeon.floors){
      return(<></>)
    }
    if(this.props.props.pc.location != "Dungeon"){
      return(<></>)
    }

    return this.addComponents()
  }

  addComponents(){
    this.rooms = [];
    this.components = [this.underlay];
    this.connectors = [];
    this.alreadyConnected = [];
    let src;
    console.log(this.props.dungeon)

    this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms.forEach(room => {
      if(room.cleared || room.id === this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom].id){
        if(room.id === this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom].id){
          if(room.stairsPrevious){
            src = this.props.props.images.minimapCurrentStairsPrevious
          }else if(room.stairsNext){
            src = this.props.props.images.minimapCurrentStairsNext
          }else{
            src = this.props.props.images.minimapCurrent
          }
        }else if (room.cleared){
          if(room.stairsPrevious){
            src = this.props.props.images.minimapClearedStairsPrevious
          }else if(room.stairsNext){
            src = this.props.props.images.minimapClearedStairsNext
          }else{
            src = this.props.props.images.minimapCleared
          }
        }

        let style = {left: (3536+(room.xCoord-1)*68)/42+"%", top: (1338+(6-room.yCoord)*68)/20+"%"}
        this.rooms.push(<img src={src} className="minimap-room"  style={style}/>);
        
        if(room.northRoomId){
          this.addConnector(room.xCoord, room.yCoord, "Vertical")
        }
        if(room.southRoomId){
          this.addConnector(room.xCoord, room.yCoord-1, "Vertical")
        }
        if(room.eastRoomId){
          this.addConnector(room.xCoord, room.yCoord, "Horizontal")
        }
        if(room.westRoomId){
          this.addConnector(room.xCoord-1, room.yCoord, "Horizontal")
        }
      }
    });

    this.components = this.components.concat(this.rooms).concat(this.connectors);
    this.components.push(this.overlay);
    this.addArrows(this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom]);

    return this.components;
  }

  addConnector(xCoord, yCoord, orientation){
    let connector = xCoord.toString() + yCoord.toString() + orientation[0];
    
    if(!this.alreadyConnected.includes(connector)){
      this.alreadyConnected.push(connector);
      let style;

      if(orientation == "Vertical"){
        style = {left: (3562+(xCoord-1)*68)/42+"%", top: (1388+(5-yCoord)*68)/20+"%"}
      } else {
        style = {left: (3586+(xCoord-1)*68)/42+"%", top: (1363+(6-yCoord)*68)/20+"%"}
      }

      this.connectors.push(<img src={this.props.props.images["minimapConnector"+orientation]} className={"minimap-connector-" + orientation.toLowerCase()} style={style}/>)
    } 
  }

  addArrows(room){
    if(room.northRoomId){
      this.components.push(
        <img src={this.props.props.images.minimapNorth} className="minimap-north hover-saturate" onClick={()=>this.props.props.appState("move-room", room.northRoomId)}/>
      )
    }
    if(room.southRoomId){
      this.components.push(
        <img src={this.props.props.images.minimapSouth} className="minimap-south hover-saturate" onClick={()=>this.props.props.appState("move-room", room.southRoomId)}/>
      )
    }
    if(room.eastRoomId){
      this.components.push(
        <img src={this.props.props.images.minimapEast} className="minimap-east hover-saturate" onClick={()=>this.props.props.appState("move-room", room.eastRoomId)}/>
      )
    }
    if(room.westRoomId){
      this.components.push(
        <img src={this.props.props.images.minimapWest} className="minimap-west hover-saturate" onClick={()=>this.props.props.appState("move-room", room.westRoomId)}/>
      )
    }
  }
}

export default Minimap;