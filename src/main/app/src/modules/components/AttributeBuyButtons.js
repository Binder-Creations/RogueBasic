import React from "react";
import c from "../../data/CommonProperties";
import AppServices from "../services/AppServices";

class AttributeBuyButtons extends React.Component {
  constructor(props){
    super(props);
    this.appServices = AppServices.getInstance();
  }

  render(){
    if(this.props.s.pc.attributePoints < 1 || this.props.s.combat){
        return(
          <>
            <div className="btn-atr con gray-75">
              <input className="background" type="image" src={c.images.common.buttonConstitution} alt="Constitution"/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str gray-75">
              <input className="background" type="image" src={c.images.common.buttonStrength} alt="Strength"/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>
            <div className="btn-atr dex gray-75">
              <input className="background" type="image" src={c.images.common.buttonDexterity} alt="Dexterity"/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div>
            <div className="btn-atr int gray-75">
              <input className="background" type="image" src={c.images.common.buttonIntelligence} alt="Intelligence"/>
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
              <input className="background" type="image" src={c.images.common.buttonConstitution} alt="Constitution" onClick={()=>{this.appServices.pointbuy("constitution", 1)}}/>
              <p className="v-h-centered nopointer">
                +Con
              </p>
            </div>
            <div className="btn-atr str hover-saturate">
              <input className="background" type="image" src={c.images.common.buttonStrength} alt="Strength" onClick={()=>{this.appServices.pointbuy("strength", 1)}}/>
              <p className="v-h-centered nopointer">
                +Str
              </p>
            </div>      
            <div className="btn-atr dex hover-saturate">
              <input className="background" type="image" src={c.images.common.buttonDexterity} alt="Dexterity" onClick={()=>{this.appServices.pointbuy("dexterity", 1)}}/>
              <p className="v-h-centered nopointer">
                +Dex
              </p>
            </div> 
            <div className="btn-atr int hover-saturate">
              <input className="background" type="image" src={c.images.common.buttonIntelligence} alt="Intelligence" onClick={()=>{this.appServices.pointbuy("intelligence", 1)}}/>
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