import React from "react";
import Combat from "./Combat.js"
import BuffBar from "./BuffBar.js";
import {TransitionGroup} from 'react-transition-group';
import Fade from "./Fade.js";
import FadeIn from "./FadeIn.js";
import AbilityAnimation from "./AbilityAnimation.js";
import LootMenu from "./LootMenu.js";
import QuestMenu from "./QuestMenu.js"

class Dungeon extends React.Component {
  constructor(props){
    super(props);

    this.currentRoom = null;
    this.pushMonstersByPosition = this.pushMonstersByPosition.bind(this);
  }

  render(){
    let components = [];
    this.currentRoom = this.props.dungeon.floors[this.props.dungeon.currentFloor].rooms[this.props.dungeon.currentRoom];

    if(((this.currentRoom.stairsPrevious || (this.currentRoom.stairsNext && (this.props.dungeon.currentFloor + 1 < this.props.dungeon.floorCount))) && (!this.currentRoom.monsters || this.currentRoom.monsters.length === 0))){
      components.push(
        <FadeIn 
          key='s' 
          in
        >
          <img src={this.props.props.images["dungeon"+this.props.dungeon.theme+"Stairs"]} className="dungeon-stairs hover-saturate" alt="Stairs" onClick={() => this.props.props.appState("stairs", this.currentRoom.stairsPrevious)}/>
        </FadeIn>
      );
    }
    
    if(!this.props.props.combat && this.currentRoom.lootCache && this.currentRoom.lootCache.length){
      if((this.currentRoom.stairsPrevious || (this.currentRoom.stairsNext && (this.props.dungeon.currentFloor + 1 < this.props.dungeon.floorCount)))){
        components.push(        
          <FadeIn 
            key='c' 
            in
          >
            <img src={this.props.props.images.chest} className="dungeon-chest-left hover-saturate" alt="chest" onClick={() => this.props.props.appState("menu", "loot")}/>
          </FadeIn>);
      } else {
        components.push(        
          <FadeIn 
            key='c' 
            in
          >
            <img src={this.props.props.images.chest} className="dungeon-chest hover-saturate" alt="chest" onClick={() => this.props.props.appState("menu", "loot")}/>
          </FadeIn>);
      }
      if(this.props.props.menu === "loot"){
        components.push(<LootMenu props={this.props.props} room={this.currentRoom}/>);
      }
    } 
    if(this.props.props.combat){
      components.push(
        this.props.props.combatTransitions && (
        <Fade 
          key='a' 
          in
          onExited={() => this.props.props.appState("combat")}
        >
          <img src={this.props.props.images["arena"+this.props.dungeon.theme]} className="dungeon-arena" alt="Arena"/>
        </Fade>
      ));
      this.pushMonstersByPosition(components, 'b');
      this.pushMonstersByPosition(components, 'f');
      components.push(<Combat key='c' props={this.props.props} monsters={this.currentRoom.monsters}/>)

      if(this.props.props.pc.buffs && this.props.props.pc.buffs.length > 0){
        components.push(<BuffBar props={this.props.props} entity={this.props.props.pc} type={"buffs"}/>);
      }
      if(this.props.props.pc.debuffs && this.props.props.pc.debuffs.length > 0){
        components.push(<BuffBar props={this.props.props} entity={this.props.props.pc} type={"debuffs"}/>);
      }
      for(let monster of this.currentRoom.monsters) {
        if(monster.buffs && monster.buffs.length > 0){
          components.push(<BuffBar props={this.props.props} entity={monster} type={"buffs"}/>);
        }
        if(monster.debuffs && monster.debuffs.length > 0){
          components.push(<BuffBar props={this.props.props} entity={monster} type={"debuffs"}/>);
        }
      }
      if(this.props.props.renderAbilityAnimation){
        components.push(<AbilityAnimation props={this.props.props} key='n'/>);
      }   
    }
    if(this.props.props.questComplete){
      components.push(<QuestMenu props={this.props.props} reward={this.props.dungeon.reward} rewardItems={this.props.props.dungeon.rewardCache}/>);
    } 
    return (
      <TransitionGroup>
        {components}
      </TransitionGroup>
    );
  }

  pushMonstersByPosition(components, char){
    this.currentRoom.monsters.forEach(monster =>{
      if(monster.position[0] === char){
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
        let animation = (monster.position === this.props.props.positions[Math.max(this.props.props.position - 1, 0)]) ? " animate__animated animate__bounce" : ""
        components.push(
          <Fade 
            key={monster.position}
            in={true} 
          >
            <>
              <img src={this.props.props.monsters[monster.species][(monster.boss ? "boss" : monster.miniboss ? "miniboss" : monster.type) + monster.variant]} className={"monster "+monster.position+animation} alt={monster.name}/>
              <img src={this.props.props.images.barBackground} className={"monster-bar "+monster.position} alt="Health"/>
              <img src={this.props.props.images.barHealth} className={"monster-bar "+monster.position} alt="Health" style={healthStyle}/>
              <img src={this.props.props.images.barFrame} className={"monster-bar "+monster.position} alt="Health"/>
              <div className={"monster-bar "+monster.position} title={monster.currentHealth + "/" + monster.healthTotal}><p className="nowrap v-h-centered" style={nameStyle}>{"lv." + monster.level + " " + monster.name}</p></div>
            </>
          </Fade>
        );
      }
    });
  }

}

export default Dungeon;