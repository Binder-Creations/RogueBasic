import React from "react";


class DungeonPanel extends React.Component {

  constructor(props){
    super(props);

    this.boss = "";
    this.miniboss = "";
    this.bossTrue = <img className="dungeon-panel-boss" src={this.props.props.images.dungeonBoss} alt="Boss"/>;
    this.bossFalse = <></>;
    this.minibossTrue = <img className="dungeon-panel-miniboss" src={this.props.props.images.dungeonMiniboss} alt="Miniboss"/>;
    this.minibossFalse = <></>;

    this.Boss = () => {
      return(this.boss);
    }

    this.Miniboss = () => {
      return(this.miniboss);
    }
  }

  render(){
      let dungeonSmall;
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
      
      this.boss = this.props.props.pc[this.props.number-1].boss ? this.bossTrue : this.bossFalse;
      this.miniboss = this.props.props.pc[this.props.number-1].miniboss ? this.minibossTrue : this.minibossFalse;

      return(
        <div className={"dungeon-panel-"+this.props.number}>
          <img className="background" src={this.props.props.images.dungeonPanel} alt="Dungeon Panel"/>
          <img className="dungeon-small" src={dungeonSmall} alt="Dungeon"/>
          <p className="dungeon-panel-name">{this.props.props.pc.dungeonBoard[this.props.number-1].name}</p>
          <p className="dungeon-panel-theme">{this.props.props.pc.dungeonBoard[this.props.number-1].theme}</p>
          <p className="dungeon-panel-cr">{this.props.props.pc.dungeonBoard[this.props.number-1].challengeRating}</p>
          <p className="dungeon-panel-floorCount">{this.props.props.pc.dungeonBoard[this.props.number-1].floorCount}</p>
          <img className="dungeon-panel-floors" src={this.props.props.images.dungeonFloors} alt="Floors"/>
          <this.Miniboss/>
          <this.Boss/>
          <p className="dungeon-panel-quest">Quest: Defeat All Enemies</p>
          <p className="dungeon-panel-reward">{"Reward: " + this.props.props.pc.dungeonBoard[this.props.number-1].reward}</p>
        </div>
      );  
  }
}

export default DungeonPanel