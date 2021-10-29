import React from "react";

class Minimap extends React.Component {

  constructor(props){
    super(props);

    this.rooms = [];
    this.connectors = [];
    this.alreadyConnected = [];
    this.components = [];
    this.Components = () => {return this.components}
    this.addComponents = this.addComponents.bind(this);
    this.addConnector = this.addConnector.bind(this);
    this.Arrows = this.Arrows.bind(this);
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
    this.components = [];
    this.connectors = [];
    this.alreadyConnected = [];
    let xLength = this.props.dungeon.floors[this.props.dungeon.currentFloor].xlength;
    let yLength = this.props.dungeon.floors[this.props.dungeon.currentFloor].ylength;
    let longest = xLength > yLength ? xLength : yLength;
    let roomsize = (100-2*(longest-1))/longest;
    let src;

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

        let style = {position: "absolute", width: roomsize + "%", height: roomsize + "%", left: (roomsize+2)*(room.xCoord-1)+"%", bottom: (roomsize+2)*(room.yCoord-1)+"%"}
        
        this.rooms.push(<img src={src} style={style}/>);
        
        if(room.northRoomId){
          this.addConnector(room.xCoord, room.yCoord+1, "Vertical", roomsize)
        }
        if(room.southRoomId){
          this.addConnector(room.xCoord, room.yCoord, "Vertical", roomsize)
        }
        if(room.eastRoomId){
          this.addConnector(room.xCoord+1, room.yCoord, "Horizontal", roomsize)
        }
        if(room.westRoomId){
          this.addConnector(room.xCoord, room.yCoord, "Horizontal", roomsize)
        }
      }
    });

    this.components = this.components.concat(this.rooms).concat(this.connectors);

    return (
      <>
        <img className="minimap-underlay" src={this.props.props.images.mapUnderlay} alt="Minimap"/>
        <div className="minimap-room-box">
          <this.Components/>
        </div>
        <img className="minimap-overlay" src={this.props.props.images.mapOverlay} alt="Minimap"/>
        <this.Arrows/>
      </>
    );
  }

  addConnector(xCoord, yCoord, orientation, roomsize){
    let connector = xCoord.toString() + yCoord.toString() + orientation[0];
    
    if(!this.alreadyConnected.includes(connector)){
      this.alreadyConnected.push(connector);
      let style;

        if(orientation == "Vertical"){
          style = {position: "absolute", width: (roomsize*7)/32 + "%", height: roomsize/2 + "%", left: ((roomsize+2)*(xCoord-1)+((roomsize/2)-(roomsize*7)/64))+"%", bottom: ((roomsize+2)*(yCoord-1))-(1+roomsize/4)+"%"}
        } else {
          style = {position: "absolute", width: roomsize/2 + "%", height: (roomsize*7)/32 + "%", left: ((roomsize+2)*(xCoord-1))-(1+roomsize/4)+"%", bottom: ((roomsize+2)*(yCoord-1)+((roomsize/2)-(roomsize*7)/64))+"%"}
        }

      this.connectors.push(<img src={this.props.props.images["minimapConnector"+orientation]} style={style}/>)
    } 
  }

  Arrows(){
    let arrows = []
    let room = this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom];

    if(room.northRoomId){
      arrows.push(
        <img src={this.props.props.images.minimapNorth} className="minimap-north hover-saturate" onClick={()=>this.props.props.appState("move-room", room.northRoomId)}/>
      )
    }
    if(room.southRoomId){
      arrows.push(
        <img src={this.props.props.images.minimapSouth} className="minimap-south hover-saturate" onClick={()=>this.props.props.appState("move-room", room.southRoomId)}/>
      )
    }
    if(room.eastRoomId){
      arrows.push(
        <img src={this.props.props.images.minimapEast} className="minimap-east hover-saturate" onClick={()=>this.props.props.appState("move-room", room.eastRoomId)}/>
      )
    }
    if(room.westRoomId){
      arrows.push(
        <img src={this.props.props.images.minimapWest} className="minimap-west hover-saturate" onClick={()=>this.props.props.appState("move-room", room.westRoomId)}/>
      )
    }
    return arrows;
  }
}

export default Minimap;