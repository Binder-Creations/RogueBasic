import { v4 as uuidv4 } from 'uuid';
import c from "./data/CommonProperties";
import Dungeon from "./modules/components/Dungeon";
import ShopMenu from "./modules/components/ShopMenu";
import InnMenu from "./modules/components/InnMenu";
import TavernMenu from "./modules/components/TavernMenu";
import Binder from "./modules/services/Binder";
import PcServices from "./modules/services/PcServices";
import ImageServices from "./modules/services/ImageServices";
import ItemFactory from "./modules/services/ItemFactory";
import CombatEngine from "./modules/services/CombatEngine";
import App from '../../App';

class AppServices {
  static app;
  static state;

  static initialize(app){
    this.app = app;
    this.state = app.state;
    Binder.bind(this);
  }

  static assignTempAccount(){
    this.app.showTempScreen = true;
    document.cookie = "returning_user=true;max-age=99999999";
    let tempUUID = uuidv4();
    document.cookie = "player_id="+tempUUID+";max-age=99999999";
    fetch('/register/temp/'+ tempUUID, {method: 'POST'})
    .then(response => {
      this.setState({routeHome: true});
    });  
  }

  static async initialize(){
    this.app.initialized = true;
    
    try {
      let pc = await this.fetchPc(this.state.characterId);
      let dungeon = pc.currentDungeon ? await this.fetchDungeon(pc.currentDungeon) : null;
      let shop = pc.currentShop ? await this.fetchShop(pc.currentShop) : null;
      this.setState({
        pc: pc, 
        dungeon:dungeon,
        loadingDungeons: dungeon ? true : false,
        shop: shop,
        loadingShop: shop ? true : false, 
        startImageInitialization: true});
    } catch(e) {
      this.setState({characterId: null, playerId: null});
    } 
    c.pcServices = new PcServices(this.state.pc.characterClass);
    c.pcServices.updateStats(this.state.pc);
    window.addEventListener("resize", this.updateWidth);
    this.setItemSortOrder();
    this.genDungeons();
    this.genShop();
  }

  static sizeWindow(){
    let isWide = window.innerWidth >= window.innerHeight*2.1;
    c.appHeight = isWide ? window.innerHeight : window.innerWidth*0.47619047;
    c.appWidth = isWide ? window.innerHeight*2.1 : window.innerWidth;
    document.body.style.fontSize = c.appWidth*0.019 + "px";
  }

  static setItemSortOrder(){
    if(this.state.pc.characterClass === "Rogue"){
      c.itemSortOrder = ["currency", "potion", "consumable", "bow", "dagger", "headMedium", "bodyMedium", "neck", "back", "staff", "spellbook", "sword", "shield", "headLight", "bodyLight", "headHeavy", "bodyHeavy"];
    } else if (this.state.pc.characterClass === "Wizard"){
      c.itemSortOrder = ["currency", "potion", "consumable", "staff", "spellbook", "headLight", "bodyLight", "neck", "back", "bow", "dagger", "sword", "shield", "headMedium", "bodyMedium", "headHeavy", "bodyHeavy"];
    } else {
      c.itemSortOrder = ["currency", "potion", "consumable", "sword", "shield", "headHeavy", "bodyHeavy", "neck", "back", "staff", "spellbook", "bow", "dagger", "headLight", "bodyLight", "headMedium", "bodyMedium"];
    }
  }

  static initializeImages(){
    this.app.imagesInitialized = true;
    this.setClassImages();
    this.setEnvironmentImages();
    this.setMonsterImages();
    c.imageMax = ImageServices.countImages(this.state, c.images);
    ImageServices.loadCommonImages(c.images);
    ImageServices.loadCheckedImages(c.images.environment);
    ImageServices.loadCheckedImages(c.images.monster);
    ImageServices.loadPcImages(this.state.pc, c.images.items);
    ImageServices.loadShopImages(this.state.shop, c.images.items);
    ImageServices.loadDungeonItemImages(this.state.dungeon, c.images.items);
  }

  static setClassImages(pc = this.state.pc){
    c.images.class = pc
      ? c.images.classes[pc.characterClass.toLowerCase()]
      : [];
  }

  static setEnvironmentImages(dungeon = this.state.dungeon){
    c.images.environment = dungeon
      ? c.images.environments[dungeon.theme.toLowerCase()]
      : [];
  }

