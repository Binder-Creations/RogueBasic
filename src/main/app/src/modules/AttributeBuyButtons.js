import React from "react";
import buttonConstitution from "../images/button-constitution.png";
import buttonStrength from "../images/button-strength.png";
import buttonDexterity from "../images/button-dexterity.png";
import buttonIntelligence from "../images/button-intelligence.png";
import buttonConstitutionHover from "../images/button-constitution-hover.png";
import buttonStrengthHover from "../images/button-strength-hover.png";
import buttonDexterityHover from "../images/button-dexterity-hover.png";
import buttonIntelligenceHover from "../images/button-intelligence-hover.png";
import buttonConstitutionInactive from "../images/button-constitution-inactive.png";
import buttonStrengthInactive from "../images/button-strength-inactive.png";
import buttonDexterityInactive from "../images/button-dexterity-inactive.png";
import buttonIntelligenceInactive from "../images/button-intelligence-inactive.png";

class AttributeBuyButtons extends React.Component {
  render(){
    if(this.props.attributePoints < 1){
        return(
          <>
            <div className="btn-atr con">
              <input className="background" type="image" src={buttonConstitutionInactive} alt="Constitution"/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str">
              <input className="background" type="image" src={buttonStrengthInactive} alt="Strength"/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>
            <div className="btn-atr dex">
              <input className="background" type="image" src={buttonDexterityInactive} alt="Dexterity"/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div>
            <div className="btn-atr int">
              <input className="background" type="image" src={buttonIntelligenceInactive} alt="Intelligence"/>
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
              <input className="background" type="image" src={buttonConstitution} alt="Constitution"
                onMouseEnter={e => (e.currentTarget.src = buttonConstitutionHover)}
                onMouseLeave={e => (e.currentTarget.src = buttonConstitution)}
                onClick={()=>{this.props.appState("pointbuy", "con", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str">
              <input className="background" type="image" src={buttonStrength} alt="Strength"
                onMouseEnter={e => (e.currentTarget.src = buttonStrengthHover)}
                onMouseLeave={e => (e.currentTarget.src = buttonStrength)}
                onClick={()=>{this.props.appState("pointbuy", "str", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>      
            <div className="btn-atr dex">
              <input className="background" type="image" src={buttonDexterity} alt="Dexterity"
                onMouseEnter={e => (e.currentTarget.src = buttonDexterityHover)}
                onMouseLeave={e => (e.currentTarget.src = buttonDexterity)}
                onClick={()=>{this.props.appState("pointbuy", "dex", 1)}}
              />
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div> 
            <div className="btn-atr int">
              <input className="background" type="image" src={buttonIntelligence} alt="Intelligence"
                onMouseEnter={e => (e.currentTarget.src = buttonIntelligenceHover)}
                onMouseLeave={e => (e.currentTarget.src = buttonIntelligence)}
                onClick={()=>{this.props.appState("pointbuy", "int", 1)}}
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