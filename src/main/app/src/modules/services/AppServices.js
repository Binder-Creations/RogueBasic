import { v4 as uuidv4 } from 'uuid';
import c from '../../data/CommonProperties';
import Dungeon from "../components/Dungeon";
import ShopMenu from "../components/ShopMenu";
import InnMenu from "../components/InnMenu";
import TavernMenu from "../components/TavernMenu";
import PcServices from "../services/PcServices";
import ImageServices from "../services/ImageServices";
import * as ItemFactory from "../services/ItemFactory";
import CombatEngine from "../services/CombatEngine";
import ComponentFactory from "../services/ComponentFactory";
import { bindAll } from './Binder';

export default class AppServices {
  static #instance;
  static #isInternalConstructing = false;

  constructor(app){
    if(!AppServices.#isInternalConstructing) throw new TypeError("AppServices is not constructable");
    bindAll(this);
    this.app = app;
  }

  static getInstance(app){
    if(this.#instance) return this.#instance;

    if(app == undefined) return;

    this.#isInternalConstructing = true;
    this.#instance = new AppServices(app);
    this.#isInternalConstructing = false;

    this.#instance.componentFactory = ComponentFactory.getInstance();
    return this.#instance;
  }

  static startingPage(state){
    if(!state.returningUser) return "firstUser";
    if(state.characterId) return "game";
    if(state.playerId) return "home";
    return "login";
  }

  gameInitialization(){
    if(!this.isDataInitialized){
      this.initializeGameData(); 
    }

    if(!this.isImagesInitialized && this.app.state.startImageInitialization){
      this.initializeImages();
    }
  }

  gamePreRender(){
    if(this.isResting){
      this.appServices.rest()
    }

    this.checkCombat();
    this.setElements();
  }

  assignTempAccount(){
    document.cookie = "returning_user=true;max-age=99999999";
    let tempUUID = uuidv4();
    document.cookie = "player_id="+tempUUID+";max-age=99999999";
    fetch('/register/temp/'+ tempUUID, {method: 'POST'});
  }

  async initializeGameData(){
    try {
      let pc = await this.fetchPc(this.app.state.characterId);
      let dungeon = pc.currentDungeon ? await this.fetchDungeon(pc.currentDungeon) : null;
      let shop = pc.currentShop ? await this.fetchShop(pc.currentShop) : null;
      new PcServices(pc).updateStats();
      this.isDataInitialized = true;
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

    window.addEventListener("resize", this.updateWidth);
    this.setItemSortOrder();
    this.genDungeons();
    this.genShop();
  }

  sizeWindow(){
    let isWide = window.innerWidth >= window.innerHeight*2.1;
    c.appHeight = isWide ? window.innerHeight : window.innerWidth*0.47619047;
    c.appWidth = isWide ? window.innerHeight*2.1 : window.innerWidth;
    document.body.style.fontSize = c.appWidth*0.019 + "px";
  }

  isLoading(){
    if(this.app.state.loading){
      if(this.app.state.loadedImages >= c.imageMax && this.app.state.loadingDungeons && this.app.state.loadingShop) {
        this.setState({loading: false, loadingDungeons: false, loadingShop: false})
      }
      return true;
    } else {
      return false;
    }
  }

  updateTimeouts(){
    if(this.app.state.loading){
      setTimeout(() => {
        this.setState({loadedImages: ImageServices.getInstance().loadedImages})
      }, 33);
    }
    if(this.app.state.generatingDungeon){
      setTimeout(() => {
        if(this.app.state.generatingDungeon){
          this.setState({barPercent: (this.app.state.barPercent && this.app.state.barPercent < 100 ) ? this.app.state.barPercent + 20 : 20})
        }
      }, 2000);
    }
  }

  setItemSortOrder(){
    if(this.app.state.pc.characterClass === "Rogue"){
      c.itemSortOrder = ["currency", "potion", "consumable", "bow", "dagger", "headMedium", "bodyMedium", "neck", "back", "staff", "spellbook", "sword", "shield", "headLight", "bodyLight", "headHeavy", "bodyHeavy"];
    } else if (this.app.state.pc.characterClass === "Wizard"){
      c.itemSortOrder = ["currency", "potion", "consumable", "staff", "spellbook", "headLight", "bodyLight", "neck", "back", "bow", "dagger", "sword", "shield", "headMedium", "bodyMedium", "headHeavy", "bodyHeavy"];
    } else {
      c.itemSortOrder = ["currency", "potion", "consumable", "sword", "shield", "headHeavy", "bodyHeavy", "neck", "back", "staff", "spellbook", "bow", "dagger", "headLight", "bodyLight", "headMedium", "bodyMedium"];
    }
  }

  initializeImages(){
    this.isImagesInitialized = true;
    this.setClassImages();
    this.setEnvironmentImages();
    this.setMonsterImages();

    let imageServices = ImageServices.getInstance();
    c.imageMax = imageServices.countImages(this.app.state, c.images);
    imageServices.loadCommonImages(c.images);
    imageServices.loadCheckedImages(c.images.environment);
    imageServices.loadCheckedImages(c.images.monster);
    imageServices.loadPcImages(this.app.state.pc, c.images.items);
    imageServices.loadShopImages(this.app.state.shop, c.images.items);
    imageServices.loadDungeonItemImages(this.app.state.dungeon, c.images.items);
  }

  setClassImages(pc = this.app.state.pc){
    c.images.class = pc
      ? c.images.classes[pc.characterClass.toLowerCase()]
      : [];
  }

  setEnvironmentImages(dungeon = this.app.state.dungeon){
    c.images.environment = dungeon
      ? c.images.environments[dungeon.theme.toLowerCase()]
      : [];
  }

  setMonsterImages(dungeon = this.app.state.dungeon){
    c.images.monster = dungeon
      ? c.images.monsters[dungeon.theme.toLowerCase()]
      : [];
  }

  setElements(){
    this.innerElements = [];
    this.outerElements = [];
    c.disableUiMenus = false;
    c.homeButton = false;
    if (this.app.state.pc.location === "Town"){
      if(this.app.state.scene === "Default"){
        c.homeButton = true;
        this.innerElements.push(this.componentFactory.createComponent("exteriorBuildings"));
        this.outerElements.push(this.componentFactory.createComponent("helpButton"));
        if(this.app.state.help){
          this.outerElements.push(this.helpMenu(c.images.common.helpTown));
        }
      } else if(this.app.state.scene === "Tavern"){
        this.genDungeons();
        c.disableUiMenus = true;
        this.outerElements.push(<TavernMenu s={this.app.state}/>);
        if(this.app.state.generatingDungeon){
          this.outerElements.push(this.loadingBar())
        }
        this.outerElements.push(this.componentFactory.createComponent("helpButton"));
        if(this.app.state.help){
          this.outerElements.push(this.helpMenu(c.images.common.helpTavern, "tavern"));
        }
      } else if(this.app.state.scene==="Inn"){
        c.disableUiMenus = true;
        this.outerElements.push(<InnMenu s={this.app.state}/>);
      } else if(this.app.state.scene==="Shop"){
        c.disableUiMenus = true;
        this.outerElements.push(<ShopMenu s={this.app.state}/>);
      }
    } else if(this.app.state.pc.location === "Dungeon") {
      if(this.app.state.dungeon && this.app.state.dungeon.floors){
        this.innerElements.push(<Dungeon s={this.app.state}/>);
        this.outerElements.push(this.componentFactory.createComponent("helpButton"));
        if(this.app.state.help){
          this.outerElements.push( this.app.state.combat ? this.helpMenu(c.images.common.helpCombat, "combat") : this.helpMenu(c.images.common.helpDungeon));
        }
      }
    }
  }

  async fetchPc(id = this.app.state.characterId){
    let pc;
    await fetch('/pc/'+ id)
    .then(response => response.json())
    .then(data => {
      ItemFactory.parsePC(data);
      pc = this.sortPC(data);
    });
    return pc;
  }

  async fetchDungeon(id = this.app.state.pc.currentDungeon){
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
    await fetch('/dungeon/new/'+ this.app.state.pc.id)
    .then(response => response.json())
    .then(data => {
      let pc = {...this.app.state.pc};
      pc.dungeonBoard = [];
      data.forEach(dungeon => pc.dungeonBoard.push(dungeon.id))
      dungeonData = {pc: pc, dungeonBoard: data};
    });
    return dungeonData;
  }

  async fetchDungeonBoard(){
    let dungeonBoard;
    await fetch('/dungeon/getBoard/'+ this.app.state.pc.id)
    .then(response => response.json())
    .then(data => {
      dungeonBoard = data;
    });
    return dungeonBoard;
  }

  async fetchShop(id = this.app.state.pc.currentShop){
    let shop;
    await fetch('/shop/'+ id)
    .then(response => response.json())
    .then(data => {
      data.inventory = ItemFactory.parseArray(data.inventory);
      shop = data;
    });
    return shop;
  }

  async fetchNewShop(){
    let shopData;
    await fetch('/shop/new/'+ this.app.state.pc.id)
    .then(response => response.json())
    .then(data => {
      let pc = {...this.app.state.pc}
      pc.currentShop = data.id;
      data.inventory = ItemFactory.parseArray(data.inventory);
      shopData = {pc: pc, shop: data};
    });
    return shopData;
  }

  async genDungeons(){
    if(this.app.state.pc.currentDungeon && (!this.app.state.dungeon || this.app.state.dungeon.id !== this.app.state.pc.currentDungeon)){
      let dungeon = await this.fetchDungeon();
      if(this.isImagesInitialized){
        this.setEnvironmentImages(dungeon);
        this.setMonsterImages(dungeon);

        let imageServices = ImageServices.getInstance();
        imageServices.loadCheckedImages(c.images.environment);
        imageServices.loadCheckedImages(c.images.monster);
        imageServices.loadDungeonItemImages(dungeon, c.images.items);
      }
      this.setState({dungeon: dungeon, loadingDungeons: true, generatingDungeon: false, barPercent: 0});
    }
    if(!this.app.state.pc.dungeonBoard){
      let data = await this.fetchNewDungeonBoard();
      this.save({pc: data.pc}, {pc: data.pc, dungeonBoard: data.dungeonBoard, loadingDungeons: true});
    } 
    if(this.app.state.pc.dungeonBoard && (!this.app.state.dungeonBoard)){
      this.setState({dungeonBoard: await this.fetchDungeonBoard(), loadingDungeons: true});
    }     
    if(this.app.state.pc.dungeonBoard && this.app.state.dungeonBoard && !this.app.state.loadingDungeons){
      this.setState({loadingDungeons: true});
    }
  }

  async genShop(){
    if(this.app.state.pc.currentShop){
      if(!this.app.state.shop || this.app.state.shop.id !== this.app.state.pc.currentShop){
        let shop = await this.fetchShop();
        if(this.isImagesInitialized){
          ImageServices.getInstance().loadShopImages(shop, c.images.items);
        }
        this.setState({shop: shop, loadingShop: true});
      }
    } else {
      let data = await this.fetchNewShop();
      this.save({pc: data.pc}, {shop: data.shop, pc: data.pc, loadingShop: true})
    }
  }

  loadingBar(){
    return(
      <div className="loading-bar v-h-centered">
        <img className="background" src={c.images.common.barBackgroundLoading} alt="background"/>
        <img className="bar" src={c.images.common.barLoading} style={{width: (this.app.state.barPercent ? this.app.state.barPercent : 5) + "%"}} alt="load bar"/>
        <img className="background v-h-centered" src={c.images.common.barFrame} alt="load bar"/>
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
        <img className="close hover-saturate" src={c.images.common.buttonClose} alt="Close" onClick={this.app.help}/>
      </div>
    );
  }

  elipsis(){
    let elipsis = "";
    if(this.app.state.barPercent){
      for(let i = 0; i < this.app.state.barPercent/20; i++){
        elipsis += "."
      }
    }
    return elipsis
  }
  
  checkCombat(){
    if(!this.app.state.combat && this.app.state.pc.location === "Dungeon" && this.app.state.dungeon && this.app.state.dungeon.floors && this.app.state.dungeon.floors[this.app.state.dungeon.currentFloor].rooms[this.app.state.dungeon.currentRoom].monsters && this.app.state.dungeon.floors[this.app.state.dungeon.currentFloor].rooms[this.app.state.dungeon.currentRoom].monsters.length){
      this.combat();
    }
  }

  checkQuest(){
    return this.app.state.dungeon.floors.find(floor => floor.rooms.find(room => room.monsters && room.monsters.length > 0))
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
    this.setState({widthChange: this.app.state.widthChange + 1});
  }

  setState(state){
    this.checkCombatUpdateReset(state);
    this.app.setState(state);
  }

  checkCombatUpdateReset(state){
    if(this.app.state.combatUpdates.length > 0 && !state.combatUpdates){
      state.combatUpdates = []
    }
  }

  getNextPosition(){
    let nextPosition = null;
    let i = this.app.state.position;
    while(!nextPosition){
      nextPosition = this.app.state.dungeon.floors[this.app.state.dungeon.currentFloor].rooms[this.app.state.dungeon.currentRoom].monsters.find(monster => monster.position === c.positions[i])
      i++
      if(i >= c.positions.length){
        break;
      }
    }
    return nextPosition ? c.positions.indexOf(nextPosition.position) : 0;
  }

  help(){
    this.setState({help: this.app.state.help ? false : true});
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
        playerId: this.app.state.playerId, 
        characterId: this.app.state.characterId, 
        metacurrency: this.app.state.pc.metacurrency
      })
    })
    .then(response => { 
      document.cookie = "character_id=; Max-Age=-99999999;"
      this.setState({characterId: null, page: "home"})
    });
  }

  quest(index){
    let pc = {...this.app.state.pc};
    let dungeon = {...this.app.state.dungeon};
    pc.currency += this.app.state.dungeon.reward;
    pc.experience += Math.floor(this.app.state.dungeon.reward*2.5);
    new PcServices(pc).updateStats();
    pc.inventory.push(this.app.state.dungeon.rewardSet[index]);
    dungeon.rewardClaimed = true;
    this.save({pc: pc, dungeon: pc}, {pc:pc, dungeon: dungeon});
  }

  menu(menu){
    this.setState({menu: menu === this.app.state.menu ? null : menu});
  }

  ability(number){
    this.setState({
      abilityAnimation: number,
      renderAbilityAnimation: true
    });
  }

  pcCombat(index){
    let dungeon = {...this.app.state.dungeon}
    let combatEngine = CombatEngine.getInstance(this.app.state);

    document.documentElement.style.setProperty('--animate-duration', '1s');
    combatEngine.runRound(this.app.state.pc.abilities[index]);
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = combatEngine.monsters;
    this.setState({
      pc: combatEngine.pc,
      dungeon: dungeon,
      combatUpdates: combatEngine.combatUpdates,
      position: 1,
      renderAbilityAnimation: false
    });
  }

  nextCombat(){
    let dungeon = {...this.app.state.dungeon}
    let nextPosition = this.getNextPosition();
    let combatEngine = CombatEngine.getInstance(this.app.state);

    if(!nextPosition){
      combatEngine.endRound();
      let combatTransitions = combatEngine.monsters.length ? true : false;
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = combatEngine.monsters;
      this.setState({
        pc: combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: combatEngine.combatUpdates,
        combatTransitions: combatTransitions,
        position: 0
      });
    } else {
      combatEngine.runRound(null, nextPosition);
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = combatEngine.monsters;
      this.setState({
        pc: combatEngine.pc,
        dungeon: dungeon,
        combatUpdates: combatEngine.combatUpdates,
        position: nextPosition + 1
      });
    }
  }

  combat(){
    let dungeon = {...this.app.state.dungeon};
    let combatEngine = CombatEngine.getInstance(this.app.state);

    if(!this.app.state.combat){
      dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = combatEngine.monsters;
      this.setState({
        combat: true,
        dungeon: dungeon,
        combatTransitions: true
      });
    } else {
      let pc = {...this.app.state.pc};
      let pcServices = new PcServices(pc);
      pcServices.clearBuffs();
      pcServices.resetTempStats();
      pcServices.updateStats();
      CombatEngine.clearInstance()
      dungeon.questCompleted = this.checkQuest();
      this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, combat: false, position: 0});
    }    
  }

  stairs(up){
    let dungeon = {...this.app.state.dungeon};
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
    if(up){
      if(dungeon.currentFloor === 0){
        let pc = {...this.app.state.pc};
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
    let dungeon = {...this.app.state.dungeon};
    dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
    dungeon.floors[dungeon.currentFloor].rooms.some(room => {
      if(room.id === id){
        dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.indexOf(room);
        return true;
      }
      return false;
    });
    if(!dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared){
      let pc = {...this.app.state.pc};
      let pcServices = new PcServices(pc);
      pcServices.regenHealth();
      pcServices.regenEnergy();
      this.save({pc: pc, dungeon: dungeon}, {dungeon: dungeon, pc:pc});
    }else {
      this.save({dungeon: dungeon}, {dungeon: dungeon});
    }
  }

  scene(scene, location){
    if(this.app.state.pc.location === location) {
      this.setState({scene: scene, menu: null});
    } else {
      let pc = {...this.app.state.pc}
      pc.location = location;
      this.save({pc: pc}, {pc: pc, scene: scene, menu: null})
    }
  }

  toDungeon(){
    let pc = {...this.app.state.pc};
    pc.location = "Dungeon";
    this.save({pc: pc}, {pc: pc});
  }

  setDungeon(id){
    let pc = {...this.app.state.pc};
    pc.currentDungeon = id;     
    this.save({pc: pc}, {pc: pc, generatingDungeon: true});
  }

  startRest(){
    let pc = {...this.app.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = false;
    pc.currency -= 250;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.isResting = true;
    this.setState({pc: pc, rested: this.app.state.rested +1});
  }

  async rest(){
    this.isResting = false;
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
    let pc = {...this.app.state.pc};
    pc.currentHealth = pc.healthTotal;
    pc.currentEnergy = pc.energyTotal;
    pc.ate = true;
    pc.currency -= 100;
    pc.currency = pc.currency > 0 ? pc.currency : 0;
    this.save({pc: pc}, {pc: pc, eaten: this.app.state.eaten + 1});
  }

  lootAll(){
    let pc = {...this.app.state.pc};
    let dungeon = {...this.app.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    for(let item of room.loot){
      item.addTo(pc);
    }
    room.loot = [];
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon, menu: null});
  }

  loot(item){
    let pc = {...this.app.state.pc};
    let dungeon = {...this.app.state.dungeon};
    let room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom];
    item.addTo(pc);
    item.removeFrom(room.loot);
    this.save({pc: pc, dungeon: dungeon}, {pc: pc, dungeon: dungeon});
  }

  shopStore(item){
    let pc = {...this.app.state.pc};
    let shop = {...this.app.state.shop};
    item.removeFromShop(shop.inventory);
    pc.currency -= item.cost*5;
    if(pc.currency < 0){
      pc.currency = 0;
    }
    item.addTo(pc);
    this.save({pc: pc, shop: shop}, {pc: pc, shop: shop});
  }

  shopPlayer(item){
    let pc = {...this.app.state.pc};
    let shop = {...this.app.state.shop};
    pc.currency += item.cost;
    item.removeFrom(pc.inventory);
    item.addToShop(shop);
    this.save({pc: pc, shop: shop}, {pc: pc, shop: shop});
  }

  inventory(item){
    let pc = {...this.app.state.pc};
    item.removeFrom(pc.inventory);
    item.doAction(this.app.state, pc); 
  }

  unequip(item){  
    if(item.id !== c.zeroId.id){
      let pc = {...this.app.state.pc};
      item.addTo(pc);
      item.unequip(pc);
    }
  }
  
  pointbuy(attribute){
    let pc = {...this.app.state.pc};
    pc.attributePoints > 0
      ? pc.attributePoints -= 1
      : pc.attributePoints = 0;
    pc[attribute] += 1;
    new PcServices(pc).updateStats();
    this.save({pc: pc}, {pc: pc});
  }

}