  static setMonsterImages(dungeon = this.state.dungeon){
    c.images.monster = dungeon
      ? c.images.monsters[dungeon.theme.toLowerCase()]
      : [];
  }

  static setElements(){
    this.app.innerElements = [];
    this.app.outerElements = [];
    c.disableUiMenus = false;
    c.homeButton = false;
    if (this.state.pc.location === "Town"){
      if(this.state.scene === "Default"){
        this.app.backgroundSrc = c.images.common.town;
        c.homeButton = true;
        this.app.innerElements.push(this.app.exteriorBuildings);
        this.app.outerElements.push(this.app.helpButton);
        if(this.state.help){
          this.app.outerElements.push(this.app.helpMenu(c.images.common.helpTown));
        }
      } else if(this.state.scene === "Tavern"){
        this.genDungeons();
        c.disableUiMenus = true;
        this.app.backgroundSrc = c.images.common.tavern;
        this.app.outerElements.push(<TavernMenu s={this.state}/>);
        if(this.state.generatingDungeon){
          this.app.outerElements.push(this.app.loadingBar())
        }
        this.app.outerElements.push(this.app.helpButton);
        if(this.state.help){
          this.app.outerElements.push(this.app.helpMenu(c.images.common.helpTavern, "tavern"));
        }
      } else if(this.state.scene==="Inn"){
        c.disableUiMenus = true;
        this.app.backgroundSrc = c.images.common.inn;
        this.app.outerElements.push(<InnMenu s={this.state}/>);
      } else if(this.state.scene==="Shop"){
        c.disableUiMenus = true;
        this.app.backgroundSrc = c.images.common.shop;
        this.app.outerElements.push(<ShopMenu s={this.state}/>);
      }
    } else if(this.state.pc.location === "Dungeon") {
      if(this.state.dungeon && this.state.dungeon.floors){
        this.app.backgroundSrc = c.images.environment["background" + this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].variant];
        this.app.innerElements.push(<Dungeon s={this.state}/>);
        this.app.outerElements.push(this.app.helpButton);
        if(this.state.help){
          this.app.outerElements.push( this.state.combat ? this.app.helpMenu(c.images.common.helpCombat, "combat") : this.app.helpMenu(c.images.common.helpDungeon));
        }
      } else {
        this.app.backgroundSrc = c.images.common.tavern
      }
    } else {
      this.app.backgroundSrc = c.images.common.town
    }
  }

  static async fetchPc(id = this.state.characterId){
    let pc;
    await fetch('/pc/'+ id)
    .then(response => response.json())
    .then(data => {
      ItemFactory.parsePC(data);
      pc = this.sortPC(data);
    });
    return pc;
  }

  static async fetchDungeon(id = this.state.pc.currentDungeon){
    let dungeon;
    await fetch('/dungeon/'+ id)
    .then(response => response.json())
    .then(data => {
      ItemFactory.parseDungeon(data);
      dungeon = this.sortDungeon(data);
    });
    return dungeon;
  }

  static async fetchNewDungeonBoard(){
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

  static async fetchDungeonBoard(){
    let dungeonBoard;
    await fetch('/dungeon/getBoard/'+ this.state.pc.id)
    .then(response => response.json())
    .then(data => {
      dungeonBoard = data;
    });
    return dungeonBoard;
  }

  static async fetchShop(id = this.state.pc.currentShop){
    let shop;
    await fetch('/shop/'+ id)
    .then(response => response.json())
    .then(data => {
      data.inventory = ItemFactory.parseArray(data.inventory);
      shop = data;
    });
    return shop;
  }

  static async fetchNewShop(){
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

  static async genDungeons(){
    if(this.state.pc.currentDungeon && (!this.state.dungeon || this.state.dungeon.id !== this.state.pc.currentDungeon)){
      let dungeon = await this.fetchDungeon();
      if(this.app.imagesInitialized){
        this.setEnvironmentImages(dungeon);
        this.setMonsterImages(dungeon);
        ImageServices.loadCheckedImages(c.images.environment);
        ImageServices.loadCheckedImages(c.images.monster);
        ImageServices.loadDungeonItemImages(dungeon, c.images.items);
      }
      this.setState({dungeon: dungeon, loadingDungeons: true, generatingDungeon: false, barPercent: 0});
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

  static async genShop(){
    if(this.state.pc.currentShop){
      if(!this.state.shop || this.state.shop.id !== this.state.pc.currentShop){
        let shop = await this.fetchShop();
        if(this.app.imagesInitialized){
          ImageServices.loadShopImages(shop, c.images.items);
        }
        this.setState({shop: shop, loadingShop: true});
      }
    } else {
      let data = await this.fetchNewShop();
      this.save({pc: data.pc}, {shop: data.shop, pc: data.pc, loadingShop: true})
    }
  }

  static loadingBar(){
    return(
      <div className="loading-bar v-h-centered">
        <img className="background" src={c.images.common.barBackgroundLoading} alt="background"/>
        <img className="bar" src={c.images.common.barLoading} style={{width: (this.state.barPercent ? this.state.barPercent : 5) + "%"}} alt="load bar"/>
        <img className="background v-h-centered" src={c.images.common.barFrame} alt="load bar"/>
        <p className="v-h-centered">
          {"Generating Dungeon" + this.elipsis()}
        </p>
      </div>
    );
  }

  static helpMenu(src, classExtension = ""){
    return(
      <div className={"help-menu-" + classExtension}>
        <img src={src} className="background" alt="Help Menu"/>
        <img className="close hover-saturate" src={c.images.common.buttonClose} alt="Close" onClick={this.app.help}/>
      </div>
    );
  }

  static elipsis(){
    let elipsis = "";
    if(this.state.barPercent){
      for(let i = 0; i < this.state.barPercent/20; i++){
        elipsis += "."
      }
    }
    return elipsis
  }
  
  static checkCombat(){
    if(!this.state.combat && this.state.pc.location === "Dungeon" && this.state.dungeon && this.state.dungeon.floors && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.length){
      this.combat();
    }
  }

  static checkQuest(){
    return this.state.dungeon.floors.find(floor => floor.rooms.find(room => room.monsters && room.monsters.length > 0))
      ? false
      : true;
  }

  static sortDungeon(dungeon){     
    for(let floor of dungeon.floors){
      floor.rooms.sort((a,b) => a.count - b.count);
    }
    dungeon.floors.sort((a,b) => a.level - b.level);
    return dungeon;
  }

  static sortPC(pc){
    pc.abilities.sort((a,b) => a.level - b.level);
    return pc;
  }

  static async save(data, state){
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

  static updateWidth(){
    this.setState({widthChange: this.state.widthChange + 1});
  }

  static setState(state){
    if(this.state.combatUpdates.length > 0 && !state.combatUpdates){
      state.combatUpdates = []
    }
    app.setState(state);
  }

  static getNextPosition(){
    let nextPosition = null;
    let i = this.state.position;
    while(!nextPosition){
      nextPosition = this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.find(monster => monster.position === c.positions[i])
      i++
      if(i >= c.positions.length){
        break;
      }
    }
    return nextPosition ? c.positions.indexOf(nextPosition.position) : 0;
  }

  static help(){
    this.setState({help: this.state.help ? false : true});
  }

  static gameOver(update){
    this.setState({gameOver: update});
  }

  static endGame(){
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

  static quest(index){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    pc.currency += this.state.dungeon.reward;
    pc.experience += Math.floor(this.state.dungeon.reward*2.5);
    c.pcServices.updateStats(pc);
    pc.inventory.push(this.state.dungeon.rewardSet[index]);
    dungeon.rewardClaimed = true;
    this.save({pc: pc, dungeon: pc}, {pc:pc, dungeon: dungeon});
  }

  static menu(menu){
    this.setState({menu: menu === this.state.menu ? null : menu});
  }

  static ability(number){
    this.setState({
      abilityAnimation: number,
      renderAbilityAnimation: true
    });
  }

  static pcCombat(index){
    let dungeon = {...this.state.dungeon}
    document.documentElement.style.setProperty('--animate-duration', '1s');
    c.combatEngine.runRound(this.state.pc.abilities[index]);
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = c.combatEngine.monsters;
    this.setState({
      pc: c.combatEngine.pc,
      dungeon: dungeon,
      combatUpdates: c.combatEngine.combatUpdates,
      position: 1,
      renderAbilityAnimation: false
    });
  }

  static nextCombat(){
    let dungeon = {...this.state.dungeon}
    let nextPosition = this.getNextPosition();
    if(!nextPosition){
      c.combatEngine.endRound();
      let combatTransitions = c.combatEngine.monsters.length ? true : false;
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = c.combatEngine.monsters;
      this.setState({
        pc: c.combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: c.combatEngine.combatUpdates,
        combatTransitions: combatTransitions,
        position: 0
      });
    } else {
      c.combatEngine.runRound(null, nextPosition);
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = c.combatEngine.monsters;
      this.setState({
        pc: c.combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: c.combatEngine.combatUpdates,
        position: nextPosition + 1
      });
    }
  }

  static combat(){
    let dungeon = {...this.state.dungeon};
    if(!this.state.combat){
      c.combatEngine = new CombatEngine(this.state);
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = c.combatEngine.monsters;
      this.setState({
        combat: true,
        dungeon: dungeon,
        combatTransitions: true
      });
    } else {
      let pc = {...this.state.pc}
      c.pcServices.clearBuffs(pc);
      c.pcServices.resetTempStats(pc);
      c.pcServices.updateStats(pc);
      c.combatEngine = null;
      dungeon.questCompleted = this.checkQuest();
      this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, combat: false, position: 0});
    }    
  }

  static stairs(up){
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

  static moveRoom(id){
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
      c.pcServices.regenHealth(pc);
      c.pcServices.regenEnergy(pc);
      this.save({pc: pc, dungeon: dungeon}, {dungeon: dungeon, pc:pc});
    }else {
      this.save({dungeon: dungeon}, {dungeon: dungeon});
    }
  }

  static scene(scene, location){
    if(this.state.pc.location === location) {
      this.setState({scene: scene, menu: null});
    } else {
      let pc = {...this.state.pc}
      pc.location = location;
      this.save({pc: pc}, {pc: pc, scene: scene, menu: null})
    }
  }

  static toDungeon(){
    let pc = {...this.state.pc};
    pc.location = "Dungeon";
    this.save({pc: pc}, {pc: pc});
  }

  static setDungeon(id){
    let pc = {...this.state.pc};
    pc.currentDungeon = id;     
    this.save({pc: pc}, {pc: pc, generatingDungeon: true});
  }

  static startRest(){
    let pc = {...this.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = false;
    pc.currency -= 250;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.app.toRest = true;
    this.setState({pc: pc, rested: this.state.rested +1});
  }

  static async rest(){
    this.app.toRest = false;
    let dungeonData = await this.fetchNewDungeonBoard();
    let shopData = await this.fetchNewShop();
    let pc = dungeonData.pc;
    pc.currentDungeon = null;
    pc.currentFloor = -1;
    pc.currentRoom = -1;
    pc.currentShop = shopData.shop.id;
    this.save({pc: pc}, {shop: shopData.shop, dungeonBoard: dungeonData.dungeonBoard, pc: pc, loadingShop: true, dungeon: null});
  }

  static eat(){
    let pc = {...this.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = true;
    pc.currency -= 100;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.save({pc: pc}, {pc: pc, eaten: this.state.eaten + 1});
  }

  static lootAll(){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    for(let item of room.loot){
      item.addTo(pc);
    }
    room.loot = [];
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, menu: null});
  }

  static loot(item){
    let pc = {...this.state.pc};
    let dungeon = {...this.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    item.addTo(pc);
    item.removeFrom(room.loot);
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon});
  }

  static shopStore(item){
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

  static shopPlayer(item){
    let pc = {...this.state.pc};
    let shop = {...this.state.shop};
    pc.currency += item.cost;
    item.removeFrom(pc.inventory);
    item.addToShop(shop);
    this.save({pc: pc, shop: shop}, {pc: pc, shop: shop});
  }

  static inventory(item){
    let pc = {...this.state.pc};
    item.removeFrom(pc.inventory);
    item.doAction(this.state, pc); 
  }

  static unequip(item){  
    if(item.id !== c.zeroId.id){
      let pc = {...this.state.pc};
      item.addTo(pc);
      item.unequip(pc);
    }
  }
  
  static pointbuy(attribute){
    let pc = {...this.state.pc};
    pc.attributePoints > 0
      ? pc.attributePoints -= 1
      : pc.attributePoints = 0;
    pc[attribute] += 1;
    c.pcServices.updateStats(pc);
    this.save({pc: pc}, {pc: pc});
  }

}

export default AppServices;
export let AppState = AppServices.state;