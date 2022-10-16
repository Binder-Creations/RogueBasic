import Item from "./Item";

export default class Currency extends Item {
  addTo(target){
    if(this.name === "Gold") {
      target.currency += this.count;
    }
    if(this.name === "Soul") {
      target.metacurrency += this.count;
    }
  }
}