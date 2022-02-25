import React from "react";


class DungeonPanel extends React.Component {

  constructor(props){
    super(props);

    this.boss = "";
    this.miniboss = "";
    this.bossTrue = <img className="dungeon-panel-boss" title="This dungeon includes a deadly boss encounter" src={this.props.c.images.dungeonBoss} alt="Boss"/>;
    this.bossFalse = <></>;
    this.minibossTrue = <img className="dungeon-panel-miniboss" title="This dungeon includes a dangerous miniboss encounter" src={this.props.c.images.dungeonMiniboss} alt="Miniboss"/>;
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
    let fontSize = this.props.s.dungeonBoard[this.props.number-1].name.length > 28 
      ? "70%"
      : "100%"
    let saturate = this.props.s.dungeonBoard[this.props.number-1].id === this.props.s.pc.currentDungeon
      ? "saturate-200"
      : "hover-saturate"
    let s = this.props.s.dungeonBoard[this.props.number-1].floorCount > 1 ? "s" : "";
    let Prefix = this.props.s.dungeonBoard[this.props.number-1].prefixMod
      ? () => {return(this.setPrefix())}
      : () => {return(<></>)}
    let Postfix = this.props.s.dungeonBoard[this.props.number-1].postfixMod
      ? () => {return(this.setPostfix())}
      : () => {return(<></>)}

    switch(this.props.s.dungeonBoard[this.props.number-1].theme){
      case "Arcane":
        dungeonSmall = this.props.c.images.dungeonArcaneSmall;
        break;
      case "Castle":
        dungeonSmall = this.props.c.images.dungeonCastleSmall;
        break;
      case "Cave":
        dungeonSmall = this.props.c.images.dungeonCaveSmall;
        break;
      default:
        break;
    }

    this.boss = this.props.s.dungeonBoard[this.props.number-1].boss ? this.bossTrue : this.bossFalse;
    this.miniboss = this.props.s.dungeonBoard[this.props.number-1].miniboss ? this.minibossTrue : this.minibossFalse;
    
    return(
      <div className={"dungeon-panel " + saturate + " d" + this.props.number} onClick={this.onClick} title={this.props.s.dungeonBoard[this.props.number-1].description}>
        <img className="background" src={this.props.c.images.dungeonPanel} alt="Dungeon Panel"/>
        <img className="dungeon-small" src={dungeonSmall} alt="Dungeon"/>
        <p className="dungeon-panel-name" style={{fontSize: fontSize}}>{this.props.s.dungeonBoard[this.props.number-1].name}</p>
        <p className="dungeon-panel-theme">{this.props.s.dungeonBoard[this.props.number-1].theme}</p>
        <p className="dungeon-panel-cr" title={"This dungeon is expected to be an average challenge for adventurers of level "+this.props.s.dungeonBoard[this.props.number-1].challengeRating}>{"CR: "+this.props.s.dungeonBoard[this.props.number-1].challengeRating}</p>
        <p className="dungeon-panel-floorcount" title={"This dungeon has " + this.props.s.dungeonBoard[this.props.number-1].floorCount + " floor" + s}>{this.props.s.dungeonBoard[this.props.number-1].floorCount}</p>
        <img className="dungeon-panel-floors" title={"This dungeon has " + this.props.s.dungeonBoard[this.props.number-1].floorCount + " floor" + s} src={this.props.c.images.dungeonFloors} alt="Floors"/>
        <this.Miniboss/>
        <this.Boss/>
        <Prefix/>
        <Postfix/>
        <p className="dungeon-panel-quest">Quest: Defeat All Enemies</p>
        <p className="dungeon-panel-reward">{"Reward: " + this.props.s.dungeonBoard[this.props.number-1].reward}</p>
      </div>
    );  
  }

  setPrefix(){
    let type = this.props.s.dungeonBoard[this.props.number-1].prefixMod.match(/([a-z]+)/)[1]
    let modifier = this.props.s.dungeonBoard[this.props.number-1].prefixMod.match(/(\d+)/)[1]
    return(
      <img className="dungeon-panel-prefix" title={"This dungeon has " + modifier + "% more " + type} src={this.props.c.images["dungeon" + type[0].toUpperCase()+type.substring(1)]} alt="Dungeon Modifier"/>
    );
  }

  setPostfix(){
    let type = this.props.s.dungeonBoard[this.props.number-1].postfixMod.match(/([a-z]+)/)[1]
    let modifier = this.props.s.dungeonBoard[this.props.number-1].postfixMod.match(/(\d+)/)[1]
    return(
      <img className="dungeon-panel-postfix" title={"Enemies in this dungeon have " + modifier + "% more " + type} src={this.props.c.images["dungeon" + type[0].toUpperCase()+type.substring(1)]} alt="Enemy Modifier"/>
    );
  }

  onClick(){
    if(this.props.s.dungeonBoard[this.props.number-1].id !== this.props.s.pc.currentDungeon){
      this.props.c.setDungeon(this.props.s.dungeonBoard[this.props.number-1].id);
    }
  }
}

export default DungeonPanel