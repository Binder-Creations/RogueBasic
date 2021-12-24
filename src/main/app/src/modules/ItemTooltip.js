import React from "react";

class ItemTooltip extends React.Component {
  
  constructor(props){
    super(props);
    this.state = {
      boundingRect: ""
    }
    this.tooltipBox = "";
    this.statBox = [];
    this.tooltipQuadrant = "bottom-left";
    this.style = "";
    this.previousWidthChange = 0;
    this.previousUpdate = 0;
    this.tooltipBox = React.createRef();
    
    this.setTooltipQuadrant = this.setTooltipQuadrant.bind(this);
    this.setStyle = this.setStyle.bind(this);
  }

  render(){
    this.setTooltipQuadrant();
    this.setStyle();

    return (
      <div className={"item-tooltip-box " + this.tooltipQuadrant} style={this.style} ref={this.tooltipBox}>
        <img className="absolute-fill" src={this.props.props.images.tooltipItem} alt=""/>
        <img className="item-tooltip-image" src={this.props.itemProps.image} alt="item"/>
        <img className={"item-tooltip-"+this.props.itemProps.badgeClass} src={this.props.itemProps.badge} alt="badge"/>
        <img className={"item-tooltip-"+this.props.itemProps.iconClass} src={this.props.itemProps.icon} alt="icon"/>
        <p className="item-tooltip-icon-value" style={this.props.itemProps.iconValueColor}>{this.props.itemProps.iconValue}</p>
        <p className="item-tooltip-name" style={this.props.itemProps.nameStyle}>{this.props.item.name}</p>
        <p className="item-tooltip-cost">{this.props.item.cost*this.props.costMult}</p>
        <p className="item-tooltip-description">{this.props.item.description}</p>        
        <div className="item-tooltip-general">{this.props.itemProps.typeName}</div>
        <div className="item-tooltip-statbox">{this.props.itemProps.stats}</div>
      </div>
    )
  }

  componentDidMount(){
    this.setState({boundingRect: this.tooltipBox.current.getBoundingClientRect()});
  }

  componentDidUpdate(){
    if(this.props.props.widthChange > this.previousWidthChange || (this.props.update !== null && this.props.update > this.previousUpdate)){
      this.previousWidthChange = this.props.props.widthChange;
      this.previousUpdate = this.props.update;
      this.setState({boundingRect: this.tooltipBox.current.getBoundingClientRect()});
    }
  }

  setTooltipQuadrant(){
    if(this.state.boundingRect !== ""){
      let centerPointX = 0;
      let centerPointY = 0;
      let blackSpace = (window.innerHeight - window.innerWidth*0.4286) > 0 
        ? (window.innerHeight - window.innerWidth*0.4286)/2
        : 0;
      switch(this.tooltipQuadrant){
        case "top-left":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "center-left":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.4952;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "bottom-left":
          centerPointY = this.state.boundingRect.bottom - blackSpace - this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.left - this.state.boundingRect.width*0.1857;
          break;
        case "top-right":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
        case "center-right":
          centerPointY = this.state.boundingRect.top - blackSpace + this.state.boundingRect.height*0.4952;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
        case "bottom-right":
          centerPointY = this.state.boundingRect.bottom - blackSpace - this.state.boundingRect.height*0.1238;
          centerPointX = this.state.boundingRect.right + this.state.boundingRect.width*0.1857;
          break;
        default:
          break;
      }

      let top = true;
      let left = true;
      let center = false;
      

      top = centerPointY < (window.innerWidth*0.4286)*this.props.qMods[0]
        ? true
        : false
      center = (centerPointY < (window.innerWidth*0.4286)*this.props.qMods[1] && centerPointY > (window.innerWidth*0.4286)*this.props.qMods[0])
        ? true
        : false
      left = centerPointX < window.innerWidth*this.props.qMods[2]
        ? true
        : false
      this.tooltipQuadrant = left
        ? center
          ? "center-left"
          : top
            ? "top-left"
            : "bottom-left"
        : center
          ? "center-right"
          : top
            ? "top-right"
            : "bottom-right"
    }
  }

  setStyle(){
    this.style = {height: + window.innerWidth*0.25 + "px", width: + window.innerWidth*0.1667 + "px"}
  }
}

export default ItemTooltip;