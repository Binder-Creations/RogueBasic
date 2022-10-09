import React from "react";
import c from "../../data/CommonProperties";

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
    if(!this.props.s.dungeon){
      return(<></>)
    }
    if(!this.props.s.dungeon.floors){
      return(<></>)
    }
    if(this.props.s.pc.location !== "Dungeon"){
      return(<></>)
    }

    return this.addComponents()
  }

  addComponents(){
    this.rooms = [];
    this.components = [];
    this.connectors = [];
    this.alreadyConnected = [];
    let xLength = this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].xlength;
    let yLength = this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].ylength;
    let longest = xLength > yLength ? xLength : yLength;
    let roomsize = (100-2*(longest-1))/longest;
    let src;

    this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].rooms.forEach(room => {
      if(room.cleared || room.id === this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].rooms[this.props.s.dungeon.currentRoom].id){
        if(room.id === this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].rooms[this.props.s.dungeon.currentRoom].id){
          if(room.stairsPrevious){
            src = c.images.common.minimapCurrentStairsPrevious
          }else if(room.stairsNext && (this.props.s.dungeon.currentFloor + 1 < this.props.s.dungeon.floorCount) && !this.props.s.combat){
            src = c.images.common.minimapCurrentStairsNext
          }else{
            src = c.images.common.minimapCurrent
          }
        }else if (room.cleared){
          if(room.stairsPrevious){
            src = c.images.common.minimapClearedStairsPrevious
          }else if(room.stairsNext && (this.props.s.dungeon.currentFloor + 1 < this.props.s.dungeon.floorCount)){
            src = c.images.common.minimapClearedStairsNext
          }else{
            src = c.images.common.minimapCleared
          }
        }

        let style = {position: "absolute", width: roomsize + "%", height: roomsize + "%", left: (roomsize+2)*(room.xCoord-1)+"%", bottom: (roomsize+2)*(room.yCoord-1)+"%"}
        
        this.rooms.push(<img src={src} style={style} alt="room"/>);
        
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
        <img className="minimap-underlay" src={c.images.common.mapUnderlay} alt="Minimap"/>
        <div className="minimap-room-box">
          <this.Components/>
        </div>
        <img className="minimap-overlay" src={c.images.common.mapOverlay} alt="Minimap"/>
        <this.Arrows/>
      </>
    );
  }

  addConnector(xCoord, yCoord, orientation, roomsize){
    let connector = xCoord.toString() + yCoord.toString() + orientation[0];
    
    if(!this.alreadyConnected.includes(connector)){
      this.alreadyConnected.push(connector);
      let style;

        if(orientation === "Vertical"){
          style = {position: "absolute", width: (roomsize*7)/32 + "%", height: roomsize/2 + "%", left: ((roomsize+2)*(xCoord-1)+((roomsize/2)-(roomsize*7)/64))+"%", bottom: ((roomsize+2)*(yCoord-1))-(1+roomsize/4)+"%"}
        } else {
          style = {position: "absolute", width: roomsize/2 + "%", height: (roomsize*7)/32 + "%", left: ((roomsize+2)*(xCoord-1))-(1+roomsize/4)+"%", bottom: ((roomsize+2)*(yCoord-1)+((roomsize/2)-(roomsize*7)/64))+"%"}
        }

      this.connectors.push(<img src={c.images.common["minimapConnector"+orientation]} style={style} alt=""/>)
    } 
  }

  Arrows(){
    let arrows = []
    let room = this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].rooms[this.props.s.dungeon.currentRoom];
    if(this.props.s.combat || c.isGameOver){
      arrows.push(<></>)
    } else {
      if(room.northRoomId){
        arrows.push(
          <img src={c.images.common.minimapNorth} className="minimap-north hover-saturate" onClick={()=>c.moveRoom(room.northRoomId)} alt="North"/>
        )
      }
      if(room.southRoomId){
        arrows.push(
          <img src={c.images.common.minimapNorth} className="minimap-south hover-saturate y-flip" onClick={()=>c.moveRoom(room.southRoomId)} alt="South"/>
        )
      }
      if(room.eastRoomId){
        arrows.push(
          <img src={c.images.common.minimapEast} className="minimap-east hover-saturate" onClick={()=>c.moveRoom(room.eastRoomId)} alt="East"/>
        )
      }
      if(room.westRoomId){
        arrows.push(
          <img src={c.images.common.minimapEast} className="minimap-west hover-saturate x-flip" onClick={()=>c.moveRoom(room.westRoomId)} alt="West"/>
        )
      }
    }
    return arrows;
  }
}

export default Minimap;