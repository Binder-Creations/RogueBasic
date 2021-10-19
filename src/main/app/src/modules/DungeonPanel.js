import React from "react";


class DungeonPanel extends React.Component {

  constructor(props){
    super(props);

    this.boss = "";
    this.miniboss = "";
    this.bossTrue = <img className="dungeon-panel-boss" title="This dungeon includes a deadly boss encounter" src={this.props.props.images.dungeonBoss} alt="Boss"/>;
    this.bossFalse = <></>;
    this.minibossTrue = <img className="dungeon-panel-miniboss" title="This dungeon includes a dangerous miniboss encounter" src={this.props.props.images.dungeonMiniboss} alt="Miniboss"/>;
    this.minibossFalse = <></>;
    
    this.Boss = () => {
      return(this.boss);
    }

    this.Miniboss = () => {
      return(this.miniboss);
    }

    this.setPrefix = this.setPrefix.bind(this);
    this.setPostfix = this.setPostfix.bind(this);
    this.onClick = this.onClick.bind(this)
  }

  render(){
    let dungeonSmall;
    let fontSize = this.props.props.pc.dungeonBoard[this.props.number-1].name.length > 28 
      ? "70%"
      : "100%"
    let saturate = this.props.props.pc.dungeonBoard[this.props.number-1].id == this.props.props.pc.currentDungeon
      ? "-saturate"
      : ""
    let s = this.props.props.pc.dungeonBoard[this.props.number-1].floorCount > 1 ? "s" : "";
    let Prefix = this.props.props.pc.dungeonBoard[this.props.number-1].prefixMod
      ? () => {return(this.setPrefix())}
      : () => {return(<></>)}
    let Postfix = this.props.props.pc.dungeonBoard[this.props.number-1].postfixMod
      ? () => {return(this.setPostfix())}
      : () => {return(<></>)}

    switch(this.props.props.pc.dungeonBoard[this.props.number-1].theme){
      case "Arcane":
        dungeonSmall = this.props.props.images.dungeonArcaneSmall;
        break;
      case "Castle":
        dungeonSmall = this.props.props.images.dungeonCastleSmall;
        break;
      case "Cave":
        dungeonSmall = this.props.props.images.dungeonCaveSmall;
        break;
    }

    this.boss = this.props.props.pc.dungeonBoard[this.props.number-1].boss ? this.bossTrue : this.bossFalse;
    this.miniboss = this.props.props.pc.dungeonBoard[this.props.number-1].miniboss ? this.minibossTrue : this.minibossFalse;
    
    return(
      <div className={"dungeon-panel" + saturate + " d" + this.props.number} onClick={this.onClick}>
        <img className="background" src={this.props.props.images.dungeonPanel} alt="Dungeon Panel"/>
        <img className="dungeon-small" src={dungeonSmall} alt="Dungeon"/>
        <p className="dungeon-panel-name" style={{fontSize: fontSize}}>{this.props.props.pc.dungeonBoard[this.props.number-1].name}</p>
        <p className="dungeon-panel-theme">{this.props.props.pc.dungeonBoard[this.props.number-1].theme}</p>
        <p className="dungeon-panel-cr" title={"This dungeon is expected to be an average challenge for adventurers of level "+this.props.props.pc.dungeonBoard[this.props.number-1].challengeRating}>{"CR: "+this.props.props.pc.dungeonBoard[this.props.number-1].challengeRating}</p>
        <p className="dungeon-panel-floorcount" title={"This dungeon has " + this.props.props.pc.dungeonBoard[this.props.number-1].floorCount + " floor" + s}>{this.props.props.pc.dungeonBoard[this.props.number-1].floorCount}</p>
        <img className="dungeon-panel-floors" title={"This dungeon has " + this.props.props.pc.dungeonBoard[this.props.number-1].floorCount + " floor" + s} src={this.props.props.images.dungeonFloors} alt="Floors"/>
        <this.Miniboss/>
        <this.Boss/>
        <Prefix/>
        <Postfix/>
        <p className="dungeon-panel-quest">Quest: Defeat All Enemies</p>
        <p className="dungeon-panel-reward">{"Reward: " + this.props.props.pc.dungeonBoard[this.props.number-1].reward}</p>
      </div>
    );  
  }

  setPrefix(){
    let type = this.props.props.pc.dungeonBoard[this.props.number-1].prefixMod.match(/([a-z]+)/)[1]
    let modifier = this.props.props.pc.dungeonBoard[this.props.number-1].prefixMod.match(/(\d+)/)[1]
    return(
      <img className="dungeon-panel-prefix" title={"This dungeon has " + modifier + "% more " + type} src={this.props.props.images["dungeon" + type[0].toUpperCase()+type.substring(1)]} alt="Dungeon Modifier"/>
    );
  }

  setPostfix(){
    let type = this.props.props.pc.dungeonBoard[this.props.number-1].postfixMod.match(/([a-z]+)/)[1]
    let modifier = this.props.props.pc.dungeonBoard[this.props.number-1].postfixMod.match(/(\d+)/)[1]
    return(
      <img className="dungeon-panel-postfix" title={"Enemies in this dungeon have " + modifier + "% more " + type} src={this.props.props.images["dungeon" + type[0].toUpperCase()+type.substring(1)]} alt="Enemy Modifier"/>
    );
  }

  onClick(){
    if(this.props.props.pc.dungeonBoard[this.props.number-1].id != this.props.props.pc.currentDungeon){
      this.props.props.appState("set-dungeon", this.props.props.pc.dungeonBoard[this.props.number-1].id);
    }
  }
}

export default DungeonPanel