import React from "react";
import {TransitionGroup} from 'react-transition-group';
import c from "../../data/CommonProperties";
import BuffBar from "./BuffBar";
import LootMenu from "./LootMenu";
import QuestMenu from "./QuestMenu";
import Combat from "./Combat";
import Fade from "../animations/Fade";
import FadeIn from "../animations/FadeIn";
import AbilityAnimation from "../animations/AbilityAnimation";

class Dungeon extends React.Component {
  constructor(props){
    super(props);
    this.currentRoom = null;
    this.abilityAnimationKey = 0;
    this.pushMonstersByPosition = this.pushMonstersByPosition.bind(this);
  }

  render(){
    let components = [];
    this.currentRoom = this.props.s.dungeon.floors[this.props.s.dungeon.currentFloor].rooms[this.props.s.dungeon.currentRoom];

    if(((this.currentRoom.stairsPrevious || (this.currentRoom.stairsNext && (this.props.s.dungeon.currentFloor + 1 < this.props.s.dungeon.floorCount))) && (!this.currentRoom.monsters || this.currentRoom.monsters.length === 0))){
      components.push(
        <FadeIn in key='s'>
          <img src={c.images.environment.dungeonStairs} className="dungeon-stairs hover-saturate" alt="Stairs" onClick={() => c.stairs(this.currentRoom.stairsPrevious)}/>
        </FadeIn>
      );
    }
    
    if(!this.props.s.combat && this.currentRoom.loot && this.currentRoom.loot.length){
      if((this.currentRoom.stairsPrevious || (this.currentRoom.stairsNext && (this.props.s.dungeon.currentFloor + 1 < this.props.s.dungeon.floorCount)))){
        components.push(        
          <FadeIn in key='c'>
            <img src={c.images.common.chest} className="dungeon-chest-left hover-saturate" alt="chest" onClick={() => c.menu("loot")}/>
          </FadeIn>
        );
      } else {
        components.push(        
          <FadeIn in key='c'>
            <img src={c.images.common.chest} className="dungeon-chest hover-saturate" alt="chest" onClick={() => c.menu("loot")}/>
          </FadeIn>
        );
      }
      if(this.props.s.menu === "loot"){
        components.push(
          <Fade 
            in 
            key='l'
            onExited={()=>{
              if(this.props.s.menu === "loot"){
                c.menu();
              }
            }}
          >
            <LootMenu s={this.props.s} room={this.currentRoom}/>
          </Fade>
        );
      }
    } 
    if(this.props.s.combat){
      components.push(
        this.props.s.combatTransitions && (
        <Fade 
          key='a' 
          in
          onExited={() => c.combat()}
        >
          <img src={c.images.environment.arena} className="dungeon-arena" alt="Arena"/>
        </Fade>
      ));
      this.pushMonstersByPosition(components, 'b');
      this.pushMonstersByPosition(components, 'f');
      components.push(<Combat key='c' s={this.props.s} monsters={this.currentRoom.monsters}/>)

      if(this.props.s.pc.buffs && this.props.s.pc.buffs.length > 0){
        components.push(
          <Fade in key="pc-buffs">
            <BuffBar s={this.props.s} entity={this.props.s.pc} type={"buffs"}/>
          </Fade>
        );
      }
      if(this.props.s.pc.debuffs && this.props.s.pc.debuffs.length > 0){
        components.push(
          <Fade in key="pc-debuffs">
            <BuffBar s={this.props.s} entity={this.props.s.pc} type={"debuffs"}/>
          </Fade>
        );
      }
      for(let monster of this.currentRoom.monsters) {
        if(monster.buffs && monster.buffs.length > 0){
          components.push(
            <Fade in key={monster.position+"-buffs"}>
              <BuffBar s={this.props.s} entity={monster} type={"buffs"}/>
            </Fade>
          );
        }
        if(monster.debuffs && monster.debuffs.length > 0){
          components.push(
            <Fade in key={monster.position+"pc-debuffs"}>
              <BuffBar s={this.props.s} entity={monster} type={"debuffs"}/>
            </Fade>
          );
        }
      }

      if(this.props.s.renderAbilityAnimation){
        components.push(<AbilityAnimation s={this.props.s} key={"aa" + this.abilityAnimationKey++}/>);
      }   
    }
    
    if(this.props.s.dungeon.questCompleted && !this.props.s.dungeon.rewardClaimed){
      components.push(
        <Fade in key='q'>
          <QuestMenu s={this.props.s}/>
       </Fade>
      );
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
        let animation = (monster.position === c.positions[Math.max(this.props.s.position - 1, 0)]) ? " animate__animated animate__bounce" : ""
        components.push(
          <Fade 
            key={monster.position}
            in={true} 
          >
            <>
              <img src={c.images.monster[(monster.boss ? "boss" : monster.miniboss ? "miniboss" : monster.type) + monster.variant + monster.ab]} className={"monster "+monster.position+animation} alt={monster.name}/>
              <img src={c.images.common.barBackground} className={"monster-bar "+monster.position} alt="Health"/>
              <img src={c.images.common.barHealth} className={"monster-bar "+monster.position} alt="Health" style={healthStyle}/>
              <img src={c.images.common.barFrame} className={"monster-bar "+monster.position} alt="Health"/>
              <div className={"monster-bar "+monster.position} title={monster.currentHealth + "/" + monster.healthTotal}><p className="nowrap v-h-centered" style={nameStyle}>{"lv." + monster.level + " " + monster.name}</p></div>
            </>
          </Fade>
        );
      }
    });
  }

}

export default Dungeon;