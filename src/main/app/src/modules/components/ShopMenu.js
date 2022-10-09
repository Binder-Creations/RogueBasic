import React from "react";
import c from "../../data/CommonProperties";
import Inventory from "./Inventory";


class ShopMenu extends React.Component {

  constructor(props){
    super(props);
    this.state = {update: 0, updateShop: 0};
    this.scrollable = React.createRef();
    this.scrollableShop = React.createRef();
    this.update = this.update.bind(this);
    this.updateShop = this.updateShop.bind(this);
  }

  render(){
    if(this.props.s.shop){
      return(
        <div className="menu">
          <img className="background" src={c.images.common.shopMenu} alt="Shop Screen"/>
          <p className="gold-count">{this.props.s.pc.currency}</p>
          <div className="inventory-box-wrapper" ref={this.scrollable}>
            <div className="inventory-box">
              <Inventory s={this.props.s} update={this.state.update} type="shopPlayer"/>
            </div>
          </div>
          <div className="shop-box-wrapper" ref={this.scrollableShop}>
            <table className="shop-box">
              <Inventory s={this.props.s} update={this.state.updateShop} type="shopStore"/>
            </table>
          </div>
        </div>
      )  
    } else {
      return(
        <></>
      )
    }
  }

  componentDidMount() {
    this.addListeners();
  }

  componentDidUpdate() {
    this.addListeners();
  }

  addListeners(){
    if(!this.listenersAdded){
      if(this.scrollable.current){
        this.scrollable.current.addEventListener("scroll", this.update)
        this.scrollableShop.current.addEventListener("scroll", this.updateShop)
        this.listenersAdded = true;
      }
    }
  }

  update() {
    this.setState({update: this.state.update + 1});
  }

  updateShop() {
    this.setState({updateShop: this.state.updateShop + 1});
  }
}

export default ShopMenu