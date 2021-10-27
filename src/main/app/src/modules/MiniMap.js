import React from "react";

class MiniMap extends React.Component {

  constructor(props){
    super(props);

    this.connectors = [];
    this.alreadyConnected = [];
    this.components = [];
    this.addComponents = this.addComponents.bind(this);
    this.addConnector = this.addConnector.bind(this);
  }

  render(){
    if(!this.props.dungeon.floors){
      return(<></>)
    } else {
      return this.addComponents()
    }
  }

  addComponents(){
    this.components = [];
    this.connectors = [];
    let src;
    this.props.dungeon.floors[this.props.props.pc.currentFloor].rooms.forEach(room => {
      if(room.cleared || room.id == this.props.props.pc.currentRoom){
        if(room.id == this.props.props.pc.currentRoom){
          if(room.stairsPrevious){
            src = this.props.props.images.miniMapCurrentStairsPrevious
          }else if(room.stairsNext){
            src = this.props.props.images.miniMapCurrentStairsNext
          }else{
            src = this.props.props.images.miniMapCurrent
          }
        }else if (room.cleared){
          if(room.stairsPrevious){
            src = this.props.props.images.miniMapClearedStairsPrevious
          }else if(room.stairsNext){
            src = this.props.props.images.miniMapClearedStairsNext
          }else{
            src = this.props.props.images.miniMapCleared
          }
        }

        components.push(<img src={src} className={"minimap-room-"+room.xCoord+room.yCoord}/>);
        
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

    return this.components.concat(this.connectors);
  }

  addConnector(xCoord, yCoord, orientation){
    let connector = xCoord.toString() + yCoord.toString();
    if(!this.alreadyConnected.includes(connector)){
      this.alreadyConnected.push(connector);
      this.connectors.push(<img src={this.props.props.images["miniMapConnector"+orientation]} className={"minimap-connector " + orientation.toLowerCase() + " " + connector}/>)
    } 
  }
}

export default MiniMap;