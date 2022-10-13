class ImageServices {
  static #instance;
  static #isInternalConstructing = false;
  constructor(){
    if(!ImageServices.#isInternalConstructing) throw TypeError("ImageServices is not constructable");
    
    this.loadedImages = 0;
    this.loadedImageNameArray = [];
  }

  static getInstance(){
    if(this.#instance) return this.#instance;
    
    this.#isInternalConstructing = true;
    this.#instance = new ImageServices();
    this.#isInternalConstructing = false;
    return this.#instance;
  }

  countImages(s, images) {
    let dungeonImageCount = 0;
    if(s.dungeon){
      for(let floor of s.dungeon.floors){
        for (let room of floor.rooms) {
          if(room.loot){
            dungeonImageCount += room.loot.length
          }
        }
      }
    }

    return Object.keys(images.common).length
      + Object.keys(images.abilities).length
      + Object.keys(images.monster).length
      + Object.keys(images.class).length
      + Object.keys(images.environment).length
      + (s.pc && s.pc.inventory ? s.pc.inventory.length : 0)
      + (s.pc ? [s.pc.equippedHead, s.pc.equippedBody, s.pc.equippedBack, 
        s.pc.equippedNeck, s.pc.equippedPrimay, s.pc.equippedSecondary].filter(n => n).length : 0)
      + (s.shop && s.shop.inventory ? s.shop.inventory.length : 0)
      + dungeonImageCount;
  }

  loadCommonImages(images){
    for (let image of Object.keys(images.common)) {
      this.loadImage(images.common[image]);
    }
    for (let image of Object.keys(images.abilities)) {
      this.loadImage(images.abilities[image]);
    }
    for (let image of Object.keys(images.class)) {
      this.loadImage(images.class[image]);
    }
    this.loadCheckedImage(images.items.currency.i1);
  }

  loadCheckedImages(imageSet){
    for (let image of Object.keys(imageSet)) {
      this.loadCheckedImage(imageSet[image]);
    }
  }

  loadPcImages(pc, items){
    if(pc){
      for(let item of [pc.equippedHead, pc.equippedBody, pc.equippedBack, 
      pc.equippedNeck, pc.equippedPrimay, pc.equippedSecondary]){
        if(item){
          if(item.type && item.image){
            this.loadCheckedImage(items[item.type]["i"+item.image]);
          } else {
            this.loadedImages++;
          }
        }
      }
      if(pc.inventory){
        this.loadItemImages(pc.inventory, items);
      }
    }
  }

  loadShopImages(shop, items){
    if(shop){
      this.loadItemImages(shop.inventory, items);
    }
  }

  loadItemImages(inventory, items){
    if(inventory){
      for(let item of inventory){
        this.loadCheckedImage(items[item.type]["i"+item.image]);
      }
    }
  }

  loadDungeonItemImages(dungeon, items){
    if(dungeon && dungeon.floors){
      for(let floor of dungeon.floors){
        for(let room of floor.rooms){
          if(room.loot){
            this.loadItemImages(room.loot, items);
          }
        }
      }
    }
  }

  loadImage(src) {
    let i = new Image();
    i.onload = () => this.loadedImages++;
    i.src = src;
  }

  loadCheckedImage(src){
    if(this.loadedImageNameArray.includes(src)){
      this.loadedImages++;
    } else {
      this.loadImage(src);
      this.loadedImageNameArray.push(src);
    }
  }
}

export default ImageServices;