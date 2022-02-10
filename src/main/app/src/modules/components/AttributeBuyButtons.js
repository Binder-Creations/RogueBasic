import React from "react";

class AttributeBuyButtons extends React.Component {
  render(){
    if(this.props.s.pc.attributePoints < 1 || this.props.s.combat){
        return(
          <>
            <div className="btn-atr con gray-75">
              <input className="background" type="image" src={this.props.c.images.buttonConstitution} alt="Constitution"/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str gray-75">
              <input className="background" type="image" src={this.props.c.images.buttonStrength} alt="Strength"/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>
            <div className="btn-atr dex gray-75">
              <input className="background" type="image" src={this.props.c.images.buttonDexterity} alt="Dexterity"/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div>
            <div className="btn-atr int gray-75">
              <input className="background" type="image" src={this.props.c.images.buttonIntelligence} alt="Intelligence"/>
              <p className="v-h-centered nopointer">
                +Int
              </p>
            </div>
          </>
        )
    }else{
        return(
          <>
            <div className="btn-atr con hover-saturate">
              <input className="background" type="image" src={this.props.c.images.buttonConstitution} alt="Constitution" onClick={()=>{this.props.c.pointbuy("constitution", 1)}}/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str hover-saturate">
              <input className="background" type="image" src={this.props.c.images.buttonStrength} alt="Strength" onClick={()=>{this.props.c.pointbuy("strength", 1)}}/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>      
            <div className="btn-atr dex hover-saturate">
              <input className="background" type="image" src={this.props.c.images.buttonDexterity} alt="Dexterity" onClick={()=>{this.props.c.pointbuy("dexterity", 1)}}/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div> 
            <div className="btn-atr int hover-saturate">
              <input className="background" type="image" src={this.props.c.images.buttonIntelligence} alt="Intelligence" onClick={()=>{this.props.c.pointbuy("intelligence", 1)}}/>
              <p className="v-h-centered nopointer">
                +Int
              </p>
            </div>
          </> 
        ) 
    }
  }
}

export default AttributeBuyButtons;