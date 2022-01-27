import React from "react";
import {Route, BrowserRouter as Router} from "react-router-dom";
import { v4 as uuidv4 } from 'uuid';
import Ui from "./modules/Ui"
import ShopMenu from "./modules/ShopMenu";
import InnMenu from "./modules/InnMenu";
import TavernMenu from "./modules/TavernMenu";
import PcServices from "./modules/PcServices";
import ItemServices from "./modules/ItemServices";
import Dungeon from "./modules/Dungeon";
import LoadScreen from "./modules/LoadScreen";
import * as abilities from "./images/abilities" 
import * as abilitiesSmall from "./images-small/abilities";
import * as images from "./images" 
import * as imagesSmall from "./images-small";
import * as items from "./images/items" 
import * as itemsSmall from "./images-small/items";
import * as monsters from "./images/monsters" 
import * as monstersSmall from "./images-small/monsters";
import CombatEngine from "./modules/CombatEngine";

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      pc: {name: null},
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
    if(this.state.characterId){
      try {
        fetch('/pc/'+ this.state.characterId)
        .then(response => response.json())
        .then(data => {
          this.setStateCustom({pc: this.sortPC(data)})
        });
      } catch(e) {
        this.setStateCustom({characterId: null, playerId: null})
      } 
    }
    this.returningUser = document.cookie.replace(/(?:(?:^|.*;\s*)returning_user\s*=\s*([^;]*).*$)|^.*$/, "$1");
    this.combat = false;
    this.imageMax = 498;
    this.pcServices = new PcServices(this.state.pc.characterClass);
    this.appState = this.appState.bind(this);
    this.updateWidth = this.updateWidth.bind(this);
    this.sortDungeon = this.sortDungeon.bind(this);
    this.sortPC = this.sortPC.bind(this);
    this.loadImage = this.loadImage.bind(this);
    this.preloadImages = this.preloadImages.bind(this);
    this.genDungeons = this.genDungeons.bind(this);
    this.genShop = this.genShop.bind(this);
    this.rest = this.rest.bind(this);

    this.props.props = {
      pc: this.state.pc,
      combat: this.state.combat,
      abilities: window.innerWidth > 960 ? abilities : abilitiesSmall,
      images: window.innerWidth > 960 ? images : imagesSmall, 
      items: window.innerWidth > 960 ? items : itemsSmall,
      monsters: window.innerWidth > 960 ? monsters : monstersSmall, 
      appState: this.appState,
      gameOver: false,
      positions: ["pc", "frontLeft", "frontCenter", "frontRight", "backLeft", "backCenter", "backRight"],
      colorArmor: {color: "#136f9b"},
      colorPower: {color: "#a83a0e"},
      colorHeal: {color: "#ce6eb9"},
      colorEnergize: {color: "#2a52be"}
    }

    this.props.props.itemServices = new ItemServices({
      images: window.innerWidth > 960 ? images : imagesSmall, 
      items: window.innerWidth > 960 ? items : itemsSmall, 
      monsters: window.innerWidth > 960 ? monsters : monstersSmall,
      colorArmor: this.props.props.colorArmor,
      colorPower: this.props.props.colorPower,
      colorHeal: this.props.props.colorHeal,
      colorEnergize: this.props.props.colorEnergize,
      colorCommon: "#39363a",
      colorUncommon: "#4d610f",
      colorRare: "#1e4d7a",
      colorEpic: "#4e0f62",
      colorShopCommon: "#d2d2d2",
      colorShopUncommon: "#b4de31",
      colorShopRare: "#3296f6",
      colorShopEpic: "#c111f9"
    });

    this.disableUiMenus = false;
    this.backgroundSrc = "";
    this.innerElements = []
    this.outerElements = []
    this.helpButton = 
      <button className="btn-help" onClick={ () => {this.appState("help")} }>
        <img src={images.helpIcon} alt="Help"/>
      </button>
    this.InnerElements = () => {
      return(this.innerElements);
    }
    this.OuterElements = () => {
      return(this.outerElements);
    }
  }

  render(){
    if(this.state.routeHome){
      return(
        <Router>
          <Route path='/' component={() => { 
            window.location.href = '/home'; 
            return null;
          }}/>
        </Router>
      );
    }
    if (!this.state.characterId || !this.state.playerId){
      if(!this.returningUser){
        document.cookie = "returning_user=true;max-age=99999999";
        let tempUUID = uuidv4();
        document.cookie = "player_id="+tempUUID+";max-age=99999999";
        fetch('/register/'+ tempUUID, {method: 'POST'})
        .then(response => {
          this.setState({routeHome: true})
        });     
      } else {
        return(
          <Router>
            <Route path='/' component={() => { 
              window.location.href = '/login'; 
              return null;
            }}/>
          </Router>
        );
      }
    }
    if(this.state.scene==="Home"){
        return(
          <Router>
            <Route path='/' component={() => { 
              window.location.href = '/home'; 
              return null;
            }}/>
          </Router>
        );
      }
    if(!this.state.pc.name){
      return(<></>)
    }
    if(!this.initialize){
      this.initialize = true;
      this.pcServices.updateStats(this.state.pc);
      window.addEventListener("resize", this.updateWidth)
      this.preloadImages();
      this.genDungeons();
      this.genShop();
    }

    if(this.restCheck){
      this.restCheck = false;
      this.rest()
    }

    let isWide = window.innerWidth >= window.innerHeight*2.1;
    this.props.props.appHeight = isWide ? window.innerHeight : window.innerWidth*0.47619047;
    this.props.props.appWidth = isWide ? window.innerHeight*2.1 : window.innerWidth;
    document.body.style.fontSize = this.props.props.appWidth*0.019 + "px";

    console.log(this.state)

    if(this.state.loading){
      if(this.state.loadingImages >= this.imageMax && this.state.loadingDungeons && this.state.loadingShop) {
        this.setState({loading: false, loadingImages: 0, loadingDungeons: false, loadingShop: false})
      } else {
        return(<LoadScreen props={this.props.props} imageCount={this.state.loadingImages} loadingDungeons={this.state.loadingDungeons} loadingShop={this.state.loadingShop} imageMax={this.imageMax}/>)
      }
    }

    this.checkCombat();

    this.props.props.pc = this.state.pc;
    this.props.props.combat = this.state.combat;
    this.props.props.combatUpdates = this.state.combatUpdates;
    this.props.props.widthChange = this.state.widthChange;
    this.props.props.position = this.state.position;
    this.props.props.combatTransitions = this.state.combatTransitions;
    this.props.props.abilityAnimation = this.state.abilityAnimation;
    this.props.props.renderAbilityAnimation = this.state.renderAbilityAnimation;
    this.props.props.menu = this.state.menu;
    this.props.props.questComplete = this.state.questComplete;
    this.props.props.gameOver = this.state.gameOver;
    
    this.innerElements = []
    this.outerElements = []
    this.disableUiMenus = false;
    let button = "TownButton";

    if (this.state.pc.location === "Town"){
      if(this.state.scene === "Default"){
        this.backgroundSrc = this.props.props.images.town
        button = "HomeButton"
        this.innerElements.push(
          <>
            <input className="tavern" type="image" src={this.props.props.images.tavernExterior} alt="Tavern" onClick={ () => { this.setStateCustom({scene:"Tavern"})} }/>
            <input className="inn" type="image" src={this.props.props.images.innExterior} alt="Inn" onClick={ () => { this.setStateCustom({scene:"Inn"})} }/>
            <input className="shop" type="image" src={this.props.props.images.shopExterior} alt="Shop" onClick={ () => { this.setStateCustom({scene:"Shop"})} }/>
          </>
        );
        this.outerElements.push(this.helpButton);
        if(this.state.help){
          this.outerElements.push(this.helpMenu(images.helpTown));
        }
      } else if(this.state.scene === "Tavern"){
        this.genDungeons();
        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.tavern;
        this.outerElements.push( 
          <>
            <TavernMenu props={this.props.props} dungeonBoard={this.state.dungeonBoard} dungeon={this.state.dungeon}/>
          </>
        );
        if(this.state.generatingDungeon){
          let elipsis = "";
          if(this.state.barPercent){
            for(let i = 0; i < this.state.barPercent/20; i++){
              elipsis += "."
            }
          }
          this.outerElements.push(
            <div className="loading-bar v-h-centered">
              <img className="background" src={this.props.props.images.barBackgroundLoading}/>
              <img className="bar" src={this.props.props.images.barLoading} style={{width: (this.state.barPercent ? this.state.barPercent : 5) + "%"}}/>
              <img className="background v-h-centered" src={this.props.props.images.barFrame}/>
              <p className="v-h-centered">
                {
                  "Generating Dungeon" + elipsis
                }
              </p>
            </div>
          )
        }
        this.outerElements.push(this.helpButton);
        if(this.state.help){
          this.outerElements.push(this.helpMenu(images.helpTavern, "tavern"));
        }
      } else if(this.state.scene==="Inn"){
        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.inn
        this.outerElements.push(
          <>
            <InnMenu props={this.props.props} eaten={this.state.eaten} rested={this.state.rested}/>
          </>
        );
      } else if(this.state.scene==="Shop"){
        this.disableUiMenus = true;
        this.backgroundSrc = this.props.props.images.shop
        this.outerElements.push(
          <>
            <ShopMenu props={this.props.props} shop={this.state.shop}/>
          </>
        );
      }
    } else if(this.state.pc.location === "Dungeon") {
      if(!this.state.dungeon){
        fetch('/dungeon/' + this.state.pc.currentDungeon)
          .then(response => response.json())
          .then(data => {
            this.setStateCustom({dungeon: this.sortDungeon(data)});
        });
        return(<></>)
      }
      if(!this.state.dungeon.floors && !this.state.dungeon.floorIds){
        fetch('/dungeon/' + this.state.dungeon.id)
          .then(response => response.json())
          .then(data => {
            this.setStateCustom({dungeon: this.sortDungeon(data)});
        });
      } else if(!this.state.dungeon.floors){
        fetch('/dungeon/convert/' + this.state.dungeon.id)
          .then(response => response.json())
          .then(data => {
            this.setStateCustom({dungeon: this.sortDungeon(data)});
        });
      }

      if(this.state.dungeon && this.state.dungeon.floors){
        this.backgroundSrc = this.props.props.images[this.state.dungeon.theme.toLowerCase() + this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].variant];
        this.innerElements.push(
          <Dungeon props={this.props.props} dungeon={this.state.dungeon} pcServices={this.pcServices}/>
        );
        this.outerElements.push(this.helpButton);
        if(this.state.help){
          this.outerElements.push( this.state.combat ? this.helpMenu(images.helpCombat, "combat") : this.helpMenu(images.helpDungeon));
        }
      } else {
        this.backgroundSrc = this.props.props.images.tavern
      }
    } else {
      this.backgroundSrc = this.props.props.images.town
    }
    
    return(
    <div className="app-container v-h-centered" style={{backgroundImage: "url("+this.backgroundSrc+")", height: this.props.props.appHeight + "px", width: this.props.props.appWidth + "px"}}>
      <this.InnerElements/>
      <Ui props={this.props.props} dungeon={this.state.dungeon} button={button} disableUiMenus={this.disableUiMenus}/>
      <this.OuterElements/>
    </div>
    );
    }

    componentDidUpdate(){
      if(this.state.loading){
        setTimeout(() => {
          this.setState({loadingImages: this.loadingImages})
        }, 33);
      }
      if(this.state.generatingDungeon){
        setTimeout(() => {
          if(this.state.generatingDungeon){
            this.setState({barPercent: this.state.barPercent ? this.state.barPercent + 20 : 20})
          }
        }, 2000);
      }
    }

    loadImage(image){
      return new Promise(resolve => {
        resolve(new Image().src = image);
      })
    }

    preloadImages() {
      this.loadingImages = 0;
      Object.keys(this.props.props.abilities).forEach((image) => {
        new Image().src = this.props.props.abilities[image];
        this.loadingImages++;
      });
      Object.keys(this.props.props.images).forEach((image) => {
        new Image().src = this.props.props.images[image]
        this.loadingImages++;
      });
      Object.keys(this.props.props.items).forEach((type) => {
        Object.keys(this.props.props.items[type]).forEach((item) => {
          new Image().src = this.props.props.items[type][item]
          this.loadingImages++;
        });
      });
      Object.keys(this.props.props.monsters).forEach((type) => {
        Object.keys(this.props.props.monsters[type]).forEach((item) => {
          new Image().src = this.props.props.monsters[type][item]
          this.loadingImages++;
        });
      });
    }

    genDungeons(){
      if(this.state.pc.currentDungeon && (!this.state.dungeon || this.state.dungeon.id !== this.state.pc.currentDungeon)){
        fetch('/dungeon/'+ this.state.pc.currentDungeon)
        .then(response => response.json())
        .then(data => {
          console.log("here2")
          this.setStateCustom({dungeon: this.sortDungeon(data), loadingDungeons: true, generatingDungeon: false, barPercent: 0});
        });
      }
      if(!this.state.pc.dungeonBoard){
        fetch('/dungeon/new/'+ this.state.pc.id)
        .then(response => response.json())
        .then(data => {
          let pc = {...this.state.pc};
          pc.dungeonBoard = [];
          data.forEach(dungeon => pc.dungeonBoard.push(dungeon.id))
          this.save(["pc"], [pc], {pc: pc, dungeonBoard: data, loadingDungeons: true});
        });
      } 
      if(this.state.pc.dungeonBoard && (!this.state.dungeonBoard)){
        fetch('/dungeon/getBoard/'+ this.state.pc.id)
          .then(response => response.json())
          .then(data => {
            this.setStateCustom({dungeonBoard: data, loadingDungeons: true});
          });
      }     
      if(this.state.pc.dungeonBoard && this.state.dungeonBoard && !this.state.loadingDungeons){
        this.setStateCustom({loadingDungeons: true});
      }
    }

    genShop(){
      if(this.state.pc.currentShop){
        if(!this.state.shop){
          fetch('/shop/'+ this.state.pc.currentShop)
            .then(response => response.json())
            .then(data => {
              this.setStateCustom({shop: data, loadingShop: true});
            });
        } else if(this.state.shop.id !== this.state.pc.currentShop){
          fetch('/shop/'+ this.state.pc.currentShop)
            .then(response => response.json())
            .then(data => {
              this.setStateCustom({shop: data, loadingShop: true});
            });
        }
      } else {
        fetch('/shop/new/'+ this.state.pc.id)
          .then(response => response.json())
          .then(data => {
            let pc = {...this.state.pc}
            pc.currentShop = data.id;
            this.save(["pc"], [this.state.pc], {shop: data, pc: pc, loadingShop: true});
          });
      }
    }

    helpMenu(src, classMod){
      return(
        <div className={"help-menu" + (classMod ? "-"+classMod : "")}>
          <img src={src} className="background" alt="Help Menu"/>
          <img className="close hover-saturate" src={images.buttonClose} alt="Close" onClick={() => this.appState("help")}/>
        </div>
      );
    }

    checkCombat(){
      if(!this.state.combat && this.state.pc.location === "Dungeon" && this.state.dungeon && this.state.dungeon.floors && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters && this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.length){
        this.appState("combat");
      }
    }

    checkQuest(){
      return this.state.dungeon.floors.find(
        floor => floor.rooms.find(
        room => room.monsters && room.monsters.length > 0
        )
      )
        ? false
        : true;
    }

    sortDungeon(dungeon){     
      dungeon.floors.forEach(floor=>floor.rooms.sort((a,b) => (a.count < b.count) ? -1 : (b.count < a.count ? 1 : 0)));
      dungeon.floors.sort((a,b) => (a.level < b.level) ? -1 : (b.level < a.level ? 1 : 0));
      return dungeon;
    }

    sortPC(pc){
      pc.abilities.sort((a,b) => (a.level < b.level) ? -1 : (b.level < a.level ? 1 : 0));
      return pc;
    }

    async save(types, objects, state){
      for(let i=0; i<types.length; i++){
        await fetch('/'+types[i]+'/', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(objects[i])
        });
      }
      if(state){
        this.setStateCustom(state);
      }
      return;
    }

    updateWidth(){
      this.setStateCustom({widthChange: this.state.widthChange + 1});
    }

    setStateCustom(state){
      if(!state.combatUpdates){
        state.combatUpdates = []
      }
      this.setState(state);
    }

    getNextPosition(){
      let nextPosition = null;
      let i = this.state.position;
      while(!nextPosition){
        nextPosition = this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters.find(monster => monster.position === this.props.props.positions[i])
        i++
        if(i >= this.props.props.positions.length){
          break;
        }
      }
      return nextPosition ? this.props.props.positions.indexOf(nextPosition.position) : 0;
    }

    rest(){
      let pc = {...this.state.pc};
      pc.currentHealth = pc.healthTotal;
      pc.currentEnergy = pc.energyTotal;
      pc.ate = false;
      pc.currentDungeon = null;
      pc.currentFloor = -1;
      pc.currentRoom = -1;
      pc.day += 1;
      pc.currency -= 250;
      pc.currency = pc.currency > 0 ? pc.currency : 0;
      let dungeonData;
      fetch('/dungeon/new/'+ this.state.pc.id)
      .then(response => response.json())
      .then(data => {          
        pc.dungeonBoard = [];
        data.forEach(dungeon => pc.dungeonBoard.push(dungeon.id))
        dungeonData = data;
      })
      .then(fetch('/shop/new/'+ this.state.pc.id)
      .then(response => response.json())
      .then(data => {
        pc.currentShop = data.id;
        this.save(["pc"], [this.state.pc], {shop: data, dungeonBoard: dungeonData, pc: pc, loadingShop: true, dungeon: null});
      }));
    }

    appState(method, key, value){
      let pc;
      let dungeon;
      let shop;
      let room;
      let count;
      let addItem = true;
      switch(method){
        case "help":
          this.setStateCustom({help: this.state.help ? false : true})
          break;
        case "game-over":
          this.setStateCustom({gameOver: key});
          break;
        case "end-game":
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
            this.setStateCustom({characterId: null})
          });
          break;
        case "quest":
          pc = {...this.state.pc};
          dungeon = {...this.state.dungeon};
          pc.currency += this.state.dungeon.reward;
          pc.experience += Math.floor(this.state.dungeon.reward*2.5);
          pc.inventory[this.state.dungeon.rewardCache[key].id] = 1;
          pc.inventoryCache.push(this.state.dungeon.rewardCache[key]);
          dungeon.rewardClaimed = true;
          this.save(["pc", "dungeon"], [pc, dungeon], {pc:pc, dungeon: dungeon});
          break;
        case "menu":
          this.setStateCustom({menu: key === this.state.menu ? null : key});
          break;
        case "ability":
          this.setStateCustom({
            abilityAnimation: key,
            renderAbilityAnimation: true
          })
          break;
        case "pcCombat":
          dungeon = {...this.state.dungeon}
          document.documentElement.style.setProperty('--animate-duration', '1s');
          this.combatEngine.runRound(this.state.pc.abilities[key]);
          
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
          this.setStateCustom({
            pc: this.combatEngine.pc,
            dungeon: dungeon,
            combatUpdates: this.combatEngine.combatUpdates,
            position: 1,
            renderAbilityAnimation: false
          });
          break;
        case "nextCombat":
          dungeon = {...this.state.dungeon}

          let nextPosition = this.getNextPosition();
          if(!nextPosition){
            this.combatEngine.endRound();
            let combatTransitions = this.combatEngine.monsters.length ? true : false;
            dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
            this.setStateCustom({
              pc: this.combatEngine.pc,
              dungeon: dungeon,
              combatUpdates: this.combatEngine.combatUpdates,
              combatTransitions: combatTransitions,
              position: 0
            });
            break;
          }
          this.combatEngine.runRound(null, nextPosition);
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
          this.setStateCustom({
            pc: this.combatEngine.pc,
            dungeon: dungeon,
            combatUpdates: this.combatEngine.combatUpdates,
            position: nextPosition + 1
          });
          break;
        case "combat":
          if(!this.state.combat){
              dungeon = {...this.state.dungeon}
              this.combatEngine = new CombatEngine(this.state.pc, this.pcServices, this.state.dungeon.floors[this.state.dungeon.currentFloor].rooms[this.state.dungeon.currentRoom].monsters, this.state.dungeon.postFix, this.props.props.positions, this.appState);
              dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].monsters = this.combatEngine.monsters;
              this.setStateCustom({
                combat: true,
                dungeon: dungeon,
                combatTransitions: true
              });
              break;
          } else {
            pc = {...this.state.pc}
            dungeon = {...this.state.dungeon}
            this.pcServices.clearBuffs(pc);
            this.pcServices.resetTempStats(pc);
            this.pcServices.updateStats(pc);
            this.combatEngine = null;

            dungeon.questCompleted = this.checkQuest();

            this.save(["pc", "dungeon"], [pc, dungeon], {pc: pc, dungeon: dungeon, combat: false, position: 0});
            break;
          }
          
        case "stairs":
          dungeon = {...this.state.dungeon};
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
          if(key){
            if(dungeon.currentFloor === 0){
              pc = {...this.state.pc};
              pc.location = "Town";
              this.save(["pc", "dungeon"], [pc, dungeon], {dungeon: dungeon, pc:pc, scene:"Default"});
            } else {
              dungeon.currentFloor -= 1;
              dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.length-1;
              this.save(["dungeon"], [dungeon], {dungeon: dungeon})
            }
          } else {
            dungeon.currentFloor += 1;
            dungeon.currentRoom = 0;
            this.save(["dungeon"], [dungeon], {dungeon: dungeon})
          }
          break;
        case "move-room":
          dungeon = {...this.state.dungeon};
          dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared = true;
          dungeon.floors[dungeon.currentFloor].rooms.some(room => {
            if(room.id === key){
              dungeon.currentRoom = dungeon.floors[dungeon.currentFloor].rooms.indexOf(room);
              return true;
            }
            return false;
          });
          if(!dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom].cleared){
            pc = {...this.state.pc};
            this.pcServices.regenHealth(pc);
            this.pcServices.regenEnergy(pc);
            this.save(["pc","dungeon"], [pc, dungeon], {dungeon: dungeon, pc:pc})
          }else {
            this.save(["dungeon"], [dungeon], {dungeon: dungeon})
          }
          break;
        case "scene":
          if(this.state.pc.location === value) {
            this.setStateCustom({scene: key, menu: null})
          } else {
            pc = {...this.state.pc}
            pc.location = value
            this.save(["pc"], [pc], {pc: pc, scene: key, menu: null})
          }
          break;
        case "to-dungeon":
          pc = {...this.state.pc};
          pc.location = "Dungeon";
          this.save(["pc"], [pc], {pc: pc});
          break;
        case "set-dungeon": 
          pc = {...this.state.pc};
          pc.currentDungeon = key;     
          this.save(["pc"], [pc], {pc: pc, generatingDungeon: true});
          break;
        case "rest":
          pc = {...this.state.pc};
          pc.currentHealth = pc.healthTotal;
          pc.currentEnergy = pc.energyTotal;
          pc.ate = false;
          pc.currency -= 250;
          pc.currency = pc.currency > 0 ? pc.currency : 0;
          this.restCheck = true;
          this.setStateCustom({pc: pc, rested: this.state.rested +1});
          break;
        case "eat":
          pc = {...this.state.pc};
          pc.currentHealth = pc.healthTotal;
          pc.currentEnergy = pc.energyTotal;
          pc.ate = true;
          pc.currency -= 100;
          pc.currency = pc.currency > 0 ? pc.currency : 0;

          this.save(["pc"], [pc], {pc: pc, eaten: this.state.eaten + 1});
          break;
        case "loot-all":
          pc = {...this.state.pc};
          dungeon = {...this.state.dungeon};
          room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom]
          
          for(let i = 0; i < room.lootCache.length; i++){
            count = room.loot[room.lootCache[i].id];
            
            if(room.lootCache[i].type !== "currency"){
              if(pc.inventory[room.lootCache[i].id]){
                pc.inventory[room.lootCache[i].id] += count;
              } else {
                pc.inventory[room.lootCache[i].id] = count;
              }

              if(!pc.inventoryCache.find(item => item.id === room.lootCache[i].id)){
                pc.inventoryCache.push(room.lootCache[i]);
              }
            } else {
              if(room.lootCache[i].name === "Gold") {
                pc.currency += count;
              }
              if(room.lootCache[i].name === "Soul") {
                pc.metacurrency += count;
              }
            }
          }
          room.loot = {};
          room.lootCache = [];
          this.save(["pc", "dungeon"], [pc, dungeon], {pc: pc, dungeon: dungeon, menu: null});
          break;
        case "loot":
          pc = {...this.state.pc};
          dungeon = {...this.state.dungeon};
          room = dungeon.floors[dungeon.currentFloor].rooms[dungeon.currentRoom]
          let index = room.lootCache.indexOf(key);
          count = room.loot[key.id];
          if (index !== -1){
            room.lootCache.splice(index, 1);
          }
          
          if(key.type !== "currency"){
            if(pc.inventory[key.id]){
              pc.inventory[key.id] += count;
            } else {
              pc.inventory[key.id] = count;
            }

            if(!pc.inventoryCache.find(item => item.id === key.id)){
              pc.inventoryCache.push(key);
            }
          } else {
            if(key.name === "Gold") {
              pc.currency += count;
            }
            if(key.name === "Soul") {
              pc.metacurrency += count;
            }
          }

          this.save(["pc", "dungeon"], [pc, dungeon], {pc: pc, dungeon: dungeon});
          break;
        case "shop-store":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(key.type !== "potion" && key.type !== "consumable"){
            let index = shop.inventoryCache.indexOf(key);
            if (index !== -1)
              shop.inventoryCache.splice(index, 1);
          }
          pc.currency -= key.cost*5;
          if(pc.currency < 0){
            pc.currency = 0;
          }

          if(pc.inventory[key.id]){
            pc.inventory[key.id] += 1;
          } else {
            pc.inventory[key.id] = 1;
          }
          for(const item of pc.inventoryCache){
            if(item.id === key.id){
              addItem = false;
            }
          }
          if(addItem){
            pc.inventoryCache.push(key);
          }
          this.save(["pc", "shop"], [pc, shop], {pc: pc, shop: shop});
          break;
        case "shop-player":
          pc = {...this.state.pc};
          shop = {...this.state.shop};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            let index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }

          pc.currency += key.cost;
          
          if(key.type !== "potion" && key.type !== "consumable"){
            shop.inventoryCache.push(key);
          }

          this.save(["pc", "shop"], [pc, shop], {pc: pc, shop: shop});
          break;
        case "inventory":  
          pc = {...this.state.pc};
          if(pc.inventory[key.id] > 1){
            pc.inventory[key.id] -= 1;
          }else{
            delete pc.inventory[key.id]
            let index = pc.inventoryCache.indexOf(key);
            if (index !== -1)
              pc.inventoryCache.splice(index, 1);
          }
          if(key.actionType === "heal"){
            if(this.state.combat){
              this.combatEngine.itemHeal(key.actionValue);
              this.pcServices.updateStats(this.combatEngine.pc);
              this.setStateCustom({pc: this.combatEngine.pc});
              break;
            } else {
              pc.currentHealth = (pc.currentHealth + pc.healthTotal*(key.actionValue/100)) > pc.healthTotal
                ? pc.healthTotal
                : pc.currentHealth + pc.healthTotal*(key.actionValue/100);
              this.pcServices.updateStats(pc);
              this.save(["pc"], [pc], {pc: pc});
            }
            break;
          } else if(key.actionType === "energize"){
            if(this.state.combat){
              this.combatEngine.itemEnergize(key.actionValue);
              this.pcServices.updateStats(this.combatEngine.pc);
              this.setStateCustom({pc: this.combatEngine.pc});
              break;
            } else {
              pc.currentEnergy = (pc.currentEnergy + pc.energyTotal*(key.actionValue/100)) > pc.energyTotal
                ? pc.energyTotal
                : pc.currentEnergy + pc.energyTotal*(key.actionValue/100);
              this.pcServices.updateStats(pc);
              this.save(["pc"], [pc], {pc: pc});
            }
            break;
          } else {
            var slot;
            if(key.type === "headLight" || key.type === "headMedium" || key.type === "headHeavy"){
              slot = "Head";
            } else if (key.type === "bodyLight" || key.type === "bodyMedium" || key.type === "bodyHeavy"){
              slot = "Body"
            } else if (key.type === "neck"){
              slot = "Neck"
            } else if (key.type === "back"){
              slot = "Back"
            } else if (key.type === "bow" || key.type === "staff" || key.type === "sword"){
              slot = "Primary"
            } else {
              slot = "Secondary"
            }
            if(pc["equipped" + slot]){
              pc.inventory[pc["equipped" + slot].id] = 1;
              pc.inventoryCache.push(pc["equipped" + slot])
            }
            pc["equipped" + slot] = key;
            this.pcServices.updateStats(pc);
            this.save(["pc"], [pc], {pc: pc});
            break;
          }
        case "unequip":  
          pc = {...this.state.pc};
          pc.inventory[key.id] = 1;
          pc.inventoryCache.push(key);
          if(key.type === "headLight" || key.type === "headMedium" || key.type === "headHeavy"){
            pc.equippedHead = {id: "00000000-0000-0000-0000-000000000000"};
          } else if (key.type === "bodyLight" || key.type === "bodyMedium" || key.type === "bodyHeavy"){
            pc.equippedBody = {id: "00000000-0000-0000-0000-000000000000"};
          } else if (key.type === "neck"){
            pc.equippedNeck = {id: "00000000-0000-0000-0000-000000000000"};
          } else if (key.type === "back"){
            pc.equippedBack = {id: "00000000-0000-0000-0000-000000000000"};
          } else if (key.type === "bow" || key.type === "staff" || key.type === "sword"){
            pc.equippedPrimary = {id: "00000000-0000-0000-0000-000000000000"};
          } else {
            pc.equippedSecondary = {id: "00000000-0000-0000-0000-000000000000"};
          }
          this.pcServices.updateStats(pc);
          this.save(["pc"], [pc], {pc: pc});
          break;
        case "pointbuy":
          pc = {...this.state.pc};
          pc.attributePoints > 0
            ? pc.attributePoints -= 1
            : pc.attributePoints = 0;
          pc[key] += 1;
          this.pcServices.updateStats(pc);
          this.save(["pc"], [pc], {pc: pc});
          break;
        default:
          break;
      }
    }

}

export default App
