class Binder {

  static getAllMethods(instance) {
    return Object.getOwnPropertyNames(Object.getPrototypeOf(instance))
      .filter(name => {
        return instance[name] instanceof Function;
      });
  }

  static bindAll(instance) {
    for(let mtd of Binder.getAllMethods(instance)){
      instance[mtd] = instance[mtd].bind(instance);
    }
  }
}
export default Binder;
export const bindAll = Binder.bindAll;