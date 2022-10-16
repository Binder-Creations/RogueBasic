
function getAllMethods (instance) {
  return Object.getOwnPropertyNames(Object.getPrototypeOf(instance))
    .filter(name => {
      return instance[name] instanceof Function;
    });
}

export const bindAll = (instance) => {
  for(let mtd of getAllMethods(instance)){
    instance[mtd] = instance[mtd].bind(instance);
  }
}