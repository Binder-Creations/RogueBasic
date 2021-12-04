import CombatUpdate from "../CombatUpdate";
import AbilityServices from "../AbilityServices";

class RogueAttack {
  static use(pc, monsters, combatUpdates){
    let monster = AbilityServices.getTarget("lowestHealthTotal", monsters);
    let damage = AbilityServices.calcDamagePc(AbilityServices.randomize(pc.baseDamage, 15), pc, monster);
    monster.currentHealth = (monster.currentHealth - damage) >= 0 ? (monster.currentHealth - damage) : 0;
    combatUpdates.push(new CombatUpdate(monster.position, "dmg-"+damage));
  }
  
  static tooltipMin(pc){
    return AbilityServices.calcDamageTooltip(AbilityServices.min(pc.baseDamage, 15), pc);
  }

  static tooltipMax(pc){
    return AbilityServices.calcDamageTooltip(AbilityServices.max(pc.baseDamage, 15), pc);
  }
}

export default RogueAttack;