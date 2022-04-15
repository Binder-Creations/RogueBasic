import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import { v4 as uuidv4 } from 'uuid';
import Ui from "./modules/components/Ui";
import Dungeon from "./modules/components/Dungeon";
import LoadScreen from "./modules/components/LoadScreen";
import ShopMenu from "./modules/components/ShopMenu";
import InnMenu from "./modules/components/InnMenu";
import TavernMenu from "./modules/components/TavernMenu";
import Binder from "./modules/services/Binder";
import PcServices from "./modules/services/PcServices";
import ItemServices from "./modules/services/ItemServices";
import ItemFactory from "./modules/services/ItemFactory";
import CombatEngine from "./modules/services/CombatEngine";
import * as abilities from "./images/abilities" 
import * as abilitiesSmall from "./images-small/abilities";
import * as images from "./images" 
import * as imagesSmall from "./images-small";
import * as items from "./images/items" 
import * as itemsSmall from "./images-small/items";
import * as monsters from "./images/monsters" 
import * as monstersSmall from "./images-small/monsters";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: null,
      loading: true,
      loadingImages: 0,
      loadingDungeons: false,
      loadingShop: false,
      scene: "Default",
      combat: false,
      combatUpdates: [], 
      characterId: document.cookie.replace(/(?:(?:^|.*;\s*)character_id\s*=\s*([^;]*).*$)|^.*$/, "$1"),
      playerId: document.cookie.replace(/(?:(?:^|.*;\s*)player_id\s*=\s*([^;]*).*$)|^.*$/, "$1"),
      combatTransitions: false,
      questComplete: false,
      menu: null,
      help: false,
      widthChange: 0,
      eaten: 0,
      rested: 0,
      position: 0,
      abilityAnimation: 0,
      renderAbilityAnimation: false
    };
    this.c = {
      positions: ["pc", "frontLeft", "frontCenter", "frontRight", "backLeft", "backCenter", "backRight"],
      colorArmor: {color: "#136f9b"},
      colorPower: {color: "#a83a0e"},
      colorHeal: {color: "#ce6eb9"},
      colorEnergize: {color: "#2a52be"},
      zeroId: {id: "00000000-0000-0000-0000-000000000000"},
      imageMax: 498,
      disableUiMenus: false,
      images: window.innerWidth > 960 ? images : imagesSmall,
      abilities: window.innerWidth > 960 ? abilities : abilitiesSmall,
      items: window.innerWidth > 960 ? items : itemsSmall,
      monsters: window.innerWidth > 960 ? monsters : monstersSmall,
    }
    Binder.bindAndC(this);
    this.c.itemServices = new ItemServices({
      images: this.c.images, 
      items: this.c.items, 
      monsters: this.c.monsters,
      colorArmor: this.c.colorArmor,
      colorPower: this.c.colorPower,
      colorHeal: this.c.colorHeal,
      colorEnergize: this.c.colorEnergize,
      colorCommon: "#39363a",
      colorUncommon: "#4d610f",
      colorRare: "#1e4d7a",
      colorEpic: "#4e0f62",
      colorShopCommon: "#d2d2d2",
      colorShopUncommon: "#b4de31",
      colorShopRare: "#3296f6",
      colorShopEpic: "#c111f9"
    });
    this.returningUser = document.cookie.replace(/(?:(?:^|.*;\s*)returning_user\s*=\s*([^;]*).*$)|^.*$/, "$1");
    this.backgroundSrc = "";
    this.helpButton = 
      <button className="btn-help" onClick={this.help}>
        <img src={this.c.images.helpIcon} alt="Help"/>
      </button>
    this.routeHome = 
      <Router>
        <Route path='/' component={() => { 
          window.location.href = '/home'; 
          return null;
        }}/>
      </Router>
    this.routeLogin = 
      <Router>
        <Route path='/' component={() => { 
          window.location.href = '/login'; 
          return null;
        }}/>
      </Router>
    this.exteriorBuildings =
      <>
        <input className="tavern" type="image" src={this.c.images.tavernExterior} alt="Tavern" onClick={ () => { this.setState({scene:"Tavern"})} }/>
        <input className="inn" type="image" src={this.c.images.innExterior} alt="Inn" onClick={ () => { this.setState({scene:"Inn"})} }/>
        <input className="shop" type="image" src={this.c.images.shopExterior} alt="Shop" onClick={ () => { this.setState({scene:"Shop"})} }/>
      </>
    this.tempScreen = 
      <div className="app-container v-h-centered" 
        style={{backgroundImage: "url("+this.c.images.loadingBackground+")", 
        height: this.c.appHeight + "px", width: this.c.appWidth + "px"}}>
      </div>
  }

  render(){
    if(this.state.routeHome){
      return this.routeHome;
    }
    if(this.showTempScreen){
      return this.tempScreen;
    }
    if (!this.state.characterId || !this.state.playerId){
      if(!this.returningUser){
        this.assignTempAccount();   
      } else {
        if(this.state.playerId){
          return this.routeHome;
        }
        return this.routeLogin;
      }
    }
    if(this.state.scene==="Home"){
        return this.routeHome;
      }
    this.sizeWindow();
    if(!this.initialized){
      this.initialize();
    }
    if(this.toRest){
      this.toRest = false;
      this.rest()
    } 
    if(this.state.loading){
      if(this.state.loadingImages >= this.c.imageMax && this.state.loadingDungeons && this.state.loadingShop) {
        this.setState({loading: false, loadingImages: 0, loadingDungeons: false, loadingShop: false})
      } else {
        return <LoadScreen c={this.c} s={this.state}/>;
      }
    }
    this.checkCombat();

    let innerElements = [];
    let outerElements = [];
    this.c.disableUiMenus = false;
    this.c.homeButton = false;
    if (this.state.pc.location === "Town"){
      if(this.state.scene === "Default"){
        this.backgroundSrc = this.c.images.town;
        this.c.homeButton = true;
        innerElements.push(this.exteriorBuildings);
        outerElements.push(this.helpButton);
        if(this.state.help){
          outerElements.push(this.helpMenu(this.c.images.helpTown));
        }
      } else if(this.state.scene === "Tavern"){
        this.genDungeons();
        this.c.disableUiMenus = true;
        this.backgroundSrc = this.c.images.tavern;
        outerElements.push(<TavernMenu c={this.c} s={this.state}/>);
        if(this.state.generatingDungeon){
          outerElements.push(this.loadingBar())
        }
        outerElements.push(this.helpButton);
        if(this.state.help){
          outerElements.push(this.helpMenu(this.c.images.helpTavern, "tavern"));
        }
      } else if(this.state.scene==="Inn"){
        this.c.disableUiMenus = true;
        this.backgroundSrc = this.c.images.inn;
        outerElements.push(<InnMenu c={this.c} s={this.state}/>);
      } else if(this.state.scene==="Shop"){
        this.c.disableUiMenus = true;
        this.backgroundSrc = this.c.images.shop;
        outerElements.push(<ShopMenu c={this.c} s={this.state}/>);
      }
    } else if(this.state.pc.location === "Dungeon") {
      this.getDungeon();
      if(this.state.dungeon && this.state.dungeon.floors){
        this.backgroundSrc = this.c.images[this.state.dungeon.theme.toLowerCase() + this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].variant];
        innerElements.push(<Dungeon c={this.c} s={this.state}/>);
        outerElements.push(this.helpButton);
        if(this.state.help){
          outerElements.push( this.state.combat ? this.helpMenu(this.c.images.helpCombat, "combat") : this.helpMenu(this.c.images.helpDungeon));
        }
      } else {
        this.backgroundSrc = this.c.images.tavern
      }
    } else {
      this.backgroundSrc = this.c.images.town
    }
    
    return(
      <div className="app-container v-h-centered" style={{backgroundImage: "url("+this.backgroundSrc+")", height: this.c.appHeight + "px", width: this.c.appWidth + "px"}}>
        {innerElements}
        <Ui c={this.c} s={this.state}/>
        {outerElements}
      </div>
    );
  }

  componentDidUpdate(){
    if(this.state.loading){
      setTimeout(() => {
        this.setState({loadingImages: this.imagesLoaded})
      }, 33);
    }
    if(this.state.generatingDungeon){
      setTimeout(() => {
        if(this.state.generatingDungeon){
          this.setState({barPercent: (this.state.barPercent && this.state.barPercent < 100 ) ? this.state.barPercent + 20 : 20})
        }
      }, 2000);
    }
  }

  assignTempAccount(){
    this.showTempScreen = true;
    document.cookie = "returning_user=true;max-age=99999999";
    let tempUUID = uuidv4();
    document.cookie = "player_id="+tempUUID+";max-age=99999999";
    fetch('/register/temp/'+ tempUUID, {method: 'POST'})
    .then(response => {
      this.setState({routeHome: true});
    });  
  }

  async initialize(){
    this.initialized = true;
    try {
      this.setState({pc: await this.fetchPc(this.state.characterId)});
    } catch(e) {
      this.setState({characterId: null, playerId: null});
    } 
    this.c.pcServices = new PcServices(this.state.pc.characterClass);
    this.c.pcServices.updateStats(this.state.pc);
    window.addEventListener("resize", this.updateWidth)
    this.setItemSortOrder();
    this.preloadImages();
    this.genDungeons();
    this.genShop();
  }

  sizeWindow(){
    let isWide = window.innerWidth >= window.innerHeight*2.1;
    this.c.appHeight = isWide ? window.innerHeight : window.innerWidth*0.47619047;
    this.c.appWidth = isWide ? window.innerHeight*2.1 : window.innerWidth;
    document.body.style.fontSize = this.c.appWidth*0.019 + "px";
  }

  setItemSortOrder(){
    if(this.state.pc.characterClass === "Rogue"){
      this.c.itemSortOrder = ["currency", "potion", "consumable", "bow", "dagger", "headMedium", "bodyMedium", "neck", "back", "staff", "spellbook", "sword", "shield", "headLight", "bodyLight", "headHeavy", "bodyHeavy"];
    } else if (this.state.pc.characterClass === "Wizard"){
      this.c.itemSortOrder = ["currency", "potion", "consumable", "staff", "spellbook", "headLight", "bodyLight", "neck", "back", "bow", "dagger", "sword", "shield", "headMedium", "bodyMedium", "headHeavy", "bodyHeavy"];
    } else {
      this.c.itemSortOrder = ["currency", "potion", "consumable", "sword", "shield", "headHeavy", "bodyHeavy", "neck", "back", "staff", "spellbook", "bow", "dagger", "headLight", "bodyLight", "headMedium", "bodyMedium"];
    }
  }

  preloadImages() {
    this.imagesLoaded = 0;
    let increment = () => this.imagesLoaded++
    let createElement = (src) => {
      let i = new Image();
      i.src = src;
      i.onload = increment;
    }
    for (let image of Object.keys(this.c.images)) {
      createElement(this.c.images[image]);
    }
    for (let image of Object.keys(this.c.abilities)) {
      createElement(this.c.abilities[image]);
    }
    for (let type of Object.keys(this.c.items)){
      for (let image of Object.keys(this.c.items[type])) {
        createElement(this.c.items[type][image]);
      }
    }
    for (let type of Object.keys(this.c.monsters)) {
      for (let image of Object.keys(this.c.monsters[type])) {
        createElement(this.c.monsters[type][image]);
      }
    }
  }

  async fetchPc(id = this.state.characterId){
    let pc;
    await fetch('/pc/'+ id)
    .then(response => response.json())
    .then(data => {
      ItemFactory.parsePC(data);
      pc = this.sortPC(data);
    });
    console.log(pc)
    return pc;
  }

  async fetchDungeon(id = this.state.pc.currentDungeon){
    let dungeon;
    await fetch('/dungeon/'+ id)
    .then(response => response.json())
    .then(data => {
      ItemFactory.parseDungeon(data);
      dungeon = this.sortDungeon(data);
    });
    return dungeon;
  }

  async fetchNewDungeonBoard(){
    let dungeonData;
    await fetch('/dungeon/new/'+ this.state.pc.id)
    .then(response => response.json())
    .then(data => {
      let pc = {...this.state.pc};
      pc.dungeonBoard = [];
      data.forEach(dungeon => pc.dungeonBoard.push(dungeon.id))
      dungeonData = {pc: pc, dungeonBoard: data};
    });
    return dungeonData;
  }

  async fetchDungeonBoard(){
    let dungeonBoard;
    await fetch('/dungeon/getBoard/'+ this.state.pc.id)
    .then(response => response.json())
    .then(data => {
      dungeonBoard = data;
    });
    return dungeonBoard;
  }

  async fetchShop(){
    let shop;
    await fetch('/shop/'+ this.state.pc.currentShop)
    .then(response => response.json())
    .then(data => {
      data.inventory = ItemFactory.parseArray(data.inventory);
      shop = data;
    });
    return shop;
  }

  async fetchNewShop(){
    let shopData;
    await fetch('/shop/new/'+ this.state.pc.id)
    .then(response => response.json())
    .then(data => {
      let pc = {...this.state.pc}
      pc.currentShop = data.id;
      data.inventory = ItemFactory.parseArray(data.inventory);
      shopData = {pc: pc, shop: data};
    });
    return shopData;
  }

  async genDungeons(){
    if(this.state.pc.currentDungeon && (!this.state.dungeon || this.state.dungeon.id !== this.state.pc.currentDungeon)){
      this.setState({dungeon: await this.fetchDungeon(), loadingDungeons: true, generatingDungeon: false, barPercent: 0});
    }
    if(!this.state.pc.dungeonBoard){
      let data = await this.fetchNewDungeonBoard();
      this.save({pc: data.pc}, {pc: data.pc, dungeonBoard: data.dungeonBoard, loadingDungeons: true});
    } 
    if(this.state.pc.dungeonBoard && (!this.state.dungeonBoard)){
      this.setState({dungeonBoard: await this.fetchDungeonBoard(), loadingDungeons: true});
    }     
    if(this.state.pc.dungeonBoard && this.state.dungeonBoard && !this.state.loadingDungeons){
      this.setState({loadingDungeons: true});
    }
  }

  async genShop(){
    if(this.state.pc.currentShop){
      if(!this.state.shop || this.state.shop.id !== this.state.pc.currentShop){
        this.setState({shop: await this.fetchShop(), loadingShop: true});
      }
    } else {
      let data = await this.fetchNewShop();
      this.save({pc: data.pc}, {shop: data.shop, pc: data.pc, loadingShop: true})
    }
  }

  loadingBar(){
    return(
      <div className="loading-bar v-h-centered">
        <img className="background" src={this.c.images.barBackgroundLoading} alt="background"/>
        <img className="bar" src={this.c.images.barLoading} style={{width: (this.state.barPercent ? this.state.barPercent : 5) + "%"}} alt="load bar"/>
        <img className="background v-h-centered" src={this.c.images.barFrame} alt="load bar"/>
        <p className="v-h-centered">
          {"Generating Dungeon" + this.elipsis()}
        </p>
      </div>
    );
  }

  helpMenu(src, classExtension = ""){
    return(
      <div className={"help-menu-" + classExtension}>
        <img src={src} className="background" alt="Help Menu"/>
        <img className="close hover-saturate" src={this.c.images.buttonClose} alt="Close" onClick={this.help}/>
      </div>
    );
  }

  elipsis(){
    let elipsis = "";
    if(this.state.barPercent){
      for(let i = 0; i < this.state.barPercent/20; i++){
        elipsis += "."
      }
    }
    return elipsis
  }

  async getDungeon(){
    if(!this.state.dungeon){
      this.setState({dungeon: await this.fetchDungeon()})
    }
    if(!this.state.dungeon.floors){
      this.setState({dungeon: await this.fetchDungeon(this.state.dungeon.id)});
    } 
  }
  
  checkCombat(){
    if(!this.state.combat && this.state.pc.location === "Dungeon" && this.state.dungeon && this.state.dungeon.floors && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.length){
      this.combat();
    }
  }

  checkQuest(){
    return this.state.dungeon.floors.find(floor => floor.rooms.find(room => room.monsters && room.monsters.length > 0))
      ? false
      : true;
  }

  sortDungeon(dungeon){     
    for(let floor of dungeon.floors){
      floor.rooms.sort((a,b) => a.count - b.count);
    }
    dungeon.floors.sort((a,b) => a.level - b.level);
    return dungeon;
  }

  sortPC(pc){
    pc.abilities.sort((a,b) => a.level - b.level);
    return pc;
  }

  async save(data, state){
    for(let key of Object.keys(data)){
      await fetch('/'+key+'/', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data[key])
      });
    }
    if(state){
      this.setState(state);
    }
    return;
  }

  updateWidth(){
    this.setState({widthChange: this.state.widthChange + 1});
  }

  setState(state){
    if(this.state.combatUpdates.length > 0 && !state.combatUpdates){
      state.combatUpdates = []
    }
    super.setState(state);
  }

  getNextPosition(){
    let nextPosition = null;
    let i = this.state.position;
    while(!nextPosition){
      nextPosition = this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.find(monster => monster.position === this.c.positions[i])
      i++
      if(i >= this.c.positions.length){
        break;
      }
    }
    return nextPosition ? this.c.positions.indexOf(nextPosition.position) : 0;
  }

  help(){
    this.setState({help: this.state.help ? false : true});
  }

  gameOver(update){
    this.setState({gameOver: update});
  }

  endGame(){
    fetch('/pc/', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        playerId: this.state.playerId, 
        characterId: this.state.characterId, 
        metacurrency: this.state.pc.metacurrency
      })
    })
    .then(response => { 
      document.cookie = "character_id=; Max-Age=-99999999;"
      this.setState({characterId: null})
    });
  }

  quest(index){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    pc.currency += this.state.dungeon.reward;
    pc.experience += Math.floor(this.state.dungeon.reward*2.5);
    this.c.pcServices.updateStats(pc);
    pc.inventory.push(this.state.dungeon.rewardSet[index]);
    dungeon.rewardClaimed = true;
    this.save({pc: pc, dungeon: pc}, {pc:pc, dungeon: dungeon});
  }

  menu(menu){
    this.setState({menu: menu === this.state.menu ? null : menu});
  }

  ability(number){
    this.setState({
      abilityAnimation: number,
      renderAbilityAnimation: true
    });
  }

  pcCombat(index){
    let dungeon = {...this.state.dungeon}
    document.documentElement.style.setProperty('--animate-duration', '1s');
    this.c.combatEngine.runRound(this.state.pc.abilities[index]);
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.c.combatEngine.monsters;
    this.setState({
      pc: this.c.combatEngine.pc,
      dungeon: dungeon,
      combatUpdates: this.c.combatEngine.combatUpdates,
      position: 1,
      renderAbilityAnimation: false
    });
  }

  nextCombat(){
    let dungeon = {...this.state.dungeon}
    let nextPosition = this.getNextPosition();
    if(!nextPosition){
      this.c.combatEngine.endRound();
      let combatTransitions = this.c.combatEngine.monsters.length ? true : false;
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.c.combatEngine.monsters;
      this.setState({
        pc: this.c.combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: this.c.combatEngine.combatUpdates,
        combatTransitions: combatTransitions,
        position: 0
      });
    } else {
      this.c.combatEngine.runRound(null, nextPosition);
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.c.combatEngine.monsters;
      this.setState({
        pc: this.c.combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: this.c.combatEngine.combatUpdates,
        position: nextPosition + 1
      });
    }
  }

  combat(){
    let dungeon = {...this.state.dungeon};
    if(!this.state.combat){
      this.c.combatEngine = new CombatEngine(this.c, this.state);
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.c.combatEngine.monsters;
      this.setState({
        combat: true,
        dungeon: dungeon,
        combatTransitions: true
      });
    } else {
      let pc = {...this.state.pc}
      this.c.pcServices.clearBuffs(pc);
      this.c.pcServices.resetTempStats(pc);
      this.c.pcServices.updateStats(pc);
      this.c.combatEngine = null;
      dungeon.questCompleted = this.checkQuest();
      this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, combat: false, position: 0});
    }    
  }

  stairs(up){
    let dungeon = {...this.state.dungeon};
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
    if(up){
      if(dungeon.currentFloor === 0){
        let pc = {...this.state.pc};
        pc.location = "Town";
        this.save({pc: pc, dungeon: dungeon}, {dungeon: dungeon, pc:pc, scene:"Default"});
      } else {
        dungeon.currentFloor -= 1;
        dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.length-1;
        this.save({dungeon: dungeon}, {dungeon: dungeon})
      }
    } else {
      dungeon.currentFloor += 1;
      dungeon.currentRoom = 0;
      this.save({dungeon: dungeon}, {dungeon: dungeon})
    }
  }

  moveRoom(id){
    let dungeon = {...this.state.dungeon};
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
    dungeon.floors[dungeon.currentFloor].rooms.some(room => {
      if(room.id === id){
        dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.indexOf(room);
        return true;
      }
      return false;
    });
    if(!dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared){
      let pc = {...this.state.pc};
      this.c.pcServices.regenHealth(pc);
      this.c.pcServices.regenEnergy(pc);
      this.save({pc: pc, dungeon: dungeon}, {dungeon: dungeon, pc:pc});
    }else {
      this.save({dungeon: dungeon}, {dungeon: dungeon});
    }
  }

  scene(scene, location){
    if(this.state.pc.location === location) {
      this.setState({scene: scene, menu: null});
    } else {
      let pc = {...this.state.pc}
      pc.location = location;
      this.save({pc: pc}, {pc: pc, scene: scene, menu: null})
    }
  }

  toDungeon(){
    let pc = {...this.state.pc};
    pc.location = "Dungeon";
    this.save({pc: pc}, {pc: pc});
  }

  setDungeon(id){
    let pc = {...this.state.pc};
    pc.currentDungeon = id;     
    this.save({pc: pc}, {pc: pc, generatingDungeon: true});
  }

  startRest(){
    let pc = {...this.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = false;
    pc.currency -= 250;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.toRest = true;
    this.setState({pc: pc, rested: this.state.rested +1});
  }

  async rest(){
    let dungeonData = await this.fetchNewDungeonBoard();
    let shopData = await this.fetchNewShop();
    let pc = dungeonData.pc;
    pc.currentDungeon = null;
    pc.currentFloor = -1;
    pc.currentRoom = -1;
    pc.currentShop = shopData.shop.id;
    this.save({pc: pc}, {shop: shopData.shop, dungeonBoard: dungeonData.dungeonBoard, pc: pc, loadingShop: true, dungeon: null});
  }

  eat(){
    let pc = {...this.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = true;
    pc.currency -= 100;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.save({pc: pc}, {pc: pc, eaten: this.state.eaten + 1});
  }

  lootAll(){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    for(let item of room.loot){
      item.addTo(pc);
    }
    room.loot = [];
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, menu: null});
  }

  loot(item){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    item.addTo(pc);
    item.removeFrom(room.loot);
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon});
  }

  shopStore(item){
    let pc = {...this.state.pc};
    let shop = {...this.state.shop};
    item.removeFromShop(shop.inventory);
    pc.currency -= item.cost*5;
    if(pc.currency < 0){
      pc.currency = 0;
    }
    item.addTo(pc);
    this.save({pc: pc, shop: shop}, {pc: pc, shop: shop});
  }

  shopPlayer(item){
    let pc = {...this.state.pc};
    let shop = {...this.state.shop};
    pc.currency += item.cost;
    item.removeFrom(pc.inventory);
    item.addToShop(shop);
    this.save({pc: pc, shop: shop}, {pc: pc, shop: shop});
  }

  inventory(item){
    let pc = {...this.state.pc};
    item.removeFrom(pc.inventory);
    item.doAction(this.c, this.state, pc); 
  }

  unequip(item){
    let pc = {...this.state.pc};
    item.addTo(pc);
    item.unequip(this.c, pc);
  }
  
  pointbuy(attribute){
    let pc = {...this.state.pc};
    pc.attributePoints > 0
      ? pc.attributePoints -= 1
      : pc.attributePoints = 0;
    pc[attribute] += 1;
    this.c.pcServices.updateStats(pc);
    this.save({pc: pc}, {pc: pc});
  }

}

export default App;


