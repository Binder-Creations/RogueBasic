import c from "../../data/CommonProperties";

class Binder {

  static getAllMethods(instance) {
    return Object.getOwnPropertyNames(Object.getPrototypeOf(instance))
      .filter(name => {
        return instance[name] instanceof Function;
      });
  }

  static bind(instance) {
    for(let mtd of Binder.getAllMethods(instance)){
      instance[mtd] = instance[mtd].bind(instance);
    }
  }

  static bindAndC(instance) {
    for(let mtd of Binder.getAllMethods(instance)){
      instance[mtd] = instance[mtd].bind(instance);
      c[mtd] = instance[mtd];
    }
  }
}
export default Binder;