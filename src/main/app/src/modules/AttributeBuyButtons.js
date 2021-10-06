import React from "react";

class AttributeBuyButtons extends React.Component {
  render(){
    if(this.props.props.pc.attributePoints < 1){
        return(
          <>
            <div className="btn-atr con">
              <input className="background" type="image" src={this.props.props.images.buttonConstitutionInactive} alt="Constitution"/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str">
              <input className="background" type="image" src={this.props.props.images.buttonStrengthInactive} alt="Strength"/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>
            <div className="btn-atr dex">
              <input className="background" type="image" src={this.props.props.images.buttonDexterityInactive} alt="Dexterity"/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div>
            <div className="btn-atr int">
              <input className="background" type="image" src={this.props.props.images.buttonIntelligenceInactive} alt="Intelligence"/>
              <p className="v-h-centered nopointer">
                +Int
              </p>
            </div>
          </>
        )
    }else{
        return(
          <>
            <div className="btn-atr con">
              <input className="background" type="image" src={this.props.props.images.buttonConstitution} alt="Constitution"
                onMouseEnter={e => (e.currentTarget.src = this.props.props.images.buttonConstitutionHover)}
                onMouseLeave={e => (e.currentTarget.src = this.props.props.images.buttonConstitution)}
                onClick={()=>{this.props.props.appState("pointbuy", "con", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str">
              <input className="background" type="image" src={this.props.props.images.buttonStrength} alt="Strength"
                onMouseEnter={e => (e.currentTarget.src = this.props.props.images.buttonStrengthHover)}
                onMouseLeave={e => (e.currentTarget.src = this.props.props.images.buttonStrength)}
                onClick={()=>{this.props.props.appState("pointbuy", "str", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>      
            <div className="btn-atr dex">
              <input className="background" type="image" src={this.props.props.images.buttonDexterity} alt="Dexterity"
                onMouseEnter={e => (e.currentTarget.src = this.props.props.images.buttonDexterityHover)}
                onMouseLeave={e => (e.currentTarget.src = this.props.props.images.buttonDexterity)}
                onClick={()=>{this.props.props.appState("pointbuy", "dex", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div> 
            <div className="btn-atr int">
              <input className="background" type="image" src={this.props.props.images.buttonIntelligence} alt="Intelligence"
                onMouseEnter={e => (e.currentTarget.src = this.props.props.images.buttonIntelligenceHover)}
                onMouseLeave={e => (e.currentTarget.src = this.props.props.images.buttonIntelligence)}
                onClick={()=>{this.props.props.appState("pointbuy", "int", 1)}}
              />
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