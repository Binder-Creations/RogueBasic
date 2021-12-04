import React from "react";
// import GameOver from "./GameOver.js";

class Combat extends React.Component {
  constructor(props){
    super(props);
    this.components = [];
  }

  render(){

    if(!this.props.props.combatUpdates.length){
      return(<></>);
    }

    //if combatUpdates includes "endCombat"
    //  wait(500);
    //  appState("combat");

    this.components = [];

    // playerAction();
    // checkAliveMonsters();

    // monsterActions();
    // checkAlivePlayer();
    return this.components;
  }

  // playerAction(){
  //   switch(this.props.props.pc.characterClass){
  //     case "Rogue":
  //       switch(this.props.props.combatAction){
  //         case "attack":
  //           break;
  //         case "skill-1":
  //           break;
  //         case "skill-2":
  //           break;
  //         case "skill-3":
  //           break;
  //         case "skill-4":
  //           break;
  //       }
  //       break;
  //     case "Warrior":
  //       switch(this.props.props.combatAction){
  //         case "attack":
  //           break;
  //         case "skill-1":
  //           break;
  //         case "skill-2":
  //           break;
  //         case "skill-3":
  //           break;
  //         case "skill-4":
  //           break;
  //       }
  //       break;
  //     case "Wizard":
  //       switch(this.props.props.combatAction){
  //         case "attack":
  //           break;
  //         case "skill-1":
  //           break;
  //         case "skill-2":
  //           break;
  //         case "skill-3":
  //           break;
  //         case "skill-4":
  //           break;
  //       }
  //       break;
  //   }
  // }

  // checkAliveMonsters(){
  //   this.props.monsters.forEach(function(monster, index, monsters){
  //     if(monster.currentHealth <= 0){
  //       monsters.splice(index, 1);
  //     }
  //   });

  //   if(this.props.monsters.length === 0){
  //     this.props.monsters = null;
  //   }
  // }

}

export default Combat;